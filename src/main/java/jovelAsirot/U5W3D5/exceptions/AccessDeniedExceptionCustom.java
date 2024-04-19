package jovelAsirot.U5W3D5.exceptions;


import org.springframework.security.access.AccessDeniedException;

public class AccessDeniedExceptionCustom extends AccessDeniedException {
    public AccessDeniedExceptionCustom(String message) {
        super(message);
    }
}
