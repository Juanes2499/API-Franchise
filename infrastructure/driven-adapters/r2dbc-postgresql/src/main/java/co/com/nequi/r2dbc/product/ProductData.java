package co.com.nequi.r2dbc.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Table(name = "products")
public class ProductData {

    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("price")
    private Double price;

    @Column("createdAt")
    private OffsetDateTime createdAt;

    @Column("updatedAt")
    private OffsetDateTime updatedAt;
}
