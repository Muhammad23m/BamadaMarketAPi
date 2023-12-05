package com.example.bamadamarket.Service;

import com.example.bamadamarket.Exception.NoContentException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bamadamarket.Model.Utilisateur;
import com.example.bamadamarket.Repository.UtilisateurRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.ejb.DuplicateKeyException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

@Service
public class UtilisateurService  {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository userRepository) {
        this.utilisateurRepository = userRepository;
    }

    public Utilisateur createUtilisateur (Utilisateur utilisateur, MultipartFile image) {
        try {

            if (utilisateurRepository.findByContact(utilisateur.getContact()) != null) {
                throw new DuplicateKeyException("Le contact existe déjà dans la base de données");
            }

            // Vérifie si un utilisateur avec le même pseudo existe
            if (utilisateurRepository.findByPseudo(utilisateur.getPseudo()) != null) {
                throw new DuplicateKeyException("Le pseudo existe déjà dans la base de données");
            }

            savePhoto(image, utilisateur::setImage);
            return utilisateurRepository.save(utilisateur);
        } catch (Exception e) {
            throw new EntityNotFoundException("Erreur lors de l'enregistrement de l'annonce", e);
        }
    }

    private void savePhoto(MultipartFile image, Consumer<String> setPhotoMethod) throws IOException {
        if (image != null) {
            String location = "C:\\xampp\\htdocs\\bamada";
            Path rootLocation = Paths.get(location);

            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            Path filePath = rootLocation.resolve(image.getOriginalFilename());

            if (!Files.exists(filePath)) {
                Files.copy(image.getInputStream(), filePath);
                setPhotoMethod.accept("/bamada/" + image.getOriginalFilename());
            } else {
                Files.delete(filePath);
                Files.copy(image.getInputStream(), filePath);
                setPhotoMethod.accept("/bamada/" + image.getOriginalFilename());
            }
        }
    }

    public List<Utilisateur> getAllUtilisateur(){

        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if (utilisateurs.isEmpty())
            throw new NoContentException("Aucun utilisateur trouvé");
        return utilisateurs;
    }
    public Utilisateur getUtilisateurById(long idUtilisateur){

        Utilisateur utilisateur= utilisateurRepository.findByIdUtilisateur(idUtilisateur);
        if(utilisateur ==null)
            throw new EntityNotFoundException("cet utilisateur n'existe pas");
        return utilisateur;
    }


    public Utilisateur editutilisateur(Long id, Utilisateur updatedUtilisateur, MultipartFile image) throws EntityNotFoundException {
        // Recherche de la voiture par ID
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvée avec l'ID : " + id));

        // Mise à jour des informations de base de l'annonce
        existingUtilisateur.setNom(updatedUtilisateur.getNom());
        existingUtilisateur.setContact(updatedUtilisateur.getContact());
        existingUtilisateur.setPseudo(updatedUtilisateur.getPseudo());
        existingUtilisateur.setMotDePasse(updatedUtilisateur.getMotDePasse());
        // ... Mise à jour d'autres propriétés

        // Mise à jour des photos (suppression des anciennes et ajout des nouvelles)
        deleteimage(existingUtilisateur.getImage());

        existingUtilisateur.setImage(savePhoto(image));

        // Enregistrement de l'annone mise à jour dans la base de données
        return utilisateurRepository.save(existingUtilisateur);
    }

    private String savePhoto(MultipartFile image) {
        if (image != null) {
            String location = "C:\\xampp\\htdocs\\bamada";
            Path rootLocation = Paths.get(location);

            try {
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                Path filePath = rootLocation.resolve(image.getOriginalFilename());

                if (!Files.exists(filePath)) {
                    Files.copy(image.getInputStream(), filePath);
                    return "/bamada/" + image.getOriginalFilename();
                } else {
                    Files.delete(filePath);
                    Files.copy(image.getInputStream(), filePath);
                    return "/bamada/" + image.getOriginalFilename();
                }
            } catch (IOException e) {
                // Gérer l'erreur de traitement de fichier, si nécessaire
                e.printStackTrace();
            }
        }

        return null;
    }

    public String deleteUtilisateurById(Long idUtilisateur) throws EntityNotFoundException {
        // Recherche de la voiture par ID
        Utilisateur utilisateurToDelete = utilisateurRepository.findByIdUtilisateur(idUtilisateur);
        if(utilisateurToDelete == null) throw  new EntityNotFoundException("Utilisateur non trouvée avec l'ID : " + idUtilisateur);

        // Suppression des photos associées
        deleteimage(utilisateurToDelete.getImage());


        // Suppression de la voiture
        utilisateurRepository.deleteById(idUtilisateur);
        return "Utilisateur supprimer";

    }

    private void deleteimage(String photoUrl) {
        if (photoUrl != null) {
            // Extrait le nom du fichier à partir de l'URL
            String fileName = photoUrl.substring(photoUrl.lastIndexOf("/") + 1);

            // Supprime le fichier
            Path filePath = Paths.get("C:\\xampp\\htdocs\\bamada", fileName);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Gérer l'erreur de suppression de fichier, si nécessaire
                e.printStackTrace();
            }
        }
    }

    public Utilisateur connectionUtilisateur(String pseudo, String motDePasse) {
        Utilisateur user = utilisateurRepository.findByPseudoAndMotDePasse(pseudo, motDePasse);
        if (user == null) {
            throw new EntityNotFoundException("Cet utilisateur n'existe pas");
        }

        return user;
    }


}
