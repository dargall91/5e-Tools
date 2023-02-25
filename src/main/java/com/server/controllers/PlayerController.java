package com.server.controllers;

import com.server.entities.PlayerCharacter;
import com.server.repositories.PlayerCharacterRepository;
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
 * TODO: when the player front-end is made, will they need a separate controller, or can they use this one?
 */
@RequestMapping("5eTools/api/pc")
@RestController
public class PlayerController {
    @Autowired
    private PlayerCharacterRepository playerRepo;

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
     * Adds a player character
     * @param pc a PlayerCharacter object
     * @return uri of new character
     */
    @PutMapping("add")
    public ResponseEntity<?> addPlayerCharacter(@RequestBody PlayerCharacter pc) {
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
        //pc not found
        if (!playerRepo.existsById(pc.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PC with id " + pc.getId() + " not found.");
        }

        playerRepo.save(pc);

        return ResponseEntity.ok().build();
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

    @GetMapping("{userId}/{campaignId}")
    public ResponseEntity<?> getUsersCharactersInCampaign(@PathVariable int userId, @PathVariable int campaignId) {
        List<PlayerCharacter> pcList = playerRepo.findAllByUserIdAndCampaignId(userId, campaignId);
        
        return ResponseEntity.ok(pcList);
    }
}
