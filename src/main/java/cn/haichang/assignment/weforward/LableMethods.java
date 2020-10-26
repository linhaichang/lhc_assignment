package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.impl.AssignmentImpl;
import cn.haichang.assignment.weforward.param.LableParam;
import cn.haichang.assignment.weforward.param.UpdateAssignmentParam;
import cn.haichang.assignment.weforward.param.UpdateLableParam;
import cn.haichang.assignment.weforward.view.LableView;
import cn.haichang.assignment.weforward.view.SimpleLableView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.common.util.TransResultPage;
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
    public LableView get(FriendlyObject params) throws ApiException {
        String lableId = params.getString("LableId");
        ValidateUtil.isEmpty(lableId,"标签Id不能为空");
        return LableView.valueOf(m_AssignmentService.getLable(lableId));
    }

    @KeepServiceOrigin
    @WeforwardMethod
    @DocParameter(@DocAttribute(name = "LableId", type = String.class,necessary = true, description = "标签Id"))
    @DocMethod(description = "删除标签", index = 4)
    public String delete(FriendlyObject params) throws ApiException {
        String lableId = params.getString("LableId");
        ValidateUtil.isEmpty(lableId,"标签Id不能为空");
        if (null == m_AssignmentService.getLable(lableId)){
            throw new ApiException(0,"无此标签");
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
    public ResultPage<SimpleLableView> getAll() {
        ResultPage<Lable> rp = m_AssignmentService.getAllLables();
        return new TransResultPage<SimpleLableView, Lable>(rp) {
            @Override
            protected SimpleLableView trans(Lable src) {
                return SimpleLableView.valueOf(src);
            }
        };
    }
    @KeepServiceOrigin
    @WeforwardMethod
    @DocMethod(description = "修改标签名", index = 3)
    public String update(UpdateLableParam params) throws ApiException {
        String lableId = params.getLableId();
        String lableName = params.getLableName();
        ValidateUtil.isEmpty(lableId, "标签id不能为空");
        ValidateUtil.isEmpty(lableName, "标签名不能为空");
        Lable lable = m_AssignmentService.getLable(lableId);
        if (null == lable){
            return "无此标签";
        }
        lable.setLableName(lableName);
        return "成功修改标签";
    }
}
