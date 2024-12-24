package in.neuw.passkey.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SignupDto {

    private String email;
    private String password;

}
