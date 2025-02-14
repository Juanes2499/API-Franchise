package co.com.nequi.model.franchise;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@SuperBuilder
@Builder(toBuilder = true)
public class Franchise {
    private Long id;
    private String name;
    private OffsetDateTime createdAt;
}
