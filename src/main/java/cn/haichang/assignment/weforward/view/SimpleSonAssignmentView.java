package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Assignment;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.Date;

/**
 * @author HaiChang
 * @date 2020/10/26
 **/
@DocObject(description = "简略子任务视图")
public class SimpleSonAssignmentView {
    protected Assignment m_Assignment;

    public static SimpleSonAssignmentView valueOf(Assignment assignment){
        return null == assignment ? null : new SimpleSonAssignmentView(assignment);
    }

    public SimpleSonAssignmentView(Assignment assignment) {
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


    @DocAttribute(description = "负责人")
    public String getCharger() {
        return m_Assignment.getCharger();
    }


    @DocAttribute(description = "预计结束使劲啊")
    public Date getEndTime() {
        return m_Assignment.getEndTime();
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
