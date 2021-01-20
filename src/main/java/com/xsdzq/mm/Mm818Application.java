package com.xsdzq.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Mm818Application {

	public static void main(String[] args) {
		SpringApplication.run(Mm818Application.class, args);
	}

}
