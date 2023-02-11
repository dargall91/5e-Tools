package com.server.repositories.monster;

import com.server.entities.monster.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository extends JpaRepository<Ability, Integer> {
}
