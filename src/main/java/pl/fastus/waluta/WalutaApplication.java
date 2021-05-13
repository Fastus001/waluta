package pl.fastus.waluta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class WalutaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalutaApplication.class, args);
    }

}
