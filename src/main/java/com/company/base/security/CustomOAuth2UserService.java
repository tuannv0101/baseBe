package com.company.base.security;

import com.company.base.entity.Role;
import com.company.base.entity.User;
import com.company.base.repository.admin.RoleRepository;
import com.company.base.repository.admin.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        Optional<User> userOptional = userRepository.findByUsername(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setFullName(name);
            user.setAvatarUrl(picture);
        } else {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default Role ROLE_USER not found"));

            user = User.builder()
                    .username(email)
                    .password("") // Google users don't have a local password
                    .email(email)
                    .fullName(name)
                    .avatarUrl(picture)
                    .provider(User.Provider.GOOGLE)
                    .roles(Collections.singleton(userRole))
                    .build();
        }
        userRepository.save(user);
        return oAuth2User;
    }
}
