package com.aluracursos.screetmatch;

import com.aluracursos.principal.Principal;
import com.aluracursos.repository.ISerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; // 👈 IMPORTANTE
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.aluracursos.model") // 👈 ESTO ES CLAVE
@EnableJpaRepositories("com.aluracursos.repository")

public class ScreetmatchApplication implements CommandLineRunner  {

	@Autowired
	private ISerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreetmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraElMenu();
	}
}
