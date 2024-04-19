package jovelAsirot.U5W3D5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EventDTO(@NotEmpty(message = "The event title is required")
                       @Size(min = 2, max = 20, message = "The title can't be less than two characters and more than 20 characters")
                       String title,
                       @NotEmpty(message = "The event description is required")
                       @Size(min = 2, max = 100, message = "The description can't be less than two characters and more than 100 characters")
                       String description,
                       @NotEmpty(message = "The date is required")
                       @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date pattern YYYY-MM-DD")
                       String date,
                       @NotEmpty(message = "The location is required")
                       String location,
                       @NotNull(message = "Specify the available seats")
                       int availableSeats) {
}
