package com.example.bamadamarket.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Utilisateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUtilisateur;

     @Column(nullable = false)
    private String nom;
    @Column(nullable = false, unique = true)
    private int contact;
    @Column(nullable = false, unique = true)
    private String pseudo;
    @Column(nullable = false)
    private String motDePasse;
    @Column(nullable = true)
    private String image;

    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Annonce> annonces = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idAdministrateur")
    private Administrateur administrateur;


}
