package cn.haichang.assignment.weforward;

import cn.haichang.assignment.MyException;
import cn.weforward.protocol.StatusCode;
import cn.weforward.protocol.client.execption.MicroserviceException;
import cn.weforward.protocol.support.CommonServiceCodes;
import org.springframework.stereotype.Component;

/**
 * @author HaiChang
 * @date 2020/10/27
 **/
@Component
public class AssignmentServiceCode extends CommonServiceCodes {
    private final static StatusCode CODE_ASSIGNMENT_EXCEPTION = new StatusCode(
            MicroserviceException.CUSTOM_CODE_START,"自定义异常");
    /**
     * 获取错误码
     *
     * @param e
     * @return
     */
    public static int getCode(MyException e) {
        return CODE_ASSIGNMENT_EXCEPTION.code;
    }
}
