package ManueleSeretti.u5w3d1.Controller;

import ManueleSeretti.u5w3d1.Entities.User;
import ManueleSeretti.u5w3d1.Payloads.UserLoginDTO;
import ManueleSeretti.u5w3d1.Payloads.UserLoginSuccessDTO;
import ManueleSeretti.u5w3d1.Payloads.newUserDTO;
import ManueleSeretti.u5w3d1.Services.AuthService;
import ManueleSeretti.u5w3d1.Services.UserService;
import ManueleSeretti.u5w3d1.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService usersService;

    @PostMapping("/login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body) {

        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public User saveUser(@RequestBody @Validated newUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return usersService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
