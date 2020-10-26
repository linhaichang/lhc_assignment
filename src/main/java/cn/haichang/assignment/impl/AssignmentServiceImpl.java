package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Bug;
import cn.haichang.assignment.Lable;

import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.data.log.BusinessLoggerFactory;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.framework.ApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
public class AssignmentServiceImpl extends AssignmentDiImpl implements AssignmentService {
    public AssignmentServiceImpl(PersisterFactory factory, BusinessLoggerFactory loggerFactory) {
        super(factory, loggerFactory);
    }

    /**
     * 创建任务
     * @return
     */
    @Override
    public Assignment createAssignment(String title, String content,
                                       /*String creator,*/ Set<String> handlers,
                                       String charger, String lableId, Date startTime,
                                       Date endTime, int level) {
        return new AssignmentImpl(this, title, content, handlers, charger, lableId, startTime, endTime, level);
    }

    /**
     * 创建子任务
     * @return
     */
    @Override
    public Assignment createAssignmentSon(String title, String content, /*String creator,*/
                                          Set<String> handlers, String charger,
                                          String lableId, Date startTime,
                                          Date endTime, int level, String fatherId) {
        return new AssignmentImpl(this, title, content, handlers, charger
        , lableId, startTime, endTime, level,fatherId);
    }

    /**
     * 根据id获取任务
     * @param id
     * @return
     */
    @Override
    public Assignment getAssignment(String id) throws ApiException {
        AssignmentImpl assignment = m_PsAssignment.get(id);
        if (null == assignment){
            throw new ApiException(0, "查询不到此任务");
        }
        if (assignment.isDelete()){
            throw new ApiException(0, "查询不到此任务");
        }
        return assignment;
    }

