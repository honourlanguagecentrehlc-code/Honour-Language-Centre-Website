package com.example.LoginApp;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", "").trim();
        String password = body.getOrDefault("password", "").trim();
        Map<String, String> res = new HashMap<>();
        if (username.isEmpty() || password.isEmpty()) {
            res.put("status", "error");
            res.put("message", "username and password required");
            return res;
        }
        boolean ok = service.register(username, password);
        if (ok) {
            res.put("status", "ok");
            res.put("message", "REGISTERED — edit users.json to set classType (A or B)");
        } else {
            res.put("status", "error");
            res.put("message", "USERNAME_ALREADY_EXISTS");
        }
        return res;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body, HttpSession session) {
        String username = body.getOrDefault("username", "").trim();
        String password = body.getOrDefault("password", "").trim();
        Map<String, String> res = new HashMap<>();
        if (!service.validateCredentials(username, password)) {
            res.put("status", "error");
            res.put("message", "INVALID_CREDENTIALS");
            return res;
        }
        User u = service.findByUsername(username);
        session.setAttribute("username", u.getUsername());
        session.setAttribute("classType", u.getClassType() == null ? "" : u.getClassType());
        res.put("status", "ok");
        String cls = u.getClassType() == null ? "" : u.getClassType().trim();
        if ("A".equalsIgnoreCase(cls)) {
            res.put("redirect", "/classA.html");
        } else if ("B".equalsIgnoreCase(cls)) {
            res.put("redirect", "/classB.html");
        } else {
            res.put("redirect", "/unassigned.html");
        }
        return res;
    }

    @GetMapping("/session")
    public Map<String, String> session(HttpSession session) {
        Map<String, String> m = new HashMap<>();
        Object username = session.getAttribute("username");
        Object classType = session.getAttribute("classType");
        m.put("username", username == null ? "" : username.toString());
        m.put("classType", classType == null ? "" : classType.toString());
        return m;
    }

    @GetMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> m = new HashMap<>();
        m.put("status", "ok");
        return m;
    }
}
