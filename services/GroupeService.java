package tn.antraFormationSpringBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Groupe;
import tn.antraFormationSpringBoot.repository.ApprenantRepository;
import tn.antraFormationSpringBoot.repository.GroupeRepository;

@Service
public class GroupeService implements IGroupeService {

    @Autowired
    private GroupeRepository groupeRepository;
  
    @Autowired
    private ApprenantRepository appRepp;
  
    @Override
    public List<Groupe> getGroupe() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe addGroupe(Groupe groupe) {
    	System.out.println(groupe.getFormateur());
        return groupeRepository.save(groupe);
    }

    @Override
    public Groupe updateGroupe(Long groupeId, Groupe newGroupe) {
        Groupe existingGroupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new IllegalArgumentException("Groupe non trouvé avec l'ID: " + groupeId));

        // Mettre à jour les propriétés du groupe existant avec les nouvelles valeurs
        existingGroupe.setNomGroupe(newGroupe.getNomGroupe());
        existingGroupe.setCours(newGroupe.getCours());
        existingGroupe.setDateDebut(newGroupe.getDateDebut());
        existingGroupe.setDateFin(newGroupe.getDateFin());
        existingGroupe.setFormateur(newGroupe.getFormateur());
        existingGroupe.setApprenant(newGroupe.getApprenant());
        // Ajouter d'autres propriétés à mettre à jour selon votre modèle de données

        // Enregistrer les modifications dans la base de données
        return groupeRepository.save(existingGroupe);}

    @Override
    public Groupe getGroupeById(Long id) {
        List<Groupe> groupes = groupeRepository.findAll();

        Optional<Groupe> groupeOptional = groupes.stream()
                .filter(groupe -> groupe.getId().equals(id))
                .findFirst();
        return groupeOptional.orElseThrow(() -> new RuntimeException("Aucun groupe trouvé avec l'ID : " + id));
    }
    
    public void deleteGroupe(Long id) {
        Groupe groupe = groupeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Groupe non trouvé avec l'ID: " + id));

        // Dissocier les apprenants du groupe sans les supprimer
        groupe.getApprenant().forEach(apprenant -> apprenant.setGroupe(null));

        groupeRepository.save(groupe); // Sauvegarder les modifications pour dissocier les apprenants du groupe

        groupeRepository.deleteById(id); // Supprimer uniquement le groupe
    }
    
    public List<Apprenant> getApprenantsByGroupeId(Long groupeId) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new IllegalArgumentException("Groupe non trouvé avec l'ID: " + groupeId));

        List<Apprenant> apprenants = groupe.getApprenant();
        return apprenants;
    }
   

}

