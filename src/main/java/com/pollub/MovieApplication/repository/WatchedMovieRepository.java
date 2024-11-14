package com.pollub.MovieApplication.repository;

import com.pollub.MovieApplication.entity.WatchedMovie;
import com.pollub.MovieApplication.entity.WatchedMovieKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, WatchedMovieKey> {
}
