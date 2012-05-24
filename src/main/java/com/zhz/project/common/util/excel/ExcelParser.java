package com.zhz.project.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zhz.project.common.util.alibaba.StringUtil;
import com.zhz.project.common.util.date.DateUtils;
import com.zhz.project.common.util.result.Result;
import com.zhz.project.common.util.result.ResultSupport;

/**
 * 
 * <p>
 * 通用Excel文件解析类,只支持2003
 * <p>
 * <code>ExcelParser excelParser=new ExcelParser("d:/xx.xls"){
 * <br>     public Result parseOneRow(int rowNum) {
   <br>         Result result = new ResultSupport();
   <br>            result.setSuccess(false);
   <br>             String errorMessage = "";
   <br>            boolean state = true;
   <br>
   <br>           Map map = new HashMap();
   <br>            //第二行第1列
   <br>            map.put("id1", getStringCellValue(rowNum, 0).trim());
   <br>            if (map.get("id1") == null) {
   <br>              state = false;
   <br>               errorMessage += "<p>处理文件错误，第" + rowNum + "行，不能为空\n";
   <br>           }
   <br>          //第二行第2列
   <br>          map.put("id2", getStringCellValue(rowNum, 1).trim());
   <br>          if (map.get("id2") == null) {
   <br>             state = false;
   <br>              errorMessage += "<p>处理文件错误，第" + rowNum + "行，不能为空\n";
   <br>          }
   <br>
   <br>         result.setSuccess(state);
   <br>         if (state) {
   <br>            result.setDefaultModel(map);
   <br>        } else {
   <br>            result.setDefaultModel(ERROR_MESSAGE, errorMessage);
   <br>         }
   <br>        return result;
   <br>    }
   <br>   };
 * <br>
 * }</code>
 * <br>   try{
 * <code>    Result result = excelParser.parseContent(1, excelParser.getLastRowNum());</code>
 * <br>
 * <code>   List list=(List)result.getDefaultModel()   <br>
 *         }catch (Exception e) {   <br>
 *        String message = excelParser.getParseErrors().toString(); <br>
 *        } <br>
 *</code> 
 *
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version ExcelParser.java, v 0.1 May 24, 2012 3:33:20 PM
 */
public class ExcelParser {
    private static Logger         logger        = Logger.getLogger(ExcelParser.class);

    /** -- 解析出来的对象的集合 -- */
    private List<Object>          list          = new ArrayList<Object>();

    /** -- 解析过程中的错误列表 -- */
    private Errors                errors        = new Errors();

    private List<Object>          hssfRows      = new ArrayList<Object>();

    /** excel HSSFWorkbook*/
    private HSSFWorkbook          wb            = null;

    /** excel 表单*/
    private HSSFSheet             sheet         = null;

    /** 是否解析过excel*/
    private boolean               parsed        = false;

    /** 错误信息 */
    protected static final String ERROR_MESSAGE = "errorMessage";

    /**
     * 根据给定的文件名解析Excel文件
     * 
     * @param fileName
     */
    public ExcelParser(String fileName) {
        this.parserExcelInternal(new File(fileName));
    }

    /**
     * 根据一个File对象解析Excel文件
     * 
     * @param file
     */
    public ExcelParser(File file) {
        this.parserExcelInternal(file);
    }

    /**
     * 根据一个输入流解析Excel文件
     * 
     * @param in
     */
    public ExcelParser(InputStream in) {
        this.parseExcelInternal(in);
    }

