package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Lable;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/20
 **/
@DocObject(description = "标签视图")
public class LableView {
    protected Lable m_Lable;

    public LableView(Lable lable){
        m_Lable = lable;
    }

    public static LableView valueOf(Lable lable){
        return null == lable ? null : new LableView(lable);
    }

    @DocAttribute(description = "标签id")
    public String getId(){
        return m_Lable.getId().getOrdinal();
    }

    @DocAttribute(description = "标签名")
    public String getLableName(){
        return m_Lable.getLableName();
    }

}
