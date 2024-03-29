package com.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.server.util.CampaignManager;
import com.server.entities.encounter.Encounter;
import com.server.entities.encounter.EncounterMonster;
import com.server.entities.monster.Monster;
import com.server.repositories.*;
import com.server.repositories.encounter.EncounterRepository;
import com.server.repositories.monster.MonsterRepository;
import com.server.repositories.encounter.XpThresholdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequestMapping("5eTools/api/encounter")
@RestController
public class EncounterController {
    @Autowired
    private EncounterRepository encounterRepo;
    @Autowired
    private CampaignRepository campaignRepo;
    @Autowired
    private MonsterRepository monsterRepo;
    @Autowired
    private XpThresholdsRepository xpRepo;
    @Autowired
    private MusicRepository musicRepository;

    @GetMapping("{encounterId}")
    public ResponseEntity<?> getEncounter(@PathVariable int encounterId) {
        Optional<Encounter> encounter = encounterRepo.findById(encounterId);

        if (encounter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
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

        Encounter newEncounter = new Encounter(name, CampaignManager.getCampaignId());
        newEncounter.setMusic(musicRepository.findById(1).get());
        newEncounter = encounterRepo.save(newEncounter);

        String requestMap = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(requestMap, newEncounter.getId().toString())
                .build().toUri();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("id", newEncounter.getId());
        rootNode.put("name", newEncounter.getName());

        return ResponseEntity.created(uri).body(rootNode);
    }

    @DeleteMapping("{encounterId}")
    public ResponseEntity<?> deleteEncounter(@PathVariable int encounterId) {
        try {
            encounterRepo.deleteById(encounterId);

        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
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
            encounterRepo.save(encounter.get());
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
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
            encounterRepo.save(encounter.get());
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("update")
    public ResponseEntity<?> updateEncounter(@RequestBody Encounter encounter) {
        if (!encounterRepo.existsById(encounter.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounter.getId() + " not found.");
        }

        encounterRepo.save(encounter);

        return ResponseEntity.ok().build();
    }

    @PostMapping("{encounterId}/addMonster")
    public ResponseEntity<?> addMonster(@PathVariable int encounterId, @RequestParam int monsterId) {
        Optional<Encounter> encounter = encounterRepo.findById(encounterId);

        if (encounter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
        }

        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        int index = encounter.get().getMonsterList().size();
        encounter.get().getMonsterList().add(new EncounterMonster(monster.get()));
        System.out.println(encounter.get().getMonsterList().get(0));
        encounterRepo.save(encounter.get());

        return ResponseEntity.ok(encounter.get().getMonsterList().get(index));
    }

    @DeleteMapping("{encounterId}/deleteMonster")
    public ResponseEntity<?> deleteMonsterData(@PathVariable int encounterId, @RequestParam int index) {
        Optional<Encounter> encounter = encounterRepo.findById(encounterId);

        if (encounter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
        }

        if (encounter.get().getMonsterList().size() > index + 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EncounterMonster index not found.");
        }

        encounter.get().getMonsterList().remove(index);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("list")
    public ResponseEntity<?> getEncounterList() {
        if (CampaignManager.getCampaign() == null) {
            CampaignManager.setCampaign(campaignRepo.findOneByActive(true));
        }

        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }

        return ResponseEntity.ok(encounterRepo.findAllActiveByCampaignId(CampaignManager.getCampaignId()));
    }

    @GetMapping("xp")
    public ResponseEntity<?> getXpThresholds() {
        return ResponseEntity.ok(xpRepo.findAll());
    }
}
