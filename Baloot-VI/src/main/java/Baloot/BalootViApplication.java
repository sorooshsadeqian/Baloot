package Baloot;

import Baloot.Api.APIFactory;
import Baloot.Server.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BalootViApplication {

    public static void main(String[] args) {
        Initializer initializer = new Initializer(APIFactory.getInstance());
        initializer.initServerData();
        SpringApplication.run(BalootViApplication.class, args);
    }

}
