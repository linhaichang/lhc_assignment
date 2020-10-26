package cn.haichang.assignment.weforward.param;

import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 拥有分页属性的任务id参数类
 * @author HaiChang
 * @date 2020/10/26
 **/
@DocObject(description = "任务列表参数")
public class AssignmentListParam extends DocPageParams {
    protected String m_AssignmentId;

    public AssignmentListParam() {

    }

    public void setAssignmentId(String id) {
        m_AssignmentId = id;
    }

    @DocAttribute(description = "任务的id")
    public String getAssignmentId() {
        return m_AssignmentId;
    }
}
