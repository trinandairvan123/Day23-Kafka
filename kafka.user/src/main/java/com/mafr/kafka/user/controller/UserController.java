package com.mafr.kafka.user.controller;

import com.mafr.kafka.user.entity.UserEntity;
import com.mafr.kafka.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserEntity> getAll() {
        List<UserEntity> response = service.getAll();
        log.info("Success get data");
        return response;
    }

    @GetMapping("/{id}")
    public UserEntity getByID(@PathVariable Long id) {
        UserEntity response = service.getByID(id);
        log.info("Success get data with ID {}", id);
        return response;
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable Long id) {
        try {
            String response = service.order(id);
            log.info("Success {}", id);
            return response;
        } catch (Exception e) {
            log.error("Failed {}", e.getMessage());
            return "Failed : " + e.getMessage() + ", " + e.getCause();
        }
    }

}