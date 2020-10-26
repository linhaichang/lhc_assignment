package cn.haichang.assignment.weforward.specialWord;

import cn.haichang.assignment.Assignment;
import cn.weforward.framework.doc.AbstractDocSpecialWord;
import org.springframework.stereotype.Component;

/**
 * 任务删除状态
 * @author HaiChang
 * @date 2020/10/26
 **/
@Component
public class AssignmentDeleteSpecialWord extends AbstractDocSpecialWord {
    public AssignmentDeleteSpecialWord() {
        super("任务删除状态", null);
        setTabelHeader("任务删除id","任务删除");
        addTableItem(Assignment.STATE_DELETE);
    }
}
