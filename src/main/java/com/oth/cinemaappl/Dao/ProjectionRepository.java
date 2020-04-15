package com.oth.cinemaappl.Dao;

import com.oth.cinemaappl.entities.ProjectionFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionRepository extends JpaRepository<ProjectionFilm,Long> {
}
