package com.springboot.demo.provider;

import com.alibaba.fastjson.JSON;
import com.springboot.demo.dataobject.Brand;
import com.springboot.demo.model.ResultBase;
import com.springboot.demo.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "商标对外接口", description = "商标对外接口")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("selectByPrimaryKey/{id}")
    @ApiOperation(value = "根据商标号进行查询", notes = "根据商标号进行查询")
    public ResultBase<Brand> selectByPrimaryKey(@ApiParam(name = "商标号") @PathVariable("id") Long id) {
        Brand brand = brandService.selectByPrimaryKey(id);
        log.info(JSON.toJSONString(brand));

        ResultBase resultBase = new ResultBase();
        resultBase.setSuccess(Boolean.TRUE);
        resultBase.setValue(brand);

        return resultBase;
    }
}
