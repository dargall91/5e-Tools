package com.server.tools.repositories;

import com.server.tools.entities.PlayerCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Integer> {
    List<PlayerCharacter> findAllByCampaignId(int campaignId);
}
