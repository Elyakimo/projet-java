package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Utilisateur;
import com.onocia.gestionstage.repository.UtilisateurRepository;
import com.onocia.gestionstage.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Utilisateur utilisateur) {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé.");
        }

        utilisateur.setRole("ETUDIANT");
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurRepository.save(utilisateur);

        return ResponseEntity.ok("Compte créé avec succès.");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> identifiants) {
        String email = identifiants.get("email");
        String motDePasse = identifiants.get("motDePasse");

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, motDePasse)
        );

        String token = jwtService.genererToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
