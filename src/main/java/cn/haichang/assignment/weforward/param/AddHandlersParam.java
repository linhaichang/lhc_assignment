package cn.haichang.assignment.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/29
 **/
@DocObject(description = "增加处理人参数")
public class AddHandlersParam {
    String assignmentId;
    List<String > handlers;

    @DocAttribute(description = "任务Id")
    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    @DocAttribute(description = "憎加的处理人")
    public List<String> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<String> handlers) {
        this.handlers = handlers;
    }
}
