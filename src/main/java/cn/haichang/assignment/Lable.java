package cn.haichang.assignment;

import cn.weforward.data.UniteId;

import java.util.List;

/**
 * @author HaiChang
 * @date 2020/10/16
 **/
public interface Lable {
    UniteId getId();

    void setLableName(String lableName);
    String getLableName();

    void deleteLable();

//    List<String> getAssignments();
}
