package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.data.persister.PersisterFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
public class AssignmentServiceImpl extends AssignmentDiImpl implements AssignmentService {
    public AssignmentServiceImpl(PersisterFactory factory) {
        super(factory);
    }


    @Override
    public Assignment createAssignment(String title, String content,
                                       String creator, List<String> handlers,
                                       String charger, String lableId, Date startTime,
                                       Date endTime, int level) {
        return new AssignmentImpl(this, title, content/*, creator*/, handlers, charger, lableId, startTime, endTime, level);
    }

    @Override
    public Assignment getAssignment(String id) {
        return m_PsAssignment.get(id);
    }
    @Override
    public ResultPage<Assignment> searchAssignment(String personName,int personType,int assignmentState) {
        ResultPage<? extends Assignment> resultPage = m_PsAssignment.startsWith(personName);
        List<Assignment> assignmentList = new ArrayList<>();
        for (Assignment assignment : ResultPageHelper.toForeach(resultPage)) {
            if (isMatch(assignment, personName, personType)){
                continue;
            }
            if (isMatch(assignment, assignmentState)){
                continue;
            }
            assignmentList.add(assignment);
        }
        return ResultPageHelper.toResultPage(assignmentList);
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
    public boolean deleteLable(String lableId) {
        return m_PsLable.remove(lableId);
    }


    private static boolean isMatch(Assignment assignment, String personName, int personType) {
        if (null == assignment) {
            return false;
        }
        List<String> handler = assignment.getHandler();
        String charger = assignment.getCharger();
        List<String> follower = assignment.getFollower();
        if (handler.isEmpty() && charger.isEmpty() && follower.isEmpty()) {
            return false;
        }
        if (OPTION_PERSON_HANDLE == personType && handler.contains(personName)) {
            return true;
        }
        if (OPTION_PERSON_CHARGE == personType && charger.contains(personName)) {
            return true;
        }
        if (OPTION_PERSON_FOLLOW == personType && follower.contains(personName)) {
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
        if (OPTION_ASSIGN_FINISH != AssignmentState && OPTION_ASSIGN_FINISH != state.id){
            return true;
        }
        return false;
    }
}