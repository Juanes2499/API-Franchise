package co.com.nequi.r2dbc.product;

import co.com.nequi.r2dbc.franchise.FranchiseData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductDataRepository extends ReactiveCrudRepository<ProductData, Long>,
        ReactiveQueryByExampleExecutor<ProductData>  {

    @Query(value = """
            SELECT "id", "name", description, price, "createdAt", "updatedAt"
            FROM "franchisesSchema".products
            WHERE "id" = :id\s""")
    Mono<ProductData> getProductById(Long id);
}
