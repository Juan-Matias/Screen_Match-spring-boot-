package com.aluracursos.screetmatch;

import com.aluracursos.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; // 👈 IMPORTANTE

@SpringBootApplication
@EntityScan("com.aluracursos.model") // 👈 ESTO ES CLAVE
public class ScreetmatchApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(ScreetmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
