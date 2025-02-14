package co.com.nequi.model.branch;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    private Long id;
    private Long franchiseId;
    private String name;
    private String address;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
