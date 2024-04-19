package jovelAsirot.U5W3D5.repositories;

import jovelAsirot.U5W3D5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event, Long> {
}
