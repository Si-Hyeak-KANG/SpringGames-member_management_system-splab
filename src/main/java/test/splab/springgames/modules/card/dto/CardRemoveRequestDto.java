package test.splab.springgames.modules.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardRemoveRequestDto {

    @NotNull
    Long cardId;

    @NotNull
    Long memberId;
}
