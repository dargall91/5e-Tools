package com.server.controllers;

import com.server.entities.abilityscore.*;
import com.server.entities.monster.Ability;
import com.server.entities.monster.Action;
import com.server.entities.monster.LegendaryAction;
import com.server.entities.monster.Monster;
import com.server.payloads.Payload;
import com.server.repositories.AbilityScoreRepository;
import com.server.repositories.MonsterRepository;
import com.server.repositories.views.monster.NameIdView;
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
     *
     * TODO: update to only take in a name param, campaign id will come from Util (maybe rename to CampaignManager?)
     * @param addMonster
     * @return
     */
    @PutMapping("add")
    public ResponseEntity<?> addMonster(@RequestBody Payload.AddMonster addMonster) {
        Monster newMonster = monsterRepo.save(new Monster(addMonster));
        String requestMap = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(requestMap, newMonster.getId().toString())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * Delets the monster with he specified id
     * @param monsterId
     * @return
     */
    @DeleteMapping("{monsterId}/delete")
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
     * Adds a new feature of the specified type to the monster with the specified id
     * @param monsterId
     * @param type "ability", "action", or "legendaryAction"
     * @return
     */
    @PostMapping("{monsterId}/addFeature")
    public ResponseEntity<?> addFeature(@PathVariable int monsterId, @RequestParam String type) {
        Optional<Monster> monster = monsterRepo.findById(monsterId);

        if (monster.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + monsterId + " not found.");
        }

        //add specified feature to monster
        int index = 0;

        switch (type) {
            case "ability" -> {
                index = monster.get().getAbilities().size();
                monster.get().getAbilities().add(new Ability());
            }
            case "action" -> {
                index = monster.get().getActions().size();
                monster.get().getActions().add(new Action());
            }
            case "legendaryAction" -> {
                index = monster.get().getLegendaryActions().size();
                monster.get().getLegendaryActions().add(new LegendaryAction());
            }
            default -> {
                return ResponseEntity.badRequest().body("Invalid feature type");
            }
        }
        //save and return added feature
        monsterRepo.save(monster.get());

        ///return newly added feature
        if (type.equals("ability")) {
            return ResponseEntity.ok(monster.get().getAbilities().get(index));
        }

        if (type.equals("action")) {
            return ResponseEntity.ok(monster.get().getActions().get(index));
        }

        return ResponseEntity.ok(monster.get().getLegendaryActions().get(index));
    }

    /**
     * Gets a list of al monsters in the specified campaign id
     * @param campaignId
     * @return
     */
    @GetMapping("list/{campaignId}")
    public List<NameIdView> getMonsterList(@PathVariable int campaignId) {
        return monsterRepo.findAllByCampaignId(campaignId);
    }
}
