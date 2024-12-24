package in.neuw.passkey.db.repositories;

import in.neuw.passkey.db.entities.CredentialRecordsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PasskeyRecordsJPARepository extends CrudRepository<CredentialRecordsEntity, String> {

    List<CredentialRecordsEntity> findByUserId(String userId);

    Optional<CredentialRecordsEntity> findByCredentialId(String credentialId);
}
