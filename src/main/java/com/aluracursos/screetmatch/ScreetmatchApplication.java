package com.aluracursos.screetmatch;

import com.aluracursos.model.DatosEpisodio;
import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosTemporadas;
import com.aluracursos.service.ConsumoAPI;
import com.aluracursos.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreetmatchApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(ScreetmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obtenerDatos(	"https://www.omdbapi.com/?t=game+of+thrones&Season=1&apikey=fe9a08ca");
		System.out.println(json);

		ConvierteDatos conversos = new ConvierteDatos();
		var datos = conversos.obtenerDatos(json, DatosSerie.class);

		System.out.println(datos);
		json= consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=fe9a08ca");
		DatosEpisodio episodios = conversos.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodios);

		List<DatosTemporadas> temporadas = new ArrayList<>();

		for (int i =1 ; i<= datos.totalDeTemporada();i++){
			json = consumoAPI.obtenerDatos(	"https://www.omdbapi.com/?t=game+of+thrones&Season="+i+"&apikey=fe9a08ca");
			var datosTemporadas = conversos.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);
	}
}
