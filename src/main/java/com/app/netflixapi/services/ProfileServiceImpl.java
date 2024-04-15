package com.app.netflixapi.services;

import com.app.netflixapi.entities.Profile;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.exceptions.ProfileAlreadyExists;
import com.app.netflixapi.exceptions.ProfileNotFound;
import com.app.netflixapi.repositories.ProfileRepository;
import com.app.netflixapi.services.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;

    @Override
    public Profile addProfile(String name, boolean isChild) {
        User user = userService.getAuthenticatedUser();

        Profile profileExists = profileRepository.findByNameAndUserId(name, user.getId()).orElse(null);
        if (profileExists != null) {
            throw new ProfileAlreadyExists("Profile with this name already exists");
        }

        Profile profile = new Profile();
        profile.setName(name);
        profile.setChild(isChild);
        profile.setUser(user);
        profile.setPictureUrl("https://occ-0-6601-56.1.nflxso.net/dnm/api/v6/vN7bi_My87NPKvsBoib006Llxzg/AAAABZumJ3wvSKM7od-r3UjhVF9j3yteWlQYA-51F3SNoI682llhul1Xf_CUkMnfP_17Md2lpOOhbwHeGufvo8kOTjptoS_bcwtniHKz.png?r=e6e)");

        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ProfileNotFound("Profile not found"));

        if (updatedProfile.getName() != null) {
            profile.setName(updatedProfile.getName());
        }
        if (updatedProfile.isChild() != profile.isChild()) {
            profile.setChild(updatedProfile.isChild());
        }
        if (updatedProfile.getPictureUrl() != null) {
            profile.setPictureUrl(updatedProfile.getPictureUrl());
        }

        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public Set<Profile> getAllProfile() {
        User user = userService.getAuthenticatedUser();

        return user.getProfiles();
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new ProfileNotFound("Profile not found"));
    }
}
