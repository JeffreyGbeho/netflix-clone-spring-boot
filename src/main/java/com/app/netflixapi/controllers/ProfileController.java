package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.ProfileDto;
import com.app.netflixapi.entities.Profile;
import com.app.netflixapi.services.interfaces.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> addProfile(@Valid @RequestBody ProfileDto profileDto) {
        try {
            log.info("POST api/v1/profile - START");
            return ResponseEntity.status(HttpStatus.CREATED).body(profileService.addProfile(profileDto.getName(), profileDto.isChild()));
        } finally {
            log.info("POST api/v1/profile - DONE");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        try {
            log.info("DEL api/v1/profile/{} - START");
            profileService.deleteProfile(id);
        } finally {
            log.info("DEL api/v1/profile/{} - DONE");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile updateProfile) {
        try {
            log.info("PUT api/v1/profile/{} - START");
            return ResponseEntity.ok(profileService.updateProfile(id, updateProfile));
        } finally {
            log.info("PUT api/v1/profile/{} - DONE");
        }

    }

    @GetMapping
    public ResponseEntity<Set<Profile>> getAllProfile() {
        try {
            log.info("GET api/v1/profile - START");
            return ResponseEntity.ok(profileService.getAllProfile());
        } finally {
            log.info("GET api/v1/profile - DONE");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        try {
            log.info("GET api/v1/profile/{} - START");
            return ResponseEntity.ok(profileService.getProfileById(id));
        } finally {
            log.info("GET api/v1/profile/{} - DONE");
        }

    }
}
