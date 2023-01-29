package com.server.repositories;

import com.server.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Campaign findOneByActive(boolean b);

}
