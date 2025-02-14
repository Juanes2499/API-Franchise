package co.com.nequi.r2dbc.branchProduct;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BranchProductDataRepository extends ReactiveCrudRepository<BranchProductData, Long>,
        ReactiveQueryByExampleExecutor<BranchProductData> {

    @Query(value = """
            SELECT\s
            \tBP."id",\s
            \tBP."branchId",
            \tBS."franchiseId" as "branchFranchiseId",
            \tBS."name" as "branchName",
            \tBS."address" as "branchAddress",
            \tBS."phoneNumber" as "branchPhoneNumber",
            \tBS."createdAt" as "branchCreatedAt",
            \tBS."updatedAt" as "branchUpdatedAt",
            \tBP."productId",
            \tP."name" as "productName",
            \tP."description" as "productDescription",
            \tP."price" as "productPrice",
            \tP."createdAt" as "productCreatedAt",
            \tP."updatedAt" as "productUpdatedAt",
            \tBP."stock",\s
            \tBP."createdAt",\s
            \tBP."updatedAt"
            FROM\s
            \t"franchisesSchema".branches_products as BP
            inner join "franchisesSchema".branches as BS on BP."branchId" = BS."id"
            inner join "franchisesSchema".products as P on BP."productId" = P."id\"""")
    Flux<BranchProductData> getAllBranchProduct();

    @Query(value = """
            SELECT\s
            \tBP."id",\s
            \tBP."branchId",
            \tBS."franchiseId" as "branchFranchiseId",
            \tBS."name" as "branchName",
            \tBS."address" as "branchAddress",
            \tBS."phoneNumber" as "branchPhoneNumber",
            \tBS."createdAt" as "branchCreatedAt",
            \tBS."updatedAt" as "branchUpdatedAt",
            \tBP."productId",
            \tP."name" as "productName",
            \tP."description" as "productDescription",
            \tP."price" as "productPrice",
            \tP."createdAt" as "productCreatedAt",
            \tP."updatedAt" as "productUpdatedAt",
            \tBP."stock",\s
            \tBP."createdAt",\s
            \tBP."updatedAt"
            FROM\s
            \t"franchisesSchema".branches_products as BP
            inner join "franchisesSchema".branches as BS on BP."branchId" = BS."id"
            inner join "franchisesSchema".products as P on BP."productId" = P."id"
            where BP."branchId" = :branchId and BP."productId" = :productId""")
    Mono<BranchProductData> getBranchProductByBranchIdAndProductId(Long branchId, Long productId);

    @Query(value = """
            WITH ranked_products AS (
                SELECT\s
                    \tBP."id",\s
                    \t\tBP."branchId",
                    \t\tBS."franchiseId" as "branchFranchiseId",
                    \t\tBS."name" as "branchName",
                    \t\tBS."address" as "branchAddress",
                    \t\tBS."phoneNumber" as "branchPhoneNumber",
                    \t\tBS."createdAt" as "branchCreatedAt",
                    \t\tBS."updatedAt" as "branchUpdatedAt",
                    \t\tBP."productId",
                    \t\tP."name" as "productName",
                    \t\tP."description" as "productDescription",
                    \t\tP."price" as "productPrice",
                    \t\tP."createdAt" as "productCreatedAt",
                    \t\tP."updatedAt" as "productUpdatedAt",
                    \t\tBP."stock",\s
                    \t\tBP."createdAt",\s
                    \t\tBP."updatedAt",
                    RANK() OVER (PARTITION BY BS."id" ORDER BY BP."stock" DESC) AS rnk
                FROM "franchisesSchema".branches_products as BP
                inner JOIN "franchisesSchema".branches BS ON BP."branchId" = BS."id"
                inner JOIN "franchisesSchema".products P ON BP."productId" = P."id"
                WHERE BS."franchiseId" = :franchiseId
            )
            SELECT\s
                *
            FROM ranked_products
            WHERE rnk = 1""")
    Flux<BranchProductData> getMaxStockProductPerBranchByFranchiseId (Long franchiseId);

    @Modifying
    @Query(value = "DELETE FROM \"franchisesSchema\".branches_products\n" +
            "WHERE \"branchId\" = :branchId and \"productId\" = :productId")
    Mono<Integer> deleteBranchProduct(Long branchId, Long productId);

    @Modifying
    @Query(value = "UPDATE \"franchisesSchema\".branches_products\n" +
            "SET \"stock\" = :stock\n" +
            "WHERE \"branchId\" = :branchId and \"productId\" = :productId")
    Mono<Integer> updateStockBranchProduct(Integer stock, Long branchId, Long productId);
}
