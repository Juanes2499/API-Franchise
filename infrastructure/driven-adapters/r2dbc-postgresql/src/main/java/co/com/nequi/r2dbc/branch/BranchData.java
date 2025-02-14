package co.com.nequi.r2dbc.branch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Table(name = "branches")
public class BranchData {

    @Column("id")
    private Long id;

    @Column("franchiseId")
    private Long franchiseId;

    @Column("name")
    private String name;

    @Column("address")
    private String address;

    @Column("phoneNumber")
    private String phoneNumber;

    @Column("createdAt")
    private OffsetDateTime createdAt;

    @Column("updatedAt")
    private OffsetDateTime updatedAt;
}
