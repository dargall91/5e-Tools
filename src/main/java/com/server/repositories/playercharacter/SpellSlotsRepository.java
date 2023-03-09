package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.SpellSlots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellSlotsRepository extends JpaRepository<SpellSlots, Integer> {
}
