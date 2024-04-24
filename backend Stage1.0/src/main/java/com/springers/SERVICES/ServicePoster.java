package com.springers.SERVICES;

import com.springers.ENTITIES.Poster;
import com.springers.REPOSITORIES.PosterRepo;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ServicePoster implements IServicePoster{
    private static final Logger logger = Logger.getLogger(ServicePoster.class);
    private PosterRepo posterRepo;
    
    @Override
    public void addPoster(Poster poster) {
        logger.debug("Adding poster " + poster.toString());
        posterRepo.save(poster);

    }

    @Override
    public List<Poster> getPosters() {
        logger.debug("Getting poster list");
        return posterRepo.findAll();

    }

    @Override
    public void deletPoster(Long id) {
        logger.debug("Deleting poster " + id);
        posterRepo.deleteById(id);
    }

    @Override
    public void updatePoster(Poster poster) {
        logger.debug("Updating poster " + poster.toString());
        posterRepo.save(poster);
    }

    @Override
    public List<Poster> findBySessionId(Long sessionId) {
        logger.debug("Finding poster by session id " + sessionId);
        return posterRepo.findBySessionPosterId(sessionId);
    }
}