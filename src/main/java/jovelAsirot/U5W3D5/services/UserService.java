package jovelAsirot.U5W3D5.services;


import jovelAsirot.U5W3D5.entities.User;
import jovelAsirot.U5W3D5.exceptions.BadRequestException;
import jovelAsirot.U5W3D5.exceptions.NotFoundException;
import jovelAsirot.U5W3D5.payloads.UserDTO;
import jovelAsirot.U5W3D5.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO uDAO;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.uDAO.findAll(pageable);
    }

    public User save(UserDTO payload) throws BadRequestException {
        this.uDAO.findByEmail(payload.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email: " + user.getEmail() + " is already being used (ᗒᗣᗕ)՞");
                }
        );
        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), payload.role()
        );

        return uDAO.save(newUser);
    }

    public User findById(Long userId) {
        return this.uDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User updateById(Long userId, User updatedUser) {
        User userFound = this.findById(userId);

        this.uDAO.findByEmailAndId(updatedUser.getEmail(), userId).ifPresent(
                employee -> {
                    throw new BadRequestException("The email: " + employee.getEmail() + " is already being used by another user (ᗒᗣᗕ)՞ ");
                }
        );

        userFound.setName(updatedUser.getName());
        userFound.setSurname(updatedUser.getSurname());
        userFound.setEmail(updatedUser.getEmail());

        return this.uDAO.save(userFound);
    }

    public void deleteById(Long userId) {
        User userFound = this.findById(userId);

        this.uDAO.delete(userFound);
    }

    public User findByEmail(String email) {
        return uDAO.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

}
