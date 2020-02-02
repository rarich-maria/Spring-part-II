package com.myservices;

import java.math.BigDecimal;

public class ProductDTOImpl implements ProductDTO {
    private String title;
    private BigDecimal price;
    private String description;

    public ProductDTOImpl () {}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String toString() {
        return "ProductDTOImpl{" +
                  "title='" + title + '\'' +
                  ", price=" + price +
                  ", description='" + description + '\'' +
                  '}';
    }
}
