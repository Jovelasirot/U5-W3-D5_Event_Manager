package jovelAsirot.U5W3D5.controllers;

import jovelAsirot.U5W3D5.exceptions.BadRequestException;
import jovelAsirot.U5W3D5.payloads.UserDTO;
import jovelAsirot.U5W3D5.payloads.UserLoginDTO;
import jovelAsirot.U5W3D5.payloads.UserLoginResponseDTO;
import jovelAsirot.U5W3D5.payloads.UserResponseDTO;
import jovelAsirot.U5W3D5.services.AuthorizeService;
import jovelAsirot.U5W3D5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorized")
public class AuthorizeController {

    @Autowired
    private AuthorizeService authorizeService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO loginEmployee(@RequestBody UserLoginDTO payload) {
        return new UserLoginResponseDTO(this.authorizeService.authenticateEmployeeAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerEmployee(@RequestBody @Validated UserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new UserResponseDTO(this.userService.save(body).getId());
    }
    
}
