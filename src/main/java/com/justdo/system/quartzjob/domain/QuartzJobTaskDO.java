package com.justdo.system.quartzjob.domain;


import com.justdo.common.domain.BaseBean;

import java.util.Date;

/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
public class QuartzJobTaskDO extends BaseBean {

    // 主键ID
    private String schedulerTaskId;
    //任务名
    private String taskName;
    //任务状态
    private String taskStatus;
    //任务分组
    private String taskGroup;
    //cron表达式
    private String cronExpression;
    //任务调用的方法名
    private String methodName;
    //Spring bean
    private String springBean;
    //任务是否有状态
    private Integer isCurrent;
    //任务执行时调用哪个类的方法 包名+类名
    private String beanClass;
    //任务描述
    private String description;
    //创建时间
    private Date createTime;
    //更新时间
    private Date modifyTime;
    //创建者
    private String careatEmployeeId;
    //更新者
    private String modifyEmployeeId;

    /**
     * 构造方法
     */
    public QuartzJobTaskDO(){ }
    public QuartzJobTaskDO(String schedulerTaskId,String taskName,String taskStatus,String taskGroup,String cronExpression,String methodName,String springBean,Integer isCurrent,String beanClass,String description,Date createTime,Date modifyTime,String careatEmployeeId,String modifyEmployeeId){
        super();
        this.taskName= taskName;
        this.taskStatus= taskStatus;
        this.taskGroup= taskGroup;
        this.cronExpression= cronExpression;
        this.methodName= methodName;
        this.springBean= springBean;
        this.isCurrent= isCurrent;
        this.beanClass= beanClass;
        this.description= description;
        this.createTime= createTime;
        this.modifyTime= modifyTime;
        this.careatEmployeeId= careatEmployeeId;
        this.modifyEmployeeId= modifyEmployeeId;
    }
    /**
     * 设置： 主键ID
     */
    public void setSchedulerTaskId(String schedulerTaskId) {
        this.schedulerTaskId = schedulerTaskId;
    }
    /**
     * 获取： 主键ID
     */
    public String getSchedulerTaskId() {
        return schedulerTaskId;
    }

    /**
     * 设置：任务名
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    /**
     * 获取：任务名
     */
    public String getTaskName() {
        return taskName;
    }
    /**
     * 设置：任务状态
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    /**
     * 获取：任务状态
     */
    public String getTaskStatus() {
        return taskStatus;
    }
    /**
     * 设置：任务分组
     */
    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }
    /**
     * 获取：任务分组
     */
    public String getTaskGroup() {
        return taskGroup;
    }
    /**
     * 设置：cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    /**
     * 获取：cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }
    /**
     * 设置：任务调用的方法名
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /**
     * 获取：任务调用的方法名
     */
    public String getMethodName() {
        return methodName;
    }
    /**
     * 设置：Spring bean
     */
    public void setSpringBean(String springBean) {
        this.springBean = springBean;
    }
    /**
     * 获取：Spring bean
     */
    public String getSpringBean() {
        return springBean;
    }
    /**
     * 设置：任务是否有状态
     */
    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
    }
    /**
     * 获取：任务是否有状态
     */
    public Integer getIsCurrent() {
        return isCurrent;
    }
    /**
     * 设置：任务执行时调用哪个类的方法 包名+类名
     */
    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }
    /**
     * 获取：任务执行时调用哪个类的方法 包名+类名
     */
    public String getBeanClass() {
        return beanClass;
    }
    /**
     * 设置：任务描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * 获取：任务描述
     */
    public String getDescription() {
        return description;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 设置：更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    /**
     * 获取：更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }
    /**
     * 设置：创建者
     */
    public void setCareatEmployeeId(String careatEmployeeId) {
        this.careatEmployeeId = careatEmployeeId;
    }
    /**
     * 获取：创建者
     */
    public String getCareatEmployeeId() {
        return careatEmployeeId;
    }
    /**
     * 设置：更新者
     */
    public void setModifyEmployeeId(String modifyEmployeeId) {
        this.modifyEmployeeId = modifyEmployeeId;
    }
    /**
     * 获取：更新者
     */
    public String getModifyEmployeeId() {
        return modifyEmployeeId;
    }

    @Override
    public String toString() {
        return "SchedulerTaskDO{" +
                "schedulerTaskId=" + schedulerTaskId +
                ", taskName='" + taskName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskGroup='" + taskGroup + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", methodName='" + methodName + '\'' +
                ", springBean='" + springBean + '\'' +
                ", isCurrent='" + isCurrent + '\'' +
                ", beanClass='" + beanClass + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", careatEmployeeId='" + careatEmployeeId + '\'' +
                ", modifyEmployeeId='" + modifyEmployeeId + '\'' +
                '}';
    }
}
