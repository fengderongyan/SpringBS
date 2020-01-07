package com.fh.service.exammanage;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.service.base.BaseService;
import com.fh.util.BatchSql;
import com.fh.util.DateHelper;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.excel.FileHelper;
import com.fh.util.excel.HssfHelper;

/** 系统用户
 * @author fh313596790qq(青苔)
 */
@Service
public class QuestionInfoService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：获取题库列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getQuestionList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("QuestionInfoMapper.getQuestionList", pd);
	}
	
	/**
	 * 描述：获取行业类别
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getIndustryList() throws Exception{
		PageData pd = new PageData();
		pd.put("data_type_code", "1001");
		return (List<PageData>)dao.findForList("DdwMapper.getItemListByType", pd);
	}
	
	/**
	 * 描述：获取县区列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getCountyList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_lev", "3");
		return (List<PageData>) dao.findForList("QuestionInfoMapper.getOrgListByOrgLev", pd);
	}

	/**
	 * 描述：获取园区列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getAreaList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_lev", "4");
		return (List<PageData>) dao.findForList("QuestionInfoMapper.getOrgListByOrgLev", pd);
	}
	
	/**
	 * 描述：获取企业列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getOrgList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_lev", "5");
		return (List<PageData>) dao.findForList("QuestionInfoMapper.getOrgListByOrgLev", pd);
	}
	
	
	/**
	 * 描述：保存试题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public Map saveQuestion(){
		PageData pd = this.getPageData();
		pd.put("id", this.get32UUID());
		pd.put("record_id", this.getUser().getUSER_ID());
		pd.put("status", "1");
		try {
			
			String org_id = pd.getString("org_id");
			if(StringUtils.isNotBlank(org_id)){//企业定制题目
				PageData industryPd = (PageData)dao.findForObject("QuestionInfoMapper.getIndustryByOrgId", pd);
				pd.put("industry", industryPd.getString("industry"));
			}
			
			dao.save("QuestionInfoMapper.saveQuestion", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	
	public PageData getQuestionMap() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("QuestionInfoMapper.getQuestionMap", pd);
	}

	/**
	 * 描述：保存编辑信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-05
	 */
	public Map saveEdit() {
		PageData pd = this.getPageData();
		try {
			String org_id = pd.getString("org_id");
			if(StringUtils.isNotBlank(org_id)){//企业定制题目
				PageData industryPd = (PageData)dao.findForObject("QuestionInfoMapper.getIndustryByOrgId", pd);
				pd.put("industry", industryPd.getString("industry"));
			}
			dao.update("QuestionInfoMapper.saveEdit", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	
	/**
	 * 描述：删除试题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public Map delQuestion() {
		PageData pd = this.getPageData();
		try {
			dao.update("QuestionInfoMapper.delQuestion", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}

	public Map impResult(CommonsMultipartFile file) {
		String record_id = this.getUser().getUSER_ID();
		String v_table_name = "ls_" + DateHelper.getImageName();
		Map resultMap = new HashMap();
		if(file == null) {
			resultMap.put("code", 1);
			resultMap.put("errorMsg", "上传文件不存在!");
			return resultMap;
        }
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet1 = wb.getSheetAt(0);
            int num = sheet1.getLastRowNum();
            if(num < 1) {
            	resultMap.put("code", 1);
    			resultMap.put("errorMsg", "excel中无数据!");
    			return resultMap;
            }
            String save_dir = this.getRequest().getSession().getServletContext().getRealPath("/uploadFiles/uploadFile/exammanage/questioninfo/");
            FileHelper fileHelper = new FileHelper();
            String fileName = save_dir + fileHelper.getToFileName(file.getOriginalFilename());
            fileHelper.copyFile(file, fileName);
            
            HSSFRow row = wb.getSheetAt(0).getRow(0);
            int cols = row.getPhysicalNumberOfCells();
            String[] columns = null;
            String table_columns = null;
            String[][] header = null;
            String err = "";
            //导入 和 覆盖 模版字段相同
            columns = new String[] {"试题类型", "题目", "教材来源", "选项A", "选项B", "选项C", "选项D", "选项E", "正确答案", "适用行业"};
            // 创建临时表
            table_columns = " ( type varchar(300), question_info varchar(2000), source varchar(500), " +
            				" optionA varchar(500), optionB varchar(500), optionC varchar(500), optionD varchar(500), optionE varchar(500), "
            				+ " answer varchar(50), industry varchar(100), id varchar(255))";
            header = new String[][] { {"试题类型", "type"}, {"题目", "question_info"},
                        {"教材来源", "source"}, {"选项A", "optionA"},
                        {"选项B", "optionB"}, {"选项C", "optionC"},
                        {"选项D", "optionD"}, {"选项E", "optionE"},
                        {"答案", "answer"}, {"适用行业", "industry"},
                        {"错误信息", "error"}};
            resultMap = this.checkExcelHead(wb, resultMap, cols, columns);
            if(resultMap.size() > 0){
            	return resultMap;
            }
            BatchSql batchSql = new BatchSql();
            PageData colPd = new PageData();
            colPd.put("v_table_name", v_table_name);
            colPd.put("table_columns", table_columns);
            batchSql.addBatch("QuestionInfoMapper.createTempTable", "create", colPd);
            
            HssfHelper hssfHelper = new HssfHelper();
            short[] map = new short[cols];
            for(int i = 0; i < cols; i++) {
                map[i] = Short.parseShort(Integer.toString(i));
            }
            PageData paramPd = new PageData();
            paramPd.put("v_table_name", v_table_name);
            this.importExcelToTableForUser(file, map, "QuestionInfoMapper.insertTempTable", batchSql, paramPd, 0);
            
            try {
            	dao.batchExcute(batchSql);
                
                String processSql = "";
            	BatchSql batch = new BatchSql();
            	PageData paramSql = new PageData();
            	//错误信息预留字段
            	processSql = " alter table  " + v_table_name.toLowerCase() + " add (error varchar(400)) ";
            	paramSql.put("processSql", processSql);
            	batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
            	
            	processSql = " update " + v_table_name.toLowerCase() + 
           		     " set type = trim(type), " +
           		     "     question_info = trim(question_info), " +
           		     "     source = trim(source), " +
           		     "     optionA = trim(optionA), " +
           		     "     optionB = trim(optionB), " +
	            	"     optionC = trim(optionC), " +
	            	"     optionD = trim(optionD), " +
	            	"     optionE = trim(optionE), " +
	            	"     answer = trim(answer), " +
	            	"     industry = trim(industry) " ;
            	paramSql.put("processSql", processSql);
    	       batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	       
    	       //删除空数据
    	       processSql = " delete from " + v_table_name.toLowerCase() + 
    	          		     " where type = '' " +
    	          		     "   and question_info = '' " +
    	          		     "   and source = '' " +
    	          		     "   and optionA = '' " +
    	          		     "   and optionB = '' " +
			    	       "   and optionC = '' " +
			    	       "   and optionD = '' " +
			    	       "   and optionE = '' " +
			    	       "   and answer = '' " +
			    	       "   and answer = '' " +
			    	       "   and industry = '' ";
    	       paramSql.put("processSql", processSql);
    	       batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	       
    	      //试题类型不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	   		   		"    set error = '试题类型不能为空！' " +
    	   		   		"  where type = '' " +
    	   		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	      batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	      
    	      processSql = " update " + v_table_name.toLowerCase() + 
  	   		   		"    set error = '试题类型请填写单选、多选、判断！' " +
  	   		   		"  where type not in('单选', '多选', '判断') " +
  	   		   		"    and error is  null ";
	  	      paramSql.put("processSql", processSql);
	  	      batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	      
    	      //题目不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	   		   		"    set error = '题目不能为空！' " +
    	   		   		"  where question_info = '' " +
    	   		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	       
    	      //教材来源不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '教材来源不能为空！' " +
    	      		   		"  where source = '' " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	       
    	      //'正确答案不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '正确答案不能为空！' " +
    	      		   		"  where answer = '' " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	      batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	      
    	      //适用行业不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '适用行业不能为空！' " +
    	      		   		"  where industry = '' " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	      batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	      
    	      
    	       
    	      processSql = " insert into t_question_info " +
    						  "(id,  " +
    						  "industry,  " +
    						  "source,  " +
    						  "type,  " +
    						  "question_info,  " +
    						  "status,  " +
    						  "optionA,  " +
    						  "optionB,  " +
    						  "optionC,  " +
    						  "optionD, " + 
    						  "optionE,  " +
    						  "answer, " + 
    						  "record_id, " +
    						  "record_date) " +
    						 "select a.id, " +
    						 		"IFNULL(b.dd_item_code, '100100'),  " +
    						 		"source,  " +
    						 		"case when type = '单选' then '100301' when type = '多选' then '100302' when type = '判断' then '100303' end ,  " +
    						 		"question_info,  " +
    						 		"'1',  " +
    						 		"optionA,  " +
    						 		"optionB,  " +
    						 		"optionC, " +
    						 		"optionD, " +
    						 		"optionE, " +
    						 		"answer, " +
    						 		"'"+record_id + "', " +
    						 		"SYSDATE() " +
    						   "from  " + v_table_name + " a left join (select * from t_ddw where data_type_code = '1001')  b on a.industry = b.dd_item_name "
    						   		+ "  where error is null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql);
    	     //删除临时表无错数据
//    	     processSql = " delete from  " + v_table_name.toLowerCase() + " where error is  null ";
//    	     paramSql.put("processSql", processSql);
    	     String  processSql2 = " delete from  " + v_table_name.toLowerCase() + " where error is  null ";
    	     PageData paramSql2 = new PageData();
    	     paramSql2.put("processSql", processSql2);
    	     batch.addBatch("QuestionInfoMapper.updateTempTable", "update", paramSql2);
    	     //执行导入
    	     try {
    	    	 dao.batchExcute(batch);
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    			resultMap.put("code", 1);
    			resultMap.put("errorMsg", "数据执行失败!");
    		}
    	     String dir = this.getRequest().getSession().getServletContext().getRealPath("/uploadFiles/downloadFile/exammanage/questioninfo/");
             HSSFWorkbook wbExport = new HSSFWorkbook();
             PageData tablePd = new PageData();
             tablePd.put("v_table_name", v_table_name);
             List exp_list = (List)dao.findForList("QuestionInfoMapper.selectTempTable", tablePd);
             if(exp_list != null && exp_list.size() > 0){
            	 wbExport = new HssfHelper().export(exp_list, header, "sheet1");
                 String to_file_name = v_table_name.toLowerCase() + ".xls";
                 File outFile = new File(dir, to_file_name);
                 try {
                     FileOutputStream outStream = new FileOutputStream(outFile);
                     wbExport.write(outStream);
                     outStream.close();
                     resultMap.put("code", 2);
                     resultMap.put("to_file_name", to_file_name);
                 }
                 catch (Exception e) {
                	 e.printStackTrace();
                 }
             }else{
            	 resultMap.put("code", 0);
                 resultMap.put("errorMsg", "导入成功");
             }
				
		} catch (Exception e) {
				e.printStackTrace();
		}finally {
			PageData tablePd = new PageData();
			tablePd.put("v_table_name", v_table_name);
			dao.update("QuestionInfoMapper.dropTempTable", tablePd);
		}
            
         
		 
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", 1);
			resultMap.put("errorMsg", "excel解析错误!");
			return resultMap;
		}
		
		
		
		
		
		
		return resultMap;
	}
	
	
	public Map checkExcelHead(HSSFWorkbook wb, Map resultMap, int cols, String[] columns) {
        HssfHelper hssfHelper = new HssfHelper();
        HSSFRow row = wb.getSheetAt(0).getRow(0);
        if(row == null) {
        	resultMap.put("code", 1);
        	resultMap.put("errorMsg", "导入格式错误：没有标题行!");
        } else if(cols != columns.length) {
        	resultMap.put("code", 1);
        	resultMap.put("errorMsg", "导入格式错误：导入的Excel必须为" + columns.length + "列！");
        }
        String cellValues = ",";
        for(short i = 0; i < columns.length; i++) {
            HSSFCell cell = row.getCell(i);
            String cellValue = hssfHelper.getCellStringValue(cell);
            cellValues += cellValue + ",";
            if(cellValue.equals("")) {
            	resultMap.put("code", 1);
            	resultMap.put("errorMsg", "标题行格式错误：导入第" + (i + 1) + "列列名不能为空！");
            } else if(!cellValue.equals(columns[i])) {
            	resultMap.put("code", 1);
            	resultMap.put("errorMsg", "标题行格式错误：导入第" + (i + 1) + "列列名必须为“" + columns[i] + "”！");
            }
        }
        return resultMap;
    }
	
	public int importExcelToTableForUser(CommonsMultipartFile file, short[] map, String sqlMapper,
            BatchSql batchSql, PageData paramPd, int row_index) {
		HssfHelper hssfHelper = new HssfHelper();
        if (file == null) {
            return -1;
        }
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(file.getInputStream());
        } catch (Exception ex) {
            return -2;
        }

        HSSFSheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            return -3;
        }

        Iterator iter = sheet.rowIterator();
        for (int i = 0; iter.hasNext(); i++) {
            HSSFRow row = (HSSFRow) iter.next();
            if (i <= row_index) {
                continue;
            }
            Object[] params = new Object[map.length];
            List paramList = new ArrayList();
            for (int j = 0; j < map.length; j++) {
                HSSFCell cell = row.getCell(map[j]);
                paramList.add(hssfHelper.getCellStringValue(cell));
            }
            paramList.add(this.get32UUID());
            paramPd.put("paramList", paramList);
            batchSql.addBatch(sqlMapper, "insert", paramPd);
        }
        return 1;
    }


}
