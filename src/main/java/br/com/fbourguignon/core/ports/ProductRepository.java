package br.com.fbourguignon.core.ports;

import br.com.fbourguignon.core.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAllProductsByTenantID(UUID TenantId);
}
