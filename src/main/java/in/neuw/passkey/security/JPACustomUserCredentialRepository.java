package in.neuw.passkey.security;

import in.neuw.passkey.db.entities.CredentialRecordsEntity;
import in.neuw.passkey.db.repositories.PasskeyRecordsJPARepository;
import org.springframework.security.web.webauthn.api.*;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JPACustomUserCredentialRepository implements UserCredentialRepository {

	private final PasskeyRecordsJPARepository passkeyRecordsRepository;

	public JPACustomUserCredentialRepository(PasskeyRecordsJPARepository passkeyRecordsJPARepository) {
		this.passkeyRecordsRepository = passkeyRecordsJPARepository;
	}

	@Override
	public void delete(Bytes credentialId) {
		Assert.notNull(credentialId, "credentialId cannot be null");
		passkeyRecordsRepository.deleteById(credentialId.toBase64UrlString());
	}

	@Override
	public void save(CredentialRecord credentialRecord) {
		Assert.notNull(credentialRecord, "credentialRecord cannot be null");
		System.out.println(credentialRecord.getTransports());
		System.out.println(credentialRecord.getCredentialType().getValue());
		System.out.println(credentialRecord.getSignatureCount());
		System.out.println(passkeyRecordsRepository.save(transform(credentialRecord)));
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

	private CredentialRecord transform(CredentialRecordsEntity credentialRecordsEntity) {
		var transports = Stream.of(
                credentialRecordsEntity
                        .getTransports()
                        .split(",")
        ).map(AuthenticatorTransport::valueOf).collect(Collectors.toCollection(HashSet::new));
        return ImmutableCredentialRecord.builder()
                .credentialId(Bytes.fromBase64(credentialRecordsEntity.getCredentialId()))
                .credentialType(PublicKeyCredentialType.valueOf(credentialRecordsEntity.getCredentialType()))
                .attestationClientDataJSON(Bytes.fromBase64(credentialRecordsEntity.getAttestationClientDataJSON()))
                .attestationObject(Bytes.fromBase64(credentialRecordsEntity.getAttestationObject()))
                .backupEligible(credentialRecordsEntity.isBackupEligible())
                .backupState(credentialRecordsEntity.isBackupState())
                .created(Instant.ofEpochMilli(credentialRecordsEntity.getCreated()))
                .lastUsed(Instant.ofEpochMilli(credentialRecordsEntity.getLastUsed()))
                .label(credentialRecordsEntity.getLabel())
                .userEntityUserId(Bytes.fromBase64(credentialRecordsEntity.getUserId()))
                .publicKey(new ImmutablePublicKeyCose(Bytes.fromBase64(credentialRecordsEntity.getPublicKey()).getBytes()))
                .signatureCount(credentialRecordsEntity.getSignatureCount())
                .uvInitialized(credentialRecordsEntity.isUvInitialized())
				.transports(transports)
                .build();
	}

	private CredentialRecordsEntity transform(CredentialRecord credentialRecord) {
		var transports = credentialRecord.getTransports().stream()
				.map(AuthenticatorTransport::getValue)
				.collect(Collectors.joining(","));
		credentialRecord.getTransports().forEach(t -> System.out.println("t is "+t));
        return new CredentialRecordsEntity()
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
