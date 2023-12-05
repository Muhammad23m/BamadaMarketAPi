package com.example.bamadamarket.Controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bamadamarket.Model.Annonce;
import com.example.bamadamarket.Service.AnnonceService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("annonce")
@AllArgsConstructor
public class AnnonceController {

    private final AnnonceService annonceService;

    //CREER
    @PostMapping("/create")
    @Operation(summary = "Ajouter nouvelle annonce")
    public ResponseEntity<Annonce> create(
            @Valid @RequestParam("annonce") String annonceString,
            @RequestParam(value = "photo1", required = false) MultipartFile photo1) throws Exception {


        Annonce annonce = new JsonMapper().readValue(annonceString, Annonce.class);

        Annonce savedAnnonce = annonceService.creer(annonce,photo1);

        return new ResponseEntity<>(savedAnnonce, HttpStatus.CREATED);
    }

    @GetMapping("/lire")
    @Operation(summary = "affichage des annonces")
    public List<Annonce> lire(){
        return annonceService.lire();
    }


    @PutMapping("/update/{id}")
    @Operation(summary = "Modifier une annonce par ID")
    public ResponseEntity<Annonce> update(
            @PathVariable Long idAnnonce,
            @Valid @RequestParam("annonce") String annonceString,
            @RequestParam(value = "photo1", required = false) MultipartFile photo1) throws Exception {

        Annonce updatedAnnonce = new JsonMapper().readValue(annonceString, Annonce.class);

        Annonce savedAnnonce = annonceService.modifier(idAnnonce, updatedAnnonce, photo1);

        return new ResponseEntity<>(savedAnnonce, HttpStatus.OK);
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public List<Annonce> getAnnoncesByUtilisateur(@PathVariable Long idUtilisateur) {
        return annonceService.getAnnoncesByUtilisateur(idUtilisateur);
    }

    @DeleteMapping("/Supprimer")
    @Operation(summary = "suppression  d'un annonce")
    public String supprimer(@RequestParam("id") long idAnnonce){
        annonceService.supprimer(idAnnonce);
        return "Vous avez supprimé la annonce à l'id "+idAnnonce;
    }
}
