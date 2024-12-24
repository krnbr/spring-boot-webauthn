package in.neuw.passkey.db.repositories;

import in.neuw.passkey.db.entities.PasskeyUserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPasskeyEntityJPARepository extends CrudRepository<PasskeyUserEntity, String> {

    Optional<PasskeyUserEntity> findByUsername(String username);

}
