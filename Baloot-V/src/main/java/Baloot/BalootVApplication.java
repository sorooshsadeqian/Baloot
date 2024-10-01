package Baloot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Baloot.Api.APIFactory;
import Baloot.Server.Initializer;


@SpringBootApplication
public class BalootVApplication {

	public static void main(String[] args) {
		Initializer initializer = new Initializer(APIFactory.getInstance());
		initializer.initServerData();
		SpringApplication.run(BalootVApplication.class, args);
	}

}
