package tn.antraFormationSpringBoot;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.services.AdminService;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "tn.antraFormationSpringBoot") // Specify the base package to scan for components
public class Application implements WebMvcConfigurer{

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        AdminService adminService = applicationContext.getBean(AdminService.class);
        Admin existingAdmin = adminService.getAdminByEmail("admin@gmail.com");
        if (existingAdmin == null) {
            Admin admin = new Admin(null, "admin", "admin@gmail.com", "administrateur");
            adminService.createAdmin(admin);
        } else {
            System.out.println("Un administrateur existe déjà avec cette adresse e-mail.");
        }
    }

 
   
  @Bean
     public WebMvcConfigurer corsConfigurer() {
    
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }
   
 /*   public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/login/login")
            .allowedOrigins("http://localhost:4200") // Autoriser les requêtes depuis ce domaine
            .allowedMethods("POST") // Autoriser uniquement les méthodes POST
            .allowedHeaders("*"); // Autoriser tous les en-têtes
    }*/
}

