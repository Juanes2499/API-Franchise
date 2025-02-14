package co.com.nequi.model.branchproduct.gateways;

import co.com.nequi.model.branchproduct.BranchProduct;
import co.com.nequi.model.branchproduct.dto.DeleteBranchProductRequestDto;
import co.com.nequi.model.branchproduct.dto.UpdateStockBranchProductRequestDto;
import co.com.nequi.model.generalmodel.GeneralModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductGateway {
    Flux<BranchProduct> getAllBranchesProducts();
    Mono<BranchProduct> getBranchProductByBranchIdAndProductId(Long branchId, Long productId);
    Flux<BranchProduct> getMaxStockProductPerBranchByFranchiseId (Long franchiseId);
    Mono<GeneralModel> deleteBranchProduct(DeleteBranchProductRequestDto deleteBranchProductRequestDto);
    Mono<GeneralModel> updateStockBranchProduct(UpdateStockBranchProductRequestDto updateStockBranchProductRequestDto);
}
