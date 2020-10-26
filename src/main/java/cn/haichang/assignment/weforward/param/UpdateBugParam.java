package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
@DocObject(description = "更新缺陷参数")
public class UpdateBugParam{
    protected String m_BugContent;
    protected int m_Severity;
    protected List<String > m_Testers;
    protected List<String > m_Handlers;
    protected String m_Id;

    @DocAttribute(necessary = true,description = "BugId",example = "123")
    public String getId(){
        return m_Id;
    }
    public void setId(String id){
        m_Id = id;
    }

    @DocAttribute(necessary = true,description = "处理人员",example = "21")
    public List<String > getHandlers(){return m_Handlers;}
    public void setHandlers(List<String > handlers){
        this.m_Handlers = handlers;
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
    public List<String> getTesters() {
        return m_Testers;
    }
    public void setTesters(List<String> Testers) {
        this.m_Testers = Testers;
    }

}
