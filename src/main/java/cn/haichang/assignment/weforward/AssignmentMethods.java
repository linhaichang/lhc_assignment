package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.param.*;
import cn.haichang.assignment.weforward.view.AssignmentView;
import cn.haichang.assignment.weforward.view.BugAnalysisView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.KeepServiceOrigin;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.framework.exception.ForwardException;
import cn.weforward.framework.support.Global;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.support.datatype.FriendlyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

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
        Date startTime = params.getStartTime();
        Date endTime = params.getEndTime();
        int level = params.getLevel();
        ValidateUtil.isEmpty(title, "标题不能为空");
        ValidateUtil.isEmpty(content, "内容不能为空");
        ValidateUtil.isEmpty(lableId, "标签不能为空");
        ValidateUtil.isEmpty(startTime, "预计开始时间不能为空");
        ValidateUtil.isEmpty(endTime, "预计结束时间能为空");
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
        ValidateUtil.isEmpty(lableId, "标签不能为空");
        ValidateUtil.isEmpty(startTime, "预计开始时间不能为空");
        ValidateUtil.isEmpty(endTime, "预计结束时间能为空");
        ValidateUtil.isEmpty(fatherId, "父Id不能为空");
        Assignment assignment = m_AssignmentService.createAssignmentSon(title
                , content, getCreator(), handlers, charger, lableId, startTime
                ,endTime,level,fatherId);
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "通过任务id获取任务",index = 2)
    public AssignmentView get(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "LableId",type = String.class,necessary = true,description = "标签id"))
    @DocMethod(description = "通过标签id获取任务",index = 3)
    public ResultPage<AssignmentImpl> getByLableId(FriendlyObject params) throws ApiException {
        String id = params.getString("LableId");
        ValidateUtil.isEmpty(id, "标签Id不能为空");
        Lable lableId = m_AssignmentService.getLable(id);
        return lableId.getAssignments();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "fatherId",type = String.class,necessary = true,description = "父id"))
    @DocMethod(description = "通过父id获取子任务",index = 4)
    public ResultPage<Assignment> getByfahterId(FriendlyObject params) throws ApiException {
        String fatherId = params.getString("fatherId");
        ValidateUtil.isEmpty(fatherId, "父Id不能为空");
        return m_AssignmentService.getSonAssignments(fatherId);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "通过条件查询获取任务" , index = 5)
    public ResultPage<Assignment> getByCondition(ConditionQueryAssignmentParam params) throws ApiException {
        String personName = params.getPersonName();
        int personType = params.getPersonType();
        int conditionState = params.getConditionState();
        ValidateUtil.isEmpty(personName, "人名不能为空");
        ValidateUtil.isEmpty(personType, "类型不能为空");
        ValidateUtil.isEmpty(conditionState, "任务状态不能为空");
        return m_AssignmentService.searchAssignment(personName,personType,conditionState);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "keywords",type = String.class,necessary = true,description = "搜索关键词"))
    @DocMethod(description = "通过关键词获取任务",index = 6)
    public ResultPage<Assignment> getByKeyWord(FriendlyObject params) throws ApiException {
        String keywords = params.getString("keywords");
        ValidateUtil.isEmpty(keywords, "关键词不能为空");
        return m_AssignmentService.getByKeyWord(keywords);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "获取所有任务" , index = 7)
    public ResultPage<Assignment> getAllAssignment(){
        return m_AssignmentService.getAllAssignments();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "更新任务" , index = 8)
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
        String followers = params.getFollowers();
        assignment.addFollower(followers);

        String charger = params.getCharger();
        if (!StringUtil.isEmpty(charger)){
            assignment.setCharger(params.getCharger());
        }
        /*修改标签*/
        String lableId = params.getLableId();
        if (!StringUtil.isEmpty(lableId)){
            assignment.setLableId(lableId);
        }
        Date startTime = params.getStartTime();
        if (null != startTime){
            assignment.setStartTime(startTime);
        }
        Date endTime = params.getEndTime();
        if (null != endTime){
        assignment.setEndTime(endTime);
        }
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
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "assignmentId", necessary = true, description = "待操作的任务Id"),
            @DocAttribute(name = "handlerName", necessary = true, description = "待移除的跟进人姓名")
    })
    @DocMethod(description = "移除跟进人", index = 9)
    public String removeHandler(FriendlyObject params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getString("assignmentId"));
        assignment.removeHandler(params.getString("handlerName"));
        return "移除成功";
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId", type = String.class,necessary = true, description = "待删除的任务Id"))
    @DocMethod(description = "删除任务", index = 10)
    public String delete(FriendlyObject params) throws ApiException {
        m_AssignmentService.deleteaAssignment(params.getString("assignmentId"));
        return "删除成功";
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId", type = String.class,necessary = true, description = "待删除的任务Id"))
    @DocMethod(description = "获取缺陷分析", index = 11)
    public BugAnalysisView getBugAnalysis(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        int bugsCount = assignment.getBugsCount();
        int bugsFinishCount = assignment.getBugsFinishCount();
        Map<String, Integer> stateAnalysis = assignment.getStateAnalysis();
        Map<String, Integer> testerAndCount = assignment.getTesterAndCount();
        Map<String, Integer> handlerAndCount = assignment.getHandlerAndCount();
        BugAnalysisView view = new BugAnalysisView(bugsCount,bugsFinishCount,stateAnalysis,
                testerAndCount,handlerAndCount);
        return view;
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至评估中",index = 12)
    public AssignmentView turnEstimate(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnEstimate();
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至规划中",index = 13)
    public AssignmentView turnPlanning(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnPlanning();
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至待开发",index = 14)
    public AssignmentView turnWaitingDevelop(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnWaitingDevelop();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至开发中",index = 15)
    public AssignmentView turnDevelop(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnDevelop();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至待测试",index = 16)
    public AssignmentView turnWaitingTest(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnWaitingTest();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至测试中",index = 17)
    public AssignmentView turnTesting(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnTesting();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至测试通过",index = 18)
    public AssignmentView turnPassTest(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnPassTest();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至上线",index = 19)
    public AssignmentView turnOnLine(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnOnLine();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至已拒绝",index = 20)
    public AssignmentView turnReject(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnReject();
        return AssignmentView.valueOf(assignment);
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至挂起",index = 21)
    public AssignmentView turnPending(FriendlyObject params) throws ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);        assignment.turnPending();
        return AssignmentView.valueOf(assignment);
    }

    @WeforwardMethod
    @DocMethod(description = "获取任务日志", index = 22)
    public ResultPage<LogView> logs(LogsParam params) throws ApiException {
        String id = params.getId();
        ValidateUtil.isEmpty(id, "id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(id);
        ForwardException.forwardToIfNeed(assignment);
        return new TransResultPage<LogView, BusinessLog>(assignment.getLogs()) {
            @Override
            protected LogView trans(BusinessLog src) {
                return LogView.valueOf(src);
            }
        };
    }

}
