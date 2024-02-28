package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.ProfileDto;
import com.app.netflixapi.entities.Profile;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.repositories.UserRepository;
import com.app.netflixapi.services.ProfileService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final UserRepository userRepository;

    @PostMapping
    public Profile addProfile(@RequestBody ProfileDto profileDto) {
        return this.profileService.addProfile(profileDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathParam("id") Long profileId) {
        this.profileService.deleteProfile(profileId);
    }

    @PutMapping
    public Profile updateProfile(@RequestBody Profile profile) {
        return this.profileService.updateProfile(profile);
    }

    @GetMapping
    public Set<Profile> getAllProfile() {
        return this.profileService.getAllProfile();
    }

    @GetMapping("/")
    public User getUserAuthenticated() {
        return this.userRepository.findByEmail("test@test.com").orElse(null);
    }
}
