package org.ost.entity.report.dto;

import org.ost.entity.project.dto.ProjectContactsDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lugia on 2017/3/14.
 */
public class XiaoShouReportDto {
    private Integer id;
    private Integer customerID;
    private String customerName;
    private String projectName;
    private String projectTypeID;
    private String projectType;
    private String state;
    private Date startTime;
    private BigDecimal projectAmount;
    private Integer projectPaymentRate;
    private String createTimeStr;
    private String projectStep;
    private String projectState;
    private List<ProjectContactsDto> managerOwner;

    private Integer totalProjectAmount;
    private Integer totalProjectPaymentRate;
    private Integer totalConversionRate;
    private Integer totalCount;

    public Integer getTotalConversionRate() {
        return totalConversionRate;
    }

    public void setTotalConversionRate(Integer totalConversionRate) {
        this.totalConversionRate = totalConversionRate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getProjectStep() {
        return projectStep;
    }

    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public Integer getTotalProjectAmount() {
        return totalProjectAmount;
    }

    public void setTotalProjectAmount(Integer totalProjectAmount) {
        this.totalProjectAmount = totalProjectAmount;
    }

    public Integer getTotalProjectPaymentRate() {
        return totalProjectPaymentRate;
    }

    public void setTotalProjectPaymentRate(Integer totalProjectPaymentRate) {
        this.totalProjectPaymentRate = totalProjectPaymentRate;
    }

    public List<ProjectContactsDto> getManagerOwner() {
        return managerOwner;
    }

    public void setManagerOwner(List<ProjectContactsDto> managerOwner) {
        this.managerOwner = managerOwner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(String projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(BigDecimal projectAmount) {
        this.projectAmount = projectAmount;
    }

    public Integer getProjectPaymentRate() {
        return projectPaymentRate;
    }

    public void setProjectPaymentRate(Integer projectPaymentRate) {
        this.projectPaymentRate = projectPaymentRate;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
