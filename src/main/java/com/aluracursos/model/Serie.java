package com.aluracursos.model;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name ="series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;
    private int totalDeTemporada;
    private double evaluacion;
    private String poster;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String actores;
    private String sinopsis;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Episodio> episodios;

    //Constructor predeterminado
    public Serie(){}

    public Serie(DatosSerie datosSerie){
        this.titulo = datosSerie.Titulo();

        try {
            this.totalDeTemporada = Integer.parseInt(datosSerie.totalDeTemporada());
        } catch (NumberFormatException e) {
            this.totalDeTemporada = 0; // valor por defecto si no se puede convertir
        }

        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
        this.poster = datosSerie.poster(); // ojo: aquí pusiste `sinopsis()` por error
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
        this.actores = datosSerie.actores();
        this.sinopsis = datosSerie.sinopsis();
    }
    // Constructors :


    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTotalDeTemporada() {
        return totalDeTemporada;
    }

    public void setTotalDeTemporada(int totalDeTemporada) {
        this.totalDeTemporada = totalDeTemporada;
    }

    public double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    // Zona de Mensaje
    @Override
    public String toString() {
        return
                ", genero=" + genero +
                        " Titulo='" + titulo + '\'' +
                        ", totalDeTemporada=" + totalDeTemporada +
                        ", evaluacion=" + evaluacion +
                        ", poster='" + poster + '\'' +
                        ", actores='" + actores + '\'' +
                        ", sinopsis='" + sinopsis + '\''+
                        ", Episodios='" + episodios + '\'';

    }

}
