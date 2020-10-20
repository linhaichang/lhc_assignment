package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * @author HaiChang
 * @date 2020/10/19
 **/
@DocObject(description = "更新任务参数")
public class UpdateAssignmentParam extends AssignmentParam{
    protected String m_Id;
    protected int m_level;

    public void setId(String id){
        m_Id = id;
    }
    @DocAttribute(necessary = true,description = "任务id",example = "123")
    public String getId(){
        return m_Id;
    }

}
