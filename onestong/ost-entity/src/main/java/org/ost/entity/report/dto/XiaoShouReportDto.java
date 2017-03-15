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
    private String name;
    private String projectTypeID;
    private String projetTypeName;
    private String state;
    private Date startTime;
    private BigDecimal amount;
    private Integer rate;
    private List<ProjectContactsDto> managerOwner;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(String projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public String getProjetTypeName() {
        return projetTypeName;
    }

    public void setProjetTypeName(String projetTypeName) {
        this.projetTypeName = projetTypeName;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
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
}
