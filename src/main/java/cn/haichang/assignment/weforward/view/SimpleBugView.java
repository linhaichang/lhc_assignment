package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Bug;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/26
 **/
@DocObject(description = "简略缺陷视图")
public class SimpleBugView {
    protected Bug m_Bug;
    public static SimpleBugView valueOf(Bug bug){
        return null == bug ? null : new SimpleBugView(bug);
    }

    public SimpleBugView(Bug m_Bug) {
        this.m_Bug = m_Bug;
    }

    @DocAttribute(description = "缺陷id")
    public String getId(){
        return m_Bug.getId().getOrdinal();
    }
    @DocAttribute(description = "Bug内容")
    public String getBugContent(){
        return m_Bug.getBugContent();
    }
    @DocAttribute(description = "Bug状态")
    public String getState(){
        return m_Bug.getState().getName();
    }
    @DocAttribute(description = "Bug严重性")
    public String  getSeverityDesc(){
        return m_Bug.getSeverity();
    }
    @DocAttribute(description = "测试人")
    public Set<String > getTesters(){
        return m_Bug.getTesters();
    }
    @DocAttribute(description = "处理人")
    public Set<String > getTestHandlers(){
        return m_Bug.getTestHandlers();
    }
    @DocAttribute(description = "创建人")
    public String getCreator(){
        return m_Bug.getCreator();
    }
    @DocAttribute(description = "最后处理时间")
    public Date getLastTime() throws ParseException {
        return m_Bug.getLastTime();
    }

}
