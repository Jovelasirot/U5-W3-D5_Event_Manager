package jovelAsirot.U5W3D5.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jovelAsirot.U5W3D5.entities.Booking;
import jovelAsirot.U5W3D5.entities.User;
import jovelAsirot.U5W3D5.enums.Role;
import jovelAsirot.U5W3D5.exceptions.BadRequestException;
import jovelAsirot.U5W3D5.exceptions.UnauthorizedException;
import jovelAsirot.U5W3D5.payloads.BookingDTO;
import jovelAsirot.U5W3D5.repositories.BookingDAO;
import jovelAsirot.U5W3D5.security.JwtTools;
import jovelAsirot.U5W3D5.services.BookingService;
import jovelAsirot.U5W3D5.services.EventService;
import jovelAsirot.U5W3D5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/book")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDAO bDAO;

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;


    @GetMapping
    public Page<Booking> getAllBooking(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return this.bookingService.getAll(page, size, sortBy);
    }

    @PostMapping("event/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bookEvent(@PathVariable Long eventId, @RequestBody BookingDTO payLoadBooking, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Please insert the token");

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        User userFound = this.userService.findById(Long.valueOf(id));

        if (userFound.getRole() == Role.ORGANIZER) {
            eventService.updateSeats(eventId);
            User userSet = userService.findById(payLoadBooking.userId());
            Booking booking = new Booking(eventService.findById(eventId), userSet);
            bDAO.save(booking);
        } else {
            if (!Objects.equals(userFound.getId(), payLoadBooking.userId())) {
                throw new BadRequestException("You can only book for yourself");
            } else {
                User userSet = userService.findById(payLoadBooking.userId());
                eventService.updateSeats(eventId);
                Booking booking = new Booking(eventService.findById(eventId), userSet);
                bDAO.save(booking);
            }
        }
    }
}
