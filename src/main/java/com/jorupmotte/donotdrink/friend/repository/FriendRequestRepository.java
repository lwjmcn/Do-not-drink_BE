package com.jorupmotte.donotdrink.friend.repository;

import com.jorupmotte.donotdrink.friend.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findById(Long requestId);
    List<FriendRequest> findAllByTo_Id(Long receiverId);
}
