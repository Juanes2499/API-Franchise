package co.com.nequi.model.branchproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockBranchProductRequestDto {
    private Long branchId;
    private Long productId;
    private Integer stock;
}
