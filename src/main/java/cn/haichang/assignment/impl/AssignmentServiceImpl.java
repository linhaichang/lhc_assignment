package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.Bug;
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

    @Override
    public Assignment createAssignment(String title, String content,
                                       String creator, Set<String> handlers,
                                       String charger, String lableId, Date startTime,
                                       Date endTime, int level) {
        return new AssignmentImpl(this, title, content, handlers, charger, lableId, startTime, endTime, level);
    }

    @Override
    public Assignment createAssignmentSon(String title, String content, String creator,
                                          Set<String> handlers, String charger,
                                          String lableId, Date startTime,
                                          Date endTime, int level, String fatherId) {
        return new AssignmentImpl(this, title, content, handlers, charger
        , lableId, startTime, endTime, level,fatherId);
    }

    @Override
    public Assignment getAssignment(String id) {
        return m_PsAssignment.get(id);
    }

    @Override
    public ResultPage<Assignment> searchAssignment(String personName,int personType,int assignmentState) {
        ResultPage<? extends Assignment> resultPage = m_PsAssignment.startsWith("");
        List<Assignment> assignmentList = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(resultPage)) {
            if (isMatch(assignment, personName, personType)){
                if (isMatch(assignment, assignmentState)){
                    assignmentList.add(assignment);
                    continue;
                }
            }
        }
        return ResultPageHelper.toResultPage(assignmentList);
    }

    @Override
    public ResultPage<Assignment> getSonAssignments(String fatherId) {
        ResultPage<? extends Assignment> rp = m_PsAssignment.startsWith(fatherId);
        List<Assignment> list = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(rp)) {
            if (assignment.getFatherId() ==null || assignment.getFatherId().length() == 0){
                continue;
            }
            list.add(assignment);
        }
        return ResultPageHelper.toResultPage(list);
    }

    @Override
    public void deleteaAssignment(String id) throws ApiException {
        if (null == m_PsAssignment.get(id)){
            throw new ApiException(0, "无此任务");
        }
        AssignmentImpl assignment = m_PsAssignment.get(id);
        assignment.delete();
    }

    @Override
    public Lable createLable(String lableName) {
        return new LableImpl(this,lableName);
    }

    @Override
    public Lable getLable(String lableId) {
        return m_PsLable.get(lableId);
    }

    @Override
    public ResultPage<Lable> getAllLables() {
        ResultPage<? extends Lable> rp = m_PsLable.startsWith("");
        List<Lable> list = new ArrayList<>();
        for (Lable lable : ResultPageHelper.toForeach(rp)) {
            list.add(lable);
        }
        return ResultPageHelper.toResultPage(list);
    }

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

    @Override
    public boolean deleteLable(String lableId) {
        return m_PsLable.remove(lableId);
    }

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

    private static boolean isMatch(Assignment assignment,int AssignmentState){
        if (null == assignment){
            return false;
        }
        NameItem state = assignment.getState();
        if (state.id == AssignmentState){
            return true;
        }
        if (OPTION_ASSIGN_FINISH.id != AssignmentState && OPTION_ASSIGN_FINISH.id != state.id){
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













