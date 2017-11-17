package com.atguigu.bos.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.bean.Region;
import com.atguigu.bos.bean.Subarea;
import com.atguigu.bos.service.SubareaService;
import com.atguigu.bos.utils.FileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
				  
@RequestMapping("/subareaController")
@Controller
public class SubareaController {
	@Autowired
	SubareaService subareaService;
	
	@ResponseBody
	@RequestMapping("/listAjax")
	public List<Subarea> listAjax() throws IOException{
		List<Subarea> list = subareaService.findListNotAssociation();
		return list;
	}
	
	/**
	 * 使用POI写入Excel文件，提供下载
	 * @throws IOException 
	 */
	@RequestMapping("/exportXls")
	public String exportXls(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Subarea> list = subareaService.findAll();
		// 在内存中创建一个Excel文件，通过输出流写到客户端提供下载
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");

		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			Region region = subarea.getRegion();
			dataRow.createCell(3).setCellValue(region.getProvince()+region.getCity()+region.getDistrict());
		}
		String filename = "qwer.xls";
		String agent = request.getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		//一个流两个头
		ServletOutputStream out = response.getOutputStream();
		//String contentType = request.getServletContext().getMimeType(filename);
		response.setContentType("xls");
		response.setHeader("content-disposition", "attchment;filename="+filename);
		workbook.write(out);
		return "";
	}
	@RequestMapping("/add")
	public String add(Subarea subarea){
		subareaService.add(subarea);
		return "base/subarea";
		
		
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public PageInfo pageQuery(HttpServletRequest request,Subarea subarea){
		
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		
		int pages=1;
		int rowss=5;
		
		try {
			pages=Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			rowss=Integer.parseInt(rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageHelper.startPage(pages, rowss);
		List<Subarea> subareas = subareaService.pageQuery(subarea);
		PageInfo pageInfo = new PageInfo(subareas, rowss);
		return pageInfo;
	}
}
