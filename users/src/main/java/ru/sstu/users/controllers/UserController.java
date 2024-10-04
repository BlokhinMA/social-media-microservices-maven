package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.users.services.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    public ResponseEntity<?> getUserByLogin(@PathVariable String login, @RequestParam("user_login") String userLogin) {
        return ResponseEntity.ok(userService.getUserByLogin(login, userLogin));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam String login) {
        return ResponseEntity.ok(userService.getAll(login));
    }

    @GetMapping("/update_role")
    public ResponseEntity<?> updateRole(@RequestParam int id, @RequestParam String role, @RequestParam String login) {
        return ResponseEntity.ok(userService.updateRole(id, role, login));
    }

}
