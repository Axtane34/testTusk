package ru.easybot.testTusk.services;

import ru.easybot.testTusk.models.entities.Product;

public interface ProductsService {
    default Product prepareProductForUpdate(Product product, Product updatedProduct){
        if (updatedProduct.getPrice() != null)
            product.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getVendor() != null)
            product.setVendor(updatedProduct.getVendor());
        if (updatedProduct.getQuantity() != null)
            product.setQuantity(updatedProduct.getQuantity());
        if (updatedProduct.getSerialNumber() != null)
            product.setSerialNumber(updatedProduct.getSerialNumber());
        return product;
    }
}
