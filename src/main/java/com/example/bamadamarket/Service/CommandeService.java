package com.example.bamadamarket.Service;
import com.example.bamadamarket.Model.Annonce;
import com.example.bamadamarket.Model.Commande;
import com.example.bamadamarket.Repository.CommandeRepository;
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
public class CommandeService {

    private  final CommandeRepository commandeRepository;

    @Autowired
    public CommandeService(CommandeRepository comRepository) {
        this.commandeRepository = comRepository;
    }


    public Commande creer (Commande commande, MultipartFile photo) {
        try {
            savePhoto(photo, commande::setPhoto);

            return commandeRepository.save(commande);
        } catch (Exception e) {
            throw new EntityNotFoundException("Erreur lors de l'enregistrement de la voiture", e);
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

    public List<Commande> lire(){
        return commandeRepository.findAll();
    }

    public  List<Commande> lireByIdUtilisateur(Long idUtilisateur){
        return commandeRepository.findByUtilisateurIdUtilisateur(idUtilisateur);
    }
    public void deleteById(long id) {
        commandeRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return commandeRepository.existsById(id);
    }
}
