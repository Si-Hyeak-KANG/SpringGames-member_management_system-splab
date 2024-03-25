package test.splab.springgames.modules.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class SlackWebhookUriBuilder {

    @Value("${slack.message.webhook.url}")
    private String slackWebhookUrl;

    public URI buildUri() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(slackWebhookUrl);
        return uriBuilder.build().encode().toUri();
    }
}
