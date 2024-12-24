package in.neuw.passkey.db.repositories;

import in.neuw.passkey.db.entities.UserPasskeyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPasskeyEntityJPARepository extends CrudRepository<UserPasskeyEntity, String> {

    Optional<UserPasskeyEntity> findByUsername(String username);

}
