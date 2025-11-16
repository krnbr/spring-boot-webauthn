package in.neuw.passkey.security;

import in.neuw.passkey.db.entities.PasskeyCredentialRecordsEntity;
import in.neuw.passkey.db.repositories.PasskeyCredentialRecordsJPARepository;
import org.springframework.security.web.webauthn.api.*;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JPACustomUserCredentialRepository implements UserCredentialRepository {

	private final PasskeyCredentialRecordsJPARepository passkeyRecordsRepository;

	public JPACustomUserCredentialRepository(PasskeyCredentialRecordsJPARepository passkeyCredentialRecordsJPARepository) {
		this.passkeyRecordsRepository = passkeyCredentialRecordsJPARepository;
	}

	@Override
	public void delete(Bytes credentialId) {
		Assert.notNull(credentialId, "credentialId cannot be null");
		passkeyRecordsRepository.deleteById(credentialId.toBase64UrlString());
	}

	@Override
	public void save(CredentialRecord credentialRecord) {
		Assert.notNull(credentialRecord, "credentialRecord cannot be null");
	}

	@Override
	public CredentialRecord findByCredentialId(Bytes credentialId) {
		Assert.notNull(credentialId, "credentialId cannot be null");
		var optionalCredentialRecord = passkeyRecordsRepository.findByCredentialId(credentialId.toBase64UrlString());
        return optionalCredentialRecord.map(this::transform).orElse(null);
    }

	@Override
	public List<CredentialRecord> findByUserId(Bytes userId) {
		Assert.notNull(userId, "userId cannot be null");
		var credentialRecordEntities = passkeyRecordsRepository.findByUserId(userId.toBase64UrlString());
		return credentialRecordEntities.stream().map(this::transform).toList();
	}

	private CredentialRecord transform(PasskeyCredentialRecordsEntity passkeyCredentialRecordsEntity) {
		var transports = Stream.of(
                passkeyCredentialRecordsEntity
                        .getTransports()
                        .split(",")
        ).map(AuthenticatorTransport::valueOf).collect(Collectors.toCollection(HashSet::new));
        return ImmutableCredentialRecord.builder()
                .credentialId(Bytes.fromBase64(passkeyCredentialRecordsEntity.getCredentialId()))
                .credentialType(PublicKeyCredentialType.valueOf(passkeyCredentialRecordsEntity.getCredentialType()))
                .attestationClientDataJSON(Bytes.fromBase64(passkeyCredentialRecordsEntity.getAttestationClientDataJSON()))
                .attestationObject(Bytes.fromBase64(passkeyCredentialRecordsEntity.getAttestationObject()))
                .backupEligible(passkeyCredentialRecordsEntity.isBackupEligible())
                .backupState(passkeyCredentialRecordsEntity.isBackupState())
                .created(Instant.ofEpochMilli(passkeyCredentialRecordsEntity.getCreated()))
                .lastUsed(Instant.ofEpochMilli(passkeyCredentialRecordsEntity.getLastUsed()))
                .label(passkeyCredentialRecordsEntity.getLabel())
                .userEntityUserId(Bytes.fromBase64(passkeyCredentialRecordsEntity.getUserId()))
                .publicKey(new ImmutablePublicKeyCose(Bytes.fromBase64(passkeyCredentialRecordsEntity.getPublicKey()).getBytes()))
                .signatureCount(passkeyCredentialRecordsEntity.getSignatureCount())
                .uvInitialized(passkeyCredentialRecordsEntity.isUvInitialized())
				.transports(transports)
                .build();
	}

	private PasskeyCredentialRecordsEntity transform(CredentialRecord credentialRecord) {
		var transports = credentialRecord.getTransports().stream()
				.map(AuthenticatorTransport::getValue)
				.collect(Collectors.joining(","));
        return new PasskeyCredentialRecordsEntity()
                .setCredentialId(credentialRecord.getCredentialId().toBase64UrlString())
                .setUserId(credentialRecord.getUserEntityUserId().toBase64UrlString())
                .setCreated(credentialRecord.getCreated().toEpochMilli())
                .setLastUsed(credentialRecord.getLastUsed().toEpochMilli())
                .setLabel(credentialRecord.getLabel())
				.setAttestationClientDataJSON(credentialRecord.getAttestationClientDataJSON().toBase64UrlString())
				.setAttestationObject(credentialRecord.getAttestationObject().toBase64UrlString())
				.setBackupEligible(credentialRecord.isBackupEligible())
				.setBackupState(credentialRecord.isBackupState())
				.setCredentialId(credentialRecord.getCredentialId().toBase64UrlString())
				.setSignatureCount(credentialRecord.getSignatureCount())
				.setUvInitialized(credentialRecord.isUvInitialized())
				.setTransports(transports)
				.setCredentialType(credentialRecord.getCredentialType().getValue())
				.setPublicKey(new Bytes(credentialRecord.getPublicKey().getBytes()).toBase64UrlString());
	}

}
