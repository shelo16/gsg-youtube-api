package com.tornikeshelia.gsgyoutubeapi.security.service;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private GsgUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GsgUser myUser = userRepository.searchByUsername(username);
        return new User(myUser.getUserName(), myUser.getBcryptedPassword(), new ArrayList<>());
    }
}
