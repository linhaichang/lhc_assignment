package cn.haichang.assignment.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.*;

/**
 * @author HaiChang
 * @date 2020/10/23
 **/
@DocObject(description = "Bug分析视图")
public class BugAnalysisView {
    protected int m_BugsCount;
    protected int m_FinishCount;

//    protected Map<String ,Integer> m_StateAnalysis;
//    protected Map<String ,Integer> m_TesterAndCount;
//    protected Map<String ,Integer> m_HandlerAndCount;
    protected List<String > stateName;
    protected List<Integer> stateNum;

    protected List<String > testerList;
    protected List<Integer > testerNum;

    protected List<String > handlerList;
    protected List<Integer > handlerNum;

    public BugAnalysisView(int m_BugsCount, int m_FinishCount,
                           Map<String, Integer> m_StateAnalysis,
                           Map<String, Integer> m_TesterAndCount,
                           Map<String, Integer> m_HandlerAndCount) {
        this.m_BugsCount = m_BugsCount;
        this.m_FinishCount = m_FinishCount;
        stateName = new ArrayList<>();
        stateNum = new ArrayList<>();
        testerList = new ArrayList<>();
        testerNum = new ArrayList<>();
        handlerList = new ArrayList<>();
        handlerNum = new ArrayList<>();
        for (String s : m_StateAnalysis.keySet()) {
            stateName.add(s);
            stateNum.add(m_StateAnalysis.get(s));
        }
        for (String s : m_TesterAndCount.keySet()) {
            testerList.add(s);
            testerNum.add(m_TesterAndCount.get(s));
        }
        for (String s : m_HandlerAndCount.keySet()) {
            handlerList.add(s);
            handlerNum.add(m_HandlerAndCount.get(s));
        }
    }

    @DocAttribute(description = "总缺陷数")
    public int getBugsCount() {
        return m_BugsCount;
    }
    @DocAttribute(description = "已完成数")
    public int getFinishCount() {
        return m_FinishCount;
    }

    @DocAttribute(description = "缺陷分析状态名")
    public List<String> getStateName() {
        return stateName;
    }
    @DocAttribute(description = "缺陷分析状态数量")
    public List<Integer> getStateNum() {
        return stateNum;
    }
    @DocAttribute(description = "测试人员")
    public List<String> getTesterList() {
        return testerList;
    }
    @DocAttribute(description = "测试人员测试数量")
    public List<Integer> getTesterNum() {
        return testerNum;
    }

    @DocAttribute(description = "处理人员")
    public List<String> getHandlerList() {
        return handlerList;
    }
    @DocAttribute(description = "处理人员处理数量")
    public List<Integer> getHandlerNum() {
        return handlerNum;
    }
}
