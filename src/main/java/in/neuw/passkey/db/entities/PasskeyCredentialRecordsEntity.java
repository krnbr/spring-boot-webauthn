package in.neuw.passkey.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passkey_credential_records", indexes = {
        @Index(name = "idx_userId", columnList = "userId")
})
public class PasskeyCredentialRecordsEntity {

    @Id
    private String credentialId;

    private String credentialType;

    private String userId;

    @Column(columnDefinition = "TEXT")
    private String publicKey;

    private long signatureCount;

    private boolean uvInitialized;

    private String transports;

    private boolean backupEligible;

    private boolean backupState;

    @Column(columnDefinition = "TEXT")
    private String attestationObject;

    @Column(columnDefinition = "TEXT")
    private String attestationClientDataJSON;

    private Long created;

    private Long lastUsed;

    private String label;

    @Override
    public String toString() {
        return "CredentialRecordsEntity{" +
                "credentialId='" + credentialId + '\'' +
                ", credentialType='" + credentialType + '\'' +
                ", userId='" + userId + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", signatureCount=" + signatureCount +
                ", uvInitialized=" + uvInitialized +
                ", transports='" + transports + '\'' +
                ", backupEligible=" + backupEligible +
                ", backupState=" + backupState +
                ", attestationObject='" + attestationObject + '\'' +
                ", attestationClientDataJSON='" + attestationClientDataJSON + '\'' +
                ", created=" + created +
                ", lastUsed=" + lastUsed +
                ", label='" + label + '\'' +
                '}';
    }
}
