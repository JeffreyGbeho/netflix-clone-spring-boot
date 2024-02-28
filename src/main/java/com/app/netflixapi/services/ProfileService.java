package com.app.netflixapi.services;

import com.app.netflixapi.dtos.ProfileDto;
import com.app.netflixapi.entities.Profile;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.repositories.ProfileRepository;
import com.app.netflixapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Profile addProfile(ProfileDto profileDto) {
        User user = this.userService.getAuthenticatedUser();

        Profile profile = new Profile();
        profile.setName(profileDto.getName());
        profile.setChild(profileDto.isChild());

        Profile profileSaved = this.profileRepository.save(profile);

        Set<Profile> set = user.getProfiles();
        set.add(profileSaved);
        user.setProfiles(set);
        userRepository.save(user);

        return profileSaved;
    }

    public Profile updateProfile(Profile profileUpdated) {
        return this.profileRepository.save(profileUpdated);
    }

    public void deleteProfile(Long profileId) {
        this.profileRepository.deleteById(profileId);
    }

    public Set<Profile> getAllProfile() {
        User user = this.userService.getAuthenticatedUser();

        return user.getProfiles();
    }
}
