package com.springboot.demo.provider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:FileController
 * @Despriction: 文件上传控制器Controller
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/27  0:13
 * @Version1.0
 **/

@Slf4j
@Controller
@RequestMapping("file")
@Api(tags = "文件上传接口", description = "文件上传接口")
public class FileController {

    /**
     * 打开文件上传请求页面--响应到/WEB-INF/jsp/file/upload.jsp页面上
     *
     * @return
     */
    @GetMapping("upload/request")
    @ApiOperation(value = "打开文件上传请求页面--响应到/WEB-INF/jsp/file/upload.jsp页面上", notes = "打开文件上传请求页面--响应到/WEB-INF/jsp/file/upload.jsp页面上")
    public String uploadPage() {
        return "/file/updaload";
    }

    /**
     * 处理上传文件的结果
     *
     * @param flag
     * @param msg
     * @return
     */
    private Map<String, Object> dealResultMap(boolean flag, String msg) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        hashMap.put("flag", flag);
        hashMap.put("msg", msg);
        return hashMap;
    }

    /**
     * 第一种方式:使用HttpServletRequest方式作为参数
     *
     * @param request
     * @return
     */
    @PostMapping("upload/request")
    @ResponseBody
    @ApiOperation(value = "第一种方式:使用HttpServletRequest方式作为参数", notes = "第一种方式:使用HttpServletRequest方式作为参数")
    public Map<String, Object> uploadRequest(HttpServletRequest request) {

        boolean flag = false;

        MultipartHttpServletRequest multipartHttpServletRequest = null;
        //将HttpServletRequest转换为MultipartHttpServletRequest接口对象,用这个进行操作文件,这个任务是通过MultipartResolver接口实现的,springboot默认使用StandardServletMultipartResolver进行实现类操作的
        if (request instanceof MultipartHttpServletRequest) {
            multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        } else {
            return dealResultMap(Boolean.FALSE, "文件上传失败");
        }
        //获取MultipartFile文件信息---file11是上传文件的属性名称
        MultipartFile mf = multipartHttpServletRequest.getFile("file11");
        //获取上文件的文件名称
        String filename = mf.getOriginalFilename();

        File file = new File(filename);
        try {
            //保存文件---将获取的文件对象转换为我们使用的文件对象
            mf.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(Boolean.FALSE, "文件上传失败");
        }
        return dealResultMap(Boolean.TRUE, "文件上传成功");
    }

    /**
     * 第二种方式:使用SpringMVC的MutipartFile---SpringMVC封装一层的请求
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("upload/mutipart")
    @ResponseBody
    @ApiOperation(value = "第二种方式:使用SpringMVC的MutipartFile---SpringMVC封装一层的请求", notes = "第二种方式:使用SpringMVC的MutipartFile---SpringMVC封装一层的请求")
    public Map<String, Object> uploadMultipartFile(@RequestParam("file") MultipartFile multipartFile) {
        //获取上文件的文件名称
        String fileName = multipartFile.getOriginalFilename();

        File file = new File(fileName);
        try {
            //保存文件---将获取的文件对象转换为我们使用的文件对象
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(Boolean.FALSE, "文件上传失败");
        }
        return dealResultMap(Boolean.TRUE, "文件上传成功");
    }

    /**
     *
     * 第三种方式,根据原生的servlet里面的入参
     * @param file
     * @return
     */
    @PostMapping("upload/part")
    @ResponseBody
    @ApiOperation(value = "第三种方式,根据原生的servlet里面的入参", notes = "第三种方式,根据原生的servlet里面的入参")
    public Map<String, Object> uploadPart(Part file) {
        //获取提交的文件名称
        String fileName = file.getSubmittedFileName();
        try {
            file.write(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(Boolean.FALSE, "文件上传失败");
        }
        return dealResultMap(Boolean.TRUE, "文件上传成功");
    }
}
