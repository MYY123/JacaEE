package com.atguigu.bos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.bos.bean.Region;
import com.atguigu.bos.bean.Staff;
import com.atguigu.bos.service.RegionService;
import com.atguigu.bos.utils.PinYin4jUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class RegionController {
	@Autowired
	RegionService regionService;
	/**
	 * 查询区域，全部查询
	 * 查询所有的区域数据，返回json
	 */
	@ResponseBody    
	@RequestMapping("regionControllerListajax")
	public List regionControllerListajax(@RequestParam(value="q",required =false)String q  ){//模糊查询条件
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			list = regionService.findByQ(q);
		}else{
			list = regionService.findAll();
		}
		/*String[] excludes = new String[]{"subareas"};
		this.writeList2Json(list, excludes);*/
		return list;
	}
	/**
	 * 分区的分页查询
	 * @param myFile
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("regionPageQuery")
	@ResponseBody
	public PageInfo regionPageQuery(HttpServletRequest request){
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
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pages, rowss);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<Region> emps = regionService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo pageInfo = new PageInfo(emps, rowss);
		return pageInfo;
	}
	@RequestMapping("/regionImportXls")
	public String regionImportXls(@RequestParam("myFile") MultipartFile myFile,HttpServletResponse response)throws IOException{
		System.out.println("OriginalFilename: " + myFile.getOriginalFilename());
		System.out.println("InputStream: " + myFile.getInputStream());
		
		InputStream inputStream=myFile.getInputStream();
		String flag = "1";
		//使用ＰＯＩ解析Ｅｘｃｅｌ文件
		try{
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			//获得第一个sheet页
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<Region>();
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if(rowNum == 0){
					//第一行，标题行，忽略
					continue;
				}
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				Region region = new Region(id, province, city, district, postcode, null, null, null);
				
				city  = city.substring(0, city.length() - 1);
				String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
				String citycode = StringUtils.join(stringToPinyin, "");
				
				//简码---->>HBSJZCA
				province  = province.substring(0, province.length() - 1);
				district  = district.substring(0, district.length() - 1);
				String info = province + city + district;//河北石家庄长安
				String[] headByString = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(headByString, "");
				
				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				list.add(region);
			}
			regionService.saveBatch(list);
		}catch (Exception e) {
			flag = "0";
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(flag);
		//return NONE;
		return "";
	}
}
