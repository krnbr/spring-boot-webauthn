package in.neuw.passkey.services;

import in.neuw.passkey.models.CredentialRecordDto;
import in.neuw.passkey.utils.CommonUtils;
import org.springframework.security.web.webauthn.api.CredentialRecord;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProfileService {

    private final PublicKeyCredentialUserEntityRepository credentialUserEntityRepository;
    private final UserCredentialRepository userCredentialRepository;

    public ProfileService(final PublicKeyCredentialUserEntityRepository publicKeyCredentialUserEntityRepository,
                          final UserCredentialRepository userCredentialRepository) {
        this.credentialUserEntityRepository = publicKeyCredentialUserEntityRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    /**
     * Resolve Passkey Credential Records for frontend
     */
    public List<CredentialRecordDto> resolvedCredentialRecords(final String username) {
        var userEntity = this.credentialUserEntityRepository.findByUsername(username);
        return (userEntity != null)
                ? this.userCredentialRepository.findByUserId(userEntity.getId()).stream().map(this::transform).toList()
                : Collections.emptyList();
    }

    private CredentialRecordDto transform(final CredentialRecord credentialRecord) {
        var credentialRecordDto = new CredentialRecordDto();
        return credentialRecordDto
                .setId(credentialRecord.getCredentialId().toBase64UrlString())
                .setLabel(credentialRecord.getLabel())
                .setCreated(CommonUtils.formatDate(credentialRecord.getCreated()))
                .setLastUsed(CommonUtils.formatDate(credentialRecord.getLastUsed()))
                .setSignatureCount(credentialRecord.getSignatureCount());
    }
}