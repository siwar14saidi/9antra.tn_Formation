package tn.antraFormationSpringBoot.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.antraFormationSpringBoot.entites.Formateur;

@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {
	  Optional<Formateur> findById(Long id);
	  Formateur findFById(Long id);
	  Formateur findByEmail(String email);
	   default boolean existsByEmailAndPassword(String email, String password) {
	        Formateur formateur = findByEmail(email);
	        if (formateur != null) {
	            // Comparer le mot de passe déchiffré avec le mot de passe saisi
	            String decodedPassword = new String(Base64.decodeBase64(formateur.getMotDePasse().getBytes()));
	            return decodedPassword.equals(password);
	        }
	        return false;
	    }
	   List<Formateur> findByCinUserAndEmail(int cinUser, String email);

}
