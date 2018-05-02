package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class WorkflowNodeRel implements Serializable {
    private Long id;

    private Long workflowId;

    private Long currentNodeId;

    private Long parentNodeId;

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

    public Long getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(Long currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public Long getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Long parentNodeId) {
        this.parentNodeId = parentNodeId;
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
        WorkflowNodeRel other = (WorkflowNodeRel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWorkflowId() == null ? other.getWorkflowId() == null : this.getWorkflowId().equals(other.getWorkflowId()))
            && (this.getCurrentNodeId() == null ? other.getCurrentNodeId() == null : this.getCurrentNodeId().equals(other.getCurrentNodeId()))
            && (this.getParentNodeId() == null ? other.getParentNodeId() == null : this.getParentNodeId().equals(other.getParentNodeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWorkflowId() == null) ? 0 : getWorkflowId().hashCode());
        result = prime * result + ((getCurrentNodeId() == null) ? 0 : getCurrentNodeId().hashCode());
        result = prime * result + ((getParentNodeId() == null) ? 0 : getParentNodeId().hashCode());
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
        sb.append(", currentNodeId=").append(currentNodeId);
        sb.append(", parentNodeId=").append(parentNodeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}