package com.example.bamadamarket.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAnnonce;
    @Column(nullable = false)
    private String titre ;
    @NotBlank(message = "La description de l'annonce ne doit pas être null ou vide")
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String etat;
    @Column(nullable = false)
    private int prix;
    @Column(nullable = true)
    private String photo1;


    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idAdministrateur")
    private Administrateur administrateur;
}
