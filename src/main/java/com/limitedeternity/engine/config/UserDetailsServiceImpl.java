package com.limitedeternity.engine.config;

import com.limitedeternity.engine.models.UserModel;
import com.limitedeternity.engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository users;

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = users.findByEmail(username);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("User " + username + "not found");
    }
}
