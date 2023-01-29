package com.server.controllers;

import com.server.CampaignManager;
import com.server.entities.Campaign;
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

@RequestMapping("5eTools/api/campaign")
@RestController
public class CampaignController {
    @Autowired
    private CampaignRepository campaignRepo;

    /**
     * Gets the currently selected campaign. if no campaign set, the last selected campaign in the database will be
     * selected. If no such campaign exists, a 404 error will be returned.
     * @return
     */
    @GetMapping("getActive")
    public ResponseEntity<?> getActiveCampaign() {
        if (CampaignManager.getCampaign() == null) {
            CampaignManager.setCampaign(campaignRepo.findOneByActive(true));
        }

        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }

        return ResponseEntity.ok(CampaignManager.getCampaign());
    }

    /**
     * Gets the currently selected campaign. if no campaign set, the last selected campaign in the database will be
     * selected. If no such campaign exists, a 404 error will be returned.
     * @return
     */
    @GetMapping("{campaignId}")
    public ResponseEntity<?> getCampaign(@PathVariable int campaignId) {
        Optional<Campaign> result = campaignRepo.findById(campaignId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign with id " + campaignId + " not found.");
        }

        return ResponseEntity.ok(result.get());
    }

    /**
     * Sets the campaign with the specified id as active. The currently selected campaign is marked as inactive.
     * The newly activated campaign is returned.
     * @param campaignId
     * @return
     */
    @PostMapping("{campaignId}/setActive")
    public ResponseEntity<?> setActiveCampaign(@PathVariable int campaignId) {
        Optional<Campaign> result = campaignRepo.findById(campaignId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign with id " + campaignId + " not found.");
        }

        //deactivate current campaign
        if (CampaignManager.getCampaign() != null && CampaignManager.getCampaign().isActive()) {
            CampaignManager.getCampaign().setActive(false);
            campaignRepo.save(CampaignManager.getCampaign());
        }

        result.get().setActive(true);
        CampaignManager.setCampaign(result.get());
        campaignRepo.save(CampaignManager.getCampaign());

        return ResponseEntity.ok(CampaignManager.getCampaign());
    }

    /**
     * Adds a new campaign to the database
     * @param campaign
     * @return
     */
    @PutMapping("add")
    public ResponseEntity<?> addCampaign(@RequestParam String name) {
        Campaign addCampaign = campaignRepo.save(new Campaign(name));
        String requestMap = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(requestMap, addCampaign.getId().toString())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * Renames the active campaign
     * @param campaignId
     * @param name
     * @return
     */
    @PostMapping("rename")
    public ResponseEntity<?> renameCampaign(@RequestParam String name) {
        if (CampaignManager.getCampaign() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active campaign found.");
        }

        CampaignManager.getCampaign().setName(name);
        campaignRepo.save(CampaignManager.getCampaign());

        return null;
    }

    /**
     * Gets a list of all camapigns in the database
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(campaignRepo.findAll());
    }


    /**
     * Delets a campaign from the database. The active camapign cannot be deleted
     * @return
     */
    @DeleteMapping("{campaignId}")
    public ResponseEntity<?> deleteCampaign(@PathVariable int campaignId) {
        if (CampaignManager.getCampaign() != null && CampaignManager.getCampaign().getId() == campaignId) {
            return ResponseEntity.badRequest().body("Cannot delete active campaign");
        }
        try {
            campaignRepo.deleteById(campaignId);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign with id " + campaignId + " not found.");
        }

        return ResponseEntity.noContent().build();
    }
}
