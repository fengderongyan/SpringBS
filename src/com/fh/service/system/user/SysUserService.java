package com.fh.service.system.user;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.service.base.BaseService;
import com.fh.util.BatchSql;
import com.fh.util.Const;
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
public class SysUserService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private RedisDaoImpl redisDaoImpl;

	public List<PageData> getSysUserList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("SysUserMapper.getSysUserList", pd);
	}

	public List<PageData> getChildRole(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("SysUserMapper.getChildRole", pd);
	}

	public PageData getRoleLev(PageData pd) throws Exception{
		
		return (PageData)dao.findForObject("SysUserMapper.getRoleLev", pd);
	}

	public List<PageData> loadOrgTree(PageData rpd) throws Exception{
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SysUserMapper.loadOrgTree", rpd);
	}

	/**
	 * 描述：保存用户信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	public Map saveUser() {
		try {
			PageData pd = this.getPageData();
			//保存之前先检查该组织是否限制下载人数
			PageData limitMap =  (PageData)dao.findForObject("SysUserMapper.checkLimitNum", pd);
			if(limitMap != null && limitMap.size() > 0){
				if("1".equals(limitMap.getString("is_limit"))){//若限制人数
					int limitNum = Integer.parseInt(limitMap.getString("limit_num"));//限制人数
					PageData countMap =  (PageData)dao.findForObject("SysUserMapper.findUserCountByOrgId", pd);
					int nowCnt = Integer.parseInt(countMap.getString("cnt"));//现有人数
					if(nowCnt >= limitNum){//现有人数大于限制人数
						return ResultUtils.returnWebError("人数已达到最大限制，无法再添加！");
					}
				}
			}
			pd.put("user_id", this.get32UUID());
			pd.put("password", new SimpleHash("SHA-1", pd.get("username"), "123456").toString());//默认密码123456
			pd.put("status", "0");//默认0为正常
			pd.put("head_img", "https://lnys.oss-cn-shanghai.aliyuncs.com/man.png");
			String certificate_end_date = pd.getString("certificate_end_date");
			String certificate_begain_date = pd.getString("certificate_begain_date");
			if("".equals(certificate_end_date)){
				pd.put("certificate_end_date", null);
			}
			if("".equals(certificate_begain_date)){
				pd.put("certificate_begain_date", null);
			}
			dao.save("SysUserMapper.saveUser", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
		
	}

	/**
	 * 描述：校验用户名是否重复
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	public String checkUsername() throws Exception{
		PageData pd = this.getPageData();
		PageData result = (PageData)dao.findForObject("SysUserMapper.checkUsername", pd);
		
		return result.getString("cnt");
	}

	public String checkPhone() throws Exception{
		PageData pd = this.getPageData();
		PageData result = (PageData)dao.findForObject("SysUserMapper.checkPhone", pd);
		return result.getString("cnt");
	}
	
	/**
	 * 描述：查询用户详情信息
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	public PageData getSysUserMap() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("SysUserMapper.getSysUserMap", pd);
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
			String certificate_end_date = pd.getString("certificate_end_date");
			if("".equals(certificate_end_date)){
				pd.put("certificate_end_date", null);
			}
			dao.update("SysUserMapper.saveEdit", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}

	public Map saveUpload(CommonsMultipartFile file, String org_id) {
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
            String save_dir = this.getRequest().getSession().getServletContext().getRealPath("/uploadFiles/uploadFile/system/user/");
            FileHelper fileHelper = new FileHelper();
            String fileName = save_dir + fileHelper.getToFileName(file.getOriginalFilename());
            fileHelper.copyFile(file, fileName);
            
            HSSFRow row = wb.getSheetAt(0).getRow(0);
            int cols = row.getPhysicalNumberOfCells();
            String[] columns = null;
            String table_columns = null;
            String insertSql = null;
            String[][] header = null;
            String err = "";
            //导入 和 覆盖 模版字段相同
            columns = new String[] {"用户名", "姓名", "性别", "电话", "岗位"};
            // 创建临时表
            table_columns = " ( username varchar(300), name varchar(300), sex varchar(300), " +
            				" phone varchar(300), station varchar(300), password varchar(300), user_id varchar(300))";
            // 插入数据到临时表
            insertSql = "insert into " + v_table_name.toLowerCase() + " values(?, ?, ?, ?, ?, ?, replace(UUID(),'-',''))";
            header = new String[][] { {"用户名", "username"}, {"姓名", "name"},
                        {"性别", "sex"}, {"电话", "phone"},
                        {"岗位", "station"}, {"错误信息", "error"}};
            resultMap = this.checkExcelHead(wb, resultMap, cols, columns);
            if(resultMap.size() > 0){
            	return resultMap;
            }
            BatchSql batchSql = new BatchSql();
            PageData colPd = new PageData();
            colPd.put("v_table_name", v_table_name);
            colPd.put("table_columns", table_columns);
            batchSql.addBatch("SysUserMapper.createTempTable", "create", colPd);
            
            HssfHelper hssfHelper = new HssfHelper();
            short[] map = new short[cols];
            for(int i = 0; i < cols; i++) {
                map[i] = Short.parseShort(Integer.toString(i));
            }
            PageData paramPd = new PageData();
            paramPd.put("v_table_name", v_table_name);
            this.importExcelToTableForUser(file, map, "SysUserMapper.insertTempTable", batchSql, paramPd, 0);
            
            try {
            	dao.batchExcute(batchSql);
                
                String processSql = "";
            	BatchSql batch = new BatchSql();
            	PageData paramSql = new PageData();
            	//错误信息预留字段
            	processSql = " alter table  " + v_table_name.toLowerCase() + " add (error varchar(400)) ";
            	paramSql.put("processSql", processSql);
            	batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
            	
            	processSql = " update " + v_table_name.toLowerCase() + 
           		     " set username = trim(username), " +
           		     "     name = trim(name), " +
           		     "     sex = trim(sex), " +
           		     "     phone = trim(phone), " +
           		     "     station = trim(station) " ;
            	paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	       //删除空数据
    	       processSql = " delete from " + v_table_name.toLowerCase() + 
    	          		     " where username = '' " +
    	          		     "   and name = '' " +
    	          		     "   and sex = '' " +
    	          		     "   and phone = '' " +
    	          		     "   and station = '' " ;
    	       paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	      //用户名不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	   		   		"    set error = '用户名不能为空！' " +
    	   		   		"  where username = '' " +
    	   		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	      batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	      
    	      //用户名在Excel中重复
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	   		   		" a, (select username from " + v_table_name.toLowerCase() + " group by username having count(1) > 1 ) b  " + 
    	      		   		"    set a.error = '用户名在Excel中重复' " +
    	      		   		"  where a.username = b.username " +
    	      		   		"    and a.error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	      //用户名已存在
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	  		   			" a, sys_user b  " + 
    	      		   		"    set a.error = '用户名已存在！' " +
    	      		   		"  where a.username = b.username " +
    	      		   		"    and b.status = 0 " +
    	      		   		"    and a.error is  null ";
    	      paramSql.put("processSql", processSql);
    	      batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	      
    	      //姓名不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	   		   		"    set error = '姓名不能为空！' " +
    	   		   		"  where name = '' " +
    	   		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	      //电话号码不能为空 
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '电话不能为空！' " +
    	      		   		"  where phone = '' " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	      //电话必须为数字
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '电话必须为数字！' " +
    	      		   		"  where  (phone REGEXP '[^0-9.]') = 1 " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	      //性别不能为空 
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '性别不能为空！' " +
    	      		   		"  where sex = '' " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	      //性别请填写男或女
    	      batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	      
    	      processSql = " update " +  v_table_name.toLowerCase() + 
    	    		   		" set error = '性别请填写男或女'"
    	    		   		+ " where sex not in('男', '女') "
    	    		   		+ "  and error is null ";
    	      paramSql.put("processSql", processSql);
    	      batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	      
    	      //岗位不能为空
    	      processSql = " update " + v_table_name.toLowerCase() + 
    	      		   		"    set error = '岗位不能为空！' " +
    	      		   		"  where station = '' " +
    	      		   		"    and error is  null ";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	       
    	      processSql = " insert into sys_user " +
    						  "(user_id,  " +
    						  "username,  " +
    						  "role_id,  " +
    						  "password,  " +
    						  "name,  " +
    						  "org_id,  " +
    						  "status,  " +
    						  "phone,  " +
    						  "sex,  " +
    						  "head_img, " + 
    						  "station,  " +
    						  "is_need_certificate, " + 
    						  "record_id, " +
    						  "record_date) " +
    						 "select user_id, " +
    						 		"username,  " +
    						 		"'9ce87784fd90448b9e4b6e03427979ac',  " +
    						 		"password,  " +
    						 		"name,  " +
    						 		"'"+ org_id + "',  " +
    						 		"0,  " +
    						 		"phone,  " +
    						 		"case when sex = '男' then 1 else 0 end, " +
    						 		"'https://lnys.oss-cn-shanghai.aliyuncs.com/man.png', " +
    						 		"station, " +
    						 		"0, " +
    						 		"'"+record_id + "', " +
    						 		"SYSDATE() " +
    						   "from  " + v_table_name + " where error is null";
    	      paramSql.put("processSql", processSql);
    	       batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql);
    	     //删除临时表无错数据
//    	     processSql = " delete from  " + v_table_name.toLowerCase() + " where error is  null ";
//    	     paramSql.put("processSql", processSql);
    	     String  processSql2 = " delete from  " + v_table_name.toLowerCase() + " where error is  null ";
    	     PageData paramSql2 = new PageData();
    	     paramSql2.put("processSql", processSql2);
    	     batch.addBatch("SysUserMapper.updateTempTable", "update", paramSql2);
    	     //执行导入
    	     try {
    	    	 dao.batchExcute(batch);
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    			resultMap.put("code", 1);
    			resultMap.put("errorMsg", "数据执行失败!");
    		}
    	     String dir = this.getRequest().getSession().getServletContext().getRealPath("/uploadFiles/downloadFile/system/user/");
             HSSFWorkbook wbExport = new HSSFWorkbook();
             PageData tablePd = new PageData();
             tablePd.put("v_table_name", v_table_name);
             List exp_list = (List)dao.findForList("SysUserMapper.selectTempTable", tablePd);
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
			dao.update("SysUserMapper.dropTempTable", tablePd);
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
            paramList.add(new SimpleHash("SHA-1", hssfHelper.getCellStringValue(row.getCell(map[0])), "123456").toString());
            paramList.add(this.get32UUID());
            System.out.println("paramList::" + paramList);
            paramPd.put("paramList", paramList);
            batchSql.addBatch(sqlMapper, "insert", paramPd);
        }
        return 1;
    }

	/**
	 * 描述：删除信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-06
	 */
	public Map delUser() {
		PageData pd = this.getPageData();
		try {
			dao.update("SysUserMapper.delUser", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}

	public Map resetPwd() {
		PageData pd = this.getPageData();
		String password = new SimpleHash("SHA-1", pd.getString("username"), "123456").toString();
		pd.put("password", password);
		try {
			dao.update("SysUserMapper.resetPwd", pd);
			String username = pd.getString("username");
			//redis key置空
			String userRedisKey = Const.TOKEN_KEY_FRIST + username + Const.TOKEN_KEY_END;
			boolean hasKey = redisDaoImpl.hasKey(userRedisKey);
			System.out.println("userRedisKey===============" + userRedisKey + ",hasKey============" + hasKey);
			if(hasKey)
				redisDaoImpl.delete(userRedisKey);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}

	public Map updatePwd() {
		PageData pd = this.getPageData();
		String oldPassword = pd.getString("oldPassword");
		String oldPassword2 = new SimpleHash("SHA-1", this.getUser().getUSERNAME(), oldPassword).toString();
		if(!oldPassword2.equals(this.getUser().getPASSWORD())){
			return ResultUtils.returnWebError("当前密码错误！");
		}
		pd.put("user_id", this.getUser().getUSER_ID());
		pd.put("password", new SimpleHash("SHA-1", this.getUser().getUSERNAME(), pd.getString("password")).toString());
		try {
			dao.update("SysUserMapper.updatePwd", pd);
			//redis key置空
			String userRedisKey = Const.TOKEN_KEY_FRIST + this.getUser().getUSERNAME() + Const.TOKEN_KEY_END;
			boolean hasKey = redisDaoImpl.hasKey(userRedisKey);
			System.out.println("userRedisKey===============" + userRedisKey + ",hasKey============" + hasKey);
			if(hasKey)
				redisDaoImpl.delete(userRedisKey);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError("网络异常！");
		}
	}
	
	
}
