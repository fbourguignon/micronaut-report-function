package br.com.fbourguignon.adapters.persistence;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Introspected
@Table(schema = "public", name = "tb_product")
public class ProductJpaEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private UUID uuid = UUID.randomUUID();
    private String name;
    private Double price;
    private String category;
    private int quantity;
    @Column(name = "tenant_id")
    private UUID tenantId;
}
