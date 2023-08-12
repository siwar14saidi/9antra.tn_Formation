package tn.antraFormationSpringBoot.services;

import java.util.List;
import java.util.Optional;

import tn.antraFormationSpringBoot.entites.Event;

public interface IEventService {
	public List<Event> getEvent();
	public Event addEvent(Event event);
	public void deleteEvent(Long id); 
	public Event updateEvent(Long id, Event newEvent);
	public Boolean eventExist(Long id);
	public Optional<Event> getEventById(Long id);

}
