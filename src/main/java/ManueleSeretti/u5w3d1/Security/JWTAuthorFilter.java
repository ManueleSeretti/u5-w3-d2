package ManueleSeretti.u5w3d1.Security;

import ManueleSeretti.u5w3d1.Entities.User;
import ManueleSeretti.u5w3d1.Services.UserService;
import ManueleSeretti.u5w3d1.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService usersService;

    @Override
//METODO CHE VIENE CHIAMATO OGNI VOLTA CHE IL CLIENT MANDA UNA RICHIESTA AL SERVER E SERVE PER EFFETTUARE TUTTI I VARI FILTRI
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new UnauthorizedException("per favore passa il Bearer token nell'authorization!");

        } else {
            //VERIFICO IL TOKEN
            String token = authHeader.substring(7);
            jwtTools.verifyToken(token);


            //PRENDO l'USER dall'ID
            String id = jwtTools.extractIdFromToken(token);
            User currentUser = usersService.findById(Integer.parseInt(id));

            //SEGNALO A SPRING SECURITY CHE L'UTENTE HA IL PERSEMMO DI PROCEDERE
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

//passiamo al prossimo filtro d applicare!
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Questo metodo serve per specificare quando il filtro JWTAuthFilter non debba entrare in azione
        // Ad es tutte le richieste al controller /auth/** non devono essere filtrate
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }


}
