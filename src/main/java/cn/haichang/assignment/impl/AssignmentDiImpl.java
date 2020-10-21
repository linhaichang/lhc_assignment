package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.di.AssignmentDi;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.data.persister.Condition;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.data.persister.ext.ConditionUtil;

/**
 * @author HaiChang
 * @date 2020/10/18
 **/
public class AssignmentDiImpl implements AssignmentDi {

    protected PersisterFactory m_Factory;
    protected Persister<AssignmentImpl> m_PsAssignment;
    protected Persister<LableImpl> m_PsLable;


    public AssignmentDiImpl(PersisterFactory factory) {
        m_Factory = factory;
        m_PsAssignment = m_Factory.createPersister(AssignmentImpl.class, this);
        m_PsLable = m_Factory.createPersister(LableImpl.class, this);
    }
    @Override
    public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
        return m_Factory.getPersister(clazz);
    }

    @Override
    public Assignment getAssignment(String id) {
        return m_PsAssignment.get(id);
    }
    @Override
    public Lable getLable(UniteId id) {
        return m_PsLable.get(id);
    }

    @Override
    public ResultPage<AssignmentImpl> searchAssignmentByLableId(String lableId) {
        System.out.println(lableId);
        ResultPage<AssignmentImpl> assignments = m_PsAssignment.search(
                ConditionUtil.eq(ConditionUtil.field("lableId"), lableId));
        return assignments;
    }
}
