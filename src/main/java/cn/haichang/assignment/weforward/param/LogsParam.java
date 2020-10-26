package cn.haichang.assignment.weforward.param;

import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/23
 **/
@DocObject(description = "日志搜索参数")
public class LogsParam extends DocPageParams {

    protected String m_Id;

    public LogsParam() {

    }

    public void setId(String id) {
        m_Id = id;
    }

    @DocAttribute(description = "目标的id")
    public String getId() {
        return m_Id;
    }
}