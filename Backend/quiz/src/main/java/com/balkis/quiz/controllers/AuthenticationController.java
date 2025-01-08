package com.balkis.quiz.controllers;


import com.balkis.quiz.models.LoginRequest;
import com.balkis.quiz.models.User;
import com.balkis.quiz.repositories.UserRepository;

import com.balkis.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    public final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @Autowired
    private UserRepository userRepository;

    // Assuming LoginRequest has email and password
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Vérifie si l'utilisateur existe
            Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

            if (user.isPresent()) {
                // Comparaison simple entre le mot de passe envoyé et celui en base
                if (user.get().getPassword().equals(loginRequest.getPassword())) {
                    // Authentification réussie, retourne le message et les détails de l'utilisateur (username et password)
                    return ResponseEntity.ok().body(
                            "{\"message\": \"Login successful\", \"username\": \"" + user.get().getUsername() + "\", \"password\": \"" + user.get().getPassword() + "\", \"userId\": \"" + user.get().getId() +"\"    }"
                    );
                } else {
                    // Mot de passe incorrect
                    return ResponseEntity.status(401).body("{\"error\": \"Invalid username or password\"}");
                }
            } else {
                // Utilisateur non trouvé
                return ResponseEntity.status(401).body("{\"error\": \"Invalid username or password\"}");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("{\"error\": \"Internal Server Error\"}");
        }
    }


    @PostMapping("/update-score/{id}")
    public ResponseEntity<?> updateScore(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        int points = payload.get("points");
        user.setScore(user.getScore() + points); // Mise à jour du score
        userService.saveUser(user); // Sauvegarde de l'utilisateur

        return ResponseEntity.ok(Map.of("message", "Score mis à jour", "newScore", user.getScore()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest registerRequest) {
        try {
            // Vérifier si l'utilisateur existe déjà
            Optional<User> existingUser = userRepository.findByUsername(registerRequest.getUsername());

            if (existingUser.isPresent()) {
                // Si l'utilisateur existe déjà, retourner une erreur
                return ResponseEntity.status(400).body("{\"error\": \"Username already exists\"}");
            }

            // Créer un nouvel utilisateur
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());  // Vous pouvez ajouter un hashage ici si nécessaire

            // Sauvegarder l'utilisateur dans la base de données
            userRepository.save(newUser);

            // Retourner une réponse de succès
            return ResponseEntity.status(201).body("{\"message\": \"User registered successfully\"}");
        } catch (Exception ex) {
            // Gérer les erreurs internes
            return ResponseEntity.status(500).body("{\"error\": \"Internal Server Error\"}");
        }
    }


}
