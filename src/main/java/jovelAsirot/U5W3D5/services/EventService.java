package jovelAsirot.U5W3D5.services;

import jovelAsirot.U5W3D5.entities.Event;
import jovelAsirot.U5W3D5.exceptions.NotFoundException;
import jovelAsirot.U5W3D5.payloads.EventDTO;
import jovelAsirot.U5W3D5.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventDAO eDAO;

    public Event save(EventDTO payload) {
        Event event = new Event(payload.title(), payload.description(), payload.date(), payload.location(), payload.availableSeats());

        return eDAO.save(event);
    }

    public Event findById(Long eventId) {
        return this.eDAO.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public Page<Event> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.eDAO.findAll(pageable);
    }

    public Event updateById(Long eventId, Event updatedEvent) {
        Event eventFound = this.findById(eventId);

        eventFound.setTitle(updatedEvent.getTitle());
        eventFound.setDescription(updatedEvent.getDescription());
        eventFound.setDateTime(updatedEvent.getDateTime());
        eventFound.setLocation(updatedEvent.getLocation());
        eventFound.setAvailableSeats(updatedEvent.getAvailableSeats());


        return this.eDAO.save(eventFound);
    }

    public void deleteById(Long eventId) {
        Event eventFound = this.findById(eventId);

        this.eDAO.delete(eventFound);
    }

}
