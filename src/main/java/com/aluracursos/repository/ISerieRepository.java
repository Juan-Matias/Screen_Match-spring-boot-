package com.aluracursos.repository;

import com.aluracursos.model.Categoria;
import com.aluracursos.model.Episodio;
import com.aluracursos.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ISerieRepository extends JpaRepository<Serie,Long> {
    Optional<Serie>findByTituloContainsIgnoreCase(String nombreSerie);
    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    //List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporada, Double evaluacion);

    // Usar JPQL : es el codigo nativo de base de datos de java
    //@Query(value = "SELECT * FROM series WHERE series.total_de_temporada <= 6 AND evaluacion >= 7.5", nativeQuery = true)

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporada <= :totalTemporada AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalTemporada, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC")
    List<Episodio> top5Episodios(Serie serie, org.springframework.data.domain.Pageable pageable);

}
