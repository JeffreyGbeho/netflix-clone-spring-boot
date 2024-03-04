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

    @PostMapping
    public Profile addProfile(@RequestBody ProfileDto profileDto) {
        return profileService.addProfile(profileDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }

    @PutMapping("/{id}")
    public Profile updateProfile(@PathVariable Long id, @RequestBody Profile updateProfile) {
        return profileService.updateProfile(id, updateProfile);
    }

    @GetMapping
    public Set<Profile> getAllProfile() {
        return profileService.getAllProfile();
    }

    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }
}
