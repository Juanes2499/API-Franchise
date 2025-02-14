package co.com.nequi.model.product.gateways;

import co.com.nequi.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductGateway {
    Mono<Product> getProductById(Long id);
}
