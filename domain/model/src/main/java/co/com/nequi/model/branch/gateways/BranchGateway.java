package co.com.nequi.model.branch.gateways;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.dto.CreateBranchRequestDto;
import reactor.core.publisher.Mono;

public interface BranchGateway {
    Mono<Branch> createBranch(CreateBranchRequestDto createBranchRequestDto);
}
