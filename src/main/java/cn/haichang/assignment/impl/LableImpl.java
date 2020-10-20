package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.di.AssignmentDi;
import cn.weforward.common.util.TransList;
import cn.weforward.data.UniteId;
import cn.weforward.data.persister.support.AbstractPersistent;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/16
 **/
public class LableImpl extends AbstractPersistent<AssignmentDi> implements Lable {
    @Resource
    protected String m_LableName;

    @Resource
    protected List<String> m_Assignments;


    protected LableImpl(AssignmentDi di) {
        super(di);
    }
    /**
     * 以业务依赖接口构造对象
     *
     * @param di 业务依赖接口
     */
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
        m_LableName = lableName;
        markPersistenceUpdate();
    }

    @Override
    public String getLableName() {
        return m_LableName;
    }

    @Override
    public void deleteLable() {

    }

//    @Override
//    public List<String> getAssignments() {
//        return m_Assignments;
//    }

}
