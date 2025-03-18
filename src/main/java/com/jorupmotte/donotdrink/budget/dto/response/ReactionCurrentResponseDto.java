package com.jorupmotte.donotdrink.budget.dto.response;

import com.jorupmotte.donotdrink.budget.model.Reaction;
import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ReactionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;

import java.util.List;

public class ReactionCurrentResponseDto extends ResponseDto {
    private final List<ReactionDto> reactions;
    public ReactionCurrentResponseDto(List<Reaction> reactionList) {
        super();
        this.reactions = reactionList.stream().map(ReactionDto::from).toList();
    }

    public static ResponseEntity<ReactionCurrentResponseDto> success(List<Reaction> reactionList) {
        ReactionCurrentResponseDto responseBody = new ReactionCurrentResponseDto(reactionList);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noBudget() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.BUDGET_UNDEFINED, ResponseMessage.BUDGET_UNDEFINED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }


    private record ReactionDto(ReactionType type, int count) {
        private static ReactionDto from(Reaction reaction) {
            return new ReactionDto(reaction.getReactionType(), reaction.getCount());
        }
    }
}
