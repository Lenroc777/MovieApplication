package com.pollub.MovieApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<com.example.MovieApp.entity.Movie, Long> {
}
