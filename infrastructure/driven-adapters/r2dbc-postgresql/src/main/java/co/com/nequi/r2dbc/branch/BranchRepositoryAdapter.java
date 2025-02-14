package co.com.nequi.r2dbc.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.dto.CreateBranchRequestDto;
import co.com.nequi.model.branch.gateways.BranchGateway;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.franchise.FranchiseData;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@Repository
public class BranchRepositoryAdapter extends ReactiveAdapterOperations
        <Branch, BranchData, Long, BranchDataRepository> implements BranchGateway {

    public BranchRepositoryAdapter(BranchDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    @Override
    public Mono<Branch> createBranch(CreateBranchRequestDto createBranchRequestDto) {
        OffsetDateTime createdAt = OffsetDateTime.now();
        OffsetDateTime updatedAt = OffsetDateTime.now();
        return repository.createBranch(
                createBranchRequestDto.getFranchiseId(),
                createBranchRequestDto.getName(),
                createBranchRequestDto.getAddress(),
                createBranchRequestDto.getPhoneNumber(),
                createdAt,
                updatedAt
        ).map(branchCreated -> {
            return mapToBranch(branchCreated.getId(), createBranchRequestDto, createdAt, updatedAt);
        });
    }

    private Branch mapToBranch(Long id, CreateBranchRequestDto branchData, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return Branch.builder()
                .id(id)
                .franchiseId(branchData.getFranchiseId())
                .name(branchData.getName())
                .address(branchData.getAddress())
                .phoneNumber(branchData.getPhoneNumber())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
