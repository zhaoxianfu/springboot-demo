package com.springboot.demo.service;

import com.springboot.demo.dataobject.Brand;

/**
 * @ClassName:BrandService
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/30  14:48
 * @Version1.0
 **/

public interface BrandService {

    Brand selectByPrimaryKey(Long id);
}
