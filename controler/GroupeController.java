package tn.antraFormationSpringBoot.controler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Groupe;
import tn.antraFormationSpringBoot.repository.FormateurRepository;
import tn.antraFormationSpringBoot.repository.GroupeRepository;
import tn.antraFormationSpringBoot.services.ApprenantService;
import tn.antraFormationSpringBoot.services.EmailService;
import tn.antraFormationSpringBoot.services.FileService;
import tn.antraFormationSpringBoot.services.FormateurService;
import tn.antraFormationSpringBoot.services.GroupeService;

@RestController	
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Groupe")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private ApprenantService apprenantService;

    @Autowired
    private FormateurService formateurService;

    @Autowired
    private FileService fileService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private GroupeRepository groupeRepository;

    @GetMapping("/all")
    public List<Groupe> getGroupes() {
        return groupeService.getGroupe();
    }
    
    @GetMapping("/groupes/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        Groupe groupe = groupeService.getGroupeById(id);
        return ResponseEntity.ok(groupe);
    }


    @PostMapping("/updateGroupe/{id}")
    public Groupe updateGroupe(@PathVariable Long id, @Validated @RequestBody Groupe newGroupe) {
        return groupeService.updateGroupe(id, newGroupe);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupe(@PathVariable Long id) {
        groupeService.deleteGroupe(id);
        return ResponseEntity.ok("Le groupe a été supprimé avec succès");
    }
    
    @PostMapping("/addGroupe")
    public Groupe addGroupe(@Validated @RequestBody Groupe groupe) {

    	return groupeService.addGroupe(groupe);
    }


    @GetMapping("/{groupeId}/apprenants")
    public List<Apprenant> getApprenantsByGroupeId(@PathVariable Long groupeId) {
        return groupeService.getApprenantsByGroupeId(groupeId);
    }


}

