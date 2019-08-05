package com.gobestsoft.survey.basic;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.survey.basic.enums.Order;
import com.gobestsoft.survey.basic.page.PageInfoBT;
import com.gobestsoft.survey.basic.tips.Tip;
import com.gobestsoft.survey.util.DateUtil;
import com.gobestsoft.survey.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class BaseController extends HttpBasic{

    protected final String LOGIN_TIP = "tip";

    /**
     * 成功数据
     *
     * @return
     */
    protected Tip success() {
        return new Tip(200, null, null);
    }

    protected Tip success(Object data) {
        return new Tip(200, null, data);
    }

    /**
     * 成功数据
     *
     * @return
     */
    protected Tip fail() {
        return new Tip(500, "服务器异常", null);
    }

    /**
     * 成功数据
     *
     * @return
     */
    protected Tip fail(String message) {
        return new Tip(500, message, null);
    }

    /**
     * 成功数据
     *
     * @return
     */
    protected Tip tip(int code, String message, Object data) {
        return new Tip(code, message, data);
    }


    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }

    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWrapper warpper) {
        return warpper.wrap();
    }


    /**
     * 获取默认分页
     *
     * @return
     */
    public Page defaultPage() {
        HttpServletRequest request = getHttpServletRequest();
        int limit = 10;
        int current = 0;
        if (StringUtils.isNumeric(request.getParameter("limit"))) {
            limit = Integer.valueOf(request.getParameter("limit"));
        }
        if (StringUtils.isNumeric(request.getParameter("page"))) {
            current = Integer.valueOf(request.getParameter("page"));
        }
        String sort = request.getParameter("sort");         //排序字段名称
        String order = request.getParameter("order");       //asc或desc(升序或降序)
        if (ToolUtil.isEmpty(sort)) {
            Page page = new Page(current, limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page page = new Page(current, limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }


    /**
     * 移除map中的键值对
     */
    public void removeValue(Map map, String key){
        if(map!=null&&map.containsKey(key)){
            map.remove(key);
        }
    }
    public void removeValue(List<Map> list,String key){
        for(Map m:list){
            removeValue(m,key);
        }
    }
    public void removeValue(List<Map> list,String[] keys){
        if(keys==null|| keys.length==0){
            return;
        }
        for(Map<String,Object> m:list){
            Iterator<Map.Entry<String,Object>> iterator = m.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                for(String key:keys){
                    if(key.equals(next.getKey())){
                        iterator.remove();
                    }
                }

            }
        }
    }

    /**
     * 处理后台日期为正常格式
     * @param oldDateStr
     * @param newPattern
     * @return
     */
    public String getNortDate(String oldDateStr,String newPattern){
        if(StringUtils.isEmpty(oldDateStr)){
            return "";
        }
        if(StringUtils.isEmpty(newPattern)){
            newPattern  = "yyyy-MM-dd";
        }

        return DateUtil.parseAndFormat(oldDateStr,"yyyyMMddHHmmss",newPattern);
    }
    public String getNortDate(String oldDateStr){
        return getNortDate(oldDateStr,null);
    }

}
