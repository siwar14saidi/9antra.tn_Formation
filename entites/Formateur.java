package tn.antraFormationSpringBoot.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Formateur")
public class Formateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cinUser;
	    private String nom;
	    private String prenom;
	    private String email;
	    private Integer numero;
	    private String formation;
	    private String ville;
	    private String adresse;
	    private String cv;
	    private boolean etat = false;
	    private String role ="Formateur";
	    private String motDePasse;
	    private String nomImage;  
	    
	    @JsonIgnore
	    @OneToMany(mappedBy = "formateur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    private List <Groupe> Groupes;
	    /*@JsonIgnore
	    @OneToOne(mappedBy = "formateur")
	    private List <Groupe> groupe;*/
}
