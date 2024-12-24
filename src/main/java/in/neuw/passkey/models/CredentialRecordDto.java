package in.neuw.passkey.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO class
 */
@Getter
@Setter
@Accessors(chain = true)
public class CredentialRecordDto {

    private String id;
    private String label;
    private String created;
    private String lastUsed;
    private Long signatureCount;

}
