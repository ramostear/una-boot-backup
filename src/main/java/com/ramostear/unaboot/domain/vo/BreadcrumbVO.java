package com.ramostear.unaboot.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BreadcrumbVO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:28
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreadcrumbVO {
    private String name;
    private String link;
}
