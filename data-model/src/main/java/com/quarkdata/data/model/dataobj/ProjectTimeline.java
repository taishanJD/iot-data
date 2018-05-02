package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class ProjectTimeline implements Serializable {
    private Long id;

    private Long projectId;

    private String operateType;

    private String operateObjectType;

    private Long operateObjectId;

    private Date createTime;

    private Long createUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getOperateObjectType() {
        return operateObjectType;
    }

    public void setOperateObjectType(String operateObjectType) {
        this.operateObjectType = operateObjectType == null ? null : operateObjectType.trim();
    }

    public Long getOperateObjectId() {
        return operateObjectId;
    }

    public void setOperateObjectId(Long operateObjectId) {
        this.operateObjectId = operateObjectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProjectTimeline other = (ProjectTimeline) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getOperateType() == null ? other.getOperateType() == null : this.getOperateType().equals(other.getOperateType()))
            && (this.getOperateObjectType() == null ? other.getOperateObjectType() == null : this.getOperateObjectType().equals(other.getOperateObjectType()))
            && (this.getOperateObjectId() == null ? other.getOperateObjectId() == null : this.getOperateObjectId().equals(other.getOperateObjectId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getOperateType() == null) ? 0 : getOperateType().hashCode());
        result = prime * result + ((getOperateObjectType() == null) ? 0 : getOperateObjectType().hashCode());
        result = prime * result + ((getOperateObjectId() == null) ? 0 : getOperateObjectId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", operateType=").append(operateType);
        sb.append(", operateObjectType=").append(operateObjectType);
        sb.append(", operateObjectId=").append(operateObjectId);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}