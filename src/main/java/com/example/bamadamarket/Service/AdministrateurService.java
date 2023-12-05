package com.example.bamadamarket.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bamadamarket.Model.Administrateur;
import com.example.bamadamarket.Repository.AdministrateurRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdministrateurService {
    
        private final AdministrateurRepository administrateurRepository;
        
        @Autowired
        public AdministrateurService(AdministrateurRepository administrateurRepository) {
            this.administrateurRepository = administrateurRepository;
        }

         public Administrateur connectionAdministrateur(String email, String motDePasse) {
        Administrateur user = administrateurRepository.findByEmailAndMotDePasse(email, motDePasse);
        if (user == null) {
            throw new EntityNotFoundException("Cet administrateur n'existe pas");
        }

        return user;
    }
}
