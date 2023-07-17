package br.com.fbourguignon.application.repository;

import br.com.fbourguignon.adapters.persistence.ProductJpaRepository;
import br.com.fbourguignon.application.mapper.ProductMapper;
import br.com.fbourguignon.core.domain.Product;
import br.com.fbourguignon.core.ports.ProductRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class ProductRepositoryImpl implements ProductRepository {

    @Inject
    private ProductJpaRepository repository;
    @Inject
    private ProductMapper mapper;
    @Override
    public List<Product> findAllProductsByTenantID(UUID tenantId) {
        try {
            return repository.findAllByTenantId(tenantId)
                    .stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }catch (Exception e){
            log.error("An error has occurred on list products", e);
            throw e;
        }
    }
}
