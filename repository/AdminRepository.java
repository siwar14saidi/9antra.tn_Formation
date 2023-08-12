package tn.antraFormationSpringBoot.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
	
			Admin findByEmailAndMotDePasse(String email, String motDePasse);
			Admin save(Admin admin);
			@Modifying
			@Transactional
			void deleteByEmail(String email);
			Admin findByEmail(String email);
			   default boolean existsByEmailAndPassword(String email, String password) {
			        Admin admin = findByEmail(email);
			        if (admin != null) {
			            // Comparer le mot de passe déchiffré avec le mot de passe saisi
			            String decodedPassword = new String(Base64.decodeBase64(admin.getMotDePasse().getBytes()));
			            return decodedPassword.equals(password);
			        }
			        return false;
			    }
				  Optional<Admin> findById(Long id);

}
