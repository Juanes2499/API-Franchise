package co.com.nequi.r2dbc.franchise;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

public interface FranchiseDataRepository extends ReactiveCrudRepository<FranchiseData, Long>,
        ReactiveQueryByExampleExecutor<FranchiseData> {

    @Query(value = "INSERT INTO \"franchisesSchema\".franchises (\"name\", \"createdAt\") VALUES(:name, :createdAt) RETURNING \"id\", \"name\", \"createdAt\"")
    Mono<FranchiseData> createFranchise(String name, OffsetDateTime createdAt);

    @Query(value = """
            SELECT "id", "name", "createdAt"
            FROM "franchisesSchema".franchises
            WHERE "id" = :id\s""")
    Mono<FranchiseData> getFranchiseById(Long id);
}
