package com.movielix.movieApi.repositories;

import com.movielix.movieApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
