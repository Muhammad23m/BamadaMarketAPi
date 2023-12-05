package com.example.bamadamarket.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bamadamarket.Model.Annonce;
import com.example.bamadamarket.Model.Utilisateur;
import com.example.bamadamarket.Service.AnnonceService;
import com.example.bamadamarket.Service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
public class AdministrateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    private AnnonceService annonceService ;

    @GetMapping("/utilisateurs")
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/utilisateurs/{id}")
    public Utilisateur getUtilisateurById(@PathVariable(value = "id") Long utilisateurId) {
        return utilisateurService.getUtilisateurById(utilisateurId);
    }

    @GetMapping("/annonces")
    public List<Annonce> getAllAnnonces() {
        return annonceService.lire();
    }

    @DeleteMapping("/Supprimer")
    @Operation(summary = "suppression  d'un annonce")
    public String supprimer(@RequestParam("id") long idAnnonce){
        annonceService.supprimer(idAnnonce);
        return "Vous avez supprimé la annonce à l'id "+idAnnonce;
    }

}
