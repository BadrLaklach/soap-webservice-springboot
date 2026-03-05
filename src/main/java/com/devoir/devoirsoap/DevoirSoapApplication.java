package com.devoir.devoirsoap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 *
 * @SpringBootApplication active :
 *                        - @Configuration : scanne les beans annotés
 *                        - @EnableAutoConfiguration : configure automatiquement
 *                        Spring
 *                        - @ComponentScan : détecte les composants
 *                        (@Endpoint, @Repository…)
 *
 *                        Après démarrage, le WSDL est disponible à :
 *                        http://localhost:8080/ws/etudiant.wsdl
 */
@SpringBootApplication
public class DevoirSoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevoirSoapApplication.class, args);
    }
}
