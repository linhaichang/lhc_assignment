package cn.haichang.assignment.weforward.specialWord;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Bug;
import cn.weforward.framework.doc.AbstractDocSpecialWord;
import org.springframework.stereotype.Component;

/**
 * 任务选项规则
 * @author HaiChang
 * @date 2020/10/26
 **/
@Component
public class AssignmentOptionSpecialWord extends AbstractDocSpecialWord {
    public AssignmentOptionSpecialWord() {
        super("任务优先级选项", null);
        setTabelHeader("任务优先级选项id", "任务优先级选项名称");
        addTableItem(Assignment.LEVELS);
    }
}
