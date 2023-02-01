package com.server.repositories;

import com.server.entities.monster.ChallengeRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRatingRepository extends JpaRepository<ChallengeRating, Integer> {
}
