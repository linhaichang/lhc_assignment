package cn.haichang.assignment.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HaiChang
 * @date 2020/10/23
 **/
@DocObject(description = "Bug分析视图")
public class BugAnalysisView {
    protected int m_BugsCount;
    protected int m_FinishCount;

    protected Map<String ,Integer> m_StateAnalysis;

    protected Map<String ,Integer> m_TesterAndCount;
    protected Map<String ,Integer> m_HandlerAndCount;

    public BugAnalysisView(int m_BugsCount, int m_FinishCount,
                           Map<String, Integer> m_StateAnalysis,
                           Map<String, Integer> m_TesterAndCount,
                           Map<String, Integer> m_HandlerAndCount) {
        this.m_BugsCount = m_BugsCount;
        this.m_FinishCount = m_FinishCount;
        this.m_StateAnalysis = m_StateAnalysis;
        this.m_TesterAndCount = m_TesterAndCount;
        this.m_HandlerAndCount = m_HandlerAndCount;
    }

    @DocAttribute(description = "总缺陷数")
    public int getBugsCount() {
        return m_BugsCount;
    }
    @DocAttribute(description = "已完成数")
    public int getFinishCount() {
        return m_FinishCount;
    }

    @DocAttribute
    public Map<String, Integer> getStateAnalysis() {
        return m_StateAnalysis;
    }
    @DocAttribute(description = "测试人员处理数")
    public Map<String, Integer> getTesterAndCount() {
        return m_TesterAndCount;
    }
    @DocAttribute(description = "处理人员处理数")
    public Map<String, Integer> getHandlerAndCount() {
        return m_HandlerAndCount;
    }
}
