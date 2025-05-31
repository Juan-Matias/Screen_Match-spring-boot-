package com.aluracursos.principal;

import com.aluracursos.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=fe9a08ca";
    public void muestraElMenu(){

        System.out.println("Por favor escribe el nombre de la seria que deaseas Buscar");
        var nombreSeria = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(	URL_BASE+nombreSeria.replace(" ","+")+API_KEY);

    }
}
