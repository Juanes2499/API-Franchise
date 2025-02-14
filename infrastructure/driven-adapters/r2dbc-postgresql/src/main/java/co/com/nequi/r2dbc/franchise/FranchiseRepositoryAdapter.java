package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.function.Function;

@Repository
public class FranchiseRepositoryAdapter extends ReactiveAdapterOperations
        <Franchise, FranchiseData, Long, FranchiseDataRepository> implements FranchiseGateway {

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

    private Franchise mapToFranchise(FranchiseData franchiseData) {
        return Franchise.builder()
                .id(franchiseData.getId())
                .name(franchiseData.getName())
                .createdAt(franchiseData.getCreatedAt())
                .createdAt(franchiseData.getCreatedAt())
                .build();
    }
}
