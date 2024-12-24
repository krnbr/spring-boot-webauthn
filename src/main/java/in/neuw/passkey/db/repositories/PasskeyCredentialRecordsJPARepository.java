package in.neuw.passkey.db.repositories;

import in.neuw.passkey.db.entities.PasskeyCredentialRecordsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PasskeyCredentialRecordsJPARepository extends CrudRepository<PasskeyCredentialRecordsEntity, String> {

    List<PasskeyCredentialRecordsEntity> findByUserId(String userId);

    Optional<PasskeyCredentialRecordsEntity> findByCredentialId(String credentialId);
}
