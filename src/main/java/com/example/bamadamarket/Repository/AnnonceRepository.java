package com.example.bamadamarket.Repository;

import com.example.bamadamarket.Model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    public Annonce findByIdAnnonce(long id);

    List<Annonce> findByUtilisateurIdUtilisateur(Long id);
}
