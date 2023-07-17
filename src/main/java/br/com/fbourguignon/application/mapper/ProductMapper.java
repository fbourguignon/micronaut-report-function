package br.com.fbourguignon.application.mapper;

import br.com.fbourguignon.adapters.persistence.ProductJpaEntity;
import br.com.fbourguignon.core.domain.Product;
import jakarta.inject.Singleton;

@Singleton
public class ProductMapper {

    public ProductJpaEntity toEntity(Product product){
        ProductJpaEntity entity = new ProductJpaEntity();

        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setUuid(product.getUuid());
        entity.setTenantId(product.getTenantId());
        entity.setCategory(product.getCategory());

        return entity;
    }

    public Product toDomain(ProductJpaEntity entity){

        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setPrice(entity.getPrice());
        product.setQuantity(entity.getQuantity());
        product.setUuid(entity.getUuid());
        product.setTenantId(entity.getTenantId());
        product.setCategory(entity.getCategory());

        return product;
    }
}
