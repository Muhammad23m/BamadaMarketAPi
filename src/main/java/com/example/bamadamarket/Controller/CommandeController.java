package com.example.bamadamarket.Controller;

import com.example.bamadamarket.Model.Annonce;
import com.example.bamadamarket.Model.Commande;
import com.example.bamadamarket.Service.CommandeService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("commande")
@AllArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    //CREER
    @PostMapping("/create")
    @Operation(summary = "Ajouter nouvelle commande")
    public ResponseEntity<Commande> create(
            @Valid @RequestParam("commande") String commandeString,
            @RequestParam(value = "photo", required = false) MultipartFile photo) throws Exception {


        Commande commande = new JsonMapper().readValue(commandeString, Commande.class);

        Commande savedCommande = commandeService.creer(commande,photo);

        return new ResponseEntity<>(savedCommande, HttpStatus.CREATED);
    }

    @GetMapping("/lire")
    @Operation(summary = "affichage des commandes")
    public List<Commande> lire(){
        return commandeService.lire();
    }


    @GetMapping("/utilisateur/{idUtilisateur}")
    public List<Commande> getCommandeByUtilisateur(@PathVariable Long idUtilisateur) {
        return commandeService.lireByIdUtilisateur(idUtilisateur);
    }

    @DeleteMapping("/{idCommande}")
    public ResponseEntity<String> deleteCommandeById(@PathVariable long idCommande) {
        // Vérifier si la commande existe avant de la supprimer
        if (commandeService.existsById(idCommande)) {
            commandeService.deleteById(idCommande);
            return new ResponseEntity<>("Commande supprimée avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Commande non trouvée", HttpStatus.NOT_FOUND);
        }
    }
}
