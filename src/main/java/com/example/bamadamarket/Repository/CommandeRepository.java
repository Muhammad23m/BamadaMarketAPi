package com.example.bamadamarket.Repository;

import com.example.bamadamarket.Model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    public Commande findByIdCommande(long id);

    List<Commande> findByUtilisateurIdUtilisateur(Long id);

}
