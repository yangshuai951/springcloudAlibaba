package com.ext.springcloud.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Store {

    private Long id;

    private Long productId;

    private Integer total;

    private Integer used;

    private Integer residue;
}
