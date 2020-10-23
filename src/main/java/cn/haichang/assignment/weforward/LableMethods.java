package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.param.LableParam;
import cn.haichang.assignment.weforward.param.UpdateAssignmentParam;
import cn.haichang.assignment.weforward.param.UpdateLableParam;
import cn.haichang.assignment.weforward.view.LableView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.KeepServiceOrigin;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.protocol.client.util.IdBean;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.support.datatype.FriendlyObject;
import org.springframework.beans.support.PagedListHolder;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/20
 **/
@DocMethods(index = 200)
@WeforwardMethods
public class LableMethods {
    @Resource
    protected AssignmentService m_AssignmentService;

    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "LableName", type = String.class, necessary = true, description = "标签名"))
    @DocMethod(description = "新建标签",index = 0)
    public LableView create(LableParam params) throws ApiException {
        String lableName = params.getLableName();
        ValidateUtil.isEmpty(lableName, "标签名不可为空");
        Lable lable = m_AssignmentService.createLable(lableName);
        return LableView.valueOf(lable);
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "LableId", type = String.class, necessary = true, description = "标签id"))
    @DocMethod(description = "获取标签", index = 1)
    public LableView get(FriendlyObject params){
        return LableView.valueOf(m_AssignmentService.getLable(params.getString("LableId")));
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "LableId", type = String.class,necessary = true, description = "标签Id"))
    @DocMethod(description = "删除标签", index = 4)
    public String delete(FriendlyObject params) throws ApiException {
        if (null == m_AssignmentService.getLable(params.getString("LableId"))){
            throw new ApiException(999,"无此标签");
        }
        Lable lable = m_AssignmentService.getLable(params.getString("LableId"));
        /**
         * 判断此标签的任务数是否=0
         */
        int i =lable.getAssignments().getCount();
        if (lable.getAssignments().getCount() == 0 ){
            m_AssignmentService.deleteLable(params.getString("LableId"));
        }else {
            throw new ApiException(123, "此标签下还有"+i+"条任务，不能删除");
        }
        return "成功删除标签";
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "获取所有标签", index = 2)
    public ResultPage<Lable> getAll(/*FriendlyObject params*/){
        return m_AssignmentService.getAllLables();
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "修改标签名", index = 3)
    public String update(UpdateLableParam params){
        Lable lable = m_AssignmentService.getLable(params.getLableId());
        if (null == lable){
            return "无此标签";
        }
        lable.setLableName(params.getLableName());
        return "成功修改标签";
    }
}
