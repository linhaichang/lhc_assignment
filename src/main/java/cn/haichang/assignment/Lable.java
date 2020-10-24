package cn.haichang.assignment;

import cn.haichang.assignment.impl.AssignmentImpl;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;

import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/16
 **/
public interface Lable {
    UniteId getId();

    void setLableName(String lableName);
    String getLableName();

    /**
     * 获取标签下的所有任务
     * @return
     */
    ResultPage<AssignmentImpl> getAssignments();
}
