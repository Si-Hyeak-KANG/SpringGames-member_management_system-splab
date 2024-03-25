package test.splab.springgames.modules.message;

import test.splab.springgames.modules.message.dto.SendMessageDto;

public interface SlackWebhookSendService {

    void sendMessage(SendMessageDto message);
}
