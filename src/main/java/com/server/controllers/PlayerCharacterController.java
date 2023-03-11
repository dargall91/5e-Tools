package com.server.controllers;

import com.server.entities.Campaign;
import com.server.entities.playercharacter.*;
import com.server.repositories.CampaignRepository;
import com.server.repositories.playercharacter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * The PlayerController class houses all the endpoints for the DM to access necessary player data
 */
@RequestMapping("5eTools/api/pc")
@RestController
@CrossOrigin(origins = "https://localhost:3000")
public class PlayerCharacterController {
    @Autowired
    private PlayerCharacterRepository playerRepo;
    @Autowired
    private CampaignRepository campaignRepo;
    @Autowired
    private CharacterClassRepository classRepo;
    @Autowired
    private StressStatusRepository stressRepo;
    @Autowired
    private ProficiencyBonusRepository proficiencyRepo;
    @Autowired
    private SpellSlotsRepository spellSlotsRepo;
    @Autowired
    private WarlockSpellSlotsRepository warlockSpellSlotsRepo;

    //TODO: add methods for android to update ONLY the fields it has uses (init, combatant, etc)

    @GetMapping("masterdata")
    public ResponseEntity<?> getMasterData() {
        PlayerCharacterMasterData masterData = new PlayerCharacterMasterData();
        masterData.setCharacterClasses(classRepo.findAllByOrderByNameAsc());
        masterData.setProficiencyBonuses(proficiencyRepo.findAllByOrderByLevelAsc());
        masterData.setStressStatuses(stressRepo.findAll());
        masterData.setSpellSlots(spellSlotsRepo.findAll());
        masterData.setWarlockSpellSlots(warlockSpellSlotsRepo.findAll());

        return ResponseEntity.ok(masterData);
    }


    /**
     * Gets the data for a specified character
     * @param pcId id of the character in the table
     * @return the player character or null if not found
     */
    @GetMapping("{pcId}")
    public ResponseEntity<?> getPlayerCharacter(@PathVariable int pcId) {
        Optional<PlayerCharacter> result = playerRepo.findById(pcId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PC with id " + pcId + " not found.");
        }

        return ResponseEntity.ok(result.get());
    }

    /**
     * Adds a player character. For putting from the android client
     * @param pc a PlayerCharacter object
     * @return uri of new character
     */
    @PutMapping("add")
    public ResponseEntity<?> addPlayerCharacter(@RequestBody PlayerCharacter pc) {
        Optional<StressStatus> stressStatus = stressRepo.findById(1);
        pc.setStressStatus(stressStatus.get());
        PlayerCharacter added = playerRepo.save(pc);

        String requestMap = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(requestMap, added.getId().toString())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * Deletes a player character
     * @param pcId id of the pc to delete
     * @return bad request if id does not exit, no content if it does
     */
    @DeleteMapping("delete")
    public ResponseEntity<?> deletePlayerCharacter(@RequestParam int pcId) {
        try {
            playerRepo.deleteById(pcId);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PC with id " + pcId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a player character
     * @param pc data to update
     * @return bad request if pc does not exist in database, ok if updated
     */
    @PostMapping("update")
    public ResponseEntity<?> updatePlayerCharacter(@RequestBody PlayerCharacter pc) {
        if (pc.getPrimalCompanion() == null) {
            for (ClassLevel classLevel : pc.getClassLevelList()) {
                if (classLevel.isBeastMaster()) {
                    PrimalCompanion primalCompanion = new PrimalCompanion();
                    PrimalCompanionType primalCompanionType = new PrimalCompanionType();
                    primalCompanion.setPrimalCompanionType(primalCompanionType);
                    pc.setPrimalCompanion(primalCompanion);
                    break;
                }
            }
        }

        //pc not found
        if (!playerRepo.existsById(pc.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PC with id " + pc.getId() + " not found.");
        }

        PlayerCharacter updated = playerRepo.save(pc);

        return ResponseEntity.ok(updated);
    }

    /**
     * Gets the list of player characters
     * @return the list of all pcs in the specified campaign
     */
    @GetMapping("campaignList/{campaignId}")
    public ResponseEntity<?> getPlayerCharacterList(@PathVariable int campaignId) {
        List<PlayerCharacter> list = playerRepo.findAllAliveByCampaignId(campaignId);
        Integer.compare(1, 2);
        return ResponseEntity.ok(playerRepo.findAllAliveByCampaignId(campaignId));
    }

    /**
     * Kill a player character
     * @param pcId
     * @return
     */
    @PostMapping("{pcId}/kill")
    public ResponseEntity<?> killPlayerCharacter(@PathVariable int pcId) {
        Optional<PlayerCharacter> pc = playerRepo.findById(pcId);

        if (pc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PC with id " + pcId + " not found.");
        }

        pc.get().setDead(true);
        playerRepo.save(pc.get());

        return ResponseEntity.ok().build();
    }

    /**
     * Revive a player character
     * @param pcId
     * @return
     */
    @PostMapping("{pcId}/revive")
    public ResponseEntity<?> revivePlayerCharacter(@PathVariable int pcId) {
        Optional<PlayerCharacter> pc = playerRepo.findById(pcId);

        if (pc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PC with id " + pcId + " not found.");
        }

        pc.get().setDead(false);
        playerRepo.save(pc.get());

        return ResponseEntity.ok().build();
    }

    @GetMapping("list/{userId}/{campaignId}")
    public ResponseEntity<?> getUsersCharactersInCampaign(@PathVariable int userId, @PathVariable int campaignId) {
        List<PlayerCharacter> pcList = playerRepo.findAllByUserIdAndCampaignId(userId, campaignId);

        return ResponseEntity.ok(pcList);
    }

    @GetMapping("classList")
    public ResponseEntity<?> getClassList() {
        return ResponseEntity.ok(classRepo.findAllByOrderByNameAsc());
    }
}
