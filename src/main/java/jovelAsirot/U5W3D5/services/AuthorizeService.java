package jovelAsirot.U5W3D5.services;

import jovelAsirot.U5W3D5.entities.User;
import jovelAsirot.U5W3D5.exceptions.UnauthorizedException;
import jovelAsirot.U5W3D5.payloads.UserLoginDTO;
import jovelAsirot.U5W3D5.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateEmployeeAndGenerateToken(UserLoginDTO payload) {

        User user = this.userService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid email or password,  try again (ノಠൠಠ)ノ彡┻━┻.");
        }
    }

}
