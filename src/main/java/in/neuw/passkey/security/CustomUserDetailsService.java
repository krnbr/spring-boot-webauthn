package in.neuw.passkey.security;

import in.neuw.passkey.db.repositories.UserJPARepository;
import in.neuw.passkey.utils.CommonUtils;
import in.neuw.passkey.utils.Constants;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserJPARepository userRepository;

    public CustomUserDetailsService(UserJPARepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // fetch user either by email or username, can be useful for other flows, not passkeys right away.
        var userOptional = userRepository.findByUsernameOrEmail(username, username);
        if (userOptional.isEmpty()) {
            var maskedUsername = CommonUtils.maskEmail(username);
            throw new UsernameNotFoundException(maskedUsername);
        }
        var user = userOptional.get();
        // this is condition which may occur for another scenario, not to be considered for the passkeys POC.
        var password = StringUtils.hasText(user.getPassword()) ? user.getPassword() : "";
        // setting user to role = USER and NON_EMAIL_USER, if email present setting that to USER only
        User.UserBuilder userBuilder = User.builder().password(password)
                .accountLocked(user.isDeleted())
                .authorities(Constants.USER_AUTH, Constants.NON_EMAIL_AUTH)
                .disabled(!user.isEnabled());
        var resolvedUsername = user.getUsername();
        if (StringUtils.hasText(user.getEmail())) {
            resolvedUsername = user.getEmail();
            userBuilder.authorities(Constants.USER_AUTH);
        }
        userBuilder.username(resolvedUsername);
        return userBuilder.build();
    }

}
