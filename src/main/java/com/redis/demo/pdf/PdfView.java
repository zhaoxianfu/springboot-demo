package com.redis.demo.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName:PdfView
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  15:34
 * @Version1.0
 **/

public class PdfView extends AbstractPdfView {

    private PdfExportService pdfExportService;

    //创建PDF导出接口

    public PdfView(PdfExportService pdfExportService) {
        this.pdfExportService = pdfExportService;
    }

    /**
     * 调用接口实现
     *
     * @param model
     * @param document
     * @param writer
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        pdfExportService.make(model, document, writer, request, response);
    }
}
