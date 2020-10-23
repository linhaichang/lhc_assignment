package cn.haichang.assignment.weforward;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.framework.ApiException;

import java.util.Date;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
public interface Bug {
    NameItem STATE_WAIT_CORRECT = NameItem.valueOf("待修正", 20);
    NameItem STATE_WAIT_RETEST = NameItem.valueOf("待复测",21 );
    NameItem STATE_ADVISE_DONT_EDIT = NameItem.valueOf("建议不做修改", 22);
    NameItem STATE_ASK_CANT_EDIT =NameItem.valueOf("申请无法修改",23);
    NameItem STATE_SOLVED = NameItem.valueOf("已解决", 24);
    NameItem STATE_NO_EDIT = NameItem.valueOf("不作修改", 25);
    NameItem STATE_CANT_SOLVE= NameItem.valueOf("无法解决",26 );
    NameItem STATE_REOPEN= NameItem.valueOf("重新打开", 27);
    /** 全部BUG状态*/
    NameItems STATES_BUGS = NameItems.valueOf(STATE_WAIT_CORRECT,STATE_WAIT_RETEST,STATE_ADVISE_DONT_EDIT,
            STATE_ASK_CANT_EDIT,STATE_SOLVED,STATE_NO_EDIT,STATE_CANT_SOLVE,STATE_REOPEN);

    NameItem OPTION_SEVERITY_ERROR = NameItem.valueOf("功能错误", 31);
    NameItem OPTION_SEVERITY_EFFECT = NameItem.valueOf("影响流程", 32);
    NameItem OPTION_SEVERITY_NEWASSIGNMENT = NameItem.valueOf("信秀秋", 33);
    NameItem OPTION_SEVERITY_ADVISE = NameItem.valueOf("优化建议", 34);
    /** 全部严重性*/
    NameItems SEVERITY =NameItems.valueOf(OPTION_SEVERITY_ERROR,OPTION_SEVERITY_EFFECT,OPTION_SEVERITY_NEWASSIGNMENT,OPTION_SEVERITY_ADVISE);

    NameItem STATE_DELETE = NameItem.valueOf("删除", 28);

    UniteId getId();
    String getAssignmentId();

    String getBugContent();
    void setBugContent(String content);

    int getSeverity();
    void setSeverity(int severity);

    Set<String> getTesters();
    void setTesters(Set<String> tester);

    Set<String> getTestHandlers();
    void addTestHandler(Set<String> testHanders);

    NameItem getState();

//    boolean isSolved();
//    void setSolved(boolean solved);

    String getCreator();

    /*创建时间*/
    Date getCreateTime();

    void setLastTime(Date lastTime);
    Date getLastTime();

    void turnWaitingCorrcet() throws ApiException;
    void turnWaitingRetest() throws ApiException;
    void turnAdviseDontEdit()throws ApiException;
    void turnAskingCantEdit()throws ApiException;
    void turnSolved()throws ApiException;
    void turnNoEdit()throws ApiException;
    void turnCantSolved()throws ApiException;
    void turnReopen()throws ApiException;

    void deleteBug()throws ApiException;

    public ResultPage<BusinessLog> getLogs();

}
