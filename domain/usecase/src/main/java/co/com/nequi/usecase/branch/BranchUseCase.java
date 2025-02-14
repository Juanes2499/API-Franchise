package co.com.nequi.usecase.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.dto.CreateBranchRequestDto;
import co.com.nequi.model.branch.dto.CreateBranchResponseDto;
import co.com.nequi.model.branch.gateways.BranchGateway;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    private final FranchiseGateway franchiseGateway;
    private final BranchGateway branchGateway;

    public Mono<CreateBranchResponseDto> createBranch (CreateBranchRequestDto createBranchRequestDto){

        Mono<Franchise> franchise = franchiseGateway.getFranchiseById(createBranchRequestDto.getFranchiseId());

        return franchise.flatMap(franchiseData -> {
            if (franchiseData != null){
                Mono<Branch> branchCreated = branchGateway.createBranch(createBranchRequestDto);
                return branchCreated.flatMap(branchCreatedData -> {
                    if (branchCreatedData != null) {
                        CreateBranchResponseDto branchCreatedResponse = new CreateBranchResponseDto();
                        branchCreatedResponse.BuildCreateBranchResponseDto(branchCreatedData, franchiseData);
                        return Mono.just(branchCreatedResponse);
                    }else {
                        return Mono.empty();
                    }
                });
            }else {
                return Mono.empty();
            }
        });
    }
}
