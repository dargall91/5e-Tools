package com.server;

import com.server.entities.Campaign;

public class CampaignManager {
    private static Campaign campaign;

    public static void setCampaign(Campaign campaign) {
        CampaignManager.campaign = campaign;
    }

    public static Campaign getCampaign() {
        return campaign;
    }
}
