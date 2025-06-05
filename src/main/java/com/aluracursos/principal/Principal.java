package com.aluracursos.principal;

import com.aluracursos.model.DatosEpisodio;
import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosTemporadas;
import com.aluracursos.service.ConsumoAPI;
import com.aluracursos.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=fe9a08ca";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){

        System.out.println("Por favor escribe el nombre de la seria que deaseas Buscar");

        //Busca los datos generales de la seria:
        var nombreSeria = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(	URL_BASE+nombreSeria.replace(" ","+")+API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();

        /*
        for (int i = 1; i <= datos.totalDeTemporada(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSeria.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);
        */


        //Funciones lambda
        /*temporadas.forEach(
                t -> t.episodios()
                        .forEach(e -> System.out.println(e.titulo())));
*/
        //Convertir todas las informacion a una lista del tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                        .toList();

        //Top 5 episodios
        System.out.println("Top 5 mejores Episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equals("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                        .forEach(System.out::println);

        /*
        // Mostrar solo el titulo de los episodios para las temporadas

        System.out.printf("%-10s | %-8s | %s%n", "Temporada", "Episodio", "Título");
        System.out.println("-----------------------------------------------");

        temporadas.forEach(t -> {
            String temporada = String.valueOf(t.numero());
            t.episodios().forEach(e -> {
                String episodio = e.evaluacion();
                System.out.printf("%-10s | %-8s | %s%n", temporada, episodio, e.titulo());
            });
        });


        for (int i = 0; i < datos.totalDeTemporada(); i++) {
             List<DatosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();

            for (int j = 0; j < episodiosTemporadas.size(); j++) {
                System.out.println(episodiosTemporadas.get(j).titulo());
            }
        }*/

        }

}
