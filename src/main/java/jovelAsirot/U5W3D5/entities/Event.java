package jovelAsirot.U5W3D5.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

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

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Event(String title, String description, LocalDate dateTime, String location, int availableSeats) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.availableSeats = availableSeats;

    }
}
