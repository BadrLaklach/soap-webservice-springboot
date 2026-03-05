package com.devoir.devoirsoap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * EtudiantRepository — Interface Spring Data JPA.
 *
 * Spring génère automatiquement l'implémentation (findAll, findById, save…)
 * basée sur la table H2 "etudiant".
 * Plus besoin de liste statique ni de code CRUD manuel.
 */
@Repository
public interface EtudiantRepository extends JpaRepository<EtudiantEntity, Integer> {
    // Les méthodes CRUD de base (findAll, findById, save, deleteById…)
    // sont héritées directement de JpaRepository — aucun code supplémentaire
    // requis.
}
