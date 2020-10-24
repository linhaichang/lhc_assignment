package cn.haichang.assignment.di;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.Bug;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.data.counter.Counter;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.persister.BusinessDi;

import java.util.List;
import java.util.Map;

/**
 * @author HaiChang
 * @date 2020/10/16
 **/
public interface AssignmentDi extends BusinessDi {
    /**
     * 通过标签的ID获取所有此标签下的所有任务
     * @param lableId
     * @return
     */
    ResultPage<AssignmentImpl> searchAssignmentByLableId(String lableId);

    /**
     * 获取缺陷总数
     * @param AssignmentId
     * @return
     */
    int getBugsCount(String AssignmentId);

    /**
     * 获取已完成的缺陷总数
     * @param AssignmentId
     * @return
     */
    int getBugsFinishCount(String AssignmentId);

    /**
     * 获取状态分析
     * @param AssignmentId
     * @return
     */
    Map<String, Integer> getStateAnalysis(String AssignmentId);

    /**
     * 统计每个测试人员负责的缺陷总数
     * @param AssignmentId
     * @return
     */
    Map<String, Integer> getTesterAndCount(String AssignmentId);

    /**
     * 统计每个负责人员负责的缺陷总数
     * @param AssignmentId
     * @return
     */
    Map<String, Integer> getHandlerAndCount(String AssignmentId);



    void writeLog(UniteId id,String user ,String action, String what, String note);

    /**
     * 获取日志
     *
     * @param id
     * @return
     */
    ResultPage<BusinessLog> getLogs(UniteId id);

}
