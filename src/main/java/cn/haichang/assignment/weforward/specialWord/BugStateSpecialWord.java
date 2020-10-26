package cn.haichang.assignment.weforward.specialWord;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Bug;
import cn.weforward.framework.doc.AbstractDocSpecialWord;
import org.springframework.stereotype.Component;

/**
 * 缺陷状态规则
 * @author HaiChang
 * @date 2020/10/26
 **/
@Component
public class BugStateSpecialWord extends AbstractDocSpecialWord {
    public BugStateSpecialWord() {
        super("缺陷状态", null);
        setTabelHeader("缺陷状态id", "缺陷状态名称");
        addTableItem(Bug.STATES_BUGS);
    }
}
