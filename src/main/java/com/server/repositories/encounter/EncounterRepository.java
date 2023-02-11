package com.server.repositories.encounter;

import com.server.entities.encounter.Encounter;
import com.server.repositories.projections.NameIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Integer> {
    List<NameIdProjection> findAllByCampaignIdOrderByNameAsc(int campaignId);
}
