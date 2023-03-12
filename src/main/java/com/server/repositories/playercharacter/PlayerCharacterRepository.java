package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.PlayerCharacter;
import com.server.repositories.projections.AndroidPlayerCharacterProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Integer> {
    List<AndroidPlayerCharacterProjection> findAllByCampaignIdAndDeadFalse(int campaignId);
    List<PlayerCharacter> findAllByUserIdAndCampaignIdAndDeadTrue(int userId, int campaignId);
    List<PlayerCharacter> findAllByUserIdAndCampaignIdAndDeadFalse(int userId, int campaignId);
    @Transactional
    @Modifying
    @Query("UPDATE PlayerCharacter pc SET pc.initiativeBonus = :initiativeBonus, pc.rolledInitiative = " +
            ":rolledInitiative, pc.combatant = :combatant WHERE pc.id = :id")
    void updateInitiative(@Param("id") int id,
                          @Param("initiativeBonus") int initiativeBonus,
                          @Param("rolledInitiative") int rolledInitiative,
                          @Param("combatant") boolean combatant);
}
