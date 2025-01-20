package com.neeraj.spring.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neeraj.spring.services.RedisService;
import com.neeraj.spring.services.EmployeeService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private EmployeeService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/user")
    public CompletableFuture<String> saveUser(@RequestBody Map<String, String> userData) {
        String name = userData.get("name");
        String email = userData.get("email");
        return userService.saveEmployeeAsync(name, email)
                .thenCompose(result -> redisService.saveAsync(String.format("user: %s", name), email, 3600))
                .thenApply(v -> "User saved successfully!");
    }

    @GetMapping("/users")
    public CompletableFuture<List<Map<String, Object>>> getAllUsers() {
        return userService.getAllEmployeesAsync();
    }

    @GetMapping("/cache/{key}")
    public CompletableFuture<Object> getCachedValue(@PathVariable String key) {
        return redisService.getAsync(key);
    }
}
