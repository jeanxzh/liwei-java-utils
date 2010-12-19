package com.zhz.project.common.util.ibatispage;

/**  
 * @author LiWei <a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>  
 * @version 创建时间：2010-5-3 下午03:18:05
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhz.project.common.util.alibaba.StringUtil;

/*******************************************************************************
 * Page的作用是实现分页功能， list是数据，count是总数，indexPage是当前页，totalPage是总页数
 * afterPage是当前页后面的页数，before是当前页前面的页数，row是每页的记录数
 ******************************************************************************/
//@Service
public class IbatisPage {

    private IbatisPageDAO ibatisPageDAO;

    private int           row;          // 每页记录数
    private int           pageAfterNum; // 页数显示前显示多少
    private int           pageBeforeNum; // 页数显示后显示多少
    /*******************************************************************************
     * 上面是注入的属性
     ******************************************************************************/
    private int           startIndex;   // 当前开始记录
    private int           indexPage;    // 当前页数
    private int           totalPage;    // 总页数
    private int           afterPage;    // 当前页标记前显示多少
    private int           beforePage;   // 当前页标记后显示多少
    private int           count;        // 总记录数

    /**
     * 判断页面所要显示的一些值
     * @param countSql 
     * @param pageSql 
     */
    public Map getPage(Map map, String countSql, String pageSql) {
        String currentPage = (String) map.get("indexPage");
        Integer pageNo = null;
        if (StringUtil.isNotBlank(currentPage)) {
            pageNo = new Integer(currentPage);
        }
        /**
         * 如果输入为空或者小于1,则开始记录为0,当前页为1 否则开始记录为(当前页数-1)*每页显示数+1
         */
        if (pageNo == null || pageNo.intValue() < 1) {
            startIndex = 0;
            indexPage = 1;
        } else {
            startIndex = (pageNo.intValue() - 1) * row;
            indexPage = pageNo.intValue();
        }

        /**
         * 得到总记录数
         */
        if (map == null) {
            map = new HashMap();
        }
        count = ibatisPageDAO.getTotalCount(countSql, map);
        /**
         * 判断总页数
         */
        if (count % row == 0) {
            totalPage = count / row;
        } else {
            totalPage = count / row + 1;
        }
        /**
         * 如果记录数为0
         */
        if (totalPage == 0)
            startIndex = 0;
        /**
         * 如果当前页输入大于总页数,则当前页等于总页数
         */
        else if (indexPage > totalPage) {
            startIndex = (totalPage - 1) * row + 1;
            indexPage = totalPage;
        }
        /**
         * 获取数据
         */
        if (map == null) {
            map = new HashMap();
        }
        map.put("startIndex", startIndex);
        map.put("row", row);
        List list = ibatisPageDAO.getSpeList(pageSql, map);
        /**
         * 判断该页数前应该显示多少数字
         */
        if (indexPage <= pageAfterNum) {
            afterPage = indexPage - 1;
        } else {
            afterPage = pageAfterNum;
        }
        /**
         * 得到该页数后应该显示多少数字
         */
        beforePage = indexPage + pageBeforeNum;
        /**
         * 如果显示的数字大于总页数,则等于总页数
         */
        if (beforePage > totalPage) {
            beforePage = totalPage;
        }
        /**
         * 写入Request
         */
        Map result = new HashMap<String, Object>();

        result.putAll(map);

        result.put("list", list);
        result.put("total", count);
        result.put("indexPage", indexPage);
        result.put("totalPage", totalPage);
        result.put("afterPage", afterPage);
        result.put("beforePage", beforePage);
        result.put("row", row);

        return result;
    }

    public void setPageAfterNum(int pageAfterNum) {
        this.pageAfterNum = pageAfterNum;
    }

    public void setPageBeforeNum(int pageBeforeNum) {
        this.pageBeforeNum = pageBeforeNum;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getAfterPage() {
        return afterPage;
    }

    public void setAfterPage(int afterPage) {
        this.afterPage = afterPage;
    }

    public int getBeforePage() {
        return beforePage;
    }

    public void setBeforePage(int beforePage) {
        this.beforePage = beforePage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRow() {
        return row;
    }

    public IbatisPageDAO getIbatisPageDAO() {
        return ibatisPageDAO;
    }

    public void setIbatisPageDAO(IbatisPageDAO ibatisPageDAO) {
        this.ibatisPageDAO = ibatisPageDAO;
    }

    public int getPageAfterNum() {
        return pageAfterNum;
    }

    public int getPageBeforeNum() {
        return pageBeforeNum;
    }
}