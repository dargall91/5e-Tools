package com.server.repositories;

import com.server.entities.PlayerCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Integer> {
    List<PlayerCharacter> findAllByCampaignId(int campaignId);
}
