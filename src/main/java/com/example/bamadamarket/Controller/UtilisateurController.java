package com.example.bamadamarket.Controller;
import com.example.bamadamarket.Model.Utilisateur;
import com.example.bamadamarket.Service.UtilisateurService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("utilisateur")
@AllArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    @Operation(summary = "Création  d'un utilisateur")
    @PostMapping("/create")
    public ResponseEntity<?> utilisateurString(
            @Valid @RequestParam("utilisateur") String utilisateurString,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Utilisateur utilisateur = new JsonMapper().readValue(utilisateurString, Utilisateur.class);
            Utilisateur savedUtilisateur = utilisateurService.createUtilisateur(utilisateur, image);
            return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            // Gérer la duplication de clé (contact ou pseudo)
            return new ResponseEntity<>("Duplication de clé : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            // Gérer l'erreur d'entité non trouvée
            return new ResponseEntity<>("Erreur lors de l'enregistrement : " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Gérer d'autres exceptions
            return new ResponseEntity<>("Erreur inattendue : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/read")
     @Operation(summary = "Affichage de la  liste des utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateur(){
        return new ResponseEntity<>(utilisateurService.getAllUtilisateur(),HttpStatus.OK);}
   @GetMapping("/read/{id}")
   @Operation(summary = "Affichage  d'un utilisateur")
    public ResponseEntity<Utilisateur> getUtilisateurById(@Valid @PathVariable long id){
        return new ResponseEntity<>(utilisateurService.getUtilisateurById(id),HttpStatus.OK) ;}


   @PutMapping("/update")
   @Operation(summary = "Modification d'un utilisateur")
    public ResponseEntity<Utilisateur> editUtilisateur(
        @PathVariable Long idUtilisateur,
        @Valid @RequestParam("utilisateur") String utilisateurString,
        @RequestParam(value = "image", required = false) MultipartFile image) throws Exception {
        Utilisateur updatedUtilisateur = new JsonMapper().readValue(utilisateurString, Utilisateur.class);
        Utilisateur savedUtilisateur = utilisateurService.editutilisateur(idUtilisateur, updatedUtilisateur,image);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "suppression  d'un annonce")
    public String deleteUtilisateurById(@RequestParam("id") long idUtilisateur){
        utilisateurService.deleteUtilisateurById(idUtilisateur);
        return "Vous avez supprimé l'utilisateur à l'id "+idUtilisateur;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(summary = "Connexion d'un utilisateur")
    public Object connexion(@RequestParam("pseudo") String pseudo,
                            @RequestParam("motDePasse") String motDePasse) {
        return utilisateurService.connectionUtilisateur(pseudo, motDePasse);
    }
}
