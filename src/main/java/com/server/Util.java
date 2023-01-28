package com.server;

import com.server.entities.Campaign;

public class Util {
    private static Campaign campaign;

    public static void setCampaign(Campaign campaign) {
        Util.campaign = campaign;
    }

    public static Campaign getCampaign() {
        return campaign;
    }
}
