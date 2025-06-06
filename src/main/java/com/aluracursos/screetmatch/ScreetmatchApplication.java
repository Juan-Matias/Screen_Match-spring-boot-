package com.aluracursos.screetmatch;

import com.aluracursos.model.DatosEpisodio;
import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosSerie;
import com.aluracursos.model.DatosTemporadas;
import com.aluracursos.principal.EjemplosStreams;
import com.aluracursos.principal.Principal;
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
		Principal principal = new Principal();
		principal.muestraElMenu();

		//EjemplosStreams ejemplosStreams = new EjemplosStreams();
		//ejemplosStreams.muestraEjemplo();


	}
}
