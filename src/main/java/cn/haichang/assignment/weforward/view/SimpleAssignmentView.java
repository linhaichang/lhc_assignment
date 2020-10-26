package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Assignment;
import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.Date;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/26
 **/
@DocObject(description = "简略任务视图")
public class SimpleAssignmentView {
    protected Assignment m_Assignment;

    public static SimpleAssignmentView valueOf(Assignment assignment){
        return null == assignment ? null : new SimpleAssignmentView(assignment);
    }

    public SimpleAssignmentView(Assignment assignment) {
        this.m_Assignment = assignment;
    }


    @DocAttribute(description = "任务标题")
    public String getTitle(){
        return m_Assignment.getTitle();
    }


    @DocAttribute(description = "负责人")
    public String getCharger() {
        return m_Assignment.getCharger();
    }


    @DocAttribute(description = "任务创建时间")
    public Date getCreateTime() {
        return m_Assignment.getCreateTime();
    }


    @DocAttribute(description = "任务状态描述")
    public String getStateDesc(){
        return m_Assignment.getState().getName();
    }

    @DocAttribute(description = "优先级")
    public String  getLevel(){
        return m_Assignment.getLevel().getName();
    }
}
