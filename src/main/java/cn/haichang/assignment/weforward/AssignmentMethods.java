package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.param.AssignmentParam;
import cn.haichang.assignment.weforward.param.ConditionQueryAssignmentParam;
import cn.haichang.assignment.weforward.param.SonAssignmentParam;
import cn.haichang.assignment.weforward.param.UpdateAssignmentParam;
import cn.haichang.assignment.weforward.view.AssignmentView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.KeepServiceOrigin;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.framework.support.Global;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.support.datatype.FriendlyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
@DocMethods(index = 100)
@WeforwardMethods
public class AssignmentMethods {
    final static Logger logger = LoggerFactory.getLogger(AssignmentMethods.class);
    @Resource
    protected AssignmentService m_AssignmentService;

    private String getCreator(){
        String creator = Global.TLS.getValue("creator");
        if (null == creator){
            creator = "boss";
        }
        return creator;
    }

    @WeforwardMethod
    @DocMethod(description = "创建任务",index = 0)
    public AssignmentView create(AssignmentParam params) throws ApiException {
        String title = params.getTitle();
        String content = params.getContent();
        /**list 转 set*/
        Set<String> handlers = new HashSet<>(params.getHandlers());
        String charger = params.getCharger();
        String lableId = params.getLableId();
        System.out.println(lableId);
        Date startTime = params.getStartTime();
        Date endTime = params.getEndTime();
        int level = params.getLevel();
        ValidateUtil.isEmpty(title, "标题不能为空");
        ValidateUtil.isEmpty(content, "内容不能为空");
        Assignment assignment = m_AssignmentService.createAssignment(title
        , content, getCreator(), handlers, charger, lableId, startTime
        ,endTime,level);
        return AssignmentView.valueOf(assignment);
    }

    @WeforwardMethod
    @DocMethod(description = "创建子任务",index = 1)
    public AssignmentView createSon(SonAssignmentParam params) throws ApiException {
        String title = params.getTitle();
        String content = params.getContent();
        /**list 转 set*/
        Set<String> handlers = new HashSet<>(params.getHandlers());
        String charger = params.getCharger();
        String lableId = params.getLableId();
        Date startTime = params.getStartTime();
        Date endTime = params.getEndTime();
        String fatherId = params.getFatherId();
        int level = params.getLevel();
        ValidateUtil.isEmpty(title, "标题不能为空");
        ValidateUtil.isEmpty(content, "内容不能为空");
        Assignment assignment = m_AssignmentService.createAssignmentSon(title
                , content, getCreator(), handlers, charger, lableId, startTime
                ,endTime,level,fatherId);
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "通过任务id获取任务",index = 2)
    public AssignmentView get(FriendlyObject params){
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "LableId",type = String.class,necessary = true,description = "标签id"))
    @DocMethod(description = "通过标签id获取任务",index = 3)
    public ResultPage<AssignmentImpl> getByLableId(FriendlyObject params){
        Lable lableId = m_AssignmentService.getLable(params.getString("LableId"));
        return lableId.getAssignments();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "fatherId",type = String.class,necessary = true,description = "父id"))
    @DocMethod(description = "通过父id获取子任务",index = 4)
    public ResultPage<Assignment> getByfahterId(FriendlyObject params){
        return m_AssignmentService.getSonAssignments(params.getString("fatherId"));
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "通过条件查询获取任务",index = 5)
    public ResultPage<Assignment> getByCondition(ConditionQueryAssignmentParam params){
        String personName = params.getPersonName();
        int personType = params.getPersonType();
        int conditionState = params.getConditionState();
        return m_AssignmentService.searchAssignment(personName,personType,conditionState);
    }


    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "获取所有任务" , index = 6)
    public ResultPage<Assignment> getAllAssignment(){
        return m_AssignmentService.getAllAssignments();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "更新任务" , index = 7)
    public AssignmentView update(UpdateAssignmentParam params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getId());

        if (null == assignment){
            return null;
        }
        String title = params.getTitle();
        if (!StringUtil.isEmpty(title)){
            assignment.setTitle(title);
        }

        String content = params.getContent();
        if (!StringUtil.isEmpty(content)){
            assignment.setContent(content);
        }

        assignment.addHandler(new HashSet<>(params.getHandlers()));
        assignment.addFollower(params.getFollowers());
        assignment.setCharger(params.getCharger());
        /*修改标签*/
        assignment.setLableId(params.getLableId());
        assignment.setStartTime(params.getStartTime());
        assignment.setEndTime(params.getEndTime());
        int level = params.getLevel();
        if (Assignment.OPTION_LEVEL_HIGHEST.id == level){
            assignment.LevelHighest();
        }
        if (Assignment.OPTION_LEVEL_HIGH.id == level){
            assignment.LevelHigh();
        }
        if (Assignment.OPTION_LEVEL_MIDDLE.id == level){
            assignment.LevelMiddle();
        }
        if ((Assignment.OPTION_LEVEL_LOW.id == level)){
            assignment.LevelLow();
        }
        /*状态扭转*/
        int state = params.getState();
        if (assignment.getState().id != state){
            assignment.changeState(state);
        }
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId", type = String.class,necessary = true, description = "待删除的任务Id"))
    @DocMethod(description = "删除任务", index = 8)
    public String delete(FriendlyObject params) throws ApiException {
        m_AssignmentService.deleteaAssignment(params.getString("AssignmentId"));
        return "删除成功";
    }


    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至评估中",index = 9)
    public AssignmentView turnEstimate(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnEstimate();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至规划中",index = 10)
    public AssignmentView turnPlanning(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnPlanning();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至待开发",index = 11)
    public AssignmentView turnWaitingDevelop(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnWaitingDevelop();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至开发中",index = 12)
    public AssignmentView turnDevelop(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnDevelop();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至待测试",index = 13)
    public AssignmentView turnWaitingTest(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnWaitingTest();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至测试中",index = 14)
    public AssignmentView turnTesting(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnTesting();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至测试通过",index = 15)
    public AssignmentView turnPassTest(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnPassTest();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至上线",index = 16)
    public AssignmentView turnOnLine(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnOnLine();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至已拒绝",index = 17)
    public AssignmentView turnReject(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnReject();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "AssignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至挂起",index = 18)
    public AssignmentView turnPending(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("AssignmentId"));
        assignment.turnPending();
        return AssignmentView.valueOf(assignment);
    }



}
