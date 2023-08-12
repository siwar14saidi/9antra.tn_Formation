package tn.antraFormationSpringBoot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Groupe;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
	 Optional<Groupe> findById(Long id);
}
