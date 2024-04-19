package jovelAsirot.U5W3D5.controllers;

import jovelAsirot.U5W3D5.entities.Event;
import jovelAsirot.U5W3D5.exceptions.BadRequestException;
import jovelAsirot.U5W3D5.payloads.EventDTO;
import jovelAsirot.U5W3D5.payloads.EventResponseDTO;
import jovelAsirot.U5W3D5.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDTO saveEvent(@RequestBody @Validated EventDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new EventResponseDTO(this.eventService.save(payload).getId());
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
        return eventService.updateById(eventId, updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteById(eventId);
    }
    
}
