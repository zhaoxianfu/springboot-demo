package com.springboot.demo.service.impl;

import com.springboot.demo.annotation.LogAnnotation;
import com.springboot.demo.annotation.OperationLogDetail;
import com.springboot.demo.enums.OperationTypeEnum;
import com.springboot.demo.enums.OperationUnitEnum;
import com.springboot.demo.pojo.User;
import com.springboot.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:UserServiceImpl
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  13:54
 * @Version1.0
 **/

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    @OperationLogDetail(detail = "通过手机号[{{tel}}]获取用户名", level = 3,
            operationUnitEnum = OperationUnitEnum.USER, operationTypeEnum = OperationTypeEnum.SELECT)

    @LogAnnotation(detail = "通过手机号[{{tel}}]获取用户名", level = 3,
            operationUnitEnum = OperationUnitEnum.USER, operationTypeEnum = OperationTypeEnum.SELECT)

    public String findUserName(String tel) {

        return "赵宪福";
    }

    @Override
    public List<User> findUsers(String userName, String note) {
        return null;
    }
}
