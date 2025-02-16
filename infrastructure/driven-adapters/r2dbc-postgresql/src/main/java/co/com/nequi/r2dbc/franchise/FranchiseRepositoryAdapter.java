package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.dto.UpdateFranchiseNameByIdRequestDto;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.model.generalmodel.GeneralModel;
import co.com.nequi.model.generalmodel.utils.GeneralModelUtils;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@Repository
public class FranchiseRepositoryAdapter extends ReactiveAdapterOperations
        <Franchise, FranchiseData, Long, FranchiseDataRepository> implements FranchiseGateway {

    private final GeneralModelUtils generalModelUtils = new GeneralModelUtils();

    public FranchiseRepositoryAdapter(FranchiseDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }

    @Override
    public Mono<Franchise> createFranchise(String name) {
        OffsetDateTime createdAt = OffsetDateTime.now();
        return repository.createFranchise(name, createdAt).map(this::mapToFranchise);
    }

    @Override
    public Mono<Franchise> getFranchiseById(Long id) {
        return repository.getFranchiseById(id)
                .map(this::mapToFranchise)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<GeneralModel> updateFranchiseNameById (UpdateFranchiseNameByIdRequestDto updateFranchiseNameByIdRequestDto){
        Long franchiseId = updateFranchiseNameByIdRequestDto.getId();
        String name = updateFranchiseNameByIdRequestDto.getName();

        return repository.updateFranchiseNameById(name, franchiseId)
                .map(franchiseUpdate -> {
                    if(franchiseUpdate == 1){
                        return generalModelUtils.mapToGeneralModel("UPDATE", String.format("Franchise name with ID %d was successfully updated.", franchiseId), true);
                    }else{
                        return generalModelUtils.mapToGeneralModel("UPDATE", String.format("Franchise name with ID %d was NOT successfully updated.", franchiseId), false);
                    }
                });
    }

    private Franchise mapToFranchise(FranchiseData franchiseData) {
        return Franchise.builder()
                .id(franchiseData.getId())
                .name(franchiseData.getName())
                .createdAt(franchiseData.getCreatedAt())
                .createdAt(franchiseData.getCreatedAt())
                .build();
    }
}
