package cn.haichang.assignment.weforward;

import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.weforward.param.BugParam;
import cn.haichang.assignment.weforward.view.BugView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.KeepServiceOrigin;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.support.datatype.FriendlyObject;

import javax.annotation.Resource;
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
    public BugView create(BugParam params){
        String assignmentId = params.getAssignmentId();
        String bugContent = params.getBugContent();
        int severity = params.getSeverity();
        Set<String > testers = new HashSet<>(params.getTester());
        String versionAndPlatform = params.getVersionAndPlatform();
        System.out.println(versionAndPlatform);
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
        System.out.println(id);
        return m_AssignmentService.getBugByAssignmentId(id);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "BugId", type = String.class, necessary = true, description = "BugId"))
    @DocMethod(description = "根据BugId搜索Bug", index = 1)

}
