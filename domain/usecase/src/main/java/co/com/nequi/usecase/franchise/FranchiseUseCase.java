package co.com.nequi.usecase.franchise;
import co.com.nequi.model.branchproduct.gateways.BranchProductGateway;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.dto.GetMaxStockProductPerBranchByFranchiseIdResponseDto;
import co.com.nequi.model.generalmodel.GeneralModel;
import lombok.RequiredArgsConstructor;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseGateway franchiseGateway;
    private final BranchProductGateway branchProductGateway;

    public Mono<Franchise> createFranchise (String name){
        Mono<Franchise> franchiseCreated = franchiseGateway.createFranchise(name);
        return franchiseCreated.flatMap(Mono::just);
    }

    public Mono<GetMaxStockProductPerBranchByFranchiseIdResponseDto> getBranchProductByBranchIdAndProductId(Long franchiseId) {
        return franchiseGateway.getFranchiseById(franchiseId)
                .flatMap(franchiseData -> {
                    return
                        branchProductGateway.getMaxStockProductPerBranchByFranchiseId(franchiseId)
                                .flatMap(Flux::just)
                                .collectList()
                                .map(branchProducts -> {
                                    GetMaxStockProductPerBranchByFranchiseIdResponseDto data = new GetMaxStockProductPerBranchByFranchiseIdResponseDto();
                                    data.setId(franchiseData.getId());
                                    data.setName(franchiseData.getName());
                                    data.setCreatedAt(franchiseData.getCreatedAt());
                                    data.setMaxStockList(branchProducts);
                                    return data;
                                })
                                .switchIfEmpty(Mono.empty());

                })
                .switchIfEmpty(Mono.empty());
    }

}
