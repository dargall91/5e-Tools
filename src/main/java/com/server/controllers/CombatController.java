package com.server.controllers;

import com.server.repositories.projections.NameIdProjection;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("5eTools/api/combat")
@Controller
public class CombatController {
    @Autowired
    private EncounterRepository encounterRepo;
    private List<Combatant> combatantList;

    @GetMapping("combatants")
    public ResponseEntity<List<Combatant>> getCombatants() {
        if (combatantList == null) {
            combatantList = new ArrayList<>();
        }

        return ResponseEntity.ok(combatantList);
    }

    @PostMapping("setCombatants")
    public ResponseEntity<?> setCombatanats(@RequestBody List<Combatant> combatants) {
        combatantList = combatants;

        return ResponseEntity.noContent().build();
    }

    @PostMapping("endCombat")
    public ResponseEntity<?> endCombat() {
        combatantList.clear();
        return ResponseEntity.noContent().build();
    }
}
