package com.example.bamadamarket.Service;

import com.example.bamadamarket.Model.Annonce;
import com.example.bamadamarket.Repository.AnnonceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

@Service
public class AnnonceService {
    private final AnnonceRepository annonceRepository;

    @Autowired
    public AnnonceService(AnnonceRepository annRepository) {
        this.annonceRepository = annRepository;
    }


   public Annonce creer (Annonce annonce,MultipartFile photo1) {
        try {

            savePhoto(photo1, annonce::setPhoto1);


            return annonceRepository.save(annonce);
        } catch (Exception e) {
            throw new EntityNotFoundException("Erreur lors de l'enregistrement de la voiture", e);
        }
    }

    private void savePhoto(MultipartFile photo, Consumer<String> setPhotoMethod) throws IOException {
        if (photo != null) {
            String location = "C:\\xampp\\htdocs\\bamada";
            Path rootLocation = Paths.get(location);

            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            Path filePath = rootLocation.resolve(photo.getOriginalFilename());

            if (!Files.exists(filePath)) {
                Files.copy(photo.getInputStream(), filePath);
                setPhotoMethod.accept("/bamada/" + photo.getOriginalFilename());
            } else {
                Files.delete(filePath);
                Files.copy(photo.getInputStream(), filePath);
                setPhotoMethod.accept("/bamada/" + photo.getOriginalFilename());
            }
        }
    }

    public List<Annonce> lire(){
        return annonceRepository.findAll();
    }

    public List<Annonce> getAnnoncesByUtilisateur(Long idUtilisateur) {
        return annonceRepository.findByUtilisateurIdUtilisateur(idUtilisateur);
    }

    public Annonce modifier(Long id, Annonce updatedAnnonce, MultipartFile photo1) throws EntityNotFoundException {
        // Recherche de la voiture par ID
        Annonce existingAnnonce = annonceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Annonce non trouvée avec l'ID : " + id));

        // Mise à jour des informations de base de l'annonce
        existingAnnonce.setTitre(updatedAnnonce.getTitre());
        existingAnnonce.setDescription(updatedAnnonce.getDescription());
        existingAnnonce.setEtat(updatedAnnonce.getEtat());
        existingAnnonce.setPrix(updatedAnnonce.getPrix());
        // ... Mise à jour d'autres propriétés

        // Mise à jour des photos (suppression des anciennes et ajout des nouvelles)
        deletePhoto(existingAnnonce.getPhoto1());


        existingAnnonce.setPhoto1(savePhoto(photo1));

        // Enregistrement de l'annone mise à jour dans la base de données
        return annonceRepository.save(existingAnnonce);
    }

    private String savePhoto(MultipartFile photo) {
        if (photo != null) {
            String location = "C:\\xampp\\htdocs\\bamada";
            Path rootLocation = Paths.get(location);

            try {
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                Path filePath = rootLocation.resolve(photo.getOriginalFilename());

                if (!Files.exists(filePath)) {
                    Files.copy(photo.getInputStream(), filePath);
                    return "/bamada/" + photo.getOriginalFilename();
                } else {
                    Files.delete(filePath);
                    Files.copy(photo.getInputStream(), filePath);
                    return "/bamada/" + photo.getOriginalFilename();
                }
            } catch (IOException e) {
                // Gérer l'erreur de traitement de fichier, si nécessaire
                e.printStackTrace();
            }
        }

        return null;
    }



    public String supprimer(Long idAnnonce) throws EntityNotFoundException {
        // Recherche de la voiture par ID
        Annonce annonceToDelete = annonceRepository.findByIdAnnonce(idAnnonce);
        if(annonceToDelete == null) throw  new EntityNotFoundException("Annonce non trouvée avec l'ID : " + idAnnonce);

        // Suppression des photos associées
        deletePhoto(annonceToDelete.getPhoto1());


        // Suppression de la voiture
        annonceRepository.deleteById(idAnnonce);
        return "Annonce supprimer";

    }
    private void deletePhoto(String photoUrl) {
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
}
