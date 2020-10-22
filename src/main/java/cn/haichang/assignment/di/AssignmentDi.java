package cn.haichang.assignment.di;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.Bug;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.data.counter.Counter;
import cn.weforward.data.persister.BusinessDi;

import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/16
 **/
public interface AssignmentDi extends BusinessDi {
    ResultPage<AssignmentImpl> searchAssignmentByLableId(String lableId);

}
