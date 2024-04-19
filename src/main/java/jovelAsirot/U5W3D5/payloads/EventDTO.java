package jovelAsirot.U5W3D5.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EventDTO(@NotEmpty(message = "The event title is required")
                       @Size(min = 2, max = 20, message = "The title can't be less than two characters and more than 20 characters")
                       String title,
                       @NotEmpty(message = "The event description is required")
                       @Size(min = 2, max = 100, message = "The description can't be less than two characters and more than 100 characters")
                       String description,
                       @NotEmpty(message = "The date is required")
                       LocalDate date,
                       @NotEmpty(message = "The location is required")
                       String location,
                       @NotEmpty(message = "Specify the available seats")
                       int availableSeats) {
}
