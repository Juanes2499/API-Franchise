package co.com.nequi.model.product;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
