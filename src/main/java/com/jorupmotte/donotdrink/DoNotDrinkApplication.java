package com.jorupmotte.donotdrink;

//import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DoNotDrinkApplication {

	public static void main(String[] args) {
//		Dotenv dotenv = Dotenv.configure().load();
//		dotenv.entries().forEach((entry -> System.setProperty(entry.getKey(), entry.getValue())));

		SpringApplication.run(DoNotDrinkApplication.class, args);
	}

}
