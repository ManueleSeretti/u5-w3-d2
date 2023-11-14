package ManueleSeretti.u5w3d1.Controller;

import ManueleSeretti.u5w3d1.Entities.User;
import ManueleSeretti.u5w3d1.Payloads.newUserDTO;
import ManueleSeretti.u5w3d1.Services.UserService;
import ManueleSeretti.u5w3d1.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UtenteController {
    @Autowired
    private UserService usersService;

    @GetMapping("")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return usersService.getUsers(page, size);
    }

    // 2. POST http://localhost:3001/users (+ body)
    @PostMapping("")
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

    // 3. GET http://localhost:3001/users/:id
    @GetMapping("/{id}")
    public User
    findById(@PathVariable int id) {
        return usersService.findById(id);
    }

    // 4. PUT http://localhost:3001/users/:id (+ body)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User
    findByIdAndUpdate(@PathVariable int id, @RequestBody User
            body) {
        return usersService.findByIdAndUpdate(id, body);
    }

    // 5. DELETE http://localhost:3001/users/:id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable long id) {
        usersService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/me")
    public String uploadExample(@AuthenticationPrincipal User currentUser, @RequestParam("image") MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return usersService.uploadPicture(currentUser.getId(), body);
    }

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }

    ;

    @PutMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal User currentUser, @RequestBody User body) {
        return usersService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void getProfile(@AuthenticationPrincipal User currentUser) {
        usersService.findByIdAndDelete(currentUser.getId());
    }


}
