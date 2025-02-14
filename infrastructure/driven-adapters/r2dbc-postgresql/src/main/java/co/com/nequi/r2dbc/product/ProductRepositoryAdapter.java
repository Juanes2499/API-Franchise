package co.com.nequi.r2dbc.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductGateway;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;

public class ProductRepositoryAdapter extends ReactiveAdapterOperations
        <Product, ProductData, Long, ProductDataRepository> implements ProductGateway {

    public ProductRepositoryAdapter(ProductDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return repository.getProductById(id).map(this::mapToProduct);
    }

    private Product mapToProduct(ProductData productData) {
        return Product.builder()
                .id(productData.getId())
                .name(productData.getName())
                .description(productData.getDescription())
                .price(productData.getPrice())
                .createdAt(productData.getCreatedAt())
                .createdAt(productData.getCreatedAt())
                .build();
    }
}
