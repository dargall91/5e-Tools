package com.server.controllers;

import com.server.CampaignManager;
import com.server.entities.encounter.Encounter;
import com.server.models.Combatant;
import com.server.repositories.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("5eTools/api/combat")
@Controller
public class CombatController {
    @Autowired
    private EncounterRepository encounterRepo;
    private Encounter loadedEncounter;

    @GetMapping("/")
    public String combatView(Model model) {
        model.addAttribute("campaignName", CampaignManager.getCampaign().getName());
        return "combat";
    }

    @PostMapping("load")
    public ResponseEntity<?> loadEncounter(@RequestParam int encounterId) {
        Optional<Encounter> encounter = encounterRepo.findById(encounterId);

        if (encounter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter with id " + encounterId + " not found.");
        }

        loadedEncounter = encounter.get();

        return ResponseEntity.noContent().build();
    }

    /**
     * Starts an encounter with the specified id - this overrides any previously loaded encounters
     * @param encounterId
     * @return
     */
    @PostMapping("start")
    public ResponseEntity<?> startCombat(@RequestParam(required = false) Integer encounterId) {
        //no encounter id provided and no encounter loaded
        if (encounterId == null && loadedEncounter == null) {
            return ResponseEntity.badRequest().body("No encounter loaded or specified.");
        }

        //if a new encounterId was provided, load that encounter
        if (encounterId != null) {
            ResponseEntity loadResponse = loadEncounter((encounterId));

            //load not successful
            if (!loadEncounter(encounterId).getStatusCode().is2xxSuccessful()) {
                return loadResponse;
            }
        }

        //todo: play music

        return ResponseEntity.noContent().build();
    }

    @PostMapping("setCombatants")
    public ResponseEntity<?> setCombatanats(@RequestBody List<Combatant> combatants, Model model) {
        model.addAttribute("campaignName", CampaignManager.getCampaign().getName());
        model.addAttribute("combatantList", combatants);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets the loaded encounter. WIll be null if no encounter is loaded
     * @return
     */
    @GetMapping("loaded")
    public ResponseEntity<?> getLoadedEncounter() {
        return ResponseEntity.ok(loadedEncounter);
    }

    @PostMapping("stop")
    public String stopCombat() {
        //todo: stop music
        loadedEncounter = null;
        return null;
    }
}
