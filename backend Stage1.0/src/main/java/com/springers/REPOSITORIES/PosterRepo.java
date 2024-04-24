package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosterRepo extends JpaRepository<Poster, Long> {
    List<Poster> findBySessionPosterId(Long sessionId);
}