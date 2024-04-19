package jovelAsirot.U5W3D5.repositories;

import jovelAsirot.U5W3D5.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDAO extends JpaRepository<Booking, Long> {
}
