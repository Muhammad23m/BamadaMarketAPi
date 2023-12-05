package com.example.bamadamarket.Repository;

import com.example.bamadamarket.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByPseudo(String pseudo);
    public Utilisateur findByContact(int contact);
    public Utilisateur findByPseudoAndMotDePasse(String pseudo, String mot_de_passe);
    Utilisateur findByIdUtilisateur(long id);


}

