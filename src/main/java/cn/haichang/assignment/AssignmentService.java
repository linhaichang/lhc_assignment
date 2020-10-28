package cn.haichang.assignment;

import cn.haichang.assignment.Bug;
import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.common.ResultPage;


import java.util.Date;
import java.util.Set;

/**
 * 任务服务
 * @author HaiChang
 * @date 2020/10/19
 **/
public interface AssignmentService {
    NameItem OPTION_PERSON_HANDLE = NameItem.valueOf("选项：处理人", 0);
    NameItem OPTION_PERSON_CHARGE = NameItem.valueOf("选项：负责人", 1);
    NameItem OPTION_PERSON_FOLLOW = NameItem.valueOf("选项：跟进人", 2);
    NameItem OPTION_ASSIGN_FINISH = NameItem.valueOf("选项：任务完成", 7);
    NameItems CONDITIONS = NameItems.valueOf(OPTION_ASSIGN_FINISH,OPTION_PERSON_CHARGE,OPTION_PERSON_FOLLOW,OPTION_PERSON_HANDLE);

    /**
     * 创建任务
     * @return
     */
    Assignment createAssignment(String title, String content,/*String creator, */Set<String> handlers,
                                String charger, String lableId, Date startTime,
                                Date endTime,int level);
    /**
     * 创建子任务
     * @return
     */
    Assignment createAssignmentSon(String title, String content,/*String creator,*/ Set<String> handlers,
                                   String charger, String lableId, Date startTime,
                                   Date endTime,int level,String fatherId) throws MyException;

    /**
     * 根据id获取任务
     * @param id
     * @return
     */
    Assignment getAssignment(String id) throws MyException;

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
     * @throws MyException
     */
    void deleteAssignment(String id) throws MyException;

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
    Lable getLable(String lableId) throws MyException;

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
     * @param handlers
     * @param versionAndPlatform
     * @return
     */
    Bug createBug(String assignmentId,
                  String bugContent, String severity,
                  Set<String> handlers, String versionAndPlatform);

    /**
     * 根据任务Id获取缺陷
     * @param AssignmentId
     * @return
     */
    ResultPage<Bug> getBugByAssignmentId(String AssignmentId) throws MyException;

    /**
     * 根据缺陷id 获取缺陷
     * @param id
     * @return
     */
    Bug getBug(String id) throws MyException;

    /**
     * 通过关键字搜索缺陷内容，获取缺陷
     *
     * @param assignmentId
     * @param keywords
     * @return
     */
    ResultPage<Bug> getBugByKeyWord(String assignmentId,String keywords) throws MyException;
    ResultPage<Bug> searchBugs(String assignmentId,String tester,String handler,int state) throws MyException;
}