    /**
     * 通过 人名，人的类型(负责人，处理人，跟进人),任务是否完成条件 获取任务
     * @param personName 人名
     * @param personType 类型
     * @param assignmentState 任务状态是否为已完成
     * @return
     */
    @Override
    public ResultPage<Assignment> searchAssignment(String personName,int personType,int assignmentState) {
        ResultPage<? extends Assignment> resultPage = m_PsAssignment.startsWith("");
        List<Assignment> assignmentList = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(resultPage)) {
            if (assignment.isDelete()){
                continue;
            }
            if (isMatch(assignment, personName, personType)){
                if (isMatch(assignment, assignmentState)){
                    assignmentList.add(assignment);
                    continue;
                }
            }
        }
        return ResultPageHelper.toResultPage(assignmentList);
    }

    /**
     * 获取子任务
     * @param fatherId
     * @return
     */
    @Override
    public ResultPage<Assignment> getSonAssignments(String fatherId) {
        ResultPage<? extends Assignment> rp = m_PsAssignment.startsWith(fatherId);
        List<Assignment> list = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(rp)) {
            if (assignment.isDelete()){
                continue;
            }
            if (assignment.getFatherId() ==null || assignment.getFatherId().length() == 0){
                continue;
            }
            list.add(assignment);
        }
        return ResultPageHelper.toResultPage(list);
    }

    /**
     * 根据id删除任务
     * @param id
     * @throws ApiException
     */
    @Override
    public void deleteaAssignment(String id) throws ApiException {
        if (null == m_PsAssignment.get(id)){
            throw new ApiException(0, "无此任务");
        }
        AssignmentImpl assignment = m_PsAssignment.get(id);
        assignment.delete();
    }

    /**
     * 创建标签
     * @param lableName
     * @return
     */
    @Override
    public Lable createLable(String lableName) {
        return new LableImpl(this,lableName);
    }

    /**
     * 获取标签
     * @param lableId
     * @return
     */
    @Override
    public Lable getLable(String lableId) {
        return m_PsLable.get(lableId);
    }

    /**
     * 获取所有标签
     * @return
     */
    @Override
    public ResultPage<Lable> getAllLables() {
        ResultPage<? extends Lable> rp = m_PsLable.startsWith("");
        List<Lable> list = new ArrayList<>();
        for (Lable lable : ResultPageHelper.toForeach(rp)) {
            list.add(lable);
        }
        return ResultPageHelper.toResultPage(list);
    }
    /**
     * 通过关键字搜索标题 获取任务
     * @param keyword 关键词
     * @return
     */
    @Override
    public ResultPage<Assignment> getByKeyWord(String keyword) {
        ResultPage<? extends Assignment> rp = m_PsAssignment.startsWith("");
        List<Assignment> list = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(rp)) {
            if (assignment.getTitle().contains(keyword) && !assignment.isDelete())
            list.add(assignment);
        }
        return ResultPageHelper.toResultPage(list);
    }

    /**
     * 删除标签
     * @param lableId
     * @return
     */
    @Override
    public boolean deleteLable(String lableId) {
        return m_PsLable.remove(lableId);
    }

    /**
     * 获取所有任务
     * @return
     */
    @Override
    public ResultPage<Assignment> getAllAssignments() {
        ResultPage<? extends Assignment> rp = m_PsAssignment.startsWith("");
        List<Assignment> list = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(rp)) {
            if (assignment.isDelete()){
                continue;
            }
            list.add(assignment);
        }
        return ResultPageHelper.toResultPage(list);
    }

    /**
     * 通过判断人名、人的类型匹配符合要求的任务
     * @param assignment 任务
     * @param personName 人名
     * @param personType 类型
     * @return
     */
    private static boolean isMatch(Assignment assignment, String personName, int personType) {
        if (null == assignment) {
            return false;
        }
        Set<String> handler = assignment.getHandler();
        String charger = assignment.getCharger();
        Set<String> follower = assignment.getFollower();
        if (handler.isEmpty() && charger.isEmpty() && follower.isEmpty()) {
            return false;
        }
        if (OPTION_PERSON_HANDLE.id == personType && handler.contains(personName)) {
            return true;
        }
        if (OPTION_PERSON_CHARGE.id == personType && charger.contains(personName)) {
            return true;
        }
        if (OPTION_PERSON_FOLLOW.id == personType && follower.contains(personName)) {
            return true;
        }
        return false;
    }

    /**
     * 通过判断任务状态是否为已完成，匹配符合要求的任务
     * @param assignment 任务
     * @param assignmentState 需要的任务状态
     * @return
     */
    private static boolean isMatch(Assignment assignment,int assignmentState){
        if (null == assignment){
            return false;
        }
        NameItem state = assignment.getState();
        if (state.id == assignmentState){
            return true;
        }
        if (OPTION_ASSIGN_FINISH.id != assignmentState && OPTION_ASSIGN_FINISH.id != state.id){
            return true;
        }
        return false;
    }

    /*------------------------------------------BUG相关-------------------------------------------------*/

    public Bug createBug(String assignmentId,
                         String bugContent,int severity,
                         Set<String> tester,String versionAndPlatform){
        return new BugImpl(this,assignmentId,bugContent,severity
        ,tester,versionAndPlatform);
    }

    @Override
    public ResultPage<Bug> getBugByAssignmentId(String fatherId) {
        ResultPage<? extends Bug> rp = m_PsBug.startsWith(fatherId);
        List<Bug> list = new ArrayList<>();
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            list.add(bug);
        }
        return ResultPageHelper.toResultPage(list);
    }

    @Override
    public Bug getBug(String id) {
        return m_PsBug.get(id);
    }

    @Override
    public ResultPage<Bug> getBugByKeyWord(String keywords) {
        ResultPage<? extends Bug> rp = m_PsBug.startsWith("");
        List<Bug> list = new ArrayList<>();
        for (Bug bug : ResultPageHelper.toForeach(rp)) {
            if (bug.getBugContent().contains(keywords))
                list.add(bug);
        }
        return ResultPageHelper.toResultPage(list);
    }
}













