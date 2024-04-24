package com.springers.SERVICES;

import com.springers.ENTITIES.Poster;
import java.util.List;

public interface IServicePoster {
    void addPoster(Poster poster);
    List<Poster> getPosters();

    void deletPoster(Long id);

    void updatePoster(Poster poster);

    List<Poster> findBySessionId(Long sessionId);

}