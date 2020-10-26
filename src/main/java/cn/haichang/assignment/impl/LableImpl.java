package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.di.AssignmentDi;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.persister.support.AbstractPersistent;

import javax.annotation.Resource;

/**
 * @author HaiChang
 * @date 2020/10/16
 **/
public class LableImpl extends AbstractPersistent<AssignmentDi> implements Lable {
    @Resource
    protected String m_LableName;

    protected LableImpl(AssignmentDi di) {
        super(di);
    }

    public LableImpl(AssignmentDi di, String lableName) {
        super(di);
        genPersistenceId(lableName);
        m_LableName = lableName;
        markPersistenceUpdate();
    }

    @Override
    public UniteId getId() {
        return getPersistenceId();
    }

    @Override
    public void setLableName(String lableName) {
        if (StringUtil.eq(m_LableName, lableName)){
            return;
        }
        m_LableName = lableName;
        markPersistenceUpdate();
    }

    @Override
    public String getLableName() {
        return m_LableName;
    }

    /**
     * 获取标签下的所有任务
     * @return
     */
    @Override
    public ResultPage<AssignmentImpl> getAssignments() {
        ResultPage<AssignmentImpl> assignments = getBusinessDi().searchAssignmentByLableId(getId().getOrdinal());
        return assignments;
    }
}

