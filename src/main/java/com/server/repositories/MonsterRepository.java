package com.server.repositories;

import com.server.entities.monster.Monster;
import com.server.repositories.views.monster.NameIdCampaignView;
import com.server.repositories.views.monster.NameIdView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    List<NameIdView> findAllByCampaignId(int campaignId);
}
