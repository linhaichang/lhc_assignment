package cn.haichang.assignment;

import cn.haichang.assignment.weforward.AssignmentServiceCode;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.ExceptionHandler;

/**
 * @author HaiChang
 * @date 2020/10/27
 **/
public class MyException extends Exception {

    public MyException(String message) {
        super(message);
    }

}


