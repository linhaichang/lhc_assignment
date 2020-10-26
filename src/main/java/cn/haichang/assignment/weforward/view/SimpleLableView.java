package cn.haichang.assignment.weforward.view;

import cn.haichang.assignment.Lable;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/26
 **/
@DocObject(description = "简略标签视图")
public class SimpleLableView {
    protected Lable lable;
    public static SimpleLableView valueOf(Lable lable){
        return null == lable ? null : new SimpleLableView(lable);
    }

    public SimpleLableView(Lable lable) {
        this.lable = lable;
    }

    @DocAttribute(description = "标签id")
    public String getLableId(){
        return lable.getId().getOrdinal();
    }
    @DocAttribute(description = "标签名")
    public String getLableName(){
        return lable.getLableName();
    }
}
