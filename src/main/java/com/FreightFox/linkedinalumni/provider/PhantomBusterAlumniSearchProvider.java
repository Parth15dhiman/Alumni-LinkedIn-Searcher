package com.freightfox.linkedinalumni.provider;

import com.freightfox.linkedinalumni.config.PhantomBusterProperties;
import com.freightfox.linkedinalumni.dto.*;
import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import com.freightfox.linkedinalumni.provider.AlumniSearchProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
@Profile("phantombuster")
public class PhantomBusterAlumniSearchProvider implements AlumniSearchProvider {

    private final WebClient webClient;
    private final PhantomBusterProperties props;
    private final PhantomResultMapper resultMapper;

    public PhantomBusterAlumniSearchProvider(
            WebClient phantomBusterWebClient,
            PhantomBusterProperties props,
            PhantomResultMapper resultMapper
    ) {
        this.webClient = phantomBusterWebClient;
        this.props = props;
        this.resultMapper = resultMapper;
    }

    @Override
    public List<AlumniProfileEntity> search(AlumniSearchRequest request) {
        PhantomLaunchResponse launchResponse = launchPhantom(request);
        PhantomResultResponse resultResponse =
                pollForResult(launchResponse.containerId());

        return resultMapper.map(resultResponse);
    }

    private PhantomLaunchResponse launchPhantom(AlumniSearchRequest request) {

        PhantomLaunchRequest payload = new PhantomLaunchRequest(
                props.phantomId(),
                buildArguments(request)
        );

        return webClient.post()
                .uri("/agents/launch")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(PhantomLaunchResponse.class)
                .block(Duration.ofSeconds(props.timeoutSeconds()));
    }

    private static Map<String, Object> buildArguments(AlumniSearchRequest request) {
        return Map.of(
                "university", request.getUniversity(),
                "designation", request.getDesignation()
        );
    }

    private PhantomResultResponse pollForResult(String containerId) {

        int maxAttempts = 10;
        Duration delay = Duration.ofSeconds(3);

        for (int i = 0; i < maxAttempts; i++) {

            PhantomResultResponse response = webClient.get()
                    .uri("/containers/{id}/output", containerId)
                    .retrieve()
                    .bodyToMono(PhantomResultResponse.class)
                    .block(Duration.ofSeconds(props.timeoutSeconds()));

            if (response != null && "success".equalsIgnoreCase(response.status())) {
                return response;
            }

            sleep(delay);
        }

        throw new RuntimeException("PhantomBuster job did not complete in time");
    }

    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Polling interrupted", e);
        }
    }


}
