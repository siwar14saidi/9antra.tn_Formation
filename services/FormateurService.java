package tn.antraFormationSpringBoot.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.repository.FormateurRepository;


@Service
public class FormateurService implements IFormateurService {

	
	@Autowired
	FormateurRepository formateurRepository;

    private List<Formateur> formateur; // Déclaration de la variable

	@Override
	public String getMotDePasseNonCrypte(Long id) {
	    Optional<Formateur> formateur = formateurRepository.findById(id);
	    if (formateur.isEmpty() || formateur.get().getMotDePasse() == null) {
	        return null;
	    }
	    byte[] decodedBytes = Base64.decodeBase64(formateur.map(Formateur::getMotDePasse).get());
	    try {
	        return new String(decodedBytes, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<Formateur> getFormateur() {
		// TODO Auto-generated method stub
		return formateurRepository.findAll();
	}

	@Override
	public Optional<Formateur> getFormateurById(Long id) {
		// TODO Auto-generated method stub
		return formateurRepository.findById(id);
	}
	@Override
	public Formateur getFById(Long id) {
		// TODO Auto-generated method stub
		return formateurRepository.findFById(id);
	}
	@Override
	public Formateur createFormateur(Formateur formateur) {
	    formateur.setRole("formateur");
	    formateur.setEtat(false);

	    // Liste d'URLs d'images
	    List<String> imageUrls = Arrays.asList( 
	        "/upload/images/formateur/photoDeProfil1.jpg",
	        "/upload/images/formateur/photoDeProfil2.jpg",
	        "/upload/images/formateur/photoDeProfil3.jpg",
	        "/upload/images/formateur/photoDeProfil4.jpg",
	        "/upload/images/formateur/photoDeProfil5.jpg",
	        "/upload/images/formateur/photoDeProfil6.jpg"
	    );

	    // Génération d'un index aléatoire
	    Random random = new Random();
	    int randomIndex = random.nextInt(imageUrls.size());
	    String randomImageUrl = imageUrls.get(randomIndex);

	    formateur.setNomImage(randomImageUrl);
	    Formateur formateurEnregistre = formateurRepository.save(formateur);

	    String cheminAbsoluImage = "src/main/resources/static" + randomImageUrl;

	    // Copier l'image de profil dans le chemin d'accès absolu
	    Path pathSource = Paths.get(cheminAbsoluImage);
	    InputStream inputStream = getClass().getResourceAsStream("/static" + randomImageUrl);
	    try {
	        Files.copy(inputStream, pathSource, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    String password = generateRandomPassword();
	    String encodedPassword = Base64.encodeBase64String(password.getBytes());

	    formateur.setMotDePasse(encodedPassword);
	    return formateurRepository.save(formateur);
	}


	public String generateRandomPassword() {
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    int length = 8;
	    for (int i = 0; i < length; i++) {
	        int index = random.nextInt(chars.length());
	        sb.append(chars.charAt(index));
	    }
	    String password = sb.toString();
	    return password;
	}

	
	
@Override
	public Formateur editFormateur(Long id, @RequestPart Formateur newFormateur) {
	   // Vérifier que newFormateur n'est pas null
	     Objects.requireNonNull(newFormateur, "newFormateur cannot be null");
	
	    // Chercher le formateur à modifier par son ID
	    Optional<Formateur> optionalFormateur = formateurRepository.findById(id);
	    if (optionalFormateur.isPresent()) {
	        Formateur formateur = optionalFormateur.get();

	        // Récupérer le formateur existant dans la base de données
	        Formateur existingFormateur = formateurRepository.findById(formateur.getId()).orElseThrow(() -> new RuntimeException("Formateur not found with ID: " + formateur.getId()));

	        // Modifier les propriétés du formateur avec les nouvelles valeurs
	        BeanUtils.copyProperties(newFormateur, formateur, "id", "motDePasse", "cinUser", "nomImage", "cv");

	        // Modifier le mot de passe si nécessaire
	        if (newFormateur.getMotDePasse() != null) {
	            String encodedPassword = Base64.encodeBase64String(newFormateur.getMotDePasse().getBytes());
	            formateur.setMotDePasse(encodedPassword);
	        }

	        // Enregistrer les modifications dans la base de données 
	        Formateur updatedFormateur = formateurRepository.save(formateur);
	        System.out.println("Les modifications ont été enregistrées dans la base de données : " + updatedFormateur);
	        return updatedFormateur;
	    } else {
	        // Lever une exception si le formateur n'existe pas
	        throw new RuntimeException("Formateur not found with ID: " + id);
	    }
	}

	
	
	
	/* @Override
	public Formateur editFormateur(Long id, Formateur newFormateur) {
		// TODO Auto-generated method stub
		// Vérifier que newApprenant n'est pas null
	    Objects.requireNonNull(newFormateur, "newApprenant cannot be null");

	    // Chercher l'apprenant à modifier par son ID
	    Optional<Formateur> optionalApprenant = formateurRepository.findById(id);
	    if (optionalApprenant.isPresent()) {
	        Formateur formateur = optionalApprenant.get();
	        
	        // Récupérer l'apprenant existant dans la base de données
	        Formateur existingFormateur = formateurRepository.findById(formateur.getId()).orElseThrow(() -> new RuntimeException("Formateur not found with ID: " + formateur.getId()));

	        // Modifier les propriétés de l'apprenant avec les nouvelles valeurs
	        BeanUtils.copyProperties(newFormateur, formateur, "id", "motDePasse", "cinUser","nomImage","nom","prenom","email","numero","formation","ville","adresse","cv");

	        if (newFormateur.getNom() != null) {
	            formateur.setNom(newFormateur.getNom());
	        }   
	        if (newFormateur.getPrenom() != null) {
	            formateur.setPrenom(newFormateur.getPrenom());
	        }
	        if (newFormateur.getEmail() != null) {
	            formateur.setEmail(newFormateur.getEmail());
	        }
	        if (newFormateur.getNumero() != null) {
	            formateur.setNumero(newFormateur.getNumero());
	        }
	        if (newFormateur.getFormation() != null) {
	            formateur.setFormation(newFormateur.getFormation());
	        }
	        if (newFormateur.getVille() != null) {
	            formateur.setVille(newFormateur.getVille());
	        }
	        if (newFormateur.getAdresse() != null) {
	            formateur.setAdresse(newFormateur.getAdresse());
	        }
	       
	        if (newFormateur.getCv() != null) {
	            formateur.setCv(newFormateur.getCv());
	        }
	        
	        if (newFormateur.getCv() != null && !newFormateur.getCv().isEmpty()) {
		        formateur.setCv(newFormateur.getCv());
		    }

	        // Modifier le mot de passe si nécessaire
	        if (newFormateur.getMotDePasse() != null) {
	            String encodedPassword = Base64.encodeBase64String(newFormateur.getMotDePasse().getBytes());
	            formateur.setMotDePasse(encodedPassword);
	        } 

	        // Enregistrer les modifications dans la base de données
	        Formateur updatedFormateur = formateurRepository.save(formateur);
	        System.out.println("Les modifications ont été enregistrées dans la base de données : " + updatedFormateur);
	        return updatedFormateur;
	    } else {
	        // Lever une exception si le formateur n'existe pas
	        throw new RuntimeException("formateur not found with ID: " + newFormateur.getId());
	    }

	}
*/
	

public boolean verifyCredentials(String email, String password) {
    Formateur formateur = findByEmail(email);
    if (formateur != null) {
        // Vérifiez le mot de passe 
        return password.equals(formateur.getMotDePasse());
    }
    return false;
}

public Formateur findByEmail(String email) {
    return formateurRepository.findByEmail(email);
}

@Override
public Formateur getFormById(Long id) {
	// TODO Auto-generated method stub
	return null;
}




private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
public Formateur verifierFormateur(String email, String motDePasse) {
    // Rechercher l'apprenant par e-mail dans la base de données
    Formateur formateur = formateurRepository.findByEmail(email);

    if (formateur != null && decodeBase64(formateur.getMotDePasse()).equals(motDePasse)) {
        return formateur; // L'apprenant est trouvé et le mot de passe est correct
    }

    return null; // Aucun apprenant correspondant trouvé ou mot de passe incorrect
}


private String decodeBase64(String encodedString) {
    byte[] decodedBytes = Base64.decodeBase64(encodedString);
    return new String(decodedBytes);
}
	

}
