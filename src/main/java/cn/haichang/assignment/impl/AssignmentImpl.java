package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.di.AssignmentDi;
import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.annotation.Index;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.support.Global;

import javax.annotation.Resource;
import java.util.*;

/**
 * 任务实现类
 * @author HaiChang
 * @date 2020/10/16
 **/
public class AssignmentImpl extends AbstractPersistent<AssignmentDi> implements Assignment {

    @Resource
    protected String m_Title;
    @Resource
    protected String m_Content;
    @Resource
    protected String m_Creator;
    @Resource
    protected Set<String> m_Handlers;
    @Resource
    protected Set<String> m_Followers;
    @Resource
    protected String m_Charger;
    @Index
    @Resource
    protected String m_LableId;
    @Resource
    protected Date m_StartTime;
    @Resource
    protected Date m_EndTime;
    @Resource
    protected Date m_CreateTime;
    @Resource
    protected Date m_FinishTime;
    @Resource
    public int m_State;
    @Resource
    protected int m_Level;
    @Resource
    protected String m_FatherID;
    @Resource
    protected int m_IsDelete;


    protected AssignmentImpl(AssignmentDi di) {
        super(di);
    }

    public AssignmentImpl(AssignmentDi di,String title,String content,
                          Set<String> handlers,
                          String charger,String lableId,Date startTime,
                          Date endTime,int level){
        super(di);
        genPersistenceId();
        m_Title=title;
        m_Content=content;
        m_Creator= getCreator();
        m_Handlers=handlers;
        m_Charger=charger;
        m_Followers=new HashSet<>();
        m_LableId=lableId;
        m_StartTime=startTime;
        m_EndTime=endTime;
        m_State=STATE_ESTIMATE.id;
        m_Level=level;
        m_CreateTime=new Date();
        m_FatherID=null;
        m_IsDelete = 0;
        getBusinessDi().writeLog(getId(), m_Creator,"创建", "新任务", "");
        markPersistenceUpdate();
    }

    /**
     * 子需求构造器，加上fatherID
     *
     * @param di 业务依赖接口
     */
    public AssignmentImpl(AssignmentDi di, String title, String content,
                          Set<String> handlers, String charger,
                          String lableId, Date startTime, Date endTime,
                          int level, String fatherId/*String creator*/) {
        super(di);
        genPersistenceId(fatherId);
        m_Title = title;
        m_Content = content;
        m_Creator= Global.TLS.getValue("creator");
        m_Handlers = handlers;
        m_Charger = charger;
        m_Followers=new HashSet<>();
        m_LableId = lableId;
        m_StartTime = startTime;
        m_EndTime = endTime;
        m_State = STATE_ESTIMATE.id;
        m_Level = level;
        m_CreateTime=new Date();
        m_FatherID = fatherId;
        m_IsDelete = 0;
        getBusinessDi().writeLog(getId(), m_Creator,"创建", "新子任务", "");
        markPersistenceUpdate();
    }

    @Override
    public UniteId getId() {
        return getPersistenceId();
    }

    @Override
    public void setTitle(String title) {
        if(StringUtil.eq(title, m_Title)){
            return;
        }
        m_Title=title;
        getBusinessDi().writeLog(getId(), m_Creator,"修改", "标题为"+title, "");
        markPersistenceUpdate();
    }

    @Override
    public void setContent(String content) {
        if(StringUtil.eq(content, m_Title)){
            return;
        }
        m_Title=content;
        getBusinessDi().writeLog(getId(), m_Creator,"修改内容", "", "");
        markPersistenceUpdate();
    }

    @Override
    public void addHandler(Set<String> handlers) {
        if (0 == handlers.size()){
            return;
        }
        String oldHandlers = m_Handlers.toString();
        for (String handler : handlers) {
            m_Handlers.add(handler);
        }
        String newHandlers = m_Handlers.toString();
        getBusinessDi().writeLog(getId(), m_Creator,"修改处理人", "原处理人"+oldHandlers
                +"修改为"+newHandlers, "");
        markPersistenceUpdate();
    }

    @Override
    public void removeHandler(String handler) throws ApiException {
        if (!m_Handlers.contains(handler)){
            throw new ApiException(0,"没有此人");
        }
        m_Handlers.remove(handler);
        getBusinessDi().writeLog(getId(), m_Creator,"移除处理人", handler, "");
        markPersistenceUpdate();
    }

    @Override
    public void addFollower(String follower) {
        if (m_Followers.contains(follower)){
            return;
        }
        m_Followers.add(follower);
        getBusinessDi().writeLog(getId(), m_Creator,"跟进任务", follower, "");
        markPersistenceUpdate();
    }


    @Override
    public void setCharger(String charger) {
        String oldCharger = m_Charger;
        if(StringUtil.eq(charger, m_Charger)){
            return;
        }
        m_Charger=charger;
        getBusinessDi().writeLog(getId(), m_Creator,"修改负责人", "原负责人"+oldCharger
                +"修改为"+charger, "");
        markPersistenceUpdate();
    }

