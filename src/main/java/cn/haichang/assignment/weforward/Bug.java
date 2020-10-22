package cn.haichang.assignment.weforward;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.data.UniteId;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
public interface Bug {
    NameItem STATE_WAIT_CORRECT = NameItem.valueOf("待修复", 21);
    NameItem STATE_WAIT_RETEST = NameItem.valueOf("待复测",22 );
    NameItem STATE_ADVISE_DONT_EDIT = NameItem.valueOf("建议不做修改", 23);
    NameItem STATE_ASK_CANT_EDIT =NameItem.valueOf("申请无法修改",24 );
    NameItem STATE_SOLVED = NameItem.valueOf("已解决", 25);
    NameItem STATE_NO_EDIT = NameItem.valueOf("不作修改", 26);
    NameItem STATE_CANT_EDIT= NameItem.valueOf("无法解决",27 );
    NameItem STATE_REOPEN= NameItem.valueOf("重新打开", 28);
    /** 全部BUG状态*/
    NameItems STATES_BUG = NameItems.valueOf(STATE_WAIT_CORRECT,STATE_WAIT_RETEST,STATE_ADVISE_DONT_EDIT,
            STATE_ASK_CANT_EDIT,STATE_SOLVED,STATE_NO_EDIT,STATE_CANT_EDIT,STATE_REOPEN);

    UniteId getId();
    void setBugContent();
    String getBugContent();
}
