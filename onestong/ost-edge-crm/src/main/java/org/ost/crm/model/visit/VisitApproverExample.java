package org.ost.crm.model.visit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitApproverExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VisitApproverExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andVisiteventidIsNull() {
            addCriterion("visitEventID is null");
            return (Criteria) this;
        }

        public Criteria andVisiteventidIsNotNull() {
            addCriterion("visitEventID is not null");
            return (Criteria) this;
        }

        public Criteria andVisiteventidEqualTo(Integer value) {
            addCriterion("visitEventID =", value, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidNotEqualTo(Integer value) {
            addCriterion("visitEventID <>", value, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidGreaterThan(Integer value) {
            addCriterion("visitEventID >", value, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidGreaterThanOrEqualTo(Integer value) {
            addCriterion("visitEventID >=", value, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidLessThan(Integer value) {
            addCriterion("visitEventID <", value, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidLessThanOrEqualTo(Integer value) {
            addCriterion("visitEventID <=", value, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidIn(List<Integer> values) {
            addCriterion("visitEventID in", values, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidNotIn(List<Integer> values) {
            addCriterion("visitEventID not in", values, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidBetween(Integer value1, Integer value2) {
            addCriterion("visitEventID between", value1, value2, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andVisiteventidNotBetween(Integer value1, Integer value2) {
            addCriterion("visitEventID not between", value1, value2, "visiteventid");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userID is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userID is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userID =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userID <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userID >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userID >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userID <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userID <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userID in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userID not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userID between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userID not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("userName is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("userName is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("userName =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("userName <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("userName >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("userName >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("userName <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("userName <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("userName like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("userName not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("userName in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("userName not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("userName between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("userName not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andOrganizeidIsNull() {
            addCriterion("organizeID is null");
            return (Criteria) this;
        }

        public Criteria andOrganizeidIsNotNull() {
            addCriterion("organizeID is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizeidEqualTo(Integer value) {
            addCriterion("organizeID =", value, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidNotEqualTo(Integer value) {
            addCriterion("organizeID <>", value, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidGreaterThan(Integer value) {
            addCriterion("organizeID >", value, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("organizeID >=", value, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidLessThan(Integer value) {
            addCriterion("organizeID <", value, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidLessThanOrEqualTo(Integer value) {
            addCriterion("organizeID <=", value, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidIn(List<Integer> values) {
            addCriterion("organizeID in", values, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidNotIn(List<Integer> values) {
            addCriterion("organizeID not in", values, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidBetween(Integer value1, Integer value2) {
            addCriterion("organizeID between", value1, value2, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizeidNotBetween(Integer value1, Integer value2) {
            addCriterion("organizeID not between", value1, value2, "organizeid");
            return (Criteria) this;
        }

        public Criteria andOrganizenameIsNull() {
            addCriterion("organizeName is null");
            return (Criteria) this;
        }

        public Criteria andOrganizenameIsNotNull() {
            addCriterion("organizeName is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizenameEqualTo(String value) {
            addCriterion("organizeName =", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameNotEqualTo(String value) {
            addCriterion("organizeName <>", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameGreaterThan(String value) {
            addCriterion("organizeName >", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameGreaterThanOrEqualTo(String value) {
            addCriterion("organizeName >=", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameLessThan(String value) {
            addCriterion("organizeName <", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameLessThanOrEqualTo(String value) {
            addCriterion("organizeName <=", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameLike(String value) {
            addCriterion("organizeName like", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameNotLike(String value) {
            addCriterion("organizeName not like", value, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameIn(List<String> values) {
            addCriterion("organizeName in", values, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameNotIn(List<String> values) {
            addCriterion("organizeName not in", values, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameBetween(String value1, String value2) {
            addCriterion("organizeName between", value1, value2, "organizename");
            return (Criteria) this;
        }

        public Criteria andOrganizenameNotBetween(String value1, String value2) {
            addCriterion("organizeName not between", value1, value2, "organizename");
            return (Criteria) this;
        }

        public Criteria andRoleIsNull() {
            addCriterion("role is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("role is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(Integer value) {
            addCriterion("role =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(Integer value) {
            addCriterion("role <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(Integer value) {
            addCriterion("role >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(Integer value) {
            addCriterion("role >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(Integer value) {
            addCriterion("role <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(Integer value) {
            addCriterion("role <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<Integer> values) {
            addCriterion("role in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<Integer> values) {
            addCriterion("role not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(Integer value1, Integer value2) {
            addCriterion("role between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(Integer value1, Integer value2) {
            addCriterion("role not between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusIsNull() {
            addCriterion("approvalStatus is null");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusIsNotNull() {
            addCriterion("approvalStatus is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusEqualTo(Byte value) {
            addCriterion("approvalStatus =", value, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusNotEqualTo(Byte value) {
            addCriterion("approvalStatus <>", value, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusGreaterThan(Byte value) {
            addCriterion("approvalStatus >", value, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("approvalStatus >=", value, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusLessThan(Byte value) {
            addCriterion("approvalStatus <", value, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusLessThanOrEqualTo(Byte value) {
            addCriterion("approvalStatus <=", value, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusIn(List<Byte> values) {
            addCriterion("approvalStatus in", values, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusNotIn(List<Byte> values) {
            addCriterion("approvalStatus not in", values, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusBetween(Byte value1, Byte value2) {
            addCriterion("approvalStatus between", value1, value2, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andApprovalstatusNotBetween(Byte value1, Byte value2) {
            addCriterion("approvalStatus not between", value1, value2, "approvalstatus");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTIme is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTIme is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTIme =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTIme <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTIme >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTIme >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTIme <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTIme <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTIme in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTIme not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTIme between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTIme not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatebyIsNull() {
            addCriterion("createBy is null");
            return (Criteria) this;
        }

        public Criteria andCreatebyIsNotNull() {
            addCriterion("createBy is not null");
            return (Criteria) this;
        }

        public Criteria andCreatebyEqualTo(String value) {
            addCriterion("createBy =", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotEqualTo(String value) {
            addCriterion("createBy <>", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyGreaterThan(String value) {
            addCriterion("createBy >", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyGreaterThanOrEqualTo(String value) {
            addCriterion("createBy >=", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLessThan(String value) {
            addCriterion("createBy <", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLessThanOrEqualTo(String value) {
            addCriterion("createBy <=", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLike(String value) {
            addCriterion("createBy like", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotLike(String value) {
            addCriterion("createBy not like", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyIn(List<String> values) {
            addCriterion("createBy in", values, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotIn(List<String> values) {
            addCriterion("createBy not in", values, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyBetween(String value1, String value2) {
            addCriterion("createBy between", value1, value2, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotBetween(String value1, String value2) {
            addCriterion("createBy not between", value1, value2, "createby");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNull() {
            addCriterion("updateBy is null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNotNull() {
            addCriterion("updateBy is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyEqualTo(String value) {
            addCriterion("updateBy =", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotEqualTo(String value) {
            addCriterion("updateBy <>", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThan(String value) {
            addCriterion("updateBy >", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThanOrEqualTo(String value) {
            addCriterion("updateBy >=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThan(String value) {
            addCriterion("updateBy <", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThanOrEqualTo(String value) {
            addCriterion("updateBy <=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLike(String value) {
            addCriterion("updateBy like", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotLike(String value) {
            addCriterion("updateBy not like", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIn(List<String> values) {
            addCriterion("updateBy in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotIn(List<String> values) {
            addCriterion("updateBy not in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyBetween(String value1, String value2) {
            addCriterion("updateBy between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotBetween(String value1, String value2) {
            addCriterion("updateBy not between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNull() {
            addCriterion("isDelete is null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNotNull() {
            addCriterion("isDelete is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteEqualTo(Byte value) {
            addCriterion("isDelete =", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotEqualTo(Byte value) {
            addCriterion("isDelete <>", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThan(Byte value) {
            addCriterion("isDelete >", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThanOrEqualTo(Byte value) {
            addCriterion("isDelete >=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThan(Byte value) {
            addCriterion("isDelete <", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThanOrEqualTo(Byte value) {
            addCriterion("isDelete <=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIn(List<Byte> values) {
            addCriterion("isDelete in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotIn(List<Byte> values) {
            addCriterion("isDelete not in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteBetween(Byte value1, Byte value2) {
            addCriterion("isDelete between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotBetween(Byte value1, Byte value2) {
            addCriterion("isDelete not between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andSchemaidIsNull() {
            addCriterion("schemaID is null");
            return (Criteria) this;
        }

        public Criteria andSchemaidIsNotNull() {
            addCriterion("schemaID is not null");
            return (Criteria) this;
        }

        public Criteria andSchemaidEqualTo(String value) {
            addCriterion("schemaID =", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotEqualTo(String value) {
            addCriterion("schemaID <>", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidGreaterThan(String value) {
            addCriterion("schemaID >", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidGreaterThanOrEqualTo(String value) {
            addCriterion("schemaID >=", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidLessThan(String value) {
            addCriterion("schemaID <", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidLessThanOrEqualTo(String value) {
            addCriterion("schemaID <=", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidLike(String value) {
            addCriterion("schemaID like", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotLike(String value) {
            addCriterion("schemaID not like", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidIn(List<String> values) {
            addCriterion("schemaID in", values, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotIn(List<String> values) {
            addCriterion("schemaID not in", values, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidBetween(String value1, String value2) {
            addCriterion("schemaID between", value1, value2, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotBetween(String value1, String value2) {
            addCriterion("schemaID not between", value1, value2, "schemaid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}