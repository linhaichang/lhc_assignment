package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.weforward.param.AssignmentParam;
import cn.haichang.assignment.weforward.param.UpdateAssignmentParam;
import cn.haichang.assignment.weforward.view.AssignmentView;
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
import java.util.List;

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
        List<String> handlers = params.getHandlers();
        String charger = params.getCharger();
        String lableId = params.getLable().getId().toString();
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

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "id",type = String.class,necessary = true,description = "任务isd"))
    @DocMethod(description = "获取任务",index = 1)
    public AssignmentView get(FriendlyObject params){
        return AssignmentView.valueOf(check(m_AssignmentService.getAssignment(params.getString("id"))));
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "更新任务" , index = 2)
    public AssignmentView update(UpdateAssignmentParam params) throws ApiException {
        Assignment assignment = m_AssignmentService.getAssignment(params.getId());
        assignment = check(assignment);
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
        assignment.addHandler(params.getHandlers());
        assignment.addFollower(params.getFollowers());
        assignment.setCharger(params.getCharger());
        assignment.setLableId(params.getLable().getId().toString());
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
            assignment.changState(state);
        }
        return AssignmentView.valueOf(assignment);
    }

    private Assignment check(Assignment assignment){
        if (null == assignment){
            return null;
        }
        if (!StringUtil.eq(assignment.getCreator(), getCreator())){
            return null;
        }
        return assignment;
    }



}
