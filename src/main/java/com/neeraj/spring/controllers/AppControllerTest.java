package com.neeraj.spring.controllers;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import redis.embedded.RedisServer;
@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private RedisService redisService;

    @Test
    void testSaveUser() throws Exception {
        Map<String, String> userData = Map.of("name", "John Doe", "email", "john.doe@example.com");

        // Mocking service calls
        Mockito.doNothing().when(employeeService).saveEmployee("John Doe", "john.doe@example.com");
        Mockito.doNothing().when(edisService).save("John Doe", "john.doe@example.com");

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userData)))
                .andExpect(status().isOk())
                .andExpect(content().string("User saved successfully!"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<Map<String, Object>> mockUsers = List.of(
                Map.of("name", "John Doe", "email", "john.doe@example.com"),
                Map.of("name", "Jane Doe", "email", "jane.doe@example.com")
        );

        // Mocking service call
        when(employeeService.getAllEmployees()).thenReturn(mockUsers);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockUsers)));
    }

    @Test
    void testGetCachedValue() throws Exception {
        String key = "John Doe";
        String cachedValue = "john.doe@example.com";

        // Mocking service call
        when(redisService.get(key)).thenReturn(cachedValue);

        mockMvc.perform(get("/api/cache/{key}", key))
                .andExpect(status().isOk())
                .andExpect(content().string(cachedValue));
    }
}

