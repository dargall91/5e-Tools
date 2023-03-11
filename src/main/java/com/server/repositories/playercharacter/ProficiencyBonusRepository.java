package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.ProficiencyBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProficiencyBonusRepository extends JpaRepository<ProficiencyBonus, Integer> {
    List<ProficiencyBonus> findAllByOrderByLevelAsc();
}
