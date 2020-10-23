package cn.haichang.assignment;

import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.Bug;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.framework.ApiException;

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
    Assignment createAssignmentSon(String title, String content,String creator, Set<String> handlers,
                                   String charger, String lableId, Date startTime,
                                   Date endTime,int level,String fatherId);
    Assignment getAssignment(String id);
    ResultPage<Assignment> searchAssignment(String personName,int personType,int assignmentState);
    ResultPage<Assignment> getSonAssignments(String fatherId);
    void deleteaAssignment(String id) throws ApiException;
    Lable createLable(String lableName);
    Lable getLable(String lableId);
    ResultPage<Lable> getAllLables();

    ResultPage<Assignment> getByKeyWord(String keywords);
    boolean deleteLable(String lableId);
    ResultPage<Assignment> getAllAssignments();

    Bug createBug(String assignmentId,
                  String bugContent, int severity,
                  Set<String> tester, String versionAndPlatform);

    ResultPage<Bug> getBugByAssignmentId(String AssignmentId);
    Bug getBug(String id);
    ResultPage<Bug> getBugByKeyWord(String keywords);
}
