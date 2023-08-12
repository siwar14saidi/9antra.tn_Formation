package tn.antraFormationSpringBoot.entites;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "Groupe")
public class Groupe implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	    private String nomGroupe;
	    private String cours;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date dateDebut;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date dateFin;
	    
	    
	    
	  /*  @JsonIgnore
	    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
	    private List<Apprenant> apprenants;*/
	   
	    
	    @JsonIgnore
	    @OneToMany(mappedBy = "groupe", fetch = FetchType.LAZY)
	    private List <Apprenant> apprenant;
	
	  
	
	  

	   /* @ManyToMany(mappedBy = "apprenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)*/

	    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "formateur_id" , referencedColumnName = "id")
	    @OnDelete(action = OnDeleteAction.CASCADE)

	    private Formateur formateur;
	   /* @JsonIgnore
	    @OneToMany
	    @JoinColumn(name = "formateur_id")
	    private Formateur formateur;*/
}
