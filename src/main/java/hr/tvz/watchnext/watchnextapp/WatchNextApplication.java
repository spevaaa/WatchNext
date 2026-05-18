package hr.tvz.watchnext.watchnextapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WatchNextApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchNextApplication.class, args);
    }

}
