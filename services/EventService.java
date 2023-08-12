package tn.antraFormationSpringBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.antraFormationSpringBoot.entites.Event;
import tn.antraFormationSpringBoot.repository.EventRepository;

@Service
public class EventService implements IEventService {

	@Autowired
	EventRepository eventRepository;
	
	@Override
	public List<Event> getEvent() {
		// TODO Auto-generated method stub
		return eventRepository.findAll();
	}

	@Override
	public Event addEvent(Event event) {
		// TODO Auto-generated method stub
		return eventRepository.save(event);
	}

	@Override
	public void deleteEvent(Long id) {
		// TODO Auto-generated method stub
		eventRepository.deleteById(id);
	}

	@Override
	public Boolean eventExist(Long id) {
		// TODO Auto-generated method stub
		return eventRepository.existsById(id);
	}


	@Override
	public Optional<Event> getEventById(Long id) {
		// TODO Auto-generated method stub
		return eventRepository.findById(id);
	}

	@Override
	public Event updateEvent(Long id, Event newEvent) {
		// TODO Auto-generated method stub
Event oldEvent = eventRepository.findById(id).get();
		
		if (newEvent.getUrlImageEvent() != null && !newEvent.getUrlImageEvent().isEmpty()) {
			oldEvent.setUrlImageEvent(newEvent.getUrlImageEvent());
	    }

	    if (newEvent.getNomEvent() != null && !newEvent.getNomEvent().isEmpty()) {
	    	oldEvent.setNomEvent(newEvent.getNomEvent());
	    }

	  

	    if (newEvent.getDateDebutEvent() != null) {
	    	oldEvent.setDateDebutEvent(newEvent.getDateDebutEvent());
	    }
	    if (newEvent.getDateFinEvent() != null) {
	    	oldEvent.setDateFinEvent(newEvent.getDateFinEvent());
	    }

	    if (newEvent.getDescrpEvent() != null && !newEvent.getDescrpEvent().isEmpty()) {
	    	oldEvent.setDescrpEvent(newEvent.getDescrpEvent());
	    }
	    
	    if (newEvent.getPrixEvent() != null && newEvent.getPrixEvent() != 0) {
	    	oldEvent.setPrixEvent(newEvent.getPrixEvent());
	    }
	    
	    if (newEvent.getUrlVideoEvent() != null && !newEvent.getUrlVideoEvent().isEmpty()) {
	    	oldEvent.setUrlVideoEvent(newEvent.getUrlVideoEvent());
	    }

        return eventRepository.save(oldEvent);
        
	} 
	
	


}
