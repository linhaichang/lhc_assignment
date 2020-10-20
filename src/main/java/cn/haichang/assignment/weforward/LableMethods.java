package cn.haichang.assignment.weforward;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.AssignmentService;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.weforward.param.LableParam;
import cn.haichang.assignment.weforward.view.LableView;
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
    @DocMethod(description = "创建标签",index = 0)
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
    @DocMethod(description = "删除标签", index = 2)
    public LableView delete(FriendlyObject params) throws ApiException {
        System.out.println(params+"我是param");
        Lable lable = m_AssignmentService.getLable(params.getString("LableId"));
        System.out.println(lable+"我是lable");
        if (null == lable){
            return null;
        }
        System.out.println(params.getString("LableId")+"我是我lableid");
        if (false){
            boolean isDelete = m_AssignmentService.deleteLable(params.getString("LableId"));
        }else {
            throw new ApiException(123, "此标签下还要任务，不能删除");
        }
        return LableView.valueOf(lable);
    }
}
