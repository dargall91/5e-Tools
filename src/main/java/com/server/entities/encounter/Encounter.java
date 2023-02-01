package com.server.entities.encounter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int campaignId;
    private String name;
    @OneToOne
    @JoinColumn(name="musicId")
    private Music music;
    private boolean lairAction = false;
    private boolean archived = false;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="encounterId")
    private List<EncounterMonster> monsterList = new ArrayList<>();

    public Encounter() { }

    public Encounter(String name, int campaignId) {
        this.name = name;
        this.campaignId = campaignId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public boolean isLairAction() {
        return lairAction;
    }

    public void setLairAction(boolean lairAction) {
        this.lairAction = lairAction;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<EncounterMonster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<EncounterMonster> monsterList) {
        this.monsterList = monsterList;
    }
}
