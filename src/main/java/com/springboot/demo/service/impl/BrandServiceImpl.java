package com.springboot.demo.service.impl;

import com.springboot.demo.dataobject.Brand;
import com.springboot.demo.mapper.BrandMapper;
import com.springboot.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName:BrandServiceImpl
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/30  14:49
 * @Version1.0
 **/

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand selectByPrimaryKey(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }

}
