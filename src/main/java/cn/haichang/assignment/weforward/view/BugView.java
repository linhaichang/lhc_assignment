package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Bug;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
@DocObject(description = "缺陷视图")
public class BugView {
    protected Bug m_Bug;
    public static BugView valueOf(Bug bug){
        return null == bug ? null : new BugView(bug);
    }

    public BugView(Bug m_Bug) {
        this.m_Bug = m_Bug;
    }

    @DocAttribute(description = "缺陷id")
    public String getId(){
        return m_Bug.getId().getOrdinal();
    }
    @DocAttribute(description = "Bug内容")
    public String getBugContent(){
        return m_Bug.getBugContent();
    }
    @DocAttribute(description = "Bug状态")
    public String getState(){
        return m_Bug.getState().getName();
    }

    @DocAttribute(description = "Bug严重性描述")
    public String getSeverityDesc(){
        return m_Bug.getSeverity();
    }
    @DocAttribute(description = "测试人")
    public Set<String > getTesters(){
        return m_Bug.getTesters();
    }
    @DocAttribute(description = "处理人")
    public Set<String > getTestHandlers(){
        return m_Bug.getTestHandlers();
    }
    @DocAttribute(description = "创建人")
    public String getCreator(){
        return m_Bug.getCreator();
    }
    @DocAttribute(description = "最后处理时间")
    public Date getLastTime(){
        return m_Bug.getLastTime();
    }

    @DocAttribute(description = "可扭转的状态")
    public List<String > getAllowTurnState(){
        ArrayList<String > states = new ArrayList<>();
        if (Bug.STATE_WAIT_CORRECT.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_WAIT_CORRECT.name);
            states.add(Bug.STATE_WAIT_RETEST.name);
            states.add(Bug.STATE_ADVISE_DONT_EDIT.name);
            states.add(Bug.STATE_ASK_CANT_EDIT.name);
            return states;
        }
        if (Bug.STATE_WAIT_RETEST.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_WAIT_RETEST.name);
            states.add(Bug.STATE_WAIT_CORRECT.name);
            states.add(Bug.STATE_SOLVED.name);
            return states;
        }
        if (Bug.STATE_ADVISE_DONT_EDIT.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_ADVISE_DONT_EDIT.name);
            states.add(Bug.STATE_WAIT_CORRECT.name);
            states.add(Bug.STATE_NO_EDIT.name);
            return states;
        }
        if (Bug.STATE_ASK_CANT_EDIT.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_ASK_CANT_EDIT.name);
            states.add(Bug.STATE_WAIT_CORRECT.name);
            states.add(Bug.STATE_CANT_SOLVE.name);
            return states;
        }
        if (Bug.STATE_SOLVED.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_SOLVED.name);
            states.add(Bug.STATE_REOPEN.name);
            return states;
        }
        if (Bug.STATE_NO_EDIT.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_NO_EDIT.name);
            states.add(Bug.STATE_REOPEN.name);
            return states;
        }
        if (Bug.STATE_CANT_SOLVE.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_CANT_SOLVE.name);
            states.add(Bug.STATE_REOPEN.name);
            return states;
        }
        if (Bug.STATE_REOPEN.id == m_Bug.getState().id){
            states.add("保持为"+Bug.STATE_REOPEN.name);
            states.add(Bug.STATE_WAIT_RETEST.name);
            return states;
        }
        return states;
    }
}
