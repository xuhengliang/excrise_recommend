package exam.util;

import exam.dto.StudentReport;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.util.List;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public abstract class ExcelUtil {

	/**
	 * 生成试卷成绩单
	 * @param reportData 数据
	 * @param path 保存的路径
	 * @throws IOException
	 * @return InputStream 返回生成的文件的输入流
	 */
	public static InputStream generateExcel(List<StudentReport> reportData, String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			return new FileInputStream(file);
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建一个sheet
		HSSFSheet sheet = wb.createSheet(reportData.get(0).getTitle() + "成绩单");
		//添加表头(第0行)
		HSSFRow row = sheet.createRow(0);
		//设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置第一行数据(表头)
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("学号");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("分数");
		cell.setCellStyle(style);
		//写入数据
		StudentReport data = null;
		for (int i = 0, l = reportData.size(); i < l;i ++) {
			data = reportData.get(0);
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(data.getSid());
			row.createCell(1).setCellValue(data.getName());
			row.createCell(2).setCellValue(data.getPoint());
		}
		//保存
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(baos);
		wb.write(fos);
		wb.close();
		return new ByteArrayInputStream(baos.toByteArray());
	}
	
}
