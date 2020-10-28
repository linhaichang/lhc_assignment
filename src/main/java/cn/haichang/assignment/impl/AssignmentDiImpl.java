package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.di.AssignmentDi;
import cn.haichang.assignment.Bug;
import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.log.BusinessLogger;
import cn.weforward.data.log.BusinessLoggerFactory;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.data.persister.ext.ConditionUtil;

import java.util.*;

/**
 * di实现
 * @author HaiChang
 * @date 2020/10/18
 **/
public class AssignmentDiImpl implements AssignmentDi {

    protected PersisterFactory m_Factory;
    protected Persister<AssignmentImpl> m_PsAssignment;
    protected Persister<LableImpl> m_PsLable;
    protected Persister<BugImpl> m_PsBug;

    protected BusinessLogger m_BusinessLogger;

    public AssignmentDiImpl(PersisterFactory factory, BusinessLoggerFactory loggerFactory) {
        m_Factory = factory;
        m_PsAssignment = m_Factory.createPersister(AssignmentImpl.class, this);
        m_PsLable = m_Factory.createPersister(LableImpl.class, this);
        m_PsBug = m_Factory.createPersister(BugImpl.class, this);
        m_BusinessLogger=loggerFactory.createLogger("lhc_logger");
    }

    /**
     * 通过标签的ID获取所有此标签下的所有任务
     * @param lableId
     * @return
     */
    @Override
    public ResultPage<AssignmentImpl> searchAssignmentByLableId(String lableId) {
        ResultPage<AssignmentImpl> assignments = m_PsAssignment.search(
        //标签id相符且任务未删除
                ConditionUtil.and(
                        ConditionUtil.eq(ConditionUtil.field("lableId"), lableId),
                        ConditionUtil.ne(ConditionUtil.field("isDelete"), Assignment.STATE_DELETE.id)
                        )
        );
        return assignments;
    }


    /**
     * 获取缺陷总数
     * @param AssignmentId 任务id
     * @return 该任务的缺陷总数
     */
    @Override
    public int getBugsCount(String AssignmentId) {
        return m_PsBug.startsWith(AssignmentId).getCount();
    }

    /**
     * 获取已完成的缺陷总数
     * @param assignmentId 任务id
     * @return 该任务已完成的缺陷总数
     */
    @Override
    public int getBugsFinishCount(String assignmentId) {
        ResultPage<BugImpl> rp = m_PsBug.startsWith(assignmentId);
        //首先获得缺陷总数
        int allCount = getBugsCount(assignmentId);
        /*循环获得状态为已解决、不作修改、不能修改的缺陷数量，
         *并用总数减之
         */
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            if (Bug.STATE_SOLVED.id != bug.getState().id &&
                    Bug.STATE_NO_EDIT.id != bug.getState().id &&
                Bug.STATE_CANT_SOLVE.id != bug.getState().id){
                allCount--;
            }
        }
        return allCount;
    }

    /**
     * 获取缺陷分析
     * @param assignmentId
     * @return
     */
    @Override
    public Map<String, Integer> getStateAnalysis(String assignmentId) {
        ResultPage<BugImpl> rp = m_PsBug.startsWith(assignmentId);
        ArrayList<String > list = new ArrayList<>();
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            NameItem nameItem = Bug.STATES_BUGS.get(bug.getState().id);
            String stateName = nameItem.name;
            list.add(stateName);
        }
        return statistics(list);
    }

    /**
     * 统计每个测试人员负责的缺陷总数
     * @param AssignmentId
     * @return
     */
    @Override
    public Map<String, Integer> getTesterAndCount(String AssignmentId) {
        ArrayList<String > list = new ArrayList<>();
        ResultPage<BugImpl> rp = m_PsBug.startsWith(AssignmentId);
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            Set<String> testers = bug.getTesters();
            for (String tester : testers) {
                list.add(tester);
            }
        }
        return statistics(list);
    }

    /**
     * 统计每个负责人员负责的缺陷总数
     * @param AssignmentId
     * @return
     */
    @Override
    public Map<String, Integer> getHandlerAndCount(String AssignmentId) {
        ArrayList<String > list = new ArrayList<>();
        ResultPage<BugImpl> rp = m_PsBug.startsWith(AssignmentId);
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            Set<String> handlers = bug.getTestHandlers();
            for (String handler : handlers) {
                list.add(handler);
            }
        }
        return statistics(list);
    }

    /**
     * 统计字符串列表中元素的出现次数
     * @param list 字符串列表
     * @return 统计结果
     */
    private static Map<String,Integer> statistics(List<String > list){
        HashMap<String , Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            //如果map中有这个key，则将这个key映射的value数值+1，再覆盖原键值对
            if (map.get(list.get(i))!=null){
                map.put(list.get(i),map.get(list.get(i))+1);
            }else {
                //如果map中没有这个key，则value = 1
                map.put(list.get(i),1);
            }
        }
        return map;
    }
    @Override
    public void writeLog(UniteId id, String user,String action, String what, String note) {
        m_BusinessLogger.writeLog(id.getId(), user, action, what, note);
    }

    @Override
    public ResultPage<BusinessLog> getLogs(UniteId id) {
        return m_BusinessLogger.getLogs(id.getId());
    }

    @Override
    public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
        return m_Factory.getPersister(clazz);
    }

}
