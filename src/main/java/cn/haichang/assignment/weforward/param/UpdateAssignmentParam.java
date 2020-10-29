package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.Date;
import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
@DocObject(description = "更新任务参数")
public class UpdateAssignmentParam{
    protected String m_Id;
    protected String m_Title;
    protected String m_Content;
    protected String m_Charger;
    protected String  m_LableId;
    protected Date m_StartTime;
    protected Date m_EndTime;
    protected int m_Level;


    @DocAttribute(necessary = true,description = "任务id",example = "123")
    public String getId(){
        return m_Id;
    }
    public void setId(String id){
        m_Id = id;
    }
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

    @DocAttribute(description = "负责人",example = "小聪")
    public String getCharger() {
        return m_Charger;
    }

    public void setCharger(String m_Charger) {
        this.m_Charger = m_Charger;
    }
    @DocAttribute(necessary = true,description = "标签id，必须先有标签")
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

}
