package co.com.nequi.r2dbc.branch;

import co.com.nequi.model.branch.dto.CreateBranchRequestDto;
import co.com.nequi.r2dbc.franchise.FranchiseData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

public interface BranchDataRepository extends ReactiveCrudRepository<BranchData, Long>,
        ReactiveQueryByExampleExecutor<BranchData> {

    @Query(value = """
        INSERT INTO "franchisesSchema".branches
        ("franchiseId", "name", "address", "phoneNumber", "createdAt", "updatedAt")
        VALUES(:franchiseId, :name, :address, :phoneNumber, :createdAt, :updatedAt)
        RETURNING *
    """)
    Mono<BranchData> createBranch(
            Long franchiseId,
            String name,
            String address,
            String phoneNumber,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    );
}
