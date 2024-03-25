package test.splab.springgames.modules.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import test.splab.springgames.modules.message.dto.SendMessageDto;

@Slf4j
@RequiredArgsConstructor
@Profile("test")
@Service
public class ConsoleSlackWebhookSendServiceImpl implements SlackWebhookSendService {

    private final SlackWebhookUriBuilder slackWebhookUriBuilder;

    @Override
    public void sendMessage(SendMessageDto message) {

        log.info("Slack에 성공적으로 메시지를 전송했습니다.");
        log.info("""
                \s
                ===================\s
                uri= {} \s
                payload = {} \s
                ===================\s
                """, slackWebhookUriBuilder.buildUri(), message.toString());
    }
}
