package tn.antraFormationSpringBoot.repository;

import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.antraFormationSpringBoot.entites.Manager;


@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>  {
	  Optional<Manager> findById(Long id);
	  Manager findByEmail(String email);
	   default boolean existsByEmailAndPassword(String email, String password) {
	        Manager manager = findByEmail(email);
	        if (manager != null) {
	            // Comparer le mot de passe déchiffré avec le mot de passe saisi
	            String decodedPassword = new String(Base64.decodeBase64(manager.getMotDePasse().getBytes()));
	            return decodedPassword.equals(password);
	        }
	        return false;
	    }
}
