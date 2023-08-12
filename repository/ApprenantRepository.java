package tn.antraFormationSpringBoot.repository;



import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;

@Repository
public interface ApprenantRepository extends JpaRepository<Apprenant, Long>{
	  //Apprenant findByCinUser(Integer cinUser);
	  Optional<Apprenant> findById(Long id);
	  Apprenant findByCinUser(int cinUser);
	  Apprenant findByEmail(String email);
	   default boolean existsByEmailAndPassword(String email, String password) {
	        Apprenant apprenant = findByEmail(email);
	        if (apprenant != null) {
	            // Comparer le mot de passe déchiffré avec le mot de passe saisi
	            String decodedPassword = new String(Base64.decodeBase64(apprenant.getMotDePasse().getBytes()));
	            return decodedPassword.equals(password);
	        }
	        return false;
	    }
	   List<Apprenant> findByCinUserAndEmail(int cinUser, String email);

}
