package cn.haichang.assignment.impl;

import cn.haichang.assignment.di.AssignmentDi;
import cn.haichang.assignment.weforward.Bug;
import cn.weforward.common.NameItem;
import cn.weforward.data.UniteId;
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
    protected NameItem m_State;
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
    protected BugImpl(AssignmentDi di,String AssignmentId,
                      String bugContent,int severity,
                      Set<String> tester,String versionAndPlatform) {
        super(di);
        genPersistenceId(AssignmentId);
        m_AssignmentId = AssignmentId;
        m_BugContent = bugContent;
        m_Severity = severity;
        m_State = STATE_WAIT_CORRECT;
        m_IsSolved = false;
        m_Testers = tester;
        m_TestHandlers = new HashSet<>();
        m_Creator = Global.TLS.getValue("creator");
        m_VersionAndPlatform = versionAndPlatform;
        m_CreateTime = new Date();
        m_LastTime = new Date();
        m_IsDelete = 0;
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

    @Override
    public void setBugContent(String content) {
        m_BugContent = content;
        markPersistenceUpdate();
    }

    @Override
    public int getSeverity() {
        return m_Severity;
    }

    @Override
    public void setSeverity(int severity) {
        m_Severity = severity;
        markPersistenceUpdate();
    }

    @Override
    public Set<String> getTesters() {
        return m_Testers;
    }

    @Override
    public void setTesters(Set<String> testers) {
        m_Testers = testers;
        markPersistenceUpdate();
    }

    @Override
    public Set<String> getTestHandlers() {
        return m_TestHandlers;
    }

    @Override
    public void addTestHandler(Set<String> testHandlers) {
        for (String testHandler : testHandlers) {
            m_TestHandlers.add(testHandler);
        }
        markPersistenceUpdate();
    }

    @Override
    public NameItem getState() {
        return m_State;
    }

   /* @Override
    public void setStatus(int status) {

    }*/

    @Override
    public boolean isSolved() {
        return m_IsSolved;
    }

    @Override
    public void setSolved(boolean solved) {
        m_IsSolved = solved;
        markPersistenceUpdate();
    }

    @Override
    public String getCreator() {
        return m_Creator;
    }

    /*状态扭转*/
    @Override
    public Date getCreateTime() {
        return m_CreateTime;
    }

    @Override
    public void setLastTime(Date lastTime) {
        m_LastTime = lastTime;
        markPersistenceUpdate();
    }

    @Override
    public Date getLastTime() {
        return m_LastTime;
    }

    @Override
    public void turnWaitingCorrcet() throws ApiException {
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
        m_State = STATE_WAIT_CORRECT;
        markPersistenceUpdate();
    }

    @Override
    public void turnWaitingRetest() throws ApiException {
        NameItem state = getState();
        if (STATE_WAIT_RETEST.id == state.id){
            return;
        }
        if (STATE_WAIT_CORRECT.id != state.id && STATE_REOPEN.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_CORRECT.getName()+"、"
                    +STATE_REOPEN.getName()+
                    "的状态,不能扭转为"+STATE_WAIT_RETEST.getName());
        }
        m_State = STATE_WAIT_RETEST;
        markPersistenceUpdate();
    }

    @Override
    public void turnAdviseDontEdit() throws ApiException {
        NameItem state = getState();
        if (STATE_ADVISE_DONT_EDIT.id == state.id){
            return;
        }
        if (STATE_WAIT_CORRECT.id != state.id ){
            throw new ApiException(0, "非"+STATE_WAIT_CORRECT.getName()+
                    "的状态,不能扭转为"+STATE_ADVISE_DONT_EDIT.getName());
        }
        m_State = STATE_ADVISE_DONT_EDIT;
        markPersistenceUpdate();
    }

    @Override
    public void turnAskingCantEdit() throws ApiException {
        NameItem state = getState();
        if (STATE_ASK_CANT_EDIT.id == state.id){
            return;
        }
        if (STATE_WAIT_CORRECT.id != state.id){
            throw new ApiException(0, "非"+STATE_WAIT_CORRECT.getName()+
                    "的状态,不能扭转为"+STATE_ASK_CANT_EDIT.getName());
        }
        m_State = STATE_ASK_CANT_EDIT;
        markPersistenceUpdate();
    }

    @Override
    public void turnSolved() throws ApiException {
        NameItem state = getState();
        if (STATE_SOLVED.id == state.id){
            return;
        }
        if (STATE_WAIT_RETEST.id != state.id ){
            throw new ApiException(0, "非"+STATE_WAIT_RETEST.getName()+
                    "的状态,不能扭转为"+STATE_SOLVED.getName());
        }
        m_State = STATE_SOLVED;
        markPersistenceUpdate();
    }

    @Override
    public void turnNoEdit() throws ApiException {
        NameItem state = getState();
        if (STATE_NO_EDIT.id == state.id){
            return;
        }
        if (STATE_ADVISE_DONT_EDIT.id != state.id ){
            throw new ApiException(0, "非"+STATE_ADVISE_DONT_EDIT.getName()+
                    "的状态,不能扭转为"+STATE_NO_EDIT.getName());
        }
        m_State = STATE_NO_EDIT;
        markPersistenceUpdate();
    }

    @Override
    public void turnCantSolved() throws ApiException {
        NameItem state = getState();
        if (STATE_CANT_SOLVE.id == state.id){
            return;
        }
        if (STATE_ASK_CANT_EDIT.id != state.id ){
            throw new ApiException(0, "非"+STATE_ASK_CANT_EDIT.getName()+
                    "的状态,不能扭转为"+STATE_CANT_SOLVE.getName());
        }
        m_State = STATE_CANT_SOLVE;
        markPersistenceUpdate();
    }

    @Override
    public void turnReopen() throws ApiException {
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
        m_State = STATE_REOPEN;
        markPersistenceUpdate();
    }

    @Override
    public void deleteBug() {
        m_IsDelete = STATE_DELETE.id;
    }
}
