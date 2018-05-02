package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class WorkflowInst implements Serializable {
    private Long id;

    private Long workflowId;

    private Long schedulerTaskId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Long getSchedulerTaskId() {
        return schedulerTaskId;
    }

    public void setSchedulerTaskId(Long schedulerTaskId) {
        this.schedulerTaskId = schedulerTaskId;
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
        WorkflowInst other = (WorkflowInst) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWorkflowId() == null ? other.getWorkflowId() == null : this.getWorkflowId().equals(other.getWorkflowId()))
            && (this.getSchedulerTaskId() == null ? other.getSchedulerTaskId() == null : this.getSchedulerTaskId().equals(other.getSchedulerTaskId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWorkflowId() == null) ? 0 : getWorkflowId().hashCode());
        result = prime * result + ((getSchedulerTaskId() == null) ? 0 : getSchedulerTaskId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", workflowId=").append(workflowId);
        sb.append(", schedulerTaskId=").append(schedulerTaskId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}