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
                ConditionUtil.eq(ConditionUtil.field("lableId"), lableId)
        );
        return assignments;
    }


    @Override
    public int getBugsCount(String AssignmentId) {
        return m_PsBug.startsWith(AssignmentId).getCount();
    }

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

    @Override
    public Map<String, Integer> getStateAnalysis(String assignmentId) {
        ResultPage<BugImpl> rp = m_PsBug.startsWith(assignmentId);
        Map<String ,Integer> map = new HashMap<>(Bug.STATES_BUGS.size());
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            NameItem nameItem = Bug.STATES_BUGS.get(bug.getState().id);
            String stateName = nameItem.name;
            Integer count = null == map.get(stateName) ? 0 : map.get(stateName);
            map.put(stateName,++count);
        }
        return map;
    }



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
     * @return 统计结果
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
