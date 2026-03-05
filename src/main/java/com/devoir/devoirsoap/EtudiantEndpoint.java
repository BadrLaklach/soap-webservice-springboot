package com.devoir.devoirsoap;

import com.devoir.devoirsoap.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * EtudiantEndpoint — Point d'entrée du Web Service SOAP.
 *
 * Mappe les messages SOAP (classes JAXB générées depuis le XSD)
 * vers les opérations JPA sur la base H2, via EtudiantRepository.
 *
 * Conversion : EtudiantEntity (JPA) ↔ Etudiant (JAXB/SOAP)
 */
@Endpoint
public class EtudiantEndpoint {

    private static final String NAMESPACE_URI = "http://devoir.com/soap/etudiant";

    private final EtudiantRepository repository;

    @Autowired
    public EtudiantEndpoint(EtudiantRepository repository) {
        this.repository = repository;
    }

    // ================================================================
    // Opération 1 : AjouterEtudiant
    // ================================================================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AjouterEtudiantRequest")
    @ResponsePayload
    public AjouterEtudiantResponse ajouterEtudiant(
            @RequestPayload AjouterEtudiantRequest request) {

        // 1. Convertir le DTO SOAP → entité JPA
        var dto = request.getEtudiant();
        var entity = new EtudiantEntity(dto.getNom(), dto.getEmail());

        // 2. Persister dans H2
        var saved = repository.save(entity);

        // 3. Construire la réponse SOAP
        AjouterEtudiantResponse response = new AjouterEtudiantResponse();
        response.setMessage("Étudiant ajouté avec succès. ID attribué : " + saved.getId());
        response.setEtudiant(toDto(saved));
        return response;
    }

    // ================================================================
    // Opération 2 : ConsulterEtudiant (par ID)
    // ================================================================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConsulterEtudiantRequest")
    @ResponsePayload
    public ConsulterEtudiantResponse consulterEtudiant(
            @RequestPayload ConsulterEtudiantRequest request) {

        var entity = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Aucun étudiant trouvé avec l'ID : " + request.getId()));

        ConsulterEtudiantResponse response = new ConsulterEtudiantResponse();
        response.setEtudiant(toDto(entity));
        return response;
    }

    // ================================================================
    // Opération 3 : ListerEtudiants (tous)
    // ================================================================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListerEtudiantsRequest")
    @ResponsePayload
    public ListerEtudiantsResponse listerEtudiants(
            @RequestPayload ListerEtudiantsRequest request) {

        ListerEtudiantsResponse response = new ListerEtudiantsResponse();

        // Récupère toutes les lignes de la table H2 et les convertit en DTO SOAP
        repository.findAll()
                .stream()
                .map(this::toDto)
                .forEach(response.getEtudiant()::add);

        return response;
    }

    // ================================================================
    // Utilitaire : EtudiantEntity → Etudiant (JAXB DTO)
    // ================================================================

    private Etudiant toDto(EtudiantEntity entity) {
        Etudiant dto = new Etudiant();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
