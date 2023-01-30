package com.server.controllers;

import com.server.CampaignManager;
import com.server.entities.encounter.Encounter;
import com.server.entities.monster.Monster;
import com.server.repositories.CampaignRepository;
import com.server.repositories.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("5eTools/api/encounter")
@RestController
public class EncounterController {
    @Autowired
    private EncounterRepository encounterRepo;
    @Autowired
    private CampaignRepository campaignRepo;

    @GetMapping("{encounterId}")
    public ResponseEntity<?> getEncounter(@PathVariable int encounterId) {
        Optional<Encounter> encounter = encounterRepo.findById(encounterId);

        if (encounter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + encounterId + " not found.");
        }

        return ResponseEntity.ok(encounter.get());
    }

    @PutMapping("add")
    public ResponseEntity addEncounter(@RequestParam String name) {
        if (CampaignManager.getCampaign() == null) {
            CampaignManager.setCampaign(campaignRepo.findOneByActive(true));
        }

        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }



        return null;
    }

    @DeleteMapping("{encounterId}")
    public ResponseEntity<?> deleteEncounter(@PathVariable int encounterId) {
        try {
            encounterRepo.deleteById(encounterId);

        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + encounterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{encounterId}/archive")
    public ResponseEntity<?> archiveEncounter(@PathVariable int encounterId) {
        try {
            Optional<Encounter> encounter = encounterRepo.findById(encounterId);

            if (encounter.isEmpty()) {
                throw new EmptyResultDataAccessException(1);
            }

            encounter.get().setArchived(true);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + encounterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{encounterId}/reinstate")
    public ResponseEntity<?> reinstateEncounter(@PathVariable int encounterId) {
        try {
            Optional<Encounter> encounter = encounterRepo.findById(encounterId);

            if (encounter.isEmpty()) {
                throw new EmptyResultDataAccessException(1);
            }

            encounter.get().setArchived(false);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + encounterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("update")
    public ResponseEntity<?> updateEncounter(@RequestBody Encounter encounter) {
        return null;
    }

    @PostMapping("{encounterId}/addMonster")
    public ResponseEntity<?> addMontser(@PathVariable int encounterId, @RequestParam String name) {
        return null;
    }

    @GetMapping("list")
    public ResponseEntity<?> archiveEncounter() {
        if (CampaignManager.getCampaign() == null) {
            CampaignManager.setCampaign(campaignRepo.findOneByActive(true));
        }

        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }

        return ResponseEntity.ok(encounterRepo.findAllByCampaignId(CampaignManager.getCampaign().getId()));
    }
}
