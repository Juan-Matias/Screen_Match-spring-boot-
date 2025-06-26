package com.aluracursos.repository;

import com.aluracursos.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISerieRepository extends JpaRepository<Serie,Long> {

}
