package com.springboot.demo.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName:PdfExportService
 * @Despriction:  PDF导出接口,所有的controller进行统一接口,自己去实现自己逻辑
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  15:30
 * @Version1.0
 **/

public interface PdfExportService {

    void make(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response);

}
