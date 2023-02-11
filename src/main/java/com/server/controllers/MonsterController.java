package com.server.controllers;

import com.server.repositories.monster.*;
import com.server.util.CampaignManager;
import com.server.entities.abilityscore.*;
import com.server.entities.monster.*;
import com.server.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("5eTools/api/monster")
@RestController
public class MonsterController {
    @Autowired
    private MonsterRepository monsterRepo;
    @Autowired
    private AbilityScoreRepository<AbilityScore> abilityScoreRepo;
    @Autowired
    private CampaignRepository campaignRepo;
    @Autowired
    private ChallengeRatingRepository crRepo;
    @Autowired
    private AbilityRepository abilityRepo;
    @Autowired
    private ActionRepository actionRepo;
    @Autowired
    private LegendaryActionRepository legendaryActionRepo;

    /**
     * Gets a monster with the specified id
     * @param monsterId
     * @return
     */
    @GetMapping("{monsterId}")
    public ResponseEntity<?> getMonster(@PathVariable int monsterId) {
        Optional<Monster> result = monsterRepo.findById(monsterId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        return ResponseEntity.ok(result.get());
    }

    /**
     * Adds a new monster to the database
     * @param addMonster
     * @return
     */
    @PutMapping("add")
    public ResponseEntity<?> addMonster(@RequestParam String name) {
        if (CampaignManager.getCampaign() == null) {
            CampaignManager.setCampaign(campaignRepo.findOneByActive(true));
        }

        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }

        //default CR is index 1
        Optional<ChallengeRating> cr = crRepo.findById(1);

        if (cr.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: No challenge ratings found in database.");
        }

        Monster newMonster = monsterRepo.save(new Monster(name, CampaignManager.getCampaignId(), cr.get()));
        String requestMap = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(requestMap, newMonster.getId().toString())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * Deletes the monster with he specified id
     * @param monsterId
     * @return
     */
    @DeleteMapping("{monsterId}")
    public ResponseEntity<?> deleteMonster(@PathVariable int monsterId) {
        try {
            monsterRepo.deleteById(monsterId);

        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Archives the monster with the specified id
     * @param monsterId
     * @return
     */
    @PostMapping("{monsterId}/archive")
    public ResponseEntity<?> archiveMonster(@PathVariable int monsterId) {
        try {
            Optional<Monster> monster = monsterRepo.findById(monsterId);

            if (monster.isEmpty()) {
                throw new EmptyResultDataAccessException(1);
            }

            monster.get().setArchived(true);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Reinstates the monster with the specified id from the archive
     * @param monsterId
     * @return
     */
    @PostMapping("{monsterId}/reinstate")
    public ResponseEntity<?> reinstateMonster(@PathVariable int monsterId) {
        try {
            Optional<Monster> monster = monsterRepo.findById(monsterId);

            if (monster.isEmpty()) {
                throw new EmptyResultDataAccessException(1);
            }

            monster.get().setArchived(false);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("update")
    public ResponseEntity<?> updateMonster(@RequestBody Monster monster) {
        ///monster not found
        if (!monsterRepo.existsById(monster.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monster.getId() + " not found.");
        }

        monsterRepo.save(monster);

        return ResponseEntity.ok().build();
    }

    /**
     * Adds an ability to the monster with the specified id
     * @param monsterId
     * @return
     */
    @PutMapping("{monsterId}/addAbility")
    public ResponseEntity<?> addFeature(@PathVariable int monsterId) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        //add ability to monster
        int index = monster.get().getAbilities().size();
        monster.get().getAbilities().add(new Ability());

        //save and return added feature
        monsterRepo.save(monster.get());

        //return newly added feature
        return ResponseEntity.ok(monster.get().getAbilities().get(index));
    }

    /**
     * Adds an action to the monster with the specified id
     * @param monsterId
     * @return
     */
    @PutMapping("{monsterId}/addAction")
    public ResponseEntity<?> addAction(@PathVariable int monsterId) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        int index = monster.get().getActions().size();
        monster.get().getActions().add(new Action());
        monsterRepo.save(monster.get());

        return ResponseEntity.ok(monster.get().getActions().get(index));
    }

    /**
     * Adds a legendary action to the monster with the specified id
     * @return
     */
    @PutMapping("{monsterId}/addLegendaryAction")
    public ResponseEntity<?> addLegendaryAction(@PathVariable int monsterId) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        int index = monster.get().getLegendaryActions().size();
        monster.get().getLegendaryActions().add(new LegendaryAction());
        monsterRepo.save(monster.get());

        return ResponseEntity.ok(monster.get().getLegendaryActions().get(index));
    }

    /**
     * Gets a list of all monsters that are part of the currently selected campaign
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<?> getMonsterList() {
        if (CampaignManager.getCampaign() == null) {
            CampaignManager.setCampaign(campaignRepo.findOneByActive(true));
        }

        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }

        return ResponseEntity.ok(monsterRepo.findAllByCampaignIdOrderByNameAsc(CampaignManager.getCampaignId()));
    }

    /**
     * Copis an existing monster and makes a new one
     * @param monsterId the id of the monster to copy
     * @param name the name of the new monster
     * @return The new monster object
     */
    @PutMapping("{monsterId}/copy")
    public ResponseEntity<?> copyMonster(@PathVariable int monsterId, @RequestParam String name) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        Monster copy = new Monster(monster.get(), name);
        monsterRepo.save(copy);

        return ResponseEntity.ok(copy);
    }

    /**
     * Deletes the ability with the specified index from the monster with the specified id
     * @param monsterId
     * @param index
     * @return
     */
    @DeleteMapping("{monsterId}/abilities/{abilityId}")
    public ResponseEntity<?> deleteAbility(@PathVariable int monsterId, @PathVariable int index) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        Ability removed = monster.get().getAbilities().remove(index);
        monsterRepo.save(monster.get());
        abilityRepo.deleteById(removed.getId());

        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes the ability with the specified index from the monster with the specified id
     * @param monsterId
     * @param index
     * @return
     */
    @DeleteMapping("{monsterId}/actions/{actionId}")
    public ResponseEntity<?> deleteAction(@PathVariable int monsterId, @PathVariable int index) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        Action removed = monster.get().getActions().remove(index);
        monsterRepo.save(monster.get());
        actionRepo.deleteById(removed.getId());

        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes the legendary action with the specified index from the monster with the specified id
     * @param monsterId
     * @param index
     * @return
     */
    @DeleteMapping("{monsterId}/legendaryActions/{legendaryActionId}")
    public ResponseEntity<?> deleteLegendaryAction(@PathVariable int monsterId, @PathVariable int index) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        LegendaryAction removed = monster.get().getLegendaryActions().remove(index);
        monsterRepo.save(monster.get());
        legendaryActionRepo.deleteById(removed.getId());

        return ResponseEntity.noContent().build();
    }

    /**
     * Gets the list of all challenge ratings in the database
     * @return
     */
    @GetMapping("crList")
    public ResponseEntity<?> getChallengeRatingList() {
        List<ChallengeRating> crList = crRepo.findAll();

        return ResponseEntity.ok(crList);
    }
}
