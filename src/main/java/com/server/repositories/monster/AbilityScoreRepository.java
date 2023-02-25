package com.server.repositories.monster;

import com.server.entities.monster.abilityscore.AbilityScore;
import com.server.entities.monster.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityScoreRepository<T extends AbilityScore> extends JpaRepository<AbilityScore, Monster> {
}
