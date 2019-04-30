package com.springboot.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @ClassName:ValidatorDTO
 * @Despriction: 用来验证入参校验JSR303的DTO对象
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  11:28
 * @Version1.0
 **/

@Data
public class ValidatorDTO {

    @NotNull(message = "id不能为空")
    private Long id;

    @Future(message = "需要将来的一个日期")     //只能是将来的一个日期
//    @Past           只能是过去的一个日期
/*    @DateTimeFormat(pattern = "yyyy-MM-dd")  */ //日期格式转换,将yyyy-MM-dd格式的字符串转为日期类型,如果是属性对应的话,在springMVC配置中配置,如果是json的话,一般在jackson里面配置,一般不在这里配置
    @NotNull(message = "日期对象不能为空")
    private Date date;

    @NotNull(message = "doubleValue属性不能为空")
    @DecimalMin(value = "0.1", message = "最小值为0.1")         //指明double类型的最小值为0.1
    @DecimalMax(value = "100000.00", message = "最大值为100000.00")   //指明double类型的最大值为100000.00
    private Double doubleValue;

    @NotNull(message = "integer不能为空")
    @Min(value = 1, message = "最小值为1")          //指明Integer类型的最小值为1
    @Max(value = 100, message = "最大值为100")      //指明Integer类型的最大值为100
    private Integer integer;

    @Range(min = 1, max = 888, message = "范围为1到9999")      //限定范围
    private Integer range;

    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 20, max = 30, message = "字符串的长度要求20到30之间")
    private String size;

}
