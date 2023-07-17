package br.com.fbourguignon.adapters.persistence;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Integer> {
    List<ProductJpaEntity> findAllByTenantId(UUID tenantId);
}
