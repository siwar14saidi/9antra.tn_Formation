package tn.antraFormationSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.antraFormationSpringBoot.entites.Cours;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

}
