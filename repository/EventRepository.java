package tn.antraFormationSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.antraFormationSpringBoot.entites.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
