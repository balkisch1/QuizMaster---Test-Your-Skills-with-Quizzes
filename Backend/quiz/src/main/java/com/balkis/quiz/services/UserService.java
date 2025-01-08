package com.balkis.quiz.services;

import com.balkis.quiz.models.User;
import com.balkis.quiz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
    public void registerUser(User user) {

        user.setPassword(user.getPassword());

        userRepository.save(user);
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null); // Retourne l'utilisateur s'il existe, sinon null
    }



    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User updateUserScore(Long userId, int newScore) {
        // Récupérer l'utilisateur
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setScore(newScore);  // Mettre à jour le score
            return userRepository.save(user);  // Sauvegarder l'utilisateur avec le score mis à jour
        }
        return null;  // Si l'utilisateur n'est pas trouvé
    }


    public User saveScore(Long userId, int score) {
        // Trouver l'utilisateur dans la base de données
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Ajouter le score à l'utilisateur
        user.addScore(score);

        // Sauvegarder l'utilisateur mis à jour
        return userRepository.save(user);
    }

    // Autres méthodes pour la gestion des utilisateurs
}

