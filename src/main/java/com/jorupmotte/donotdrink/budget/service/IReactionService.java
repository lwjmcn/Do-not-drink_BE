package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.budget.dto.request.ReactToRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactToResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactionCurrentResponseDto;
import org.springframework.http.ResponseEntity;

public interface IReactionService {
    public ResponseEntity<? super ReactionCurrentResponseDto> getMyCurrentReactionsFromDb();
// TODO   public ResponseEntity<? super ReactionCurrentResponseDto> getFriendCurrentReactions(Long friendId, ReactToRequestDto requestDto);
    public ResponseEntity<? super ReactToResponseDto> reactTo(Long receiverId, ReactToRequestDto requestDto);
}
