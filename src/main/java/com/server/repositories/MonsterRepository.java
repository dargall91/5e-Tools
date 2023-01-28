package com.server.repositories;

import com.server.entities.monster.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    List<Monster> findAllByCampaignId(int campaignId);
}
