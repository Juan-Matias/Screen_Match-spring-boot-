package com.aluracursos.principal;

import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosTemporadas;
import com.aluracursos.service.ConsumoAPI;
import com.aluracursos.service.ConvierteDatos;

import java.util.ArrayList;
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

        for (int i = 1; i <= datos.totalDeTemporada(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSeria.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);
    }

}
