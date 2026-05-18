package hr.tvz.watchnext.watchnextapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WatchNextApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
<<<<<<< HEAD
        System.out.println("guest: " + encoder.encode("guest"));
        System.out.println("admin: " + encoder.encode("admin"));
=======
        System.out.println("admin: " + encoder.encode("password"));
        System.out.println("user: " + encoder.encode("user123"));
>>>>>>> f4ebf5c299e0c72d5f510cc537e1f538375dc88b
        SpringApplication.run(WatchNextApplication.class, args);
    }

}
