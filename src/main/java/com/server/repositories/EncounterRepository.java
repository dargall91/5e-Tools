package com.server.repositories;

import com.server.entities.encounter.Encounter;
import com.server.repositories.views.NameIdView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Integer> {
    List<NameIdView> findAllByCampaignIdOrderByNameAsc(int campaignId);
}
