<<<<<<< HEAD
package hr.tvz.watchnext.watchnextapp.controller;

import hr.tvz.watchnext.watchnextapp.security.JwtTokenProvider;
import hr.tvz.watchnext.watchnextapp.security.LoginRequest;
import hr.tvz.watchnext.watchnextapp.security.LoginResponse;
import org.springframework.security.core.GrantedAuthority;
=======
package hr.tvz.watchnext.watchnextapp.security;

>>>>>>> f4ebf5c299e0c72d5f510cc537e1f538375dc88b
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        String token = jwtTokenProvider.generateToken(authentication);
<<<<<<< HEAD
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");
        return ResponseEntity.ok(new LoginResponse(token, request.username(), role));
=======
        return ResponseEntity.ok(new LoginResponse(token));
>>>>>>> f4ebf5c299e0c72d5f510cc537e1f538375dc88b
    }
}