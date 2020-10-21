package cn.haichang.assignment;

import cn.haichang.assignment.impl.AssignmentImpl;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
public interface AssignmentService {

    int OPTION_PERSON_HANDLE = 0;
    int OPTION_PERSON_CHARGE = 1;
    int OPTION_PERSON_FOLLOW = 2;

    int OPTION_ASSIGN_FINISH = 7;
    Assignment createAssignment(String title, String content,String creator, Set<String> handlers,
                                String charger, String lableId, Date startTime,
                                Date endTime,int level);
    Assignment getAssignment(String id);
    ResultPage<Assignment> searchAssignment(String personName,int personType,int assignmentState);
    Lable createLable(String lableName);
    Lable getLable(String lableId);
    ResultPage<Lable> getAllLables();
    boolean deleteLable(String lableId);
//    ResultPage<AssignmentImpl> getAssignmentsByLableId(String lableId);


}
