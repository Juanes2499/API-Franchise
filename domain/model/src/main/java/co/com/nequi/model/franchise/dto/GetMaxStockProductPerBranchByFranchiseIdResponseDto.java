package co.com.nequi.model.franchise.dto;

import co.com.nequi.model.branchproduct.BranchProduct;
import co.com.nequi.model.franchise.Franchise;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMaxStockProductPerBranchByFranchiseIdResponseDto extends Franchise {
    private List<BranchProduct> maxStockList;
}
