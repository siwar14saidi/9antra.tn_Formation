package tn.antraFormationSpringBoot.services;

import java.io.IOException;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.service.spi.InjectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.PersistenceContext;
import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.repository.ApprenantRepository;


@Service
public class ApprenantService implements IApprenantService{
	
	@Autowired
     ApprenantRepository appRepository;

	
    private List<Apprenant> apprenant; // Déclaration de la variable

	//@Override
	//public Apprenant addApprenant(Apprenant apprenant) {
		// TODO Auto-generated method stub
	//	return appRepository.save(apprenant);
	//}

	
	
	
	
	
	/*
	@Override
	public Apprenant createApprenant(Apprenant apprenant) {
		// TODO Auto-generated method stub
		  apprenant.setRole("apprenant");
	        apprenant.setEtat(false);
	        
	        String nomImage = "photoDeProfil.jpg";
	        String cheminImage = "/upload/images/apprenant/" + nomImage;
	        apprenant.setNomImage(cheminImage);
	        Apprenant apprenantEnregistre = appRepository.save(apprenant);

	        // Chemin d'accès absolu de l'image de profil
	        String cheminAbsoluImage = "src/main/resources/static" + cheminImage;

	        // Copier l'image de profil dans le chemin d'accès absolu
	        Path pathSource = Paths.get(cheminAbsoluImage);
	        InputStream inputStream = getClass().getResourceAsStream("/static" + cheminImage);
	        try {
	            Files.copy(inputStream, pathSource, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    	String password = generateRandomPassword();
	    	String encodedPassword = Base64.encodeBase64String(password.getBytes());

	    	apprenant.setMotDePasse(encodedPassword);
	        return appRepository.save(apprenant);
	}
	
	
*/
    
	


