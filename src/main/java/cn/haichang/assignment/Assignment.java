package cn.haichang.assignment;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.framework.ApiException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 任务
 * @author HaiChang
 * @date 2020/10/16
 **/
public interface Assignment {
    /** 状态-评估中*/
    NameItem STATE_ESTIMATE = NameItem.valueOf("评估中", 0);
    /** 状态-规划中*/
    NameItem STATE_PLAN = NameItem.valueOf("规划中", 1);
    /** 状态-待开发*/
    NameItem STATE_WAIT_DEVELOP = NameItem.valueOf("待开发", 2);
    /** 状态-开发中*/
    NameItem STATE_DEVELOP = NameItem.valueOf("开发中", 3);
    /** 状态-待测试*/
    NameItem STATE_WAIT_TEST = NameItem.valueOf("待测试", 4);
    /** 状态-测试中*/
    NameItem STATE_TEST = NameItem.valueOf("测试中", 5);
    /** 状态-测试通过*/
    NameItem STATE_PASS_TEST = NameItem.valueOf("测试通过", 6);
    /** 状态-以上线*/
    NameItem STATE_ONLINE = NameItem.valueOf("已上线", 7);
    /** 状态-已拒绝*/
    NameItem STATE_REJECT = NameItem.valueOf("已拒绝", 8);
    /** 状态-挂起*/
    NameItem STATE_PENDING = NameItem.valueOf("挂起", 9);
    /** 状态-全部*/
    NameItems STATES = NameItems.valueOf(STATE_ESTIMATE,STATE_PLAN,STATE_WAIT_DEVELOP,STATE_DEVELOP,
            STATE_WAIT_TEST,STATE_TEST,STATE_PASS_TEST,STATE_ONLINE,STATE_REJECT,STATE_PENDING);

    /** 优先级-最高优先*/
    NameItem OPTION_LEVEL_HIGHEST = NameItem.valueOf("最高优先", 10);
    /** 优先级-高*/
    NameItem OPTION_LEVEL_HIGH = NameItem.valueOf("高", 11);
    /** 优先级-中*/
    NameItem OPTION_LEVEL_MIDDLE = NameItem.valueOf("中", 12);
    /** 优先级-低*/
    NameItem OPTION_LEVEL_LOW = NameItem.valueOf("低", 13);
    /** 优先级-全部*/
    NameItems LEVELS = NameItems.valueOf(OPTION_LEVEL_HIGHEST,OPTION_LEVEL_HIGH,OPTION_LEVEL_MIDDLE,OPTION_LEVEL_LOW);

    /** 状态-任务是否删除*/
    NameItem STATE_DELETE = NameItem.valueOf("删除", 14);

    /**
     * 唯一ID
     * @return
     */
    UniteId getId();

    /**
     * 标题
     * @param title
     */
    void setTitle(String title);

    /**
     * 内容
     * @param content
     */
    void setContent(String content);

    /**
     * 增加处理人
     * @param handlers
     */
    void addHandler(Set<String> handlers);

    /**
     * 移除处理人
     * @param handler
     * @throws ApiException
     */
    void removeHandler(String handler) throws ApiException, MyException;

    /**
     * 增加跟进人
     * @param follower
     */
    void addFollower(String follower);

    void removeFollower(String follower) throws MyException;

    /**
     * 负责人
     * @param charger
     */
    void setCharger(String charger);

    /**
     * 标签id
     * @param lableId
     */
    void setLableId(String lableId);

    /**
     * 预计开始时间
     * @param startTime
     */
    void setStartTime(Date startTime);

    /**
     * 预计结束时间
     * @param endTime
     */
    void setEndTime(Date endTime);


    String getTitle();
    String getContent();
    Set<String> getHandler();
    Set<String> getFollower();
    String getCharger();
    String getCreator();
    String getLable();
    Date getStartTime();
    Date getEndTime();
    Date getCreateTime();
    Date getFinishTime();
    NameItem getLevel();
    NameItem getState();

    /**
     * 获取父任务id
     * @return
     */
    String getFatherId();

    /* 设置优先级*/
    void LevelHighest();
    void LevelHigh();
    void LevelMiddle();
    void LevelLow();
    /*删除任务*/
    void delete();
    /*判断任务是否删除*/
    boolean isDelete();
    /* 状态扭转*/
    void turnEstimate() throws ApiException;
    void turnPlanning() throws ApiException;
    void turnWaitingDevelop() throws ApiException;
    void turnDevelop() throws ApiException;
    void turnWaitingTest() throws ApiException;
    void turnTesting() throws ApiException;
    void turnPassTest() throws ApiException;
    void turnOnLine() throws ApiException;
    void turnReject() throws ApiException;
    void turnPending() throws ApiException;

    /*任务的缺陷分析*/
    /**
     * 调用Di获取当前任务的总缺陷数量
     * @return
     */
    int getBugsCount();

    /**
     * 调用Di获取当前任务的已完成缺陷数量
     * @return
     */
    int getBugsFinishCount();

    /**
     * 调用Di获取当前任务的缺陷分析
     * @return
     */
    Map<String, Integer> getStateAnalysis();

    /**
     * 调用Di获取当前任务缺陷各测人的缺陷总量
     * @return
     */
    Map<String ,Integer> getTesterAndCount();

    /**
     * 调用Di获取当前任务缺陷的各处理人的处理总数
     * @return
     */
    Map<String ,Integer> getHandlerAndCount();

    /**
     * 获取日志列表
     * @return
     */
    ResultPage<BusinessLog> getLogs();

}
