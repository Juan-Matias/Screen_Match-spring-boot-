package com.aluracursos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        /*
        Son anotaciones que sirve para mapear propiedades de una clase a campo Json
        @JsonAlias y @JsonProperty
        */
        @JsonAlias("Title") String Titulo,
        @JsonAlias("totalSeasons") int totalDeTemporada,
        @JsonAlias("imdbRating") double evaluacion,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Plot") String sinopsis,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Actors") String actores
) {
    public int totalTemporadas() {
    return 0;
    }
}
