package com.server.entities.encounter;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int campaignId;
    private String name;
    @OneToOne
    @JoinColumn(name="id")
    private Music music;
    private boolean lairAction;
    private boolean archived;
    @OneToMany
    @JoinColumn(name="encounterId")
    private List<EncounterMonster> monsterList;

    public int getId() {
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
