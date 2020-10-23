package cn.haichang.assignment.weforward.param;

import cn.weforward.data.log.BusinessLog;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.Date;

/**
 * @author HaiChang
 * @date 2020/10/23
 **/
@DocObject(description = "日志视图")
public class LogView {

    protected BusinessLog m_Log;

    public LogView(BusinessLog log) {
        m_Log = log;
    }

    public static LogView valueOf(BusinessLog src) {
        return null == src ? null : new LogView(src);
    }

    @DocAttribute(description = "日志时间")
    public Date getTime() {
        return m_Log.getTime();
    }

    @DocAttribute(description = "操作人")
    public String getAuthor() {
        return m_Log.getAuthor();
    }

    @DocAttribute(description = "行为")
    public String getAction() {
        return m_Log.getAction();
    }

    @DocAttribute(description = "做了什么")
    public String getWhat() {
        return m_Log.getWhat();
    }

    @DocAttribute(description = "备注")
    public String getNote() {
        return m_Log.getNote();
    }

}