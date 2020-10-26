package cn.haichang.assignment.weforward.specialWord;

import cn.haichang.assignment.Assignment;
import cn.weforward.framework.doc.AbstractDocSpecialWord;
import org.springframework.stereotype.Component;

/**
 * 任务状态规则
 * @author HaiChang
 * @date 2020/10/26
 **/
@Component
public class AssignmentStateSpecialWord extends AbstractDocSpecialWord {
    public AssignmentStateSpecialWord() {
        super("任务状态", null);
        setTabelHeader("任务状态id","任务状态名称");
        addTableItem(Assignment.STATES);
    }
}
