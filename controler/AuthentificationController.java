package tn.antraFormationSpringBoot.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Manager;
import tn.antraFormationSpringBoot.services.AdminService;
import tn.antraFormationSpringBoot.services.ApprenantService;
import tn.antraFormationSpringBoot.services.FormateurService;
import tn.antraFormationSpringBoot.services.ManagerService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthentificationController {
    @Autowired
    private ApprenantService appS;
    
    @Autowired
    private FormateurService formS;
    
    @Autowired
    private ManagerService mangS;
    
    @Autowired
    private AdminService adminS;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String motDePasse = loginRequest.getMotDePasse();
        // Vérifier si les paramètres email et motDePasse sont présents
        if (email.isEmpty() || motDePasse.isEmpty()) {
            return ResponseEntity.badRequest().body("Les paramètres email et motDePasse sont obligatoires.");
        }

        // Vérifier l'utilisateur dans chaque table
        Apprenant apprenant = appS.verifierApprenant(email, motDePasse);
        Manager manager = mangS.verifierManager(email, motDePasse);
        Formateur formateur = formS.verifierFormateur(email, motDePasse);
        Admin administrateur = adminS.verifierAdmin(email, motDePasse);

        if (apprenant != null) {
            if (!apprenant.isEtat()) { // Vérifier si l'état est false
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte d'apprenant désactivé.");
            } else {
                // Rediriger vers la page de profil de l'apprenant
                return ResponseEntity.ok("Connecté en tant qu'apprenant.");
            }
        } else if (manager != null) {
            if (!manager.isEtat()) { // Vérifier si l'état est false
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte de manager désactivé.");
            } else {
                // Rediriger vers la page de profil du manager
                return ResponseEntity.ok("Connecté en tant que manager.");
            }
        } else if (formateur != null) {
            if (!formateur.isEtat()) { // Vérifier si l'état est false
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte de formateur désactivé.");
            } else {
                // Rediriger vers la page de profil du formateur
                return ResponseEntity.ok("Connecté en tant que formateur.");
            }
        } else if (administrateur != null) {
            // Rediriger vers la page de profil de l'administrateur
            return ResponseEntity.ok("Connecté en tant qu'administrateur.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects.");
        }
    }
    public static  class LoginRequest {
        private String email;
        private String motDePasse;

        public LoginRequest() {
        }

        public LoginRequest(String email, String motDePasse) {
            this.email = email;
            this.motDePasse = motDePasse;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMotDePasse() {
            return motDePasse;
        }

        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }
    }
}
