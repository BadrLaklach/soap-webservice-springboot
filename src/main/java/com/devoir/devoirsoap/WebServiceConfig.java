package com.devoir.devoirsoap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * WebServiceConfig — Configuration principale du Web Service SOAP.
 *
 * @EnableWs : active le support Spring Web Services.
 * @Configuration: les beans définis ici sont enregistrés dans le contexte
 *                 Spring.
 *
 *                 Points clés :
 *                 - MessageDispatcherServlet intercepte toutes les requêtes
 *                 SOAP sur /ws/*
 *                 - DefaultWsdl11Definition génère automatiquement le WSDL à
 *                 partir du XSD
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    // ================================================================
    // 1. Servlet SOAP — intercepte les requêtes HTTP sur /ws/*
    // ================================================================

    /**
     * Enregistre le MessageDispatcherServlet pour qu'il traite toutes
     * les requêtes arrivant sur /ws/*.
     *
     * transformWsdlLocations = true → les URLs dans le WSDL généré
     * s'adaptent automatiquement à l'URL du serveur.
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        // Toutes les requêtes /ws/* seront traitées par ce servlet
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // ================================================================
    // 2. Définition WSDL — générée automatiquement depuis le XSD
    // ================================================================

    /**
     * DefaultWsdl11Definition crée le WSDL à la volée en lisant le XSD.
     *
     * Conventions de nommage (suffixes Request/Response dans le XSD) :
     * - portTypeName : nom du portType dans le WSDL
     * - locationUri : URL d'accès au service
     * - targetNamespace: doit correspondre exactement au namespace du XSD
     *
     * Le WSDL sera accessible à :
     * http://localhost:8080/ws/etudiant.wsdl
     *
     * @Bean("etudiant") → le nom du bean détermine l'URL du WSDL
     */
    @Bean(name = "etudiant")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema etudiantSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EtudiantPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://devoir.com/soap/etudiant");
        wsdl11Definition.setSchema(etudiantSchema);
        return wsdl11Definition;
    }

    // ================================================================
    // 3. Schéma XSD — chargé depuis le classpath (src/main/resources)
    // ================================================================

    /**
     * Charge le fichier etudiant.xsd depuis src/main/resources
     * et l'expose comme un bean XsdSchema réutilisable.
     */
    @Bean
    public XsdSchema etudiantSchema() {
        return new SimpleXsdSchema(new ClassPathResource("etudiant.xsd"));
    }
}
