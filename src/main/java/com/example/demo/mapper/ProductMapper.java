package com.example.demo.mapper;

import com.example.demo.domain.Category;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);
    Product productDTOToProduct(ProductDTO productDTO);

    CategoryDTO categoryToCategoryDTO(Category category);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    OrderDTO orderToOrderDTO(Order order);
    Order orderDTOToOrder(OrderDTO orderDTO);
}
