package cn.haichang.assignment;

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

    NameItem STATE_DELETE = NameItem.valueOf("删除", 28);

    /**
     * 获取ID
     * @return
     */
    UniteId getId();


    String getAssignmentId();

    String getBugContent();
    void setBugContent(String content);

    String getSeverity();
    void setSeverity(String severity);

    Set<String> getTesters();
    void setTesters(Set<String> tester);

    Set<String> getTestHandlers();
    void setTestHandler(Set<String> testHandlers);

    NameItem getState();

    String getCreator();


    void setLastTime(Date lastTime);
    Date getLastTime();
    /*状态扭转*/
    void turnWaitingCorrect() throws ApiException;
    void turnWaitingRetest() throws ApiException;
    void turnAdviseDontEdit()throws ApiException;
    void turnAskingCantEdit()throws ApiException;
    void turnSolved()throws ApiException;
    void turnNoEdit()throws ApiException;
    void turnCantSolved()throws ApiException;
    void turnReopen()throws ApiException;

    void deleteBug()throws ApiException;

    ResultPage<BusinessLog> getLogs();

}