    /**
     * 解析Excel文件的内部方法，如果指定的文件不存在则生成一个文件没有找到的错误
     * 
     * @param file
     */
    private void parserExcelInternal(File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            this.parseExcelInternal(in);
        } catch (FileNotFoundException e) {
            errors.add("没有找到要处理的文件，请检查文件是否存在！");
        }
    }

    /**
     * 解析Excel文件的内部方法
     * <p>
     * 该方法屏蔽了处理过程中产生的异常，因为该异常对操作人员是没有任何意义的。
     * </p>
     * <p>
     * 程序在捕获异常的时候会按照异常的类型生成相应的ParseError对象，并最终把该信息显示给操作者。
     * </p>
     * <p>
     * 程序会对Excel的数据进行效验和融错，如果数据不合法，程序会记录该数据所在行、列，并把错误信息显示给操作者。
     * </p>
     * 
     * @param in
     */
    private void parseExcelInternal(InputStream in) {
        // 读取文件时发生错误，停止处理，返回一个＂读取文件错误信息＂
        if (isParsed()) {
            return;
        }
        init();
        try {
            wb = new HSSFWorkbook(in);
            in.close();
            sheet = wb.getSheetAt(0);
        } catch (Exception e) {
            logger.error("读取文件时发生错误，请检查文件格式！", e);
            errors.add("读取文件时发生错误，请检查文件格式！");
        }
    }

    /**
     * 得到最后一行的行号
     * 
     * @return
     */
    public int getLastRowNum() {
        return sheet.getLastRowNum();
    }

    /**
     * 取得解析过程中的错误列表
     * 
     * @return
     */
    public Errors getParseErrors() {
        return this.errors;
    }

    /**
     * 判断是否有错误信息
     * 
     * @return
     */
    public boolean hasErrors() {
        return this.errors.hasErrors();
    }

    /**
     * 取得解析出来的Object列表
     * 
     * @return
     */
    public List<Object> getParseResult() {
        return this.list;
    }

    /**
     * 判断当前行是否为空
     * 
     * @param row
     * @return
     */
    public boolean isRowNull(int row) {
        return getHSSFRow(row) == null;
    }

    /**
     * 判断当前列是否为空
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean isCellNull(int row, int col) {
        return getHSSFCell(row, col) == null;
    }

    /**
     * 判断当前行是否不为空
     * 
     * @param row
     * @return
     */
    public boolean isRowNotNull(int row) {
        return !isRowNull(row);
    }

    /**
     * 判断当前列是否不为空
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean isCellNotNull(int row, int col) {
        return !isCellNull(row, col);
    }

    /**
     * 加入一个错误信息
     * 
     * @param error
     */
    public void addError(String msg) {
        errors.add(msg);
    }

    /**
     * 加入一个错误信息
     * 
     * @param error
     */
    public void addError(int row, int col, String msg) {
        errors.add(row, col, msg);
    }

    /**
     * 向列表中增加一个解析出来的对象
     * 
     * @param obj
     */
    public void addObject(Object obj) {
        list.add(obj);
    }

    /**
     * 一个适配器
     * 
     * @param cell
     * @return
     */
    public String getStringCellValue(int row, int col) {
        return this.getStringCellValue(row, col, false);
    }

    /**
     * 得到单元格的类型
     * @param row 
     * @param col
     * @return
     */
    public int getCellType(int row, int col) {
        HSSFCell cell = getHSSFRow(row).getCell((short) col);
        if (cell == null) {
            return -1;
        }
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        int cellType = cell.getCellType();
        return cellType;
    }

    /**
     * 得到单元格的值(字符串类型)
     * 
     * @param cell
     * @return
     */
    public String getStringCellValue(int row, int col, boolean isDate) {
        String value = StringUtil.EMPTY_STRING;
        try {
            HSSFCell cell = getHSSFRow(row).getCell((short) col);
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                if (isDate) {
                    value = DateUtils.dtSimpleFormat(cell.getDateCellValue());
                } else {
                    value = new BigDecimal(cell.getNumericCellValue()).toString();
                }
            } else {
                value = cell.getStringCellValue();
            }
        } catch (Exception e) {
            value = StringUtil.EMPTY_STRING;
        }

        if (StringUtil.isEmpty(value)) {
            value = StringUtil.EMPTY_STRING;
        }

        return value;
    }

    /**
     * 得到单元格的值(数字类型)
     * 
     * @param cell
     * @return
     */
    public Double getNumberCellValue(int row, int col) {
        Double value = -0.0;
        try {
            HSSFCell cell = getHSSFRow(row).getCell((short) col);
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                value = cell.getNumericCellValue();
            }
        } catch (Exception e) {
            errors.add("读取单元格内容时发生错误" + e.getMessage());
        }
        return value;
    }

    /**
     * 得到单元格的值(日期类型)
     * 
     * @param cell
     * @return
     */
    public Date getDateCellValue(int row, int col) {
        Date value = null;
        try {
            HSSFCell cell = getHSSFRow(row).getCell((short) col);
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                value = cell.getDateCellValue();
            }
        } catch (Exception e) {
            errors.add("读取单元格内容时发生错误" + e.getMessage());
        }
        return value;
    }

    /**
     * 设置是否初始化
     * 
     * @param parsed
     */
    private void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    /**
     * 是否初始化
     * 
     * @return
     */
    private boolean isParsed() {
        return this.parsed;
    }

    /**
     * 取得Excel工作簿中的某一行
     * 
     * @param row
     * @return
     */
    private HSSFRow getHSSFRow(int row) {
        HSSFRow hssfRow = null;
        if (row > 0 && row <= hssfRows.size()) {
            hssfRow = (HSSFRow) hssfRows.get(row - 1);
        } else {
            hssfRow = sheet.getRow(row);
            if (hssfRow != null) {
                hssfRows.add(hssfRow);
            }
        }
        return hssfRow;
    }

    /**
     * 取得某一列
     * 
     * @param row
     * @param col
     * @return
     */
    private HSSFCell getHSSFCell(int row, int col) {
        return getHSSFRow(row).getCell((short) col);
    }

    /**
     * 验证是否为空
     * 
     * @param cell
     * @return
     */
    public boolean isNotEmpty(String value) {
        return value != null && value.length() > 0;
    }

    /**
     * 验证日期格式是否正确
     * 
     * @param cell
     * @return
     */
    public boolean isDate(String value) {
        Pattern pattern = Pattern
            .compile("^\\d{4}(\\-|/)(0?[1-9]|1[0-2])(\\-|/)(0?[1-9]|[1-2]\\d|3[0-1])$");
        return pattern.matcher(value).matches();
    }

    /**
     * 初始化ExcelParser
     * 
     * 每个ExcelParser只能初始化一次，如果用当前的ExcelParser实例再重新new一次，那么当前的ExcelParser实例中保存的解析结果就会丢失
     * 
     * 所以强制每个ExcelParser只能初始化一次
     */
    private void init() {
        list = new ArrayList<Object>();
        hssfRows = new ArrayList<Object>();
        errors = new Errors();
        wb = null;
        sheet = null;
        setParsed(true);
    }

    public HSSFSheet getSheet() {
        return sheet;
    }

    /**
     * <p>解析excel从某行（包含）到某行（包含）的内容
     * <p>行数从0开始
     * <p>使用此方法前，需要先重写parseOneRow方法
     * <p>added by anorld.zhangm
     * @param startRow
     * @param endRow
     */
    public Result parseContent(int startRow, int endRow) {
        Result result = new ResultSupport();
        boolean state = true;
        String errorMessage = "";
        if (isParsed() && wb != null && sheet != null) {
            for (int i = startRow; i <= endRow; i++) {
                //解析一行
                Result oneRowResult = parseOneRow(i);
                if (!oneRowResult.isSuccess()) {
                    state = false;
                    errorMessage += oneRowResult.getDefaultModel() + "\n";
                } else {
                    //获取这一行的解析结果
                    Object oneRow = oneRowResult.getDefaultModel();
                    if (oneRow != null) {
                        addObject(oneRow);
                    }
                }
            }
            //记录所有解析结果
            if (state) {
                result.setSuccess(true);
                result.setDefaultModel(getParseResult());
            } else {
                result.setSuccess(false);
                result.setDefaultModel(errorMessage);
            }
        } else {
            result.setSuccess(false);
            result.setDefaultModel(ERROR_MESSAGE, "解析器未初始化！");
        }
        return result;
    }

    /**
     * <p>You should Override This Method!!!
     * <p>解析一行的内容
     * <p>added by anorld.zhangm
     * @param rowNum
     * @return result
     * result.setSuccess(true);
     * result.setDefaultModel(row); 
     */
    public Result parseOneRow(int rowNum) {
        return null;
    }
}
