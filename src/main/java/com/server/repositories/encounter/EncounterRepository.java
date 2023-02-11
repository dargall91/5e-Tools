package com.server.repositories.encounter;

import com.server.entities.encounter.Encounter;
import com.server.repositories.projections.NameIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Integer> {
    @Query("SELECT id AS id, name AS name FROM Encounter e WHERE e.campaignId = :campaignId AND e.archived = false " +
            "ORDER BY name ASC")
    List<NameIdProjection> findAllActiveByCampaignId(@Param("campaignId") int campaignId);
    List<NameIdProjection> findAllByCampaignIdOrderByNameAsc(int campaignId);
}
