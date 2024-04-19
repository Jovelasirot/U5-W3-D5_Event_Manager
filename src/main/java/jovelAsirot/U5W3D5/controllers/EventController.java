package jovelAsirot.U5W3D5.controllers;

import jovelAsirot.U5W3D5.entities.Booking;
import jovelAsirot.U5W3D5.entities.Event;
import jovelAsirot.U5W3D5.exceptions.BadRequestException;
import jovelAsirot.U5W3D5.payloads.BookingDTO;
import jovelAsirot.U5W3D5.payloads.EventDTO;
import jovelAsirot.U5W3D5.payloads.EventResponseDTO;
import jovelAsirot.U5W3D5.repositories.BookingDAO;
import jovelAsirot.U5W3D5.services.EventService;
import jovelAsirot.U5W3D5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private UserService userService;

    @Autowired
    private BookingDAO bDAO;

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDTO saveEvent(@RequestBody @Validated EventDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new EventResponseDTO(this.eventService.save(payload).getId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Page<Event> getALlEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventService.getAll(page, size, sortBy);
    }


    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody EventDTO updatedEvent) {
        return eventService.updateById(eventId, updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteById(eventId);
    }

    @PostMapping("/{eventId}/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void bookEvent(@PathVariable Long eventId, @RequestBody BookingDTO payLoadBooking) {

        eventService.updateSeats(eventId);

        Booking booking = new Booking(eventService.findById(eventId), userService.findById(payLoadBooking.userId()));
        bDAO.save(booking);
    }


}
