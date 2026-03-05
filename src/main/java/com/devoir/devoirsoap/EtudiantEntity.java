package com.devoir.devoirsoap;

import jakarta.persistence.*;

/**
 * EtudiantEntity — Entité JPA mappée sur la table H2 "etudiant".
 *
 * Séparée de la classe JAXB générée (com.devoir.devoirsoap.generated.Etudiant)
 * qui sert uniquement à la sérialisation SOAP.
 */
@Entity
@Table(name = "etudiant")
public class EtudiantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    // ---------------------------------------------------------------
    // Constructeurs
    // ---------------------------------------------------------------

    public EtudiantEntity() {
    }

    public EtudiantEntity(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    // ---------------------------------------------------------------
    // Getters / Setters
    // ---------------------------------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
