package com.aluracursos.principal;

import com.aluracursos.model.*;
import com.aluracursos.repository.ISerieRepository;
import com.aluracursos.service.ConsumoAPI;
import com.aluracursos.service.ConvierteDatos;
import org.springframework.data.domain.PageRequest;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = System.getenv("URL_BASE");
    private final String API_KEY = System.getenv("API_KEY");
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private ISerieRepository repositorio;
    private List<Serie> series;

    public static void p(String text) {
        System.out.println(text);
    }

    public Principal(ISerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    \n📺 MENÚ PRINCIPAL
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar serie por titulo
                    5 - Top 5 Mejores Series
                    6 - Buscar Serie por Categoria
                    7 - Filtrar series por temporada y evaluación
                    8 - Buscar episodios por titulo
                    9 - Top 5 episodios por Serie 
                    0 - Salir
                    """;
            p(menu);
            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                p("❌ Debes ingresar un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarSerieWeb();
                case 2 -> buscarEpisodioPorSerie();
                case 3 -> mostrarSeriesBuscadas();
                case 4 -> buscarSerieTitulo();
                case 5 -> buscarTop5Series();
                case 6 -> buscarCategoria();
                case 7 -> filtrarSeriesPorTemporadaYEvaluacion();
                case 8 -> buscarEpisodiosPorTitulo();
                case 9 -> buscarTop5Episodios();
                case 0 -> p("👋 Cerrando la aplicación...");
                default -> p("❌ Opción inválida");
            }
        }
    }

    private DatosSerie getDatosSerie() {
        p("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        p(json);
        return conversor.obtenerDatos(json, DatosSerie.class);
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        p("Escribe el nombre de la serie de la cual quieras ver los episodios:");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporada(); i++) {
                var json = consumoAPI.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .toList();

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            p("❌ Serie no encontrada.");
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        p(datos.toString());
    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieTitulo() {
        p("Escribe el nombre de la serie que deseas buscar :3");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            p("La serie buscada es: " + serieBuscada.get());
        } else {
            p("❌ Serie no encontrada");
        }
    }

    private Optional<Serie> buscarSeriePorTitulo() {
        p("Escribe el nombre de la serie para mostrar sus episodios mejor evaluados:");
        var nombreSerie = teclado.nextLine();
        return repositorio.findByTituloContainsIgnoreCase(nombreSerie);
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                System.out.printf("Serie: %s - Evaluación: %.1f\n", s.getTitulo(), s.getEvaluacion()));
    }

    private void buscarCategoria() {
        p("Escribe el género/categoría que deseas buscar:");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        p("Las series de la categoría " + genero + ":");
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriesPorTemporadaYEvaluacion() {
        p("¿Filtrar series con cuántas temporadas?");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        p("¿Con evaluación a partir de qué valor?");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
        p("*** Series filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + " - Evaluación: " + s.getEvaluacion()));
    }

    private void buscarEpisodiosPorTitulo() {
        p("Escribe el nombre del episodio que deseas buscar:");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Serie: %s - Temporada: %s - Episodio: %s - Evaluación: %.1f\n",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
    }

    private void buscarTop5Episodios() {
        System.out.println("Escribe el nombre de la serie para ver sus 5 mejores episodios:");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();

            var topEpisodios = repositorio.top5Episodios(serie, PageRequest.of(0, 5));
            topEpisodios.forEach(e ->
                    System.out.printf("Serie: %s - Temporada %s - Episodio %s - Evaluación %.1f\n",
                            e.getSerie().getTitulo(),
                            e.getTemporada(),
                            e.getTitulo(),
                            e.getEvaluacion())
            );

        } else {
            System.out.println("❌ Serie no encontrada.");
        }
    }

}
