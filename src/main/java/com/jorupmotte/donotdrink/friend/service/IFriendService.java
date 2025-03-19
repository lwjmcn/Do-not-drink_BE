package com.jorupmotte.donotdrink.friend.service;

public interface IFriendService {
    public Long getUserIdFromRequestId(Long requestId);
    public boolean isFriend(Long userId, Long friendId);
}
