package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.MyException;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.param.*;
import cn.haichang.assignment.weforward.view.AssignmentView;
import cn.haichang.assignment.weforward.view.BugAnalysisView;
import cn.haichang.assignment.weforward.view.SimpleAssignmentView;
import cn.haichang.assignment.weforward.view.SimpleSonAssignmentView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.framework.*;
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

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
@DocMethods(index = 100)
@WeforwardMethods
public class AssignmentMethods implements ExceptionHandler {
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
        Assignment assignment = m_AssignmentService.createAssignment(title
        , content, handlers, charger, lableId, startTime
        ,endTime,level);
        return AssignmentView.valueOf(assignment);
    }

    @WeforwardMethod
    @DocMethod(description = "创建子任务",index = 1)
    public AssignmentView createSon(SonAssignmentParam params) throws ApiException, MyException {
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
        ValidateUtil.isEmpty(fatherId, "父Id不能为空");
        m_AssignmentService.getAssignment(fatherId);
        Assignment assignment = m_AssignmentService.createAssignmentSon(title
                , content, handlers, charger, lableId, startTime
                ,endTime,level,fatherId);
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "通过任务id获取任务",index = 2)
    public AssignmentView get(FriendlyObject params) throws MyException, ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        return AssignmentView.valueOf(assignment);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "lableId",type = String.class,necessary = true,description = "标签id"),
            @DocAttribute(name = "page",type = Integer.class,necessary = true,description = "第几页"),
            @DocAttribute(name = "pageSize",type = Integer.class,necessary = true,description = "一页的大小")
    })
    @DocMethod(description = "通过标签id获取任务",index = 3)
    public ResultPage<SimpleAssignmentView> getByLableId(FriendlyObject params) throws ApiException, MyException {
        String id = params.getString("lableId");
        ValidateUtil.isEmpty(id, "标签Id不能为空");
        Lable lableId = m_AssignmentService.getLable(id);
        ResultPage<AssignmentImpl> rp = lableId.getAssignments();
        return new TransResultPage<SimpleAssignmentView, AssignmentImpl>(rp) {
            @Override
            protected SimpleAssignmentView trans(AssignmentImpl src) {
                return SimpleAssignmentView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "通过父id获取子任务",index = 4)
    public ResultPage<SimpleSonAssignmentView> getByFatherId(AssignmentListParam params) throws MyException, ApiException {
        String fatherId = params.getAssignmentId();
        ValidateUtil.isEmpty(fatherId, "父Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(fatherId);
        ResultPage<Assignment> rp = m_AssignmentService.getSonAssignments(assignment.getId().getOrdinal());
        return new TransResultPage<SimpleSonAssignmentView, Assignment>(rp) {
            @Override
            protected SimpleSonAssignmentView trans(Assignment src) {
                return SimpleSonAssignmentView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "通过条件查询获取任务" , index = 5)
    public ResultPage<SimpleAssignmentView> getByCondition(ConditionQueryAssignmentParam params) throws ApiException {
        String personName = params.getPersonName();
        int personType = params.getPersonType();
        int conditionState = params.getConditionState();
        ValidateUtil.isEmpty(personName, "人名不能为空");
        ValidateUtil.isEmpty(personType, "类型不能为空");
        ValidateUtil.isEmpty(conditionState, "任务状态不能为空");
        ResultPage<Assignment> rp = m_AssignmentService.searchAssignment(personName, personType, conditionState);
        return new TransResultPage<SimpleAssignmentView, Assignment>(rp) {
            @Override
            protected SimpleAssignmentView trans(Assignment src) {
                return SimpleAssignmentView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "keywords",type = String.class,necessary = true,description = "搜索关键词"),
            @DocAttribute(name = "page",type = Integer.class,necessary = true,description = "第几页"),
            @DocAttribute(name = "pageSize",type = Integer.class,necessary = true,description = "一页的大小")
    })
    @DocMethod(description = "通过关键词获取任务",index = 6)
    public ResultPage<SimpleAssignmentView> getByKeyWord(FriendlyObject params) throws ApiException {
        String  keywords = params.getString("keywords");
        ValidateUtil.isEmpty(keywords, "关键词不能为空");
        ResultPage<Assignment> rp = m_AssignmentService.getByKeyWord(keywords);
        return new TransResultPage<SimpleAssignmentView,Assignment>(rp) {
            @Override
            protected SimpleAssignmentView trans(Assignment src) {
                return SimpleAssignmentView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "page",type = Integer.class,necessary = true,description = "第几页"),
            @DocAttribute(name = "pageSize",type = Integer.class,necessary = true,description = "一页的大小")
    })
    @DocMethod(description = "获取所有任务" , index = 7)
    public ResultPage<SimpleAssignmentView> getAllAssignment(FriendlyObject params){
        ResultPage<Assignment> rp = m_AssignmentService.getAllAssignments();
        return new TransResultPage<SimpleAssignmentView,Assignment>(rp) {
            @Override
            protected SimpleAssignmentView trans(Assignment src) {
                return SimpleAssignmentView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "更新任务" , index = 8)
    public AssignmentView update(UpdateAssignmentParam params) throws MyException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getId());

        if (null == assignment){
            return null;
        }
        /*修改标题*/
        String title = params.getTitle();
        if (!StringUtil.isEmpty(title)){
            assignment.setTitle(title);
        }
        /*修改内容*/
        String content = params.getContent();
        if (!StringUtil.isEmpty(content)){
            assignment.setContent(content);
        }
        /*增加处理人*/
        assignment.addHandler(new HashSet<>(params.getHandlers()));
        /*修改负责人*/
        String charger = params.getCharger();
        if (!StringUtil.isEmpty(charger)){
            assignment.setCharger(params.getCharger());
        }
        /*修改标签*/
        String lableId = params.getLableId();
        if (!StringUtil.isEmpty(lableId)){
            assignment.setLableId(lableId);
        }
        /*修改预计开始时间*/
        Date startTime = params.getStartTime();
        assignment.setStartTime(startTime);
        /*修改预计结束时间*/
        Date endTime = params.getEndTime();
        assignment.setEndTime(endTime);
        /*修改优先级*/
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
            @DocAttribute(name = "follower",necessary = true,description = "跟进人"),
    })
    @DocMethod(description = "增加跟进人" , index = 9)
    public String addFollower(FriendlyObject params) throws MyException, ApiException {
        String assignmentId = params.getString("assignmentId");
        String follower = params.getString("follower");
        ValidateUtil.isEmpty(assignmentId, "任务id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.addFollower(follower);
        return "跟进成功";
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "assignmentId", necessary = true, description = "待操作的任务Id"),
            @DocAttribute(name = "handlerName", necessary = true, description = "待移除的处理人姓名")
    })
    @DocMethod(description = "移除处理人", index = 10)
    public String removeHandler(FriendlyObject params) throws MyException, ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        String handlerName = params.getString("handlerName");
        assignment.removeHandler(handlerName);
        return "移除成功";
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId", type = String.class,necessary = true, description = "待删除的任务Id"))
    @DocMethod(description = "删除任务", index = 11)
    public String delete(FriendlyObject params) throws MyException {
        m_AssignmentService.deleteAssignment(params.getString("assignmentId"));
        return "删除成功";
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId", type = String.class,necessary = true, description = "待删除的任务id"))
    @DocMethod(description = "获取缺陷分析结果", index = 12)
    public BugAnalysisView getBugAnalysis(FriendlyObject params) throws MyException, ApiException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        //缺陷总数
        int bugsCount = assignment.getBugsCount();
        //已完成的缺陷总数
        int bugsFinishCount = assignment.getBugsFinishCount();
        //状态统计结果
        Map<String, Integer> stateAnalysis = assignment.getStateAnalysis();
        //测试人员统计结果
        Map<String, Integer> testerAndCount = assignment.getTesterAndCount();
        //处理人员统计
        Map<String, Integer> handlerAndCount = assignment.getHandlerAndCount();
        BugAnalysisView view = new BugAnalysisView(bugsCount,bugsFinishCount,stateAnalysis,
                testerAndCount,handlerAndCount);
        return view;
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至评估中",index = 13)
    public void turnEstimate(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnEstimate();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至规划中",index = 14)
    public void turnPlanning(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnPlanning();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至待开发",index = 15)
    public void turnWaitingDevelop(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnWaitingDevelop();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至开发中",index = 16)
    public void turnDevelop(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnDevelop();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至待测试",index = 17)
    public void turnWaitingTest(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnWaitingTest();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至测试中",index = 18)
    public void turnTesting(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnTesting();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至测试通过",index = 19)
    public void turnPassTest(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnPassTest();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至上线",index = 20)
    public void turnOnLine(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnOnLine();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至已拒绝",index = 21)
    public void turnReject(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnReject();
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "任务状态扭转至挂起",index = 22)
    public void turnPending(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.turnPending();
    }

    @WeforwardMethod
    @DocMethod(description = "清空预计开始时间",index = 23)
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    public void emptyStartTime(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.setStartTime(null);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"))
    @DocMethod(description = "清空预计结束时间",index = 24)
    public void emptyEndTime(FriendlyObject params) throws ApiException, MyException {
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(assignmentId, "任务Id不能为空");
        Assignment assignment = m_AssignmentService.getAssignment(assignmentId);
        assignment.setEndTime(null);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "获取任务日志", index = 25)
    public ResultPage<LogView> logs(LogsParam params) throws ApiException, MyException {
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

    @Override
    public Throwable exception(Throwable error) {
        if (error instanceof MyException){
            return new ApiException(AssignmentServiceCode.getCode((MyException) error), error.getMessage());
        }
        return error;
    }
}
