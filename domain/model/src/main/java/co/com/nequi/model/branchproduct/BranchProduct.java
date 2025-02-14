package co.com.nequi.model.branchproduct;
import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.product.Product;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchProduct {
    private Long id;
    private Branch branch;
    private Product product;
    private Integer stock;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
