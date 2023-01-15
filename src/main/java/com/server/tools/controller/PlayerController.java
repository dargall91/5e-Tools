package com.server.tools.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The PlayerController class houses all the endpoints for the DM to access necessary player data
 * TODO: when the player front-end is made, will they need a separate controller, or can they use this one?
 */
@RequestMapping("/5eTools/api/Player/")
@RestController
public class PlayerController {

    /**
     * Gets the data for a specified character
     * @param name
     * @return
     */
    @GetMapping("{name}")
    public String getPlayerCharacter(@PathVariable String name) {
        return name;
    }

    /**
     * Adds a player character
     * @param name
     * @return
     */
    @PutMapping("addCharacter")
    public String addPlayerCharacter(String name) {
        return name;
    }

    /**
     * Deletes a player character
     * @param name
     * @return
     */
    @DeleteMapping("{name}")
    public String deletePlayerCharacter(@PathVariable String name) {
        return name;
    }

    /**
     * Updates a player character
     * @param name
     * @return
     */
    @PostMapping("{name}")
    public String updatePlayerCharacter(@PathVariable String name) {
        return name;
    }

    /**
     * Gets the list of player characters
     * @return
     */
    @GetMapping("playerList")
    public List<String> getPlayerCharacterList() {
        return new ArrayList<>();
    }
}
