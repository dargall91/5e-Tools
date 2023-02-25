package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.PlayerCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Integer> {
    @Query("SELECT pc FROM PlayerCharacter pc WHERE pc.campaignId = :campaignId AND pc.dead = false " +
            "ORDER BY pc.name ASC")
    List<PlayerCharacter> findAllAliveByCampaignId(@Param("campaignId") int campaignId);
    List<PlayerCharacter> findAllByCampaignId(int campaignId);
    List<PlayerCharacter> findAllByUserIdAndCampaignId(int userId, int campaignId);
}
