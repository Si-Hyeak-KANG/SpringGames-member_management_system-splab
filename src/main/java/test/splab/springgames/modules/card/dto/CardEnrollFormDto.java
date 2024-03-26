package test.splab.springgames.modules.card.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.splab.springgames.modules.card.GameCard;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardEnrollFormDto {

    Long memberId;

    String gameType;

    @NotBlank(message = "카드 타이틀은 전체가 공백일 수 없습니다.")
    @Size(max = 100, message = "카드 타이틀은 최대 100자리까지만 허용합니다.")
    String title;

    @Min(value = 1, message = "일련번호는 1 이상의 숫자만 허용합니다.")
    Long serialNumber;

    @Min(value = 0, message = "가격은 $0 이상이어야 합니다.")
    @Max(value = 100_000, message = "가격은 $100,000 이하의 달러만 허용합니다.")
    Double price;

    public GameCard toEntity() {
        return GameCard.of(this.title, this.serialNumber, this.price);
    }
}
