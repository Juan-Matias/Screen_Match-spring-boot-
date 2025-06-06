package com.aluracursos.principal;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EjemplosStreams {

    public void muestraEjemplo() {
        // Creamos una lista inmutable con algunos nombres
        List<String> nombres = Arrays.asList(
                "Matias Robledo",
                "Luis Gamez",
                "Jose Feliciano",
                "Maria Gonazles"
        );

        // Iniciamos una operación con Streams sobre la lista de nombres
        nombres.stream()
                // Limita el stream a solo los primeros 2 elementos
                .limit(2)
                // Filtra los elementos cuyo nombre comienza con "M"
                .filter(n -> n.startsWith("M"))
                // Convierte los nombres a mayúsculas
                .map(n -> n.toUpperCase())
                // Ordena alfabéticamente los nombres (ya en mayúsculas)
                .sorted()
                // Imprime cada elemento resultante del stream en la consola
                .forEach(System.out::println);
    }
}
