package hr.tvz.watchnext.watchnextapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WatchNextApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("admin: " + encoder.encode("password"));
        System.out.println("user: " + encoder.encode("user123"));
        SpringApplication.run(WatchNextApplication.class, args);
    }

}
