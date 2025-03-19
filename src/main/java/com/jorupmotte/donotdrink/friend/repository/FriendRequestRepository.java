package com.jorupmotte.donotdrink.friend.repository;

import com.jorupmotte.donotdrink.common.type.FriendStatusType;
import com.jorupmotte.donotdrink.friend.model.FriendRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findById(Long requestId);
    List<FriendRequest> findAllByTo_Id(Long receiverId);

    boolean existsByFrom_IdAndTo_Id(Long fromId, Long toId);

    @Transactional
    @Modifying
    @Query("UPDATE FriendRequest fr SET fr.status = :status WHERE fr.id = :requestId")
    void updateStatusById(Long requestId, FriendStatusType status);
}
