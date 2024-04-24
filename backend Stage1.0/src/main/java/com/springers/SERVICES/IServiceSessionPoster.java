package com.springers.SERVICES;

import com.springers.ENTITIES.SessionPoster;

import java.util.List;

public interface IServiceSessionPoster {
    void addPosterSession(SessionPoster sessionPoster);

    List<SessionPoster> getPosterSessions();

    void deletPosterSession(Long id);

    SessionPoster getPosterSessionById(Long id);

    void updatePosterSession(SessionPoster sessionPoster);

    List<SessionPoster> searchPosterSessionsByClassroom(int classroom);

    List<SessionPoster> searchPosterSessionsByProfName(String username);
}