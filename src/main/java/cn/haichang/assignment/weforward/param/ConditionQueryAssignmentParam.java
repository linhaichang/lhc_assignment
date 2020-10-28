package cn.haichang.assignment.weforward.param;

import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
@DocObject(description = "任务条件查询参数")
public class ConditionQueryAssignmentParam extends DocPageParams {
    protected String m_PersonName;
    protected int m_PersonType;
    protected int m_ConditionState;

    @DocAttribute(necessary = true,description = "指定人名",example = "马云")
    public String getPersonName() {
        return m_PersonName;
    }
    public void setPersonName(String personName) {
        this.m_PersonName = personName;
    }

    @DocAttribute(necessary = true,description = "人的类型{处理：0，负责：1，跟进：2}",example = "0")
    public int getPersonType() {
        return m_PersonType;
    }

    public void setPersonType(int personType) {
        this.m_PersonType = personType;
    }
    @DocAttribute(necessary = true,description = "任务是否完成：{完成：7，未完成：0}",example = "7")
    public int getConditionState() {
        return m_ConditionState;
    }

    public void setConditionState(int conditionState) {
        this.m_ConditionState = conditionState;
    }
}
