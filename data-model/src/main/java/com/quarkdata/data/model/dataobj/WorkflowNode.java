package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class WorkflowNode implements Serializable {
    private Long id;

    private Long workflowId;

    private String nodeName;

    private String nodeType;

    private String nodeProcessType;

    private String nodeProcessSubType;

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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public String getNodeProcessType() {
        return nodeProcessType;
    }

    public void setNodeProcessType(String nodeProcessType) {
        this.nodeProcessType = nodeProcessType == null ? null : nodeProcessType.trim();
    }

    public String getNodeProcessSubType() {
        return nodeProcessSubType;
    }

    public void setNodeProcessSubType(String nodeProcessSubType) {
        this.nodeProcessSubType = nodeProcessSubType == null ? null : nodeProcessSubType.trim();
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
        WorkflowNode other = (WorkflowNode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWorkflowId() == null ? other.getWorkflowId() == null : this.getWorkflowId().equals(other.getWorkflowId()))
            && (this.getNodeName() == null ? other.getNodeName() == null : this.getNodeName().equals(other.getNodeName()))
            && (this.getNodeType() == null ? other.getNodeType() == null : this.getNodeType().equals(other.getNodeType()))
            && (this.getNodeProcessType() == null ? other.getNodeProcessType() == null : this.getNodeProcessType().equals(other.getNodeProcessType()))
            && (this.getNodeProcessSubType() == null ? other.getNodeProcessSubType() == null : this.getNodeProcessSubType().equals(other.getNodeProcessSubType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWorkflowId() == null) ? 0 : getWorkflowId().hashCode());
        result = prime * result + ((getNodeName() == null) ? 0 : getNodeName().hashCode());
        result = prime * result + ((getNodeType() == null) ? 0 : getNodeType().hashCode());
        result = prime * result + ((getNodeProcessType() == null) ? 0 : getNodeProcessType().hashCode());
        result = prime * result + ((getNodeProcessSubType() == null) ? 0 : getNodeProcessSubType().hashCode());
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
        sb.append(", nodeName=").append(nodeName);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", nodeProcessType=").append(nodeProcessType);
        sb.append(", nodeProcessSubType=").append(nodeProcessSubType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}