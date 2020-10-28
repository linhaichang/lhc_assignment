package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Bug;
import cn.haichang.assignment.MyException;
import cn.haichang.assignment.weforward.param.*;
import cn.haichang.assignment.weforward.view.BugView;
import cn.haichang.assignment.weforward.view.SimpleAssignmentView;
import cn.haichang.assignment.weforward.view.SimpleBugView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.framework.*;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.framework.exception.ForwardException;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.support.datatype.FriendlyObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
@DocMethods(index = 300)
@WeforwardMethods
public class BugMethods implements ExceptionHandler {
    @Resource
    protected AssignmentService m_AssignmentService;

    @WeforwardMethod
    @DocMethod(description = "创建缺陷",index = 0)
    public BugView create(BugParam params) throws ApiException {
        String assignmentId = params.getAssignmentId();
        String bugContent = params.getBugContent();
        String severity = params.getSeverity();
        Set<String > handlers = new HashSet<>(params.getHandlers());
        String versionAndPlatform = params.getVersionAndPlatform();
        ValidateUtil.isEmpty(assignmentId, "任务id不能为空");
        ValidateUtil.isEmpty(bugContent, "缺陷内容不能为空");
        ValidateUtil.isEmpty(severity, "严重性不能为空");
        ValidateUtil.isEmpty(versionAndPlatform, "版本与平台不能为空");
        Bug bug = m_AssignmentService.createBug(assignmentId,
                bugContent, severity, handlers, versionAndPlatform);
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "assignmentId", type = String.class, necessary = true, description = "任务Id"),
            @DocAttribute(name = "page", type = Integer.class, necessary = true, description = "第几页"),
            @DocAttribute(name = "pageSize", type = Integer.class, necessary = true, description = "一页的大小")
    })
    @DocMethod(description = "根据任务Id搜索缺陷", index = 1)
    public ResultPage<SimpleBugView> getBugByAssignmentId(FriendlyObject params) throws ApiException, MyException {
        String id = params.getString("assignmentId");
        ValidateUtil.isEmpty(id, "任务id不能为空");
        ResultPage<Bug> rp = m_AssignmentService.getBugByAssignmentId(id);
        return new TransResultPage<SimpleBugView,Bug>(rp) {
            @Override
            protected SimpleBugView trans(Bug src) {
                return SimpleBugView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId", type = String.class, necessary = true, description = "缺陷id"))
    @DocMethod(description = "根据缺陷Id搜索缺陷", index = 2)
    public BugView getBugByBugId(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        return BugView.valueOf(m_AssignmentService.getBug(bugId));
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter({
            @DocAttribute(name = "assignmentId",type = String.class,necessary = true,description = "任务id"),
            @DocAttribute(name = "keywords", type = String.class, necessary = true, description = "搜索关键词"),
            @DocAttribute(name = "page", type = Integer.class, necessary = true, description = "第几页"),
            @DocAttribute(name = "pageSize", type = Integer.class, necessary = true, description = "一页的大小")
    })
    @DocMethod(description = "通过关键词获取缺陷列表",index = 3)
    public ResultPage<SimpleBugView> getByKeyWord(FriendlyObject params) throws ApiException, MyException {
        String keywords = params.getString("keywords");
        String assignmentId = params.getString("assignmentId");
        ValidateUtil.isEmpty(keywords, "关键字id不能为空");
        ValidateUtil.isEmpty(assignmentId, "任务id不能为空");
        ResultPage<Bug> rp = m_AssignmentService.getBugByKeyWord(assignmentId,keywords);
        return new TransResultPage<SimpleBugView,Bug>(rp) {
            @Override
            protected SimpleBugView trans(Bug src) {
                return SimpleBugView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "通过条件查询获取缺陷" , index = 4  )
    public ResultPage<SimpleBugView> getByCondition(ConditionQueryBugParam params) throws ApiException, MyException {
        String assignmentId = params.getAssignmentId();
        String tester = params.getTester();
        String handler = params.getHandler();
        int state = params.getState();
        ValidateUtil.isEmpty(state, "状态不能为空");
        ResultPage<Bug> rp = m_AssignmentService.searchBugs(assignmentId,tester,handler
                ,state);
        return new TransResultPage<SimpleBugView, Bug>(rp) {
            @Override
            protected SimpleBugView trans(Bug src) {
                return SimpleBugView.valueOf(src);
            }
        };
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "更新Bug",index = 5)
    public BugView update(UpdateBugParam params) throws ApiException, MyException {
        String id = params.getId();
        ValidateUtil.isEmpty(id, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(id);
        if (null == bug){
            return null;
        }
        String bugContent = params.getBugContent();
        if (!StringUtil.isEmpty(bugContent)){
            bug.setBugContent(bugContent);
        }
        Set<String> testers = new HashSet<>(params.getTesters());
        bug.setTesters(testers);

        Set<String> handlers = new HashSet<>(params.getHandlers());
        bug.setTestHandler(handlers);

        bug.setLastTime(new Date());

        String severity = params.getSeverity();
        if (!StringUtil.isEmpty(severity)){
            bug.setSeverity(severity);
        }

        return BugView.valueOf(bug);
    }
    /*状态扭转*/
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至待修正",index = 6)
    public void turnWaitingCorrect(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnWaitingCorrect();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至待复测中",index = 7)
    public void turnWaitingRetest(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnWaitingRetest();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至建议不作修改",index = 8)
    public void turnAdviseDontEdit(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnAdviseDontEdit();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至申请无法修改",index = 9)
    public void turnAskingCantEdit(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnAskingCantEdit();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至已解决",index = 10)
    public void turnSolved(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnSolved();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至不作修改",index = 11)
    public void turnNoEdit(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnNoEdit();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至无法修改",index = 12)
    public void turnCantSolved(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnCantSolved();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "缺陷id"))
    @DocMethod(description = "缺陷状态扭转至重新打开",index = 13)
    public void turnReopen(FriendlyObject params) throws ApiException, MyException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnReopen();
    }

    @WeforwardMethod
    @DocMethod(description = "获取Bug日志", index = 14)
    public ResultPage<LogView> logs(LogsParam params) throws ApiException, MyException {
        String id = params.getId();
        ValidateUtil.isEmpty(id, "id不能为空");
        Bug bug = m_AssignmentService.getBug(id);
        ForwardException.forwardToIfNeed(bug);
        return new TransResultPage<LogView, BusinessLog>(bug.getLogs()) {
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
