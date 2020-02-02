package com.myservices;

import java.math.BigDecimal;

public interface ProductDTO {
    String getTitle ();
    BigDecimal getPrice ();
    String getDescription ();
    Long getId ();
}
