package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.StressStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StressStatusRepository extends JpaRepository<StressStatus, Integer> {
}
