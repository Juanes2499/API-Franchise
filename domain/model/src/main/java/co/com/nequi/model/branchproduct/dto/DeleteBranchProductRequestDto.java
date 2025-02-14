package co.com.nequi.model.branchproduct.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBranchProductRequestDto {
    private Long branchId;
    private Long productId;
}
