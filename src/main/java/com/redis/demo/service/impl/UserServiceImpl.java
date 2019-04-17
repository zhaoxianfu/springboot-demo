package com.redis.demo.service.impl;

import com.redis.demo.annotation.LogAnnotation;
import com.redis.demo.annotation.OperationLogDetail;
import com.redis.demo.enums.OperationTypeEnum;
import com.redis.demo.enums.OperationUnitEnum;
import com.redis.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName:UserServiceImpl
 * @Despriction: //TODO
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  13:54
 * @Version1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    @Override
    @OperationLogDetail(detail = "通过手机号[{{tel}}]获取用户名", level = 3,
            operationUnitEnum = OperationUnitEnum.USER, operationTypeEnum = OperationTypeEnum.SELECT)
    @LogAnnotation(des = "注解记录日志")
    public String findUserName(String tel) {
        return "赵宪福";
    }
}
