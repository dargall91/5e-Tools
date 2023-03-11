package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.WarlockSpellSlots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarlockSpellSlotsRepository extends JpaRepository<WarlockSpellSlots, Integer> {
}
