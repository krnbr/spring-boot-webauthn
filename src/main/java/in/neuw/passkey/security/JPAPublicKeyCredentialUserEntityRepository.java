package in.neuw.passkey.security;

import in.neuw.passkey.db.entities.UserPasskeyEntity;
import in.neuw.passkey.db.repositories.UserPasskeyEntityJPARepository;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.api.ImmutablePublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.util.Assert;

public class JPAPublicKeyCredentialUserEntityRepository implements PublicKeyCredentialUserEntityRepository {

	private final UserPasskeyEntityJPARepository userPasskeyEntityRepository;

	public JPAPublicKeyCredentialUserEntityRepository(UserPasskeyEntityJPARepository userPasskeyEntityRepository) {
		this.userPasskeyEntityRepository = userPasskeyEntityRepository;
	}

	@Override
	public PublicKeyCredentialUserEntity findById(Bytes id) {
		Assert.notNull(id, "id cannot be null");
		var userPasskeyEntityOptional = userPasskeyEntityRepository.findById(id.toBase64UrlString());
        return userPasskeyEntityOptional.map(this::transform).orElse(null);
    }

	@Override
	public PublicKeyCredentialUserEntity findByUsername(String username) {
		Assert.notNull(username, "username cannot be null");
		var userPasskeyEntityOptional = userPasskeyEntityRepository.findByUsername(username);
		return userPasskeyEntityOptional.map(this::transform).orElse(null);
	}

	@Override
	public void save(PublicKeyCredentialUserEntity userEntity) {
		if (userEntity == null) {
			throw new IllegalArgumentException("userEntity cannot be null");
		}
		var userPasskeyEntity = transform(userEntity);
		this.userPasskeyEntityRepository.save(userPasskeyEntity);

	}

	@Override
	public void delete(Bytes id) {
		this.userPasskeyEntityRepository.deleteById(id.toBase64UrlString());
	}

	private UserPasskeyEntity transform(PublicKeyCredentialUserEntity input) {
		var userPasskeyEntity = new UserPasskeyEntity();
		userPasskeyEntity.setUsername(input.getName());
		userPasskeyEntity.setId(input.getId().toBase64UrlString());
		userPasskeyEntity.setDisplayName(input.getDisplayName());
		return userPasskeyEntity;
	}

	private PublicKeyCredentialUserEntity transform(UserPasskeyEntity input) {
        return ImmutablePublicKeyCredentialUserEntity
				.builder()
                .id(Bytes.fromBase64(input.getId()))
				.name(input.getUsername())
				.displayName(input.getDisplayName()).build();
	}

}
