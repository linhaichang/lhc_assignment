package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/27
 **/
@DocObject(description = "缺陷条件查询参数")
public class ConditionQueryBugParam {
    protected String m_AssignmentId;
    protected String m_Tester;
    protected String m_Handler;
    protected int m_State;

    @DocAttribute(necessary = true,description = "任务id")
    public String getAssignmentId() {
        return m_AssignmentId;
    }
    public void setAssignmentId(String m_AssignmentId) {
        this.m_AssignmentId = m_AssignmentId;
    }

    @DocAttribute(description = "测试人员，空或空字符串为全部")
    public String getTester() {
        return m_Tester;
    }
    public void setTester(String tester) {
        this.m_Tester = tester;
    }

    @DocAttribute(description = "处理人员，空或者空字符串为全部")
    public String getHandler() {
        return m_Handler;
    }
    public void setHandler(String handler) {
        this.m_Handler = handler;
    }

    @DocAttribute(necessary = true,description = "任务状态常量，0为全部",example = "0")
    public int getState() {
        return m_State;
    }
    public void setState(int state) {
        this.m_State = state;
    }
}
