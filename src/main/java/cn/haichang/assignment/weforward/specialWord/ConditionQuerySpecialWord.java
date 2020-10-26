package cn.haichang.assignment.weforward.specialWord;

import cn.haichang.assignment.AssignmentService;
import cn.weforward.framework.doc.AbstractDocSpecialWord;
import org.springframework.stereotype.Component;

/**
 * 条件查询规则
 * @author HaiChang
 * @date 2020/10/26
 **/
@Component
public class ConditionQuerySpecialWord extends AbstractDocSpecialWord {
    public ConditionQuerySpecialWord(   ) {
        super("条件查询选项","查询指定人员处理的、或负责的、或跟进的任务，按（未完成:0）、（已完成:7）条件查询");
        setTabelHeader("条件id","条件名称");
        addTableItem(AssignmentService.CONDITIONS);
    }
}
