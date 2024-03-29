package com.server.repositories;

import com.server.entities.Music;
import com.server.repositories.projections.NameIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    List<NameIdProjection> findByOrderByNameAsc();
    Music findByFileName(String fileName);
}
