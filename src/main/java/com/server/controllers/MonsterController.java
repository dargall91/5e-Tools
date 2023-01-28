package com.server.controllers;

import com.server.entities.abilityscore.*;
import com.server.entities.monster.Monster;
import com.server.payloads.Payload;
import com.server.repositories.AbilityScoreRepository;
import com.server.repositories.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequestMapping("5eTools/api/monster")
@RestController
public class MonsterController {
    @Autowired
    private MonsterRepository monsterRepo;
    @Autowired
    private AbilityScoreRepository<AbilityScore> abilityScoreRepo;

    @GetMapping("{id}")
    public ResponseEntity<?> getMonster(@PathVariable int id) {
        Optional<Monster> result = monsterRepo.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monster with id " + id + " not found.");
        }

        return ResponseEntity.ok(result.get());
    }

    @PutMapping("add")
    public ResponseEntity<?> addMonster(@RequestBody Payload.AddMonster addMonster) {
        ///set defaults
        Monster newMonster = monsterRepo.save(new Monster(addMonster));
        newMonster.setStrength(abilityScoreRepo.save(new Strength()));
        newMonster.setDexterity(abilityScoreRepo.save(new Dexterity()));
        newMonster.setConstitution(abilityScoreRepo.save(new Constitution()));
        newMonster.setIntelligence(abilityScoreRepo.save(new Intelligence()));
        newMonster.setWisdom(abilityScoreRepo.save(new Wisdom()));
        newMonster.setCharisma(abilityScoreRepo.save(new Charisma()));

        String requestMap = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(requestMap, newMonster.getId().toString())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("update")
    public ResponseEntity<?> updateMonster(@RequestBody Monster monster) {
        ///monster not found
        if (!monsterRepo.existsById(monster.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Monster with id " + monster.getId() + " not found.");
        }

        abilityScoreRepo.save(monster.getStrength());
        abilityScoreRepo.save(monster.getDexterity());
        abilityScoreRepo.save(monster.getConstitution());
        abilityScoreRepo.save(monster.getIntelligence());
        abilityScoreRepo.save(monster.getWisdom());
        abilityScoreRepo.save(monster.getCharisma());
        monsterRepo.save(monster);

        return ResponseEntity.ok().build();
    }
}
