package community.whatever.petcoming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PetComingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetComingApplication.class, args);
	}

}
