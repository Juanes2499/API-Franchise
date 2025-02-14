package co.com.nequi.r2dbc.branchProduct;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Table(name = "branches_products")
public class BranchProductData {

    @Column("id")
    private Long id;

    @Column("branchId")
    private Long branchId;

    @Column("branchFranchiseId")
    private Long branchFranchiseId;

    @Column("branchName")
    private String branchName;

    @Column("branchAddress")
    private String branchAddress;

    @Column("branchPhoneNumber")
    private String branchPhoneNumber;

    @Column("branchCreatedAt")
    private OffsetDateTime branchCreatedAt;

    @Column("branchUpdatedAt")
    private OffsetDateTime branchUpdatedAt;

    @Column("productId")
    private Long productId;

    @Column("productName")
    private String productName;

    @Column("productDescription")
    private String productDescription;

    @Column("productPrice")
    private Double productPrice;

    @Column("productCreatedAt")
    private OffsetDateTime productCreatedAt;

    @Column("productUpdatedAt")
    private OffsetDateTime productUpdatedAt;

    @Column("stock")
    private Integer stock;

    @Column("createdAt")
    private OffsetDateTime createdAt;

    @Column("updatedAt")
    private OffsetDateTime updatedAt;
}