    @Override
    public void setLableId(String lableId) {
        if(StringUtil.eq(lableId, m_LableId)){
            return;
        }
        m_LableId=lableId;
        getBusinessDi().writeLog(getId(), m_Creator,"修改标签", "","");
        markPersistenceUpdate();
    }

    @Override
    public void setStartTime(Date startTime) {
        if (m_StartTime.toString() == startTime.toString()){
            return;
        }
        m_StartTime=startTime;
        getBusinessDi().writeLog(getId(), m_Creator,"修改开始时间为", startTime.toString(),"");
        markPersistenceUpdate();
    }

    @Override
    public void setEndTime(Date endTime) {
        if (m_EndTime.toString() == endTime.toString()){
            return;
        }
        m_EndTime=endTime;
        getBusinessDi().writeLog(getId(), m_Creator,"修改结束时间为", endTime.toString(),"");

        markPersistenceUpdate();
    }

    @Override
    public String getTitle() {
        return m_Title;
    }

    @Override
    public String getContent() {
        return m_Content;
    }

    @Override
    public Set<String> getHandler() {
        return m_Handlers;
    }

    @Override
    public Set<String> getFollower() {
        return m_Followers;
    }

    @Override
    public String getCharger() {
        return m_Charger;
    }

    @Override
    public String getCreator() {
        return m_Creator;
    }

    @Override
    public String getLable() {
        return m_LableId;
    }

    @Override
    public Date getStartTime() {
        return m_StartTime;
    }

    @Override
    public Date getEndTime() {
        return m_EndTime;
    }

    @Override
    public Date getCreateTime() {
        return m_CreateTime;
    }

    @Override
    public Date getFinishTime() {
        return m_FinishTime;
    }

    @Override
    public NameItem getLevel() {
        return LEVELS.get(m_Level);
    }

    @Override
    public NameItem getState() {
        return STATES.get(m_State) ;
    }

    @Override
    public String getFatherId() {
        return m_FatherID;
    }


    @Override
    public void LevelHighest() {
        m_Level= OPTION_LEVEL_HIGHEST.id;
        markPersistenceUpdate();
    }

    @Override
    public void LevelHigh() {
        m_Level= OPTION_LEVEL_HIGH.id;
        markPersistenceUpdate();
    }

    @Override
    public void LevelMiddle() {
        m_Level= OPTION_LEVEL_MIDDLE.id;
        markPersistenceUpdate();
    }

    @Override
    public void LevelLow() {
        m_Level= OPTION_LEVEL_LOW.id;
        markPersistenceUpdate();
    }

    @Override
    public void delete() {
        m_IsDelete = STATE_DELETE.id;
        markPersistenceUpdate();
    }

