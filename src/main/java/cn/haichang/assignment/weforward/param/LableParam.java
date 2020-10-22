package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/20
 **/
@DocObject(description = "标签参数")
public class LableParam {
    protected String m_LableName;
    private String m_LableId;

    @DocAttribute(necessary = true,description = "标签名",example = "我是标签名")
    public String getLableName(){
        return m_LableName;
    }
    public void setLableName(String lableName){
        m_LableName = lableName;
    }
    @DocAttribute(description = "待修改的标签id",example = "我是待修改的标签id")
    public String getLableId(){
        return m_LableId;
    }
    public void setLableId(String lableId){
        m_LableId = lableId;
    }
}
