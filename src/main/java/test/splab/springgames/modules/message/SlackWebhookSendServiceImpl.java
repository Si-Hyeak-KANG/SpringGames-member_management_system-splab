package test.splab.springgames.modules.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import test.splab.springgames.modules.message.dto.SendMessageDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackWebhookSendServiceImpl implements SlackWebhookSendService {

    private final SlackWebhookUriBuilder slackWebhookUriBuilder;
    private final WebClient.Builder webClientBuilder;

    @Override
    public void sendMessage(SendMessageDto message) {

        webClientBuilder.build()
                .method(HttpMethod.POST)
                .uri(slackWebhookUriBuilder.buildUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(message.getPayload()))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(
                        result -> log.info("메시지가 성공적으로 전송되었습니다."),
                        error -> log.error("메시지 전송에 실패했습니다." , error)
                );
    }
}
