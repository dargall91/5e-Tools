package com.server.controllers;

import com.server.util.CampaignManager;
import com.server.entities.encounter.Encounter;
import com.server.models.Combatant;
import com.server.repositories.encounter.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("5eTools/api/combat")
@Controller
public class CombatController {
    @Autowired
    private EncounterRepository encounterRepo;
    private Encounter loadedEncounter;
    private List<Combatant> combatantList;

    @GetMapping("/")
    public String combatView(Model model) {
        if (CampaignManager.getCampaign() != null) {
            model.addAttribute("campaignName", CampaignManager.getCampaign().getName());
        } else {
            model.addAttribute("campaignName", "No Campaign Selected");
        }

        model.addAttribute("combatantList", combatantList);

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

    @PostMapping("setCombatants")
    public ResponseEntity<?> setCombatanats(@RequestBody List<Combatant> combatants, Model model) {
        combatantList = combatants;

        for (int i =0 ; i < combatantList.size(); i++) {
            combatantList.get(i).setId(i + 1);
        }

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
}
