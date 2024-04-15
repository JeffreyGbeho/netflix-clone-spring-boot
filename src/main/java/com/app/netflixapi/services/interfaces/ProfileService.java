package com.app.netflixapi.services.interfaces;

import com.app.netflixapi.entities.Profile;

import java.util.Set;

public interface ProfileService {
    Profile addProfile(String name, boolean isChild);

    Profile updateProfile(Long id, Profile updatedProfile);

    void deleteProfile(Long id);

    Set<Profile> getAllProfile();

    Profile getProfileById(Long id);
}
