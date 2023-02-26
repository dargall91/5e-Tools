package com.server.repositories.playercharacter;

import com.server.entities.playercharacter.CharacterClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterClassRepository extends JpaRepository<CharacterClass, Integer> {
    List<CharacterClass> findAllByOrderByNameAsc();
}
