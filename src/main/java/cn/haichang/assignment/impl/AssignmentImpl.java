package cn.haichang.assignment.impl;

import cn.haichang.assignment.Assignment;
import cn.haichang.assignment.Lable;
import cn.haichang.assignment.di.AssignmentDi;
import cn.weforward.common.NameItem;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.annotation.Index;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.support.Global;
import cn.weforward.framework.util.ValidateUtil;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
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
    protected List<String> m_Handlers;
    @Resource
    protected List<String> m_Followers;
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
    public int m_State;
    @Resource
    protected int m_Level;
    @Resource
    protected UniteId m_FatherID;

//    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    protected AssignmentImpl(AssignmentDi di) {
        super(di);
    }

    public AssignmentImpl(AssignmentDi di,String title,String content,
                          /*String creator,*/List<String> handler,
                          String charger,String lableId,Date startTime,
                          Date endTime,int level){
        super(di);
        genPersistenceId(lableId);
        m_Title=title;
        m_Content=content;
        m_Creator= Global.TLS.getValue("creator");
        m_Handlers=handler;
        m_Charger=charger;
        m_Followers=null;
        m_LableId=lableId;
        m_StartTime=startTime;
        m_EndTime=endTime;
        m_State=STATE_ESTIMATE.id;
        m_Level=level;
        m_CreateTime=new Date();
        m_FatherID=null;
//        m_Lable.getAssignments().add(this.m_Title);
        markPersistenceUpdate();
    }

    /**
     * 子需求构造器，加上fatherID
     *
     * @param di 业务依赖接口
     */
    public AssignmentImpl(AssignmentDi di, String title, String content,
                          List<String> handlers, String charger,
                          String lableId, Date startTime, Date endTime,
                          int level, UniteId fatherID/*String creator*/) {
        super(di);
        genPersistenceId(lableId);
        m_Title = title;
        m_Content = content;
        m_Creator= Global.TLS.getValue("creator");
        m_Handlers = handlers;
        m_Charger = charger;
        m_Followers=null;
        m_LableId = lableId;
        m_StartTime = startTime;
        m_EndTime = endTime;
        m_State = STATE_ESTIMATE.id;
        m_Level = level;
        m_CreateTime=new Date();
        m_FatherID = fatherID;
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
        markPersistenceUpdate();
    }

    @Override
    public void setContent(String content) {
        if(StringUtil.eq(content, m_Title)){
            return;
        }
        m_Title=content;
        markPersistenceUpdate();
    }

    @Override
    public void addHandler(List<String> handlers) {
        for (String handler : handlers) {
            m_Handlers.add(handler);
        }
        markPersistenceUpdate();
    }

    @Override
    public void removeHandler(List<String> handlers) {
        for (String handler : handlers) {
            m_Handlers.remove(handler);
        }
        markPersistenceUpdate();
    }

    @Override
    public void addFollower(String follower) {
        m_Followers.add(follower);
        markPersistenceUpdate();
    }


    @Override
    public void setCharger(String charger) {
        m_Charger=charger;
        markPersistenceUpdate();
    }

    @Override
    public void setLableId(String lableId) {
        m_LableId=lableId;
        markPersistenceUpdate();
    }

    @Override
    public void setStartTime(Date startTime) {
        m_StartTime=startTime;
        markPersistenceUpdate();
    }

    @Override
    public void setEndTime(Date endTime) {
        m_EndTime=endTime;
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
    public List<String> getHandler() {
        return m_Handlers;
    }

    @Override
    public List<String> getFollower() {
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
    public NameItem getState() {
        return STATES.get(m_State) ;
    }



    HashMap<Integer,List> hashMap = new HashMap<>();

    @Override
    public void changState(int stateId) throws ApiException {
        hashMap.put(STATE_ESTIMATE.id, Arrays.asList(STATE_PLAN.id,STATE_REJECT.id,STATE_PENDING.id));
        List list = hashMap.get(getState().id);
        if (list.contains(stateId)) {
            m_State = stateId;
        }
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
}
