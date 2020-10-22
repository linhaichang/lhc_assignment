package cn.haichang.assignment.weforward.param;

import cn.haichang.assignment.Lable;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
@DocObject(description = "任务参数")
public class AssignmentParam {
    protected String m_Title;
    protected String m_Content;
    protected List<String> m_Handlers;
    protected String  m_Followers;
    protected String m_Charger;
    protected String  m_LableId;
    protected Date m_StartTime;
    protected Date m_EndTime;
    protected int m_Level;
    protected int m_State;

    @DocAttribute(necessary = true,description = "需求标题",example = "我是需求标题")
    public String getTitle() {
        return m_Title;
    }
    public void setTitle(String m_Title) {
        this.m_Title = m_Title;
    }

    @DocAttribute(necessary = true,description = "需求主题内容",example = "我是需求主题内容")
    public String getContent() {
        return m_Content;
    }

    public void setContent(String m_Content) {
        this.m_Content = m_Content;
    }
    @DocAttribute(necessary = true,description = "处理人",example = "小王")
    public List<String> getHandlers() {
        return m_Handlers;
    }

    public void setHandlers(List<String> m_Handlers) {
        this.m_Handlers = m_Handlers;
    }
    @DocAttribute(description = "跟进人",example = "小思")
    public String  getFollowers() {
        return m_Followers;
    }

    public void setFollowers(String m_Followers) {
        this.m_Followers = m_Followers;
    }
    @DocAttribute(necessary = true,description = "负责人",example = "小聪")
    public String getCharger() {
        return m_Charger;
    }

    public void setCharger(String m_Charger) {
        this.m_Charger = m_Charger;
    }
    @DocAttribute(necessary = true,description = "标签",example = "内务大厅")
    public String getLableId() {
        return m_LableId;
    }

    public void setLableId(String lableId) {
        this.m_LableId = lableId;
    }
    @DocAttribute(necessary = true,description = "起始时间",example = "2019-10-29T00:30:00.666Z")
    public Date getStartTime() {
        return m_StartTime;
    }

    public void setStartTime(Date m_StartTime) {
        this.m_StartTime = m_StartTime;
    }
    @DocAttribute(necessary = true,description = "结束时间",example = "2019-10-29T00:30:00.666Z")
    public Date getEndTime() {
        return m_EndTime;
    }

    public void setEndTime(Date m_EndTime) {
        this.m_EndTime = m_EndTime;
    }
    @DocAttribute(necessary = true,description = "优先级",example = "10")
    public int getLevel() {
        return m_Level;
    }
    public void setLevel(int level){
        this.m_Level = level;
    }
   /* @DocAttribute(necessary = true,description = "任务状态",example = "0-9")
    public int getState() {
        return m_State;
    }
    public void setState(int state){
        m_State = state;
    }*/

}
