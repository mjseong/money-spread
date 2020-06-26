package com.devlop.moneyspread.dao;

import com.devlop.moneyspread.domain.SpreadInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpreadInfoRepository extends JpaRepository<SpreadInfo, String > {
    Optional<SpreadInfo> findBySpreUserAndSpreRoomAndSpreToken(long spreUserId, String spreRoomId, String spreToken);
    Optional<SpreadInfo> findBySpreRoomAndSpreToken(String spreRoomId, String spreToken);
}
