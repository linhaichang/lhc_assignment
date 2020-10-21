package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/21
 **/
@DocObject(description = "更新标签参数")
public class UpdateLableParam extends LableParam {
    protected String m_Id;
    @DocAttribute(necessary = true,description = "标签id",example = "我是标签id12345")
    public String getLableId(){
        return m_Id;
    }
    public void setLableId(String id){
        m_Id = id;
    }
}
