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
    protected int m_Severity;
    protected List<String > m_Tester;
    protected String m_VersionAndPlatform;

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

    @DocAttribute(necessary = true, description = "严重性", example = "32")
    public int getSeverity() {
        return m_Severity;
    }
    public void setSeverity(int Severity) {
        this.m_Severity = Severity;
    }

    @DocAttribute(necessary = true, description = "测试人员", example = "\"小马\",\"小云\"")
    public List<String> getTester() {
        return m_Tester;
    }
    public void setTester(List<String> Tester) {
        this.m_Tester = Tester;
    }

    @DocAttribute(necessary = true, description = "版本与平台", example = "商家后台")
    public String getVersionAndPlatform() {
        return m_VersionAndPlatform;
    }
    public void setVersionAndPlatform(String VersionAndPlatform) {
        this.m_VersionAndPlatform = VersionAndPlatform;
    }
}
