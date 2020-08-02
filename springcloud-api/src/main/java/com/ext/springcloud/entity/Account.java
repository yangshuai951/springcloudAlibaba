package com.ext.springcloud.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

    private Long id;

    private Long userId;

    private Integer total;

    private Integer used;

    private Integer residue;

    private String headImg = "wwwasdfsdfv";

    private String name = "xisdiojf";
}
