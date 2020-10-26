package cn.haichang.assignment.impl;

import cn.haichang.assignment.Bug;
import cn.haichang.assignment.di.AssignmentDi;
import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.support.Global;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author HaiChang
 * @date 2020/10/22
 **/
public class BugImpl extends AbstractPersistent<AssignmentDi> implements Bug {
    @Resource
    protected String m_AssignmentId;
    @Resource
    protected String m_BugContent;
    @Resource
    protected int m_Severity;
    @Resource
    protected int m_State;
    @Resource
    protected boolean m_IsSolved;
    @Resource
    protected Set<String> m_Testers;
    @Resource
    protected Set<String> m_TestHandlers;
    @Resource
    protected String m_Creator;
    @Resource
    protected String m_VersionAndPlatform;
    @Resource
    protected Date m_CreateTime;
    @Resource
    protected Date m_LastTime;
    @Resource
    protected int m_IsDelete;

    protected BugImpl(AssignmentDi di) {
        super(di);
    }

    public BugImpl(AssignmentDi di,String assignmentId,
                      String bugContent,int severity,
                      Set<String> tester,String versionAndPlatform) {
        super(di);
        genPersistenceId(assignmentId);
        m_AssignmentId = assignmentId;
        m_BugContent = bugContent;
        m_Severity = severity;
        m_State = STATE_WAIT_CORRECT.id;
        m_IsSolved = false;
        m_Testers = tester;
        m_TestHandlers = new HashSet<>();
        m_Creator = getUser();
        m_VersionAndPlatform = versionAndPlatform;
        m_CreateTime = new Date();
        m_LastTime = new Date();
        m_IsDelete = 0;
        getBusinessDi().writeLog(getId(), m_Creator,"创建", "新BUG", "");
        markPersistenceUpdate();
    }

    @Override
    public UniteId getId() {
        return getPersistenceId();
    }

    @Override
    public String getAssignmentId() {
        return m_AssignmentId;
    }

    @Override
    public String getBugContent() {
        return m_BugContent;
    }

    /**
     * 设置缺陷内容
     * @param content
     */
    @Override
    public void setBugContent(String content) {
        if (StringUtil.eq(m_BugContent, content)){
            return;
        }
        m_BugContent = content;
        getBusinessDi().writeLog(getId(), m_Creator,"修改内容", "", "");
        markPersistenceUpdate();
    }

    @Override
    public int getSeverity() {
        return m_Severity;
    }

