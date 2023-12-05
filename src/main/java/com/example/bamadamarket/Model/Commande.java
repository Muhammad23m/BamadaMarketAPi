package com.example.bamadamarket.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DateTimeException;
import java.util.Date;

@Entity
@Data
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCommande;

    @Column(nullable = true)
    private String titre ;

    @Column(nullable = true)
    private int quantite ;

    @Column(nullable = true)
    private String photo ;

    @Column(nullable = false)
    private Date dateCommande ;

    @Column(nullable = false)
    private int prix ;

    @Column(nullable = false)
    private boolean etat ;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idAnnonce")
    private Annonce annonce;
}
