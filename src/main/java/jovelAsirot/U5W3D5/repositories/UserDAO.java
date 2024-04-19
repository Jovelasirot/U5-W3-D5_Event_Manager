package jovelAsirot.U5W3D5.repositories;

import jovelAsirot.U5W3D5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndId(String email, Long userId);
    
}
