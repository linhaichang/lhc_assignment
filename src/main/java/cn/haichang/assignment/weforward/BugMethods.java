package cn.haichang.assignment.weforward;

import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Bug;
import cn.haichang.assignment.weforward.param.BugParam;
import cn.haichang.assignment.weforward.param.LogView;
import cn.haichang.assignment.weforward.param.LogsParam;
import cn.haichang.assignment.weforward.param.UpdateBugParam;
import cn.haichang.assignment.weforward.view.BugView;
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
public class BugMethods {
    @Resource
    protected AssignmentService m_AssignmentService;

    @WeforwardMethod
    @DocMethod(description = "创建Bug",index = 0)
    public BugView create(BugParam params) throws ApiException {
        String assignmentId = params.getAssignmentId();
        String bugContent = params.getBugContent();
        int severity = params.getSeverity();
        Set<String > testers = new HashSet<>(params.getTesters());
        String versionAndPlatform = params.getVersionAndPlatform();
        ValidateUtil.isEmpty(assignmentId, "任务id不能为空");
        ValidateUtil.isEmpty(bugContent, "缺陷内容不能为空");
        ValidateUtil.isEmpty(severity, "严重性不能为空");
        ValidateUtil.isEmpty(testers, "测试人员不能为空");
        ValidateUtil.isEmpty(versionAndPlatform, "版本与平台不能为空");
        Bug bug = m_AssignmentService.createBug(assignmentId,
                bugContent, severity, testers, versionAndPlatform);
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "assignmentId", type = String.class, necessary = true, description = "任务Id"))
    @DocMethod(description = "根据任务Id搜索Bug", index = 1)
    public ResultPage<Bug> getBugByAssignmentId(FriendlyObject params) throws ApiException {
        String id = params.getString("assignmentId");
        ValidateUtil.isEmpty(id, "任务id不能为空");
        return m_AssignmentService.getBugByAssignmentId(id);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId", type = String.class, necessary = true, description = "BugId"))
    @DocMethod(description = "根据BugId搜索Bug", index = 2)
    public BugView getBugByBugId(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        return BugView.valueOf(m_AssignmentService.getBug(bugId));
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "keywords",type = String.class,necessary = true,description = "搜索关键词"))
    @DocMethod(description = "通过关键词获取Bug",index = 3)
    public ResultPage<Bug> getByKeyWord(FriendlyObject params) throws ApiException {
        String keywords = params.getString("keywords");
        ValidateUtil.isEmpty(keywords, "关键字id不能为空");
        return m_AssignmentService.getBugByKeyWord(keywords);
    }


    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "更新Bug",index = 4)
    public BugView update(UpdateBugParam params) throws ApiException {
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
        bug.addTestHandler(handlers);

        bug.setLastTime(new Date());

        int severity = params.getSeverity();
        if (Bug.OPTION_SEVERITY_ERROR.id == severity){
            bug.setSeverity(Bug.OPTION_SEVERITY_ERROR.id);
        }else if (Bug.OPTION_SEVERITY_EFFECT.id == severity){
            bug.setSeverity(Bug.OPTION_SEVERITY_EFFECT.id);
        }else if (Bug.OPTION_SEVERITY_NEWASSIGNMENT.id == severity){
            bug.setSeverity(Bug.OPTION_SEVERITY_NEWASSIGNMENT.id);
        }else if (Bug.OPTION_SEVERITY_ADVISE.id == severity){
            bug.setSeverity(Bug.OPTION_SEVERITY_ADVISE.id);
        }else {
            throw new ApiException(0, "无此选项，选项参数为，31-34");
        }
        return BugView.valueOf(bug);
    }
    /*状态扭转*/
    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至待纠正中",index = 5)
    public BugView turnWaitingCorrect(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnWaitingCorrect();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至待复测中",index = 6)
    public BugView turnWaitingRetest(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnWaitingRetest();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至建议不作修改",index = 7)
    public BugView turnAdviseDontEdit(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnAdviseDontEdit();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至申请无法修改",index = 8)
    public BugView turnAskingCantEdit(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnAskingCantEdit();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至已解决",index = 9)
    public BugView turnSolved(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnSolved();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至不作修改",index = 10)
    public BugView turnNoEdit(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnNoEdit();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至无法修改",index = 11)
    public BugView turnCantSolved(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnCantSolved();
        return BugView.valueOf(bug);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId",type = String.class,necessary = true,description = "Bugid"))
    @DocMethod(description = "缺陷状态扭转至重新打开",index = 12)
    public BugView turnReopen(FriendlyObject params) throws ApiException {
        String bugId = params.getString("BugId");
        ValidateUtil.isEmpty(bugId, "缺陷id不能为空");
        Bug bug = m_AssignmentService.getBug(bugId);
        bug.turnReopen();
        return BugView.valueOf(bug);
    }

    @WeforwardMethod
    @DocMethod(description = "获取Bug日志", index = 13)
    public ResultPage<LogView> logs(LogsParam params) throws ApiException {
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
}
