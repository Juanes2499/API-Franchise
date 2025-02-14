package co.com.nequi.r2dbc.branchProduct;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branchproduct.BranchProduct;
import co.com.nequi.model.branchproduct.dto.DeleteBranchProductRequestDto;
import co.com.nequi.model.branchproduct.dto.UpdateStockBranchProductRequestDto;
import co.com.nequi.model.branchproduct.gateways.BranchProductGateway;
import co.com.nequi.model.generalmodel.GeneralModel;
import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchProductRepositoryAdapter extends ReactiveAdapterOperations
        <BranchProduct, BranchProductData, Long, BranchProductDataRepository> implements BranchProductGateway {

    public BranchProductRepositoryAdapter(BranchProductDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BranchProduct.class));
    }

    @Override
    public Flux<BranchProduct> getAllBranchesProducts() {
        return repository.getAllBranchProduct()
                .map(this::mapToBranchProduct);
    }

    @Override
    public Mono<BranchProduct> getBranchProductByBranchIdAndProductId(Long branchId, Long productId) {
        return repository.getBranchProductByBranchIdAndProductId(branchId, productId)
                .map(this::mapToBranchProduct)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<BranchProduct> getMaxStockProductPerBranchByFranchiseId (Long franchiseId) {
        return repository.getMaxStockProductPerBranchByFranchiseId(franchiseId)
                .map(this::mapToBranchProduct)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<GeneralModel> deleteBranchProduct(DeleteBranchProductRequestDto deleteBranchProductRequestDto){
        Long branchId = deleteBranchProductRequestDto.getBranchId();
        Long productId = deleteBranchProductRequestDto.getProductId();

        return repository.deleteBranchProduct(branchId, productId)
                .map(branchProductDeleted -> {
                    if (branchProductDeleted == 1) {
                        return mapToGeneralModel("DELETE", String.format("Product ID %d was successfully delete from Branch ID %d.", branchId, productId), true);
                    }else{
                        return mapToGeneralModel("DELETE", String.format("Product ID %d was NOT successfully delete from Branch ID %d.", branchId, productId), false);
                    }
                });
    }

    @Override
    public Mono<GeneralModel> updateStockBranchProduct(UpdateStockBranchProductRequestDto updateStockBranchProductRequestDto){
        Long branchId = updateStockBranchProductRequestDto.getBranchId();
        Long productId = updateStockBranchProductRequestDto.getProductId();
        Integer stock = updateStockBranchProductRequestDto.getStock();

        return repository.updateStockBranchProduct(stock, branchId, productId)
                .map(branchProductStockUpdated -> {
                    if (branchProductStockUpdated == 1) {
                        return mapToGeneralModel("UPDATE", String.format("The product with ID %d from Branch ID %d was successfully updated with %d units in stock.", branchId, productId, stock), true);
                    }else{
                        return mapToGeneralModel("UPDATE", String.format("The product with ID %d from Branch ID %d was successfully updated with %d units in stock.", branchId, productId, stock), false);
                    }
                });
    }


    private BranchProduct mapToBranchProduct(BranchProductData branchProductData) {

        Branch branch = new Branch();
        branch.setId(branchProductData.getBranchId());
        branch.setFranchiseId(branchProductData.getBranchFranchiseId());
        branch.setName(branchProductData.getBranchName());
        branch.setAddress(branchProductData.getBranchAddress());
        branch.setPhoneNumber(branchProductData.getBranchPhoneNumber());
        branch.setCreatedAt(branchProductData.getBranchCreatedAt());
        branch.setUpdatedAt(branchProductData.getBranchUpdatedAt());

        Product product = new Product();
        product.setId(branchProductData.getProductId());
        product.setName(branchProductData.getProductName());
        product.setDescription(branchProductData.getProductDescription());
        product.setPrice(branchProductData.getProductPrice());
        product.setCreatedAt(branchProductData.getProductCreatedAt());
        product.setUpdatedAt(branchProductData.getProductUpdatedAt());

        return BranchProduct.builder()
                .id(branchProductData.getId())
                .branch(branch)
                .product(product)
                .stock(branchProductData.getStock())
                .createdAt(branchProductData.getCreatedAt())
                .updatedAt(branchProductData.getUpdatedAt())
                .build();
    }

    private GeneralModel mapToGeneralModel(String method, String message, Boolean success) {

        return GeneralModel.builder()
                .method(method)
                .message(message)
                .success(success)
                .build();
    }
}
