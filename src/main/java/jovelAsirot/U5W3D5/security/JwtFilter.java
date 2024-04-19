package jovelAsirot.U5W3D5.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jovelAsirot.U5W3D5.entities.User;
import jovelAsirot.U5W3D5.enums.Role;
import jovelAsirot.U5W3D5.exceptions.AccessDeniedExceptionCustom;
import jovelAsirot.U5W3D5.exceptions.UnauthorizedException;
import jovelAsirot.U5W3D5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Please insert the token");

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        User userFound = this.userService.findById(Long.valueOf(id));

        if (userFound.getRole()  != Role.ORGANIZER) {
            throw new AccessDeniedExceptionCustom("You do not have permission to access this resource");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userFound, null, userFound.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/authorized/**", request.getServletPath());
    }

}
