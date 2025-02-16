package co.com.nequi.model.franchise.gateways;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.dto.UpdateFranchiseNameByIdRequestDto;
import co.com.nequi.model.generalmodel.GeneralModel;
import reactor.core.publisher.Mono;

public interface FranchiseGateway {
    Mono<Franchise> createFranchise(String name);
    Mono<Franchise> getFranchiseById(Long id);
    Mono<GeneralModel> updateFranchiseNameById (UpdateFranchiseNameByIdRequestDto updateFranchiseNameByIdRequestDto);
}
