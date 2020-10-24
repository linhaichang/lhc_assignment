package cn.haichang.assignment;

import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.Bug;
import cn.weforward.common.NameItem;
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
    NameItem OPTION_PERSON_HANDLE = NameItem.valueOf("选项：处理人", 0);
    NameItem OPTION_PERSON_CHARGE = NameItem.valueOf("选项：负责人", 1);
    NameItem OPTION_PERSON_FOLLOW = NameItem.valueOf("选项：跟进人", 2);
    NameItem OPTION_ASSIGN_FINISH = NameItem.valueOf("选项：任务完成", 7);

    /**
     * 创建任务
     * @return
     */
    Assignment createAssignment(String title, String content,String creator, Set<String> handlers,
                                String charger, String lableId, Date startTime,
                                Date endTime,int level);
    /**
     * 创建子任务
     * @return
     */
    Assignment createAssignmentSon(String title, String content,String creator, Set<String> handlers,
                                   String charger, String lableId, Date startTime,
                                   Date endTime,int level,String fatherId);

    /**
     * 根据id获取任务
     * @param id
     * @return
     */
    Assignment getAssignment(String id);

    /**
     * 通过 人名，人的类型(负责人，处理人，跟进人),任务是否完成条件 获取任务
     * @param personName
     * @param personType
     * @param assignmentState
     * @return
     */
    ResultPage<Assignment> searchAssignment(String personName,int personType,int assignmentState);

    /**
     * 通过父需求id，获取所有子任务
     * @param fatherId
     * @return
     */
    ResultPage<Assignment> getSonAssignments(String fatherId);

    /**
     * 根据id删除任务
     * @param id
     * @throws ApiException
     */
    void deleteaAssignment(String id) throws ApiException;

    /**
     * 创建标签
     * @param lableName
     * @return
     */
    Lable createLable(String lableName);

    /**
     * 获取标签
     * @param lableId
     * @return
     */
    Lable getLable(String lableId);

    /**
     * 获取所有标签
     * @return
     */
    ResultPage<Lable> getAllLables();

    /**
     * 通过关键字搜索标题 获取任务
     * @param keywords
     * @return
     */
    ResultPage<Assignment> getByKeyWord(String keywords);

    /**
     * 删除标签
     * @param lableId
     * @return
     */
    boolean deleteLable(String lableId);

    /**
     * 获取所有任务
     * @return
     */
    ResultPage<Assignment> getAllAssignments();

    /**
     * 创建缺陷
     * @param assignmentId
     * @param bugContent
     * @param severity
     * @param tester
     * @param versionAndPlatform
     * @return
     */
    Bug createBug(String assignmentId,
                  String bugContent, int severity,
                  Set<String> tester, String versionAndPlatform);

    /**
     * 根据任务Id获取缺陷
     * @param AssignmentId
     * @return
     */
    ResultPage<Bug> getBugByAssignmentId(String AssignmentId);

    /**
     * 根据缺陷id 获取缺陷
     * @param id
     * @return
     */
    Bug getBug(String id);

    /**
     * 通过关键字搜索缺陷内容，获取缺陷
     * @param keywords
     * @return
     */
    ResultPage<Bug> getBugByKeyWord(String keywords);
}
