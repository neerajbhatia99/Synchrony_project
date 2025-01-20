package com.neeraj.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("taskExecutor")
    private ExecutorService executorService;

    public CompletableFuture<Integer> saveEmployeeAsync(String name, String email) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "INSERT INTO employees (name, email) VALUES (?, ?)";
            return jdbcTemplate.update(sql, name, email);
        }, executorService);
    }

    public CompletableFuture<List<Map<String, Object>>> getAllEmployeesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT * FROM employees";
            return jdbcTemplate.queryForList(sql);
        }, executorService);
    }
}
