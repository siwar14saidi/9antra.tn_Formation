package tn.antraFormationSpringBoot.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.repository.AdminRepository;
import tn.antraFormationSpringBoot.services.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard(@RequestParam String email, @RequestParam String motDePasse) {
        try {
            Admin admin = adminRepository.findByEmailAndMotDePasse(email, motDePasse);
            if (admin != null && admin instanceof Admin && admin.getEmail().equals(email) && admin.getMotDePasse().equals(motDePasse)) {
                return ResponseEntity.ok("Bienvenue dans le tableau de bord d'administration!");
            } else {
                // Supprimer la ligne existante avec cet email
                adminRepository.deleteByEmail(email);

                // Insérer la nouvelle ligne
                Admin newAdmin = new Admin(email,motDePasse);
                adminRepository.save(newAdmin);

                return ResponseEntity.ok("Nouvel administrateur créé avec succès!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/details/{id}")
	 public ResponseEntity<Admin> getAdminDetails(@PathVariable Long id) {
	     Admin admin = adminService.getAdminById(id);
	     if (admin != null) { 
	         return ResponseEntity.ok(admin);
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }


}
