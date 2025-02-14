package co.com.nequi.r2dbc.franchise;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.sql.Date;

@Data
@NoArgsConstructor
@Table(name = "franchises")
public class FranchiseData {

    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("createdAt")
    private OffsetDateTime createdAt;
}
