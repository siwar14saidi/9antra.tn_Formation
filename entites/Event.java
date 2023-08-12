package tn.antraFormationSpringBoot.entites;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "Event")
public class Event implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;

    private String urlImageEvent;
    private String nomEvent;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebutEvent;
    private Date dateFinEvent;
    
    private String descrpEvent;
    private Double prixEvent;
    private String urlVideoEvent;
    
}