    @Override
    public Apprenant createApprenant(Apprenant apprenant) {
        // Récupérer le cin et l'email de l'apprenant
        Integer cinuser = apprenant.getCinUser();
        String email = apprenant.getEmail();

        // Récupérer tous les apprenants
        List<Apprenant> allApprenants = appRepository.findAll();

        // Récupérer les apprenants ayant le même cin et le même email
        List<Apprenant> existingApprenants = allApprenants.stream()
                .filter(a -> Objects.equals(a.getCinUser(), cinuser) && Objects.equals(a.getEmail(), email))
                .collect(Collectors.toList());

        if (!existingApprenants.isEmpty()) {
            // L'apprenant existe déjà, enregistrer la nouvelle inscription avec la même image et le même mot de passe
            for (Apprenant existingApprenant : existingApprenants) {
                if (existingApprenant.getEmail().equals(email)) {
                    apprenant.setId(null); // Assurez-vous que l'ID est null pour créer une nouvelle inscription
                    apprenant.setMotDePasse(existingApprenant.getMotDePasse()); // Utilisez le même mot de passe
                    apprenant.setNomImage(existingApprenant.getNomImage()); // Utilisez la même URL d'image
                    break;
                }
            }
        } else {
            // L'apprenant n'existe pas encore, créez-le
            apprenant.setRole("apprenant");
            apprenant.setEtat(false);
            
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
            
            String nomImage;
            String cheminImage;

            if (apprenant.getNomImage() != null && !apprenant.getNomImage().isEmpty()) {
                // Utilisez l'image fournie par l'apprenant
                nomImage = apprenant.getNomImage();
                cheminImage = "/upload/images/apprenant/" + nomImage;
            } else {
                cheminImage = randomImageUrl;
            }

            apprenant.setNomImage(cheminImage);
            Apprenant apprenantEnregistre = appRepository.save(apprenant);

            // Chemin d'accès absolu de l'image de profil
            String cheminAbsoluImage = "src/main/resources/static" + cheminImage;

            // Copier l'image de profil dans le chemin d'accès absolu
            try {
                InputStream inputStream = getClass().getResourceAsStream("/static" + cheminImage);

                if (inputStream != null) {
                    Path directory = Paths.get("src/main/resources/static/upload/images/apprenant");
                    Files.createDirectories(directory);
                    Path pathDestination = Paths.get(cheminAbsoluImage);
                    Files.copy(inputStream, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    // Gérer le cas où la ressource n'est pas chargée correctement
                    System.out.println("Erreur : Impossible de charger l'image.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Générer un mot de passe aléatoire uniquement si l'apprenant n'a pas déjà fourni un mot de passe
            if (apprenantEnregistre.getMotDePasse() == null) {
                String password = generateRandomPassword();
                String encodedPassword = Base64.encodeBase64String(password.getBytes());
                apprenantEnregistre.setMotDePasse(encodedPassword);
                apprenantEnregistre = appRepository.save(apprenantEnregistre);
            }

            return apprenantEnregistre;
        }

        // Enregistrer l'apprenant dans la base de données
        return appRepository.save(apprenant);
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
	public String getMotDePasseNonCrypte(Long id) {
	    Optional<Apprenant> apprenant = appRepository.findById(id);
	    if (apprenant.isEmpty() || apprenant.get().getMotDePasse() == null) {
	        return null;
	    }
	    byte[] decodedBytes = Base64.decodeBase64(apprenant.map(Apprenant::getMotDePasse).get());
	    try {
	        return new String(decodedBytes, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return null;
	    }
	} 
	

	@Override
	public List<Apprenant> getApprenant() {
		// TODO Auto-generated method stub
		return appRepository.findAll();
	}

	/*@Override
	public Apprenant editApprenant(Long id, Apprenant newApprenant){
	    // Vérifier que newApprenant n'est pas null
	    Objects.requireNonNull(newApprenant, "newApprenant cannot be null");

	    // Chercher l'apprenant à modifier par son ID
	    Optional<Apprenant> optionalApprenant = appRepository.findById(id);
	    if (optionalApprenant.isPresent()) {
	        Apprenant apprenant = optionalApprenant.get();
	        
	        // Récupérer l'apprenant existant dans la base de données
	        Apprenant existingApprenant = appRepository.findById(apprenant.getId()).orElseThrow(() -> new RuntimeException("Apprenant not found with ID: " + apprenant.getId()));

	        // Modifier les propriétés de l'apprenant avec les nouvelles valeurs
	        BeanUtils.copyProperties(newApprenant, apprenant, "id", "motDePasse", "cinUser","nomImage","nom","prenom","email","numero","formation","ville","adresse","universiter","lieuF");

	        if (newApprenant.getNom() != null) {
	            apprenant.setNom(newApprenant.getNom());
	        }   
	        if (newApprenant.getPrenom() != null) {
	            apprenant.setPrenom(newApprenant.getPrenom());
	        }
	        if (newApprenant.getEmail() != null) {
	            apprenant.setEmail(newApprenant.getEmail());
	        }
	        if (newApprenant.getNumero() != null) {
	            apprenant.setNumero(newApprenant.getNumero());
	        }
	        if (newApprenant.getFormation() != null) {
	            apprenant.setFormation(newApprenant.getFormation());
	        }
	        if (newApprenant.getVille() != null) {
	            apprenant.setVille(newApprenant.getVille());
	        }
	        if (newApprenant.getAdresse() != null) {
	            apprenant.setAdresse(newApprenant.getAdresse());
	        }
	        if (newApprenant.getUniversiter() != null) {
	            apprenant.setUniversiter(newApprenant.getUniversiter());
	        }
	        if (newApprenant.getLieuF() != null) {
	            apprenant.setLieuF(newApprenant.getLieuF());
	        }

	        // Modifier le mot de passe si nécessaire
	        if (newApprenant.getMotDePasse() != null) {
	            String encodedPassword = Base64.encodeBase64String(newApprenant.getMotDePasse().getBytes());
	            apprenant.setMotDePasse(encodedPassword);
	        } 

	        // Enregistrer les modifications dans la base de données
	        Apprenant updatedApprenant = appRepository.save(apprenant);
	        System.out.println("Les modifications ont été enregistrées dans la base de données : " + updatedApprenant);
	        return updatedApprenant;
	    } else {
	        // Lever une exception si l'apprenant n'existe pas
	        throw new RuntimeException("Apprenant not found with ID: " + newApprenant.getId());
	    }
	}
*/
	@Override
public Apprenant editAppgrp(Long id, Apprenant newApprenant) {
    Optional<Apprenant> optionalApprenant = appRepository.findById(id);
    if (optionalApprenant.isPresent()) {
        Apprenant apprenant = optionalApprenant.get();
        apprenant.setGroupe(newApprenant.getGroupe());
        return appRepository.save(apprenant);
    } else {
        throw new IllegalArgumentException("Apprenant introuvable avec l'ID : " + id);
    }
}

	
	
	@Override
	public Apprenant editApprenant(Long id, Apprenant newApprenant) {
	    // Vérifier que newApprenant n'est pas null
	    Objects.requireNonNull(newApprenant, "newApprenant cannot be null");

	    // Chercher l'apprenant à modifier par son ID
	    Optional<Apprenant> optionalApprenant = appRepository.findById(id);
	    if (optionalApprenant.isPresent()) {
	        Apprenant apprenant = optionalApprenant.get();
	        
	        // Récupérer le cin et l'email de l'apprenant
	        Integer cinuser = apprenant.getCinUser();
	        String email = apprenant.getEmail();

	        // Récupérer tous les apprenants
	        List<Apprenant> allApprenants = appRepository.findAll();

	        // Récupérer les apprenants ayant le même cin et le même email
	        List<Apprenant> apprenantsWithSameCinAndEmail = allApprenants.stream()
	            .filter(a -> Objects.equals(a.getCinUser(), cinuser) && Objects.equals(a.getEmail(), email))
	            .collect(Collectors.toList());

	        // Modifier les propriétés de chaque apprenant avec les nouvelles valeurs
	        for (Apprenant existingApprenant : apprenantsWithSameCinAndEmail) {
	            if (newApprenant.getNom() != null) {
	                existingApprenant.setNom(newApprenant.getNom());
	            }   
	            if (newApprenant.getPrenom() != null) {
	                existingApprenant.setPrenom(newApprenant.getPrenom());
	            }
	            if (newApprenant.getEmail() != null) {
	                existingApprenant.setEmail(newApprenant.getEmail());
	            }
	            if (newApprenant.getNumero() != null) {
	                existingApprenant.setNumero(newApprenant.getNumero());
	            }
	            if (newApprenant.getVille() != null && existingApprenant.getId().equals(id)) {
	                existingApprenant.setVille(newApprenant.getVille());
	            }
	            if (newApprenant.getAdresse() != null) {
	                existingApprenant.setAdresse(newApprenant.getAdresse());
	            }
	            if (newApprenant.getUniversiter() != null) {
	                existingApprenant.setUniversiter(newApprenant.getUniversiter());
	            }
	            if (newApprenant.getMotDePasse() != null) {
	                String encodedPassword = Base64.encodeBase64String(newApprenant.getMotDePasse().getBytes());
	                existingApprenant.setMotDePasse(encodedPassword);
	            }

	            // Enregistrer les modifications dans la base de données
	            appRepository.save(existingApprenant);
	        }

	        System.out.println("Les modifications ont été enregistrées dans la base de données pour les apprenants avec le même cin et le même email.");
	        return apprenant;
	    } else {
	        // Lever une exception si l'apprenant n'existe pas
	        throw new RuntimeException("Apprenant not found with ID: " + newApprenant.getId());
	    }
	}



	@Override
	public Apprenant getApprenantById(List<Apprenant> apprenants, Long id) {
	    return apprenants.stream()
	            .filter(apprenant -> apprenant.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Aucun apprenant trouvé avec l'ID : " + id));
	}
	@Override
	public Long getAById(List<Apprenant> apprenants, Long id) {
	    Map<Long, Apprenant> apprenantMap = apprenants.stream()
	            .collect(Collectors.toMap(Apprenant::getId, Function.identity()));
	    
	    if (apprenantMap.containsKey(id)) {
	        return id;
	    } else {
	        throw new RuntimeException("Aucun apprenant trouvé avec l'ID : " + id);
	    }
	}

	@Override
	public Apprenant getApprenantByCin(List<Apprenant> apprenants, int cinUser) {
	    Optional<Apprenant> optionalApprenant = apprenants.stream()
	            .filter(apprenant -> apprenant.getCinUser() == cinUser)
	            .findFirst();
	    return optionalApprenant.orElse(null);
	}








	public boolean verifyCredentials(String email, String password) {
        Apprenant apprenant = findByEmail(email);
        if (apprenant != null) {
            // Vérifiez le mot de passe 
            return password.equals(apprenant.getMotDePasse());
        }
        return false;
    }

    public Apprenant findByEmail(String email) {
        return appRepository.findByEmail(email);
    }

    @Override
    public Apprenant getAppById(Long id) {
        return apprenant.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun apprenant trouvé avec l'ID : " + id));
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public Apprenant verifierApprenant(String email, String motDePasse) {
        // Rechercher l'apprenant par e-mail dans la base de données
        Apprenant apprenant = appRepository.findByEmail(email);

     // Vérifier si l'apprenant existe et si le mot de passe correspond
        if (apprenant != null && decodeBase64(apprenant.getMotDePasse()).equals(motDePasse)) {
            return apprenant; // L'apprenant est trouvé et le mot de passe est correct
        }

        return null; // Aucun apprenant correspondant trouvé ou mot de passe incorrect
    }

    private String decodeBase64(String encodedString) {
	    byte[] decodedBytes = Base64.decodeBase64(encodedString);
	    return new String(decodedBytes);
	}
  	
	}


