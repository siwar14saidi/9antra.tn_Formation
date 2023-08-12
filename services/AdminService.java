package tn.antraFormationSpringBoot.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Manager;
import tn.antraFormationSpringBoot.repository.AdminRepository;

@Service
public class AdminService implements IAdminService {
	@Autowired
	private AdminRepository adminRepository;
	private List<Admin> admin; // Déclaration de la variable

	public void createAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	
	public boolean verifyCredentials(String email, String motDePasse) {
        Admin admin = findByEmail(email);
        if (admin != null) {
            // Vérifiez le mot de passe 
            return motDePasse.equals(admin.getMotDePasse());
        }
        return false;
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

	@Override
	public void deleteByEmail(String email) {
		// TODO Auto-generated method stub
		adminRepository.deleteByEmail(email);	
	}

	@Override
	public Admin getAdminByEmail(String email) {
		// TODO Auto-generated method stub
		  return adminRepository.findByEmail(email);
	}


	public Admin verifierAdmin(String email, String motDePasse) {
	    // Rechercher l'apprenant par e-mail dans la base de données
	    Admin admin = adminRepository.findByEmail(email);

        // Vérifier si l'apprenant existe et si le mot de passe correspond
        if (admin != null && admin.getMotDePasse().equals(motDePasse)) {
            return admin; // L'apprenant est trouvé et le mot de passe est correct
        }

	    return null; // Aucun apprenant correspondant trouvé ou mot de passe incorrect
	}

	   @Override
	    public Admin getAdminById(Long id) {
	        return admin.stream()
	                .filter(a -> a.getId().equals(id))
	                .findFirst()
	                .orElseThrow(() -> new RuntimeException("Aucun apprenant trouvé avec l'ID : " + id));
	    }
}
