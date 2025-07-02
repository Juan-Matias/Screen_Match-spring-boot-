package com.aluracursos.principal;

import com.aluracursos.model.*;
import com.aluracursos.repository.ISerieRepository;
import com.aluracursos.service.ConsumoAPI;
import com.aluracursos.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = System.getenv("URL_BASE");
    private final String API_KEY = System.getenv("API_KEY");


    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private ISerieRepository repositorio;
    private  List<Serie> series;

    public Principal(ISerieRepository repository) {
    this.repositorio = repository;
    }

    public void muestraElMenu() {


        var opcion = -1;

        //Creacion del Menu
        while (opcion != 0) {
            var menu = """
                    \n📺 MENÚ PRINCIPAL
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar serie por titulo
                    5 - Top 5 Mejores Series
                    6 - Buscar Serie por Categoria
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes ingresar un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarSerieWeb();
                case 2 -> buscarEpisodioPorSerie();
                case 3 -> mostrarSeriesBuscadas();
                case 4 -> buscarSerieTitulo();
                case 5 -> buscarTop5Series();
                case 6 -> buscarCategoria();
                case 0 -> System.out.println("👋 Cerrando la aplicación...");
                default -> System.out.println("❌ Opción inválida");
            }


        }

    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.printf("Escribe el nombre de la serias de la cual quieras ver los episodios");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        if (serie.isPresent()) {
            var seriesEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= seriesEncontrada.getTotalDeTemporada(); i++) {
                var json = consumoAPI.obtenerDatos(URL_BASE + seriesEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .toList();
            seriesEncontrada.setEpisodios(episodios);
            repositorio.save(seriesEncontrada);
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        //datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        System.out.println(datos);
    }


    private void mostrarSeriesBuscadas() {
       series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }


    private void buscarSerieTitulo() {
        System.out.printf("Escribe el nombre de la serie que deseas buscar :3");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: " + serieBuscada.get());

        }else {
            System.out.println("Serie no encontrada ❌");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                        System.out.printf("Serie: " + s.getTitulo() + "Evaluacion" + s.getEvaluacion())
                );

    }

    private void buscarCategoria(){
        System.out.printf("Escribe el genero/categoria que desea buscar");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series de las categoria"+ genero );
        seriesPorCategoria.forEach(System.out::println);
    }

}