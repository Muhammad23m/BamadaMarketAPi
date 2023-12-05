package com.example.bamadamarket.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bamadamarket.Model.Administrateur;


public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    public Administrateur findByEmailAndMotDePasse(String email, String mot_de_passe);

    
}
