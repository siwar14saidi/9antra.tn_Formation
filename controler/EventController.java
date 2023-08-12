package tn.antraFormationSpringBoot.controler;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import tn.antraFormationSpringBoot.entites.Event;
import tn.antraFormationSpringBoot.services.EventService;
import tn.antraFormationSpringBoot.services.FileService;

@RestController
@RequestMapping("/Event")
public class EventController {
	String subPath = "event";
    String subPathFile = "event/file";
    String subPathCreator = "event/creator";
    
	@Autowired
	 EventService eventService;
	
	@Autowired
	 FileService serviceFile;
	
	//GetEvent
	 @GetMapping("/all")
	    public List<Event> getEvent(){
	        return eventService.getEvent();
	    }
	 
	 //addEvent
	//@PostMapping("/add")    
	//public Event addEvent (@RequestBody Event event) {
	//	return eventService.addEvent(event);
	//}
	 
	 //addEvent
		@PostMapping("/add")
		public ResponseEntity addEvent(
		        @RequestParam(name = "urlImageEvent", required = false) MultipartFile urlImageEvent,
		        @RequestParam(name = "nomEvent", required = false) String nomEvent,
		        @RequestParam(name = "dateDebutEvent", required = false) Date DateDebutEvent,
		        @RequestParam(name = "dateFinEvent", required = false) Date DateFinEvent,
		        @RequestParam(name = "descrpEvent", required = false) String descrpEvent,
		        @RequestParam(name = "prixEvent", required = false) Double prixEvent,
		        @RequestParam(name = "urlVideoEvent", required = false) MultipartFile UrlVideoEvent) {

		    String imageCreatorName = "";
		    String videoCreatorName = "";
		    
		    if (urlImageEvent != null) {
		        imageCreatorName = serviceFile.saveFile(urlImageEvent, this.subPathCreator);
		    }

		    if (UrlVideoEvent != null) {
		        videoCreatorName = serviceFile.saveFile(UrlVideoEvent, this.subPathCreator);
		    }

		    Event event = new Event();
		    event.setUrlImageEvent(imageCreatorName);
		    event.setNomEvent(nomEvent);
		    event.setDateDebutEvent(DateDebutEvent);
		    event.setDateFinEvent(DateFinEvent);
		    event.setPrixEvent(prixEvent);
		    String videoName = videoCreatorName != null ? videoCreatorName : ""; // Set the video name  to empty string if it is null
		    event.setUrlVideoEvent(videoName); // Append the video name to the videos folder path
		    event.setDescrpEvent(descrpEvent);

		    return ResponseEntity.ok(eventService.addEvent(event));
		}
	
		
		
	

	//UpdateEvent
		 @PutMapping("/{id}/edit")
		 public Event updateEvent(
				    @RequestParam(name = "urlImageEvent", required = false) MultipartFile urlImageEvent,
			        @RequestParam(name = "nomEvent", required = false) String nomEvent,
			        @RequestParam(name = "dateDebutEvent", required = false) Date dateDebutEvent,
			        @RequestParam(name = "dateFinEvent", required = false) Date dateFinEvent,
			        @RequestParam(name = "descrpEvent", required = false) String descrpEvent,
			        @RequestParam(name = "prixEvent", required = false) Double prixEvent,
			        @RequestParam(name = "urlVideoEvent", required = false) MultipartFile urlVideoEvent,
			        @PathVariable("id") Long id)
		 {
			 Optional<Event> event = eventService.getEventById(id);
		        if (event.isEmpty()) {
		            ResponseEntity.badRequest().build();
		        }
		        
		       
		     
		        String imageCreatorName = event.get().getUrlImageEvent();
		        String videoCreatorName = event.get().getUrlVideoEvent();
		        
		        if (urlImageEvent != null) {
		            if (!imageCreatorName.equals("")) {
		                serviceFile.deleteFile(this.subPathCreator + "/" + imageCreatorName);

		            }
		            imageCreatorName = serviceFile.saveFile(urlImageEvent , this.subPathCreator);
		        }
		        if (urlVideoEvent != null) {
		            if (!videoCreatorName.equals("")) {
		                serviceFile.deleteFile(this.subPathCreator + "/" + videoCreatorName);

		            }
		            videoCreatorName = serviceFile.saveFile(urlVideoEvent , this.subPathCreator);
		        }
		        
		        Event newEvent = new Event();
		        newEvent.setUrlImageEvent(imageCreatorName);
		        newEvent.setNomEvent(nomEvent);
		        newEvent.setPrixEvent(prixEvent);
		        newEvent.setDateDebutEvent(dateDebutEvent);
		        newEvent.setDateFinEvent(dateFinEvent);
		        newEvent.setDescrpEvent(descrpEvent);
		        newEvent.setUrlVideoEvent(videoCreatorName);
		        
		        return ResponseEntity.ok(eventService.updateEvent(id ,newEvent)).getBody();
		    }
		 
	 
		 @GetMapping("getEventById/{id}")
		    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
		        Optional<Event> event = eventService.getEventById(id);
		        if (event.isPresent()) {
		            return ResponseEntity.ok(event.get());
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		    }
		//DeletEvent 
		 @DeleteMapping("/{id}/delete")
		 public ResponseEntity<Object> deleteEvent(@PathVariable("id") Long id){
			 Optional<Event> event = eventService.getEventById(id);
		        if (event.isEmpty()) {
		            ResponseEntity.badRequest().build();
		        }
		        String imageCreatorName = event.get().getUrlImageEvent();
		        if (!imageCreatorName.equals("")) {

		            serviceFile.deleteFile(this.subPathCreator + "/" + imageCreatorName);
		        } 
		        String videoCreatorName = event.get().getUrlVideoEvent();
		        if (!videoCreatorName.equals("")) {

		            serviceFile.deleteFile(this.subPathCreator + "/" + videoCreatorName);
		        }
		        eventService.deleteEvent(id);
		        return ResponseEntity.ok().build();
		    }
		 
}
