package co.com.nequi.model.franchise.gateways;

import co.com.nequi.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseGateway {
    Mono<Franchise> createFranchise(String name);
    Mono<Franchise> getFranchiseById(Long id);
}
