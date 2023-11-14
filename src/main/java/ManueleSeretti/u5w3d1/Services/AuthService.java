package ManueleSeretti.u5w3d1.Services;

import ManueleSeretti.u5w3d1.Entities.User;
import ManueleSeretti.u5w3d1.Payloads.UserLoginDTO;
import ManueleSeretti.u5w3d1.Security.JWTTools;
import ManueleSeretti.u5w3d1.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    PasswordEncoder Bcrypt;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService userService;

    public String authenticateUser(UserLoginDTO body) {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        User user = userService.findByEmail(body.email());

        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if (Bcrypt.matches(body.password(), user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }


    }
}
