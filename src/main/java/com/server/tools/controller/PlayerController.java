package com.server.tools.controller;

import com.server.tools.entities.PlayerCharacter;
import com.server.tools.repositories.PlayerCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
     * @param id id of the character in the table
     * @return the player character or null if not found
     */
    @GetMapping("{id}")
    public PlayerCharacter getPlayerCharacter(@PathVariable int id) {
        Optional<PlayerCharacter> result = playerRepo.findById(id);
        return result.orElse(null);
    }

    /**
     * Adds a player character
     * @param pc a PlayerCharacter object
     * @return uri of new character
     */
    @PutMapping("addCharacter")
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
     * @param id id of the pc to delete
     * @return bad request if id does not exit, no content if it does
     */
    @DeleteMapping("delete")
    public ResponseEntity<?> deletePlayerCharacter(@RequestParam int id) {
        try {
            playerRepo.deleteById(id);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Character not found");
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
        if (playerRepo.findById(pc.getId()).isEmpty()) {
            return ResponseEntity.badRequest().body("Character not found");
        }

        playerRepo.save(pc);

        return ResponseEntity.ok().build();
    }

    /**
     * Gets the list of player characters
     * @return the list of all pcs in the specified campaign
     */
    @GetMapping("list/{campaignId}")
    public List<PlayerCharacter> getPlayerCharacterList(@PathVariable int campaignId) {
        return playerRepo.findAllByCampaignId(campaignId);
    }
}
