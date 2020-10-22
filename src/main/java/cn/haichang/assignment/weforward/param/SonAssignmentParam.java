package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
public class SonAssignmentParam extends AssignmentParam {
    protected String m_FatherId;
    @DocAttribute(necessary = true,description = "父任务Id",example = "")
    public String getFatherId(){
        return m_FatherId;
    }
    public void setFatherId(String fatherId){
        m_FatherId=fatherId;
    }

}
