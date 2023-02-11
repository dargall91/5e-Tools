package com.server.repositories.monster;

import com.server.entities.monster.Monster;
import com.server.repositories.projections.NameIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    List<NameIdProjection> findAllByCampaignIdOrderByNameAsc(int campaignId);
}
