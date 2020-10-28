package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.weforward.common.NameItem;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
@DocObject(description = "任务视图")
public class AssignmentView {
    protected Assignment m_Assignment;

    public static AssignmentView valueOf(Assignment assignment){
        return null == assignment ? null : new AssignmentView(assignment);
    }

    public AssignmentView(Assignment assignment) {
        this.m_Assignment = assignment;
    }

    @DocAttribute(description = "任务id")
    public String getId(){
        return m_Assignment.getId().getOrdinal();
    }

    @DocAttribute(description = "任务标题")
    public String getTitle(){
        return m_Assignment.getTitle();
    }

    @DocAttribute(description = "任务内容")
    public String getContent(){
        return m_Assignment.getContent();
    }

    @DocAttribute(description = "任务处理人")
    public Set<String> getHandler() {
        return m_Assignment.getHandler();
    }

    @DocAttribute(description = "跟进人")
    public Set<String> getFollower() {
        return m_Assignment.getFollower();
    }

    @DocAttribute(description = "负责人")
    public String getCharger() {
        return m_Assignment.getCharger();
    }

    @DocAttribute(description = "创建人")
    public String getCreator(){
        return m_Assignment.getCreator();
    }

    @DocAttribute(description = "任务标签")
    public String getLableId() {
        return m_Assignment.getLable();
    }

    @DocAttribute(description = "任务预计开始时间")
    public Date getStartTime() {
        return m_Assignment.getStartTime();
    }

    @DocAttribute(description = "任务预计结束时间")
    public Date getEndTime() {
        return m_Assignment.getEndTime();
    }

    @DocAttribute(description = "任务创建时间")
    public Date getCreateTime() {
        return m_Assignment.getCreateTime();
    }

    @DocAttribute(description = "任务完成时间")
    public Date getFinishTime(){
        return m_Assignment.getFinishTime();
    }

    @DocAttribute(description = "任务状态")
    public int getState() {
        return m_Assignment.getState().id;
    }

    @DocAttribute(description = "任务状态描述")
    public String getStateDesc(){
        return m_Assignment.getState().getName();
    }

    @DocAttribute(description = "优先级")
    public String getLevel(){
        return m_Assignment.getLevel().getName();
    }

    @DocAttribute(description = "缺陷简况")
    public String getBugProfile(){
        return m_Assignment.getBugsFinishCount()+" / "+m_Assignment.getBugsCount();
    }
}
