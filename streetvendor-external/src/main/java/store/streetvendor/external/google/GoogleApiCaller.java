package store.streetvendor.external.google;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import store.streetvendor.external.google.dto.response.GoogleUserInfoResponse;

@RequiredArgsConstructor
@Component
public class GoogleApiCaller {

    private final WebClient webClient;

    public GoogleUserInfoResponse getGoogleUserProfileInfo(String accessToken) {
        return webClient.get()
            .uri("https://www.googleapis.com/oauth2/v2/userinfo")
            .header("Bearer " + accessToken)
            .retrieve()
            .bodyToMono(GoogleUserInfoResponse.class)
            .block();
    }

}
