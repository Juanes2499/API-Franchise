package co.com.nequi.api;

import co.com.nequi.api.dto.CreateFranchiseRequestDto;
import co.com.nequi.model.branch.dto.CreateBranchRequestDto;
import co.com.nequi.model.branchproduct.dto.DeleteBranchProductRequestDto;
import co.com.nequi.model.branchproduct.dto.UpdateStockBranchProductRequestDto;
import co.com.nequi.usecase.branch.BranchUseCase;
import co.com.nequi.usecase.branchproduct.BranchProductUseCase;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final FranchiseUseCase franchiseUseCase;
    private  final BranchUseCase branchUseCase;
    private  final BranchProductUseCase branchProductUseCase;

    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateFranchiseRequestDto.class)
                .flatMap(dataRequest -> franchiseUseCase.createFranchise(dataRequest.getName()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Internal Error: " + e.getMessage()));
    }

    public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateBranchRequestDto.class)
                .flatMap(branchUseCase::createBranch)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Internal Error: " + e.getMessage()));
    }

    public Mono<ServerResponse> getAllBranchesProducts (ServerRequest serverRequest) {
       return branchProductUseCase.getAllBranchesProducts()
               .collectList()
               .flatMap(result -> ServerResponse.ok().bodyValue(result))
               .onErrorResume(e -> ServerResponse.status(500).bodyValue("Internal Error: " + e.getMessage()));
    }

    public Mono<ServerResponse> deleteBranchProduct (ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DeleteBranchProductRequestDto.class)
                .flatMap(branchProductUseCase::deleteBranchProduct)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Internal Error: " + e.getMessage()));
    }

    public Mono<ServerResponse> updateStockBranchProduct (ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpdateStockBranchProductRequestDto.class)
                .flatMap(branchProductUseCase::updateStockBranchProduct)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Internal Error: " + e.getMessage()));
    }

    public Mono<ServerResponse> getBranchProductByBranchIdAndProductId (ServerRequest serverRequest) {
        Long franchiseId = Long.parseLong(serverRequest.pathVariable("franchiseId"));
        return franchiseUseCase.getBranchProductByBranchIdAndProductId(franchiseId)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Internal Error: " + e.getMessage()));
    }
}


