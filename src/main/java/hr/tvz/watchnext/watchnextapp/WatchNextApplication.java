package hr.tvz.watchnext.watchnextapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WatchNextApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("guest: " + encoder.encode("guest"));
        System.out.println("admin: " + encoder.encode("admin"));
        SpringApplication.run(WatchNextApplication.class, args);
    }

}