    /**
     * 设置严重性
     * @param severity
     */
    @Override
    public void setSeverity(int severity) {
        int oldSeverity = m_Severity;
        if (m_Severity == severity){
            return;
        }
        m_Severity = severity;
        getBusinessDi().writeLog(getId(), m_Creator,"修改严重性", "从"+STATES_BUGS.get(oldSeverity).getName()
                +"修改为"+STATES_BUGS.get(severity).getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public Set<String> getTesters() {
        return m_Testers;
    }

    /**
     * 增加测试人
     * @param testers
     */
    @Override
    public void setTesters(Set<String> testers) {
        Set<String> oldTesters = m_Testers;
        if (0 == testers.size()){
            return;
        }
        m_Testers = testers;
        getBusinessDi().writeLog(getId(), m_Creator,"修改测试人员", "从"+oldTesters.toString()
                +"修改为"+testers.toString(), "");
        markPersistenceUpdate();
    }

    @Override
    public Set<String> getTestHandlers() {
        return m_TestHandlers;
    }

    /**
     * 增加处理人
     * @param testHandlers
     */
    @Override
    public void addTestHandler(Set<String> testHandlers) {
        if (0 == testHandlers.size()){
            return;
        }
        for (String testHandler : testHandlers) {
            m_TestHandlers.add(testHandler);
        }
        getBusinessDi().writeLog(getId(), m_Creator,"增加处理人员", "增加了"+testHandlers.toString(), "");
        markPersistenceUpdate();
    }

    @Override
    public NameItem getState() {
        return STATES_BUGS.get(m_State);
    }

    @Override
    public String getCreator() {
        return m_Creator;
    }


    @Override
    public void setLastTime(Date lastTime) {
        m_LastTime = lastTime;
        if (m_LastTime.toString() == lastTime.toString()){
            return;
        }
        markPersistenceUpdate();
    }

    @Override
    public Date getLastTime() {
        return m_LastTime;
    }

    /**
     * 扭转为待修正
     * @throws ApiException
     */
    @Override
    public synchronized void turnWaitingCorrect() throws ApiException {
        NameItem state = getState();
        if (STATE_WAIT_CORRECT.id == state.id){
            return;
        }
        if (STATE_WAIT_RETEST.id != state.id && STATE_ADVISE_DONT_EDIT.id != state.id &&
        STATE_ASK_CANT_EDIT.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_RETEST.getName()+"、"
            +STATE_ADVISE_DONT_EDIT.getName()+"、"+STATE_ASK_CANT_EDIT.getName()+
                    "的状态,不能扭转为"+STATE_WAIT_CORRECT.getName());
        }
        m_State = STATE_WAIT_CORRECT.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_WAIT_CORRECT.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转为待复测
     * @throws ApiException
     */
    @Override
    public synchronized void turnWaitingRetest() throws ApiException {
        NameItem state = getState();
        if (STATE_WAIT_RETEST.id == state.id){
            return;
        }
        if (STATE_WAIT_CORRECT.id != state.id && STATE_REOPEN.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_CORRECT.getName()+"、"
                    +STATE_REOPEN.getName()+
                    "的状态,不能扭转为"+STATE_WAIT_RETEST.getName());
        }
        m_State = STATE_WAIT_RETEST.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_WAIT_RETEST.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转为建议不作修改
     * @throws ApiException
     */
    @Override
    public synchronized void turnAdviseDontEdit() throws ApiException {
        NameItem state = getState();
        if (STATE_ADVISE_DONT_EDIT.id == state.id){
            return;
        }
        if (STATE_WAIT_CORRECT.id != state.id ){
            throw new ApiException(0, "非"+STATE_WAIT_CORRECT.getName()+
                    "的状态,不能扭转为"+STATE_ADVISE_DONT_EDIT.getName());
        }
        m_State = STATE_ADVISE_DONT_EDIT.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_ADVISE_DONT_EDIT.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转为申请无法修改
     * @throws ApiException
     */
    @Override
    public synchronized void turnAskingCantEdit() throws ApiException {
        NameItem state = getState();
        if (STATE_ASK_CANT_EDIT.id == state.id){
            return;
        }
        if (STATE_WAIT_CORRECT.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_CORRECT.getName()+
                    "的状态,不能扭转为"+STATE_ASK_CANT_EDIT.getName());
        }
        m_State = STATE_ASK_CANT_EDIT.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_ASK_CANT_EDIT.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转为已解决
     * @throws ApiException
     */
    @Override
    public synchronized void turnSolved() throws ApiException {
        NameItem state = getState();
        if (STATE_SOLVED.id == state.id){
            return;
        }
        if (STATE_WAIT_RETEST.id != state.id ){
            throw new ApiException(0, "非"+STATE_WAIT_RETEST.getName()+
                    "的状态,不能扭转为"+STATE_SOLVED.getName());
        }
        m_State = STATE_SOLVED.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_SOLVED.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转为不作修改
     * @throws ApiException
     */
    @Override
    public synchronized void turnNoEdit() throws ApiException {
        NameItem state = getState();
        if (STATE_NO_EDIT.id == state.id){
            return;
        }
        if (STATE_ADVISE_DONT_EDIT.id != state.id ){
            throw new ApiException(0, "非"+STATE_ADVISE_DONT_EDIT.getName()+
                    "的状态,不能扭转为"+STATE_NO_EDIT.getName());
        }
        m_State = STATE_NO_EDIT.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_NO_EDIT.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转为无法修改
     * @throws ApiException
     */
    @Override
    public synchronized void turnCantSolved() throws ApiException {
        NameItem state = getState();
        if (STATE_CANT_SOLVE.id == state.id){
            return;
        }
        if (STATE_ASK_CANT_EDIT.id != state.id ){
            throw new ApiException(0, "非"+STATE_ASK_CANT_EDIT.getName()+
                    "的状态,不能扭转为"+STATE_CANT_SOLVE.getName());
        }
        m_State = STATE_CANT_SOLVE.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_CANT_SOLVE.getName(), "");
        markPersistenceUpdate();
    }

    /**
     * 扭转重新打开
     * @throws ApiException
     */
    @Override
    public synchronized void turnReopen() throws ApiException {
        NameItem state = getState();
        if (STATE_REOPEN.id == state.id){
            return;
        }
        if (STATE_SOLVED.id != state.id && STATE_NO_EDIT.id != state.id
        && STATE_CANT_SOLVE.id != state.id){
            throw new ApiException(0, "非"+STATE_SOLVED.getName()+"、"+
                    STATE_NO_EDIT.getName()+"、"+STATE_CANT_SOLVE.getName()+
                    "的状态,不能扭转为"+STATE_REOPEN.getName());
        }
        m_State = STATE_REOPEN.id;
        getBusinessDi().writeLog(getId(), getUser(),"bug状态扭转", "状态从"+STATES_BUGS.get(this.m_State).getName()+"扭转为"+STATE_REOPEN.getName(), "");
        markPersistenceUpdate();
    }

    @Override
    public void deleteBug() {
        m_IsDelete = STATE_DELETE.id;
        markPersistenceUpdate();
    }

    private String getUser() {
        String creator = Global.TLS.getValue("creator");
        if (null == creator) {
            creator = "creator";
        }
        return creator;
    }

    @Override
    public ResultPage<BusinessLog> getLogs() {
        return getBusinessDi().getLogs(getId());
    }

}
