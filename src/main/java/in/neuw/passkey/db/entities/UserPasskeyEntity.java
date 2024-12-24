package in.neuw.passkey.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passkey_users", indexes = {
    @Index(name = "idx_username", columnList = "username", unique = true)
})
public class UserPasskeyEntity {

    @Id
    private String id;

    @Column(name = "username")
    private String username;

    private String displayName;

}
