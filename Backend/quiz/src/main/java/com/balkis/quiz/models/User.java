package com.balkis.quiz.models;

import jakarta.persistence.*;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users")
public class    User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
private int score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public boolean checkPassword(String inputPassword) {
        // Hacher le mot de passe saisi avec SHA-256
        String hashedInputPassword = hashPassword(inputPassword);

        // Comparer le mot de passe haché saisi avec celui stocké
        return hashedInputPassword.equals(this.password);
    }

    private String hashPassword(String password) {
        try {
            // Utiliser SHA-256 pour hacher le mot de passe
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            // Convertir le tableau d'octets en une chaîne hexadécimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    // Méthode pour ajouter au score
    public void addScore(int points) {
        this.score += points;
    }
// Getters et setters
}
