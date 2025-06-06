package com.aluracursos.principal;

import com.aluracursos.model.DatosEpisodio;
import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosTemporadas;
import com.aluracursos.model.Episodio;
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

    public void muestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar:");

        var nombreSeria = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSeria.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        // Lista para guardar temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();

        // Obtener datos de todas las temporadas
        for (int i = 1; i <= datos.totalDeTemporada(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSeria.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }

        // Convertir toda la información a una lista de DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

        // Mostrar top 5 mejores episodios por evaluación numérica
        System.out.println("Top 5 mejores episodios:");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equals("N/A"))
                .sorted(Comparator.comparing(
                        e -> Double.parseDouble(e.evaluacion()), Comparator.reverseOrder()))
                .limit(5)
                .forEach(System.out::println);

        //Conviertiendo los dato a una lista del tipo Episodios
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                .toList();

        episodios.forEach(System.out::println);
    }


}
