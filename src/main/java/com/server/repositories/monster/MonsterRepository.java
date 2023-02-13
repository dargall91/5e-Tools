package com.server.repositories.monster;

import com.server.entities.monster.Monster;
import com.server.repositories.projections.NameIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    @Query("SELECT id AS id, name AS name FROM Monster m WHERE m.campaignId = :campaignId AND m.archived = false " +
            "ORDER BY name ASC")
    List<NameIdProjection> findAllActiveByCampaignId(@Param("campaignId") int campaignId);
    List<NameIdProjection> findAllByCampaignIdOrderByNameAsc(int campaignId);
}
