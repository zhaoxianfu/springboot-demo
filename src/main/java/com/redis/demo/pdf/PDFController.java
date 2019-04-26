package com.redis.demo.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.redis.demo.pojo.User;
import com.redis.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.awt.*;
import java.util.List;

/**
 * @ClassName:PDFController
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  15:43
 * @Version1.0
 **/

@Slf4j
@RequestMapping("user")
public class PDFController {

    @Autowired
    private UserService userService;

    /**
     * 导出接口
     *
     * @param userName
     * @param note
     * @return
     */
    @GetMapping("export/pdf")
    public ModelAndView exportPdf(String userName, String note) {

        //查询用户信息列表
        List<User> users = userService.findUsers(userName, note);
        //定义PDF视图
        View pdfView = new PdfView(exportService());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(pdfView);
        modelAndView.addObject("userList", users);
        return modelAndView;
    }

    /**
     * 导出自定义的PDF
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private PdfExportService exportService() {
        //使用Lamda表达式自定义导出
        return (model, document, writer, request, response) -> {
            try {
                //A4纸张
                document.setPageSize(PageSize.A4);
                //标题
                document.addTitle("用户信息");
                //换行
                document.add(new Chunk("\n"));
                //表格, 3列
                PdfPTable table = new PdfPTable(3);
                //单元格
                PdfPCell cell = null;
                //字体,定义为蓝色加粗
                Font f8 = new Font();
                f8.setColor(Color.BLUE);
                f8.setStyle(Font.BOLD);
                //设置标题
                new PdfPCell(new Paragraph("id", f8));
                //居中对齐
                cell.setHorizontalAlignment(1);
                //将单元格加入表格
                table.addCell(cell);

                //设置标题
                new PdfPCell(new Paragraph("user_name", f8));
                //居中对齐
                cell.setHorizontalAlignment(1);
                //将单元格加入表格
                table.addCell(cell);

                //设置标题
                new PdfPCell(new Paragraph("note", f8));
                //居中对齐
                cell.setHorizontalAlignment(1);
                //将单元格加入表格
                table.addCell(cell);

                //获取数据模型中的用户列表
                List<User> userList = (List<User>) model.get("userList");
                for (User user : userList) {
                    document.add(new Chunk("\n"));
                    cell = new PdfPCell(new Paragraph(user.getId() + ""));
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(user.getUserName()));
                    table.addCell(cell);
                    String note = user.getNote() == null ? "" : user.getNote();
                    cell = new PdfPCell(new Paragraph(note));
                    table.addCell(cell);
                }
                //在文档中增加表格
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        };
    }

}