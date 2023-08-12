package tn.antraFormationSpringBoot.entites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "Manager")
public class Manager implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer cinManager;
	    private String nom;
	    private String prenom;
	    private String email;
	    private Integer numero;
	    private String ville;
	    private String adresse;
	    private boolean etat = false;
	    private String role ="Manager";
	    private String motDePasse;
	    private String nomImage;
}
