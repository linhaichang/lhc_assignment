package cn.haichang.assignment.weforward.specialWord;

import cn.haichang.assignment.Bug;
import cn.weforward.framework.doc.AbstractDocSpecialWord;
import org.springframework.stereotype.Component;

/**
 * 缺陷选项
 * @author HaiChang
 * @date 2020/10/26
 **/
@Component
public class BugOptionSpecialWord extends AbstractDocSpecialWord {
    public BugOptionSpecialWord() {
        super("缺陷严重性选项", null);
        setTabelHeader("缺陷严重性选项id", "缺陷严重性选项名称");
        addTableItem(Bug.SEVERITY);
    }
}