    @Override
    public boolean isDelete() {
        if (STATE_DELETE.id == m_IsDelete){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void turnEstimate() throws ApiException {
        NameItem state = getState();
        if (STATE_ESTIMATE.id == state.id){
            return;
        }
        if (STATE_PENDING.id != state.id ){
            throw new ApiException(0, "非"+STATE_PENDING.getName()
            +"的状态，不能扭转为"+STATE_ESTIMATE.getName());
        }
        m_State = STATE_ESTIMATE.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_ESTIMATE.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnPlanning() throws ApiException {
        NameItem state = getState();
        if (STATE_PLAN.id == state.id){
            return;
        }
        if (STATE_ESTIMATE.id != state.id ){
            throw new ApiException(0, "非"+STATE_ESTIMATE.getName()
                    +"的状态，不能扭转为"+STATE_PLAN.getName());
        }
        m_State = STATE_PLAN.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_PLAN.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnWaitingDevelop() throws ApiException {
        NameItem state = getState();
        if (STATE_WAIT_DEVELOP.id == state.id){
            return;
        }
        if (STATE_PENDING.id != state.id && STATE_PLAN.id != state.id){
            throw new ApiException(0, "非"+STATE_PENDING.getName()+"、"
                    +STATE_PLAN.getName()
                    +"的状态，不能扭转为"+STATE_WAIT_DEVELOP.getName());
        }
        m_State = STATE_WAIT_DEVELOP.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_WAIT_DEVELOP.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnDevelop() throws ApiException {
        NameItem state = getState();
        if (STATE_DEVELOP.id == state.id){
            return;
        }
        if (STATE_WAIT_DEVELOP.id != state.id && STATE_WAIT_TEST.id != state.id &&
        STATE_TEST.getName() != state.getName() && STATE_PASS_TEST.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_DEVELOP.getName()+"、"
                    +STATE_WAIT_TEST.getName()+"、"+STATE_TEST.getName()+"、"
                    +STATE_PASS_TEST.getName()
                    +"的状态，不能扭转为"+STATE_DEVELOP.getName());
        }
        m_State = STATE_DEVELOP.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_DEVELOP.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnWaitingTest() throws ApiException {
        NameItem state = getState();
        if (STATE_WAIT_TEST.id == state.id){
            return;
        }
        if (STATE_DEVELOP.id != state.id && STATE_PENDING.id != state.id){
            throw new ApiException(0, "非"+STATE_DEVELOP.getName()+"、"
                    +STATE_PENDING.getName()
                    +"的状态，不能扭转为"+STATE_WAIT_TEST.getName());
        }
        m_State = STATE_WAIT_TEST.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_WAIT_TEST.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnTesting() throws ApiException {
        NameItem state = getState();
        if (STATE_TEST.id == state.id){
            return;
        }
        if (STATE_WAIT_TEST.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_TEST.getName()
                    +"的状态，不能扭转为"+STATE_TEST.getName());
        }
        m_State = STATE_TEST.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_TEST.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnPassTest() throws ApiException {
        NameItem state = getState();
        if (STATE_PASS_TEST.id == state.id){
            return;
        }
        if (STATE_TEST.id != state.id && STATE_PENDING.id != state.id){
            throw new ApiException(0, "非"+STATE_TEST.getName()+"、"
                    +STATE_PENDING.getName()
                    +"的状态，不能扭转为"+STATE_PASS_TEST.getName());
        }
        m_State = STATE_PASS_TEST.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_PASS_TEST.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnOnLine() throws ApiException {
        NameItem state = getState();
        if (STATE_ONLINE.id == state.id){
            return;
        }
        if (STATE_PASS_TEST.id != state.id){
            throw new ApiException(0, "非"+STATE_PASS_TEST.getName()
                    +"的状态，不能扭转为"+STATE_ONLINE.getName());
        }
        m_State = STATE_ONLINE.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_ONLINE.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnReject() throws ApiException {
        NameItem state = getState();
        if (STATE_REJECT.id == state.id){
            return;
        }
        if (STATE_ESTIMATE.id != state.id && STATE_PLAN.id != state.id &&
        STATE_WAIT_DEVELOP.id != state.id && STATE_DEVELOP.id != state.id &&
        STATE_WAIT_TEST.id != state.id && STATE_TEST.id != state.id &&
        STATE_PASS_TEST.id != state.id && STATE_PENDING.id != state.id){
            throw new ApiException(0, "非"
                    +STATE_ESTIMATE.getName()+"、"
                    +STATE_PLAN.getName()+"、"+STATE_WAIT_DEVELOP.getName()+"、"
                    +STATE_DEVELOP.getName()+"、"+STATE_WAIT_TEST.getName()+"、"
                    +STATE_TEST.getName()+"、"+STATE_PASS_TEST.getName()+"、"
                    +STATE_PENDING.getName()
                    +"的状态，不能扭转为"+STATE_REJECT.getName());
        }
        m_State = STATE_REJECT.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_REJECT.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void turnPending() throws ApiException {
        NameItem state = getState();
        if (STATE_PENDING.id == state.id){
            return;
        }
        if (STATE_PLAN.id != state.id &&
                STATE_WAIT_DEVELOP.id != state.id && STATE_DEVELOP.id != state.id &&
                STATE_WAIT_TEST.id != state.id && STATE_TEST.id != state.id &&
                STATE_PASS_TEST.id != state.id ){
            throw new ApiException(0, "非"
                    +STATE_PLAN.getName()+"、"+STATE_WAIT_DEVELOP.getName()+"、"
                    +STATE_DEVELOP.getName()+"、"+STATE_WAIT_TEST.getName()+"、"
                    +STATE_TEST.getName()+"、"+STATE_PASS_TEST.getName()+"、"
                    +"的状态，不能扭转为"+STATE_PENDING.getName());
        }
        m_State = STATE_PENDING.id;
        getBusinessDi().writeLog(getId(), getUser(),"状态扭转", "状态从"+STATES.get(this.m_State).getName()+"扭转为"+STATE_PENDING.getName(), "");
        markPersistenceUpdate();
    }


    public int getBugsCount(){
        return getBusinessDi().getBugsCount(getId().getOrdinal());
    }
    public int getBugsFinishCount(){
        return getBusinessDi().getBugsFinishCount(getId().getOrdinal());
    }

    @Override
    public Map<String, Integer> getStateAnalysis() {
        return getBusinessDi().getStateAnalysis(getId().getOrdinal());
    }



    @Override
    public Map<String, Integer> getTesterAndCount() {
        return getBusinessDi().getTesterAndCount(getId().getOrdinal());
    }

    @Override
    public Map<String, Integer> getHandlerAndCount() {
        return getBusinessDi().getHandlerAndCount(getId().getOrdinal());
    }

    @Override
    public ResultPage<BusinessLog> getLogs() {
        return getBusinessDi().getLogs(getId());
    }

    private String getUser() {
        String creator = Global.TLS.getValue("creator");
        if (null == creator) {
            creator = "creator";
        }
        return creator;
    }

}
