package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
@DocObject(description = "Bug参数")
public class BugParam {
    protected String m_AssignmentId;

    protected String m_BugContent;
    protected String m_Severity;
    protected List<String > m_Testers;
    protected String m_VersionAndPlatform;
    protected List<String > m_TestHandlers;

    @DocAttribute(necessary = true, description = "需求Id", example = "输入需求的id")
    public String getAssignmentId() {
        return m_AssignmentId;
    }
    public void setAssignmentId(String AssignmentId) {
        this.m_AssignmentId = AssignmentId;
    }

    @DocAttribute(necessary = true, description = "Bug内容", example = "我是Bug内容")
    public String getBugContent() {
        return m_BugContent;
    }
    public void setBugContent(String BugContent) {
        this.m_BugContent = BugContent;
    }

    @DocAttribute(description = "处理人员", example = "小马")
    public List<String> getHandlers() {
        return m_TestHandlers;
    }
    public void setHandlers(List<String> handlers) {
        this.m_TestHandlers = handlers;
    }

    @DocAttribute(necessary = true, description = "版本与平台", example = "商家后台")
    public String getVersionAndPlatform() {
        return m_VersionAndPlatform;
    }
    public void setVersionAndPlatform(String VersionAndPlatform) {
        this.m_VersionAndPlatform = VersionAndPlatform;
    }

    @DocAttribute(necessary = true, description = "严重性", example = "优化建议")
    public String  getSeverity() {
        return m_Severity;
    }
    public void setSeverity(String Severity) {
        this.m_Severity = Severity;
    }

}
