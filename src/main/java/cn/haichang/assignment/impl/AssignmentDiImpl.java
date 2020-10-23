package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.di.AssignmentDi;
import cn.haichang.assignment.weforward.Bug;
import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.log.BusinessLogger;
import cn.weforward.data.log.BusinessLoggerFactory;
import cn.weforward.data.persister.Condition;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.data.persister.ext.ConditionUtil;
import cn.weforward.framework.WeforwardSession;
import cn.weforward.protocol.ops.User;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    @Override
    public ResultPage<AssignmentImpl> searchAssignmentByLableId(String lableId) {
        ResultPage<AssignmentImpl> assignments = m_PsAssignment.search(
                ConditionUtil.eq(ConditionUtil.field("lableId"), lableId));
        return assignments;
    }

    /**
     * 计算该任务的Bug数量
     * @param AssignmentId
     * @return
     */
    @Override
    public int getBugsCount(String AssignmentId) {
        return m_PsBug.startsWith(AssignmentId).getCount();
    }

    /**
     * 计算该任务已完成的Bug数量
     * @param assignmentId
     * @return
     */
    @Override
    public int getBugsFinishCount(String assignmentId) {
        ResultPage<BugImpl> rp = m_PsBug.startsWith(assignmentId);
        int allCount = getBugsCount(assignmentId);
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
     * 统计任务的Bug状态
     * @param assignmentId
     * @return
     */
    @Override
    public Map<String, Integer> getStateAnalysis(String assignmentId) {
        ResultPage<BugImpl> rp = m_PsBug.startsWith(assignmentId);
        Map<String ,Integer> map = new ConcurrentHashMap<>();
        /*首先将常量加入map*/
        for (int j = 0; j < Bug.STATES_BUGS.size(); j++) {
            map.put(Bug.STATES_BUGS.get(j+20).getName(), 0);
        }
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            NameItem nameItem = Bug.STATES_BUGS.get(bug.getState().id);
            Integer integer = map.get(nameItem.getName());
            map.put(nameItem.name, ++integer);
        }
        for (String key : map.keySet()) {
            if (0 == map.get(key)){
                map.remove(key);
            }
        }
        return map;
    }


    /**
     * 计算此任务中测试人员找出Bug的数量
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
        String [] testArr = list.toArray(new String[list.size()]);
        return statistics(testArr);
    }

    /**
     * 计算此任务中处理人员处理Bug的数量
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
        String [] HandArr =  list.toArray(new String[list.size()]);
        return statistics(HandArr);
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

    /**
     * 统计字符串数组中元素的出现次数
     * @param arr
     * @return
     */
    private static Map<String,Integer> statistics(String[] arr){
        HashMap<String , Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.get(arr[i])!=null){
                map.put(arr[i],map.get(arr[i])+1);
            }else {
                map.put(arr[i],1);
            }
        }
        return map;
    }
}
