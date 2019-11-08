package com.ramostear.unaboot.common.factory.support;

import com.ramostear.unaboot.common.factory.CDN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Qiniu
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:29
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Qiniu implements CDN {
    private boolean enable;

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String domain;
}
