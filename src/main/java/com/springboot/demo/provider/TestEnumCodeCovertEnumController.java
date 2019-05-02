package com.springboot.demo.provider;

import com.springboot.demo.enums.PolicyStatusEnum;
import com.springboot.demo.model.ResultBase;
import com.springboot.demo.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:TestEnumCodeCovertEnumController
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/5/2  14:47
 * @Version1.0
 **/
@Slf4j
@RestController
@RequestMapping("enum")
@Api(tags = "测试前端传入枚举code自动转为后端的枚举对象接口", description = "测试前端传入枚举code自动转为后端的枚举对象接口")
public class TestEnumCodeCovertEnumController {

    @GetMapping("testEnum/{code}")
    @ApiOperation(value = "测试前端传入枚举code自动转为后端的枚举对象", notes = "测试前端传入枚举code自动转为后端的枚举对象")
    public ResultBase<User> selectByPrimaryKey(@PathVariable("code") PolicyStatusEnum policyStatusEnum) {

        log.info("传入进来的code转换为枚举对象为{}", policyStatusEnum);
        User user = new User();
        user.setAge(21);
        user.setAddress("上海市浦东新区");
        return ResultBase.createResultBase(user);
    }
}
