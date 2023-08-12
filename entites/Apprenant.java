package tn.antraFormationSpringBoot.entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "Apprenant")
public class Apprenant implements Serializable {
	
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
	    private String universiter;
	    private boolean etat = false;
	    private String role ="apprenant";
	    private String motDePasse;
	    private String nomImage;
	    private String lieuF;

	    
	  //  @OneToMany(mappedBy = "apprenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JsonIgnore
	    @ManyToMany
	    @JoinTable(name = "coursapprenant", joinColumns = @JoinColumn(name = "apprenant_id"), inverseJoinColumns = @JoinColumn(name = "cours_id"))
	    private List<Cours> cours;

	  
	 /*  @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name = "groupe_id")
	    private Groupe groupe;*/
	    
	    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "apprenant"}) // Exclure la référence à apprenant
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "groupe" , referencedColumnName = "id")
	   
	    private Groupe groupe;
	    
	  /*  @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "apprenant_grp",
	            joinColumns = @JoinColumn(name = "id_app"),
	            inverseJoinColumns = @JoinColumn(name = "id_grp"))
	    private List<Apprenant> apprenants;*/
	    
	    /*

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(role));
			return authorities;
		}


	    @Override
	    public String getPassword() {
	        return motDePasse;
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }*/
	    private boolean active;
	    
	    // Getter and setter for active field
	    public boolean isActive() {
	        return active;
	    }
	    
	    public void setActive(boolean active) {
	        this.active = active;
	    }
	}



