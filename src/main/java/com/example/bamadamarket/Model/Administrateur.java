package com.example.bamadamarket.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Administrateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdministrateur;

    @Column(nullable = false)
     private String nom ;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @OneToMany(mappedBy = "administrateur",cascade= CascadeType.ALL)
    private List<Annonce> annonces = new ArrayList<>();

    @OneToMany(mappedBy = "administrateur",cascade= CascadeType.ALL)
     private List<Utilisateur> utilisateur = new ArrayList<>();
}
