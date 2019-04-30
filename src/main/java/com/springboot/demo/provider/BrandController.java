package com.springboot.demo.provider;

import com.alibaba.fastjson.JSON;
import com.springboot.demo.dataobject.Brand;
import com.springboot.demo.model.ResultBase;
import com.springboot.demo.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:BrandController
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/30  14:56
 * @Version1.0
 **/

@Slf4j
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("selectByPrimaryKey/{id}")
    public ResultBase<Brand> selectByPrimaryKey(@PathVariable("id") Long id) {
        Brand brand = brandService.selectByPrimaryKey(id);
        log.info(JSON.toJSONString(brand));

        ResultBase resultBase = new ResultBase();
        resultBase.setSuccess(Boolean.TRUE);
        resultBase.setValue(brand);

        return resultBase;
    }
}
