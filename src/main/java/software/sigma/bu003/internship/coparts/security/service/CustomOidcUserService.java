package software.sigma.bu003.internship.coparts.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.security.model.GoogleUserInfo;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        return takeOidcUser(oidcUser);
    }

    private OidcUser takeOidcUser(OidcUser oidcUser) {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());

        saveUserInfo(googleUserInfo);

        return oidcUser;
    }

    private void saveUserInfo(GoogleUserInfo googleUserInfo){
        User user = userRepository.findByEmail(googleUserInfo.getEmail())
                .map(userFromDB -> updateUser(userFromDB, googleUserInfo))
                .orElseGet(() -> createNewUser(googleUserInfo));

        userRepository.save(user);
    }

    private User createNewUser(GoogleUserInfo googleUserInfo){
        return User.builder()
                .googleId(googleUserInfo.getGoogleId())
                .email(googleUserInfo.getEmail())
                .fullName(googleUserInfo.getFullName())
                .givenName(googleUserInfo.getGivenName())
                .familyName(googleUserInfo.getFamilyName())
                .imageUrl(googleUserInfo.getPictureUrl())
                .locale(googleUserInfo.getLocale())
                .lastVisit(LocalDateTime.now())
                .build();
    }

    private User updateUser(User userFromDB, GoogleUserInfo googleUserInfo) {
        if(userFromDB.getGoogleId() == null ||
                !userFromDB.getGoogleId().equals(googleUserInfo.getGoogleId())) {
           userFromDB.setGoogleId(googleUserInfo.getGoogleId());
        }
        if(userFromDB.getFullName() == null ||
                !userFromDB.getFullName().equals(googleUserInfo.getFullName())) {
            userFromDB.setFullName(googleUserInfo.getFullName());
        }
        if(userFromDB.getGivenName() == null ||
                !userFromDB.getGivenName().equals(googleUserInfo.getGivenName())) {
            userFromDB.setGivenName(googleUserInfo.getGivenName());
        }
        if(userFromDB.getFamilyName() == null ||
                !userFromDB.getFamilyName().equals(googleUserInfo.getFamilyName())) {
            userFromDB.setFamilyName(googleUserInfo.getFamilyName());
        }
        if(userFromDB.getLocale() == null ||
                !userFromDB.getLocale().equals(googleUserInfo.getLocale())) {
            userFromDB.setLocale(googleUserInfo.getLocale());
        }
        if(userFromDB.getImageUrl() == null ||
                !userFromDB.getImageUrl().equals(googleUserInfo.getPictureUrl())) {
            userFromDB.setImageUrl(googleUserInfo.getPictureUrl());
        }
        
        userFromDB.setLastVisit(LocalDateTime.now());
        return userFromDB;
    }
}
