package test.splab.springgames.modules.message.dto;

import lombok.Builder;
import lombok.Getter;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.Member;

import java.util.HashMap;

/**
 * 슬랙으로 전송될 텍스트 포맷입니다.
 * 1) 지원자명
 * 2) 회원ID
 * 3) 회원명
 * 4) 변경된 레벨
 */
@Getter
@Builder
public class SendMessageDto {

    final String applicantName = "강시혁";

    Long memberId;
    String memberName;
    Level level;

    @Override
    public String toString() {
        return "지원자명: " + applicantName + "\n"
                + "회원ID: " + memberId + "\n"
                + "회원명: " + memberName + "\n"
                + "레벨: " + level;
    }

    public static SendMessageDto from(Member member) {
        return SendMessageDto.builder()
                .memberId(member.getMemberId())
                .memberName(member.getName())
                .level(member.getLevel())
                .build();
    }

    public HashMap<String, String> getPayload() {
        HashMap<String,String> payload = new HashMap<>();
        payload.put("text", this.toString());
        return payload;
    }
}
