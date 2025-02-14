package co.com.nequi.usecase.branchproduct;

import co.com.nequi.model.branch.dto.CreateBranchRequestDto;
import co.com.nequi.model.branch.dto.CreateBranchResponseDto;
import co.com.nequi.model.branchproduct.BranchProduct;
import co.com.nequi.model.branchproduct.dto.DeleteBranchProductRequestDto;
import co.com.nequi.model.branchproduct.dto.UpdateStockBranchProductRequestDto;
import co.com.nequi.model.branchproduct.gateways.BranchProductGateway;
import co.com.nequi.model.generalmodel.GeneralModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchProductUseCase {
    private final BranchProductGateway branchProductGateway;

    public Flux<BranchProduct> getAllBranchesProducts (){
        return branchProductGateway.getAllBranchesProducts().flatMap(Flux::just);
    }

    public Mono<GeneralModel> deleteBranchProduct(DeleteBranchProductRequestDto deleteBranchProductRequestDto){
        Mono<GeneralModel> generalModelMono = branchProductGateway.deleteBranchProduct(deleteBranchProductRequestDto);
        return generalModelMono.flatMap(Mono::just);
    }

    public Mono<GeneralModel> updateStockBranchProduct(UpdateStockBranchProductRequestDto updateStockBranchProductRequestDto) {
        Long branchId = updateStockBranchProductRequestDto.getBranchId();
        Long productId = updateStockBranchProductRequestDto.getProductId();

        return branchProductGateway.getBranchProductByBranchIdAndProductId(branchId, productId)
                .flatMap(branchProductData ->
                        branchProductGateway.updateStockBranchProduct(updateStockBranchProductRequestDto)
                )
                .switchIfEmpty(Mono.just(GeneralModel.builder()
                        .method("UPDATE")
                        .message(String.format("Branch ID %d and Product ID %d were not found", branchId, productId))
                        .success(false)
                        .build()));
    }
}
