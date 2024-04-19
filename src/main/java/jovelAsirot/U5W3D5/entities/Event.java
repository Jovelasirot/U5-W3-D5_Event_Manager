package jovelAsirot.U5W3D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate dateTime;

    private String location;

    private int availableSeats;

    public Event(String title, String description, LocalDate dateTime, String location, int availableSeats) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.availableSeats = availableSeats;
    }
}
