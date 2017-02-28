package org.ost.crm.dao.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProjectTypeExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayIsNull() {
            addCriterion("cycWarningDay is null");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayIsNotNull() {
            addCriterion("cycWarningDay is not null");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayEqualTo(Integer value) {
            addCriterion("cycWarningDay =", value, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayNotEqualTo(Integer value) {
            addCriterion("cycWarningDay <>", value, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayGreaterThan(Integer value) {
            addCriterion("cycWarningDay >", value, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycWarningDay >=", value, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayLessThan(Integer value) {
            addCriterion("cycWarningDay <", value, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayLessThanOrEqualTo(Integer value) {
            addCriterion("cycWarningDay <=", value, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayIn(List<Integer> values) {
            addCriterion("cycWarningDay in", values, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayNotIn(List<Integer> values) {
            addCriterion("cycWarningDay not in", values, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayBetween(Integer value1, Integer value2) {
            addCriterion("cycWarningDay between", value1, value2, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningdayNotBetween(Integer value1, Integer value2) {
            addCriterion("cycWarningDay not between", value1, value2, "cycwarningday");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableIsNull() {
            addCriterion("cycWarningEnable is null");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableIsNotNull() {
            addCriterion("cycWarningEnable is not null");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableEqualTo(String value) {
            addCriterion("cycWarningEnable =", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableNotEqualTo(String value) {
            addCriterion("cycWarningEnable <>", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableGreaterThan(String value) {
            addCriterion("cycWarningEnable >", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableGreaterThanOrEqualTo(String value) {
            addCriterion("cycWarningEnable >=", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableLessThan(String value) {
            addCriterion("cycWarningEnable <", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableLessThanOrEqualTo(String value) {
            addCriterion("cycWarningEnable <=", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableLike(String value) {
            addCriterion("cycWarningEnable like", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableNotLike(String value) {
            addCriterion("cycWarningEnable not like", value, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableIn(List<String> values) {
            addCriterion("cycWarningEnable in", values, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableNotIn(List<String> values) {
            addCriterion("cycWarningEnable not in", values, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableBetween(String value1, String value2) {
            addCriterion("cycWarningEnable between", value1, value2, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andCycwarningenableNotBetween(String value1, String value2) {
            addCriterion("cycWarningEnable not between", value1, value2, "cycwarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayIsNull() {
            addCriterion("startTimeWarningDay is null");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayIsNotNull() {
            addCriterion("startTimeWarningDay is not null");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayEqualTo(Integer value) {
            addCriterion("startTimeWarningDay =", value, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayNotEqualTo(Integer value) {
            addCriterion("startTimeWarningDay <>", value, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayGreaterThan(Integer value) {
            addCriterion("startTimeWarningDay >", value, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("startTimeWarningDay >=", value, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayLessThan(Integer value) {
            addCriterion("startTimeWarningDay <", value, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayLessThanOrEqualTo(Integer value) {
            addCriterion("startTimeWarningDay <=", value, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayIn(List<Integer> values) {
            addCriterion("startTimeWarningDay in", values, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayNotIn(List<Integer> values) {
            addCriterion("startTimeWarningDay not in", values, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayBetween(Integer value1, Integer value2) {
            addCriterion("startTimeWarningDay between", value1, value2, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningdayNotBetween(Integer value1, Integer value2) {
            addCriterion("startTimeWarningDay not between", value1, value2, "starttimewarningday");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableIsNull() {
            addCriterion("startTimeWarningEnable is null");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableIsNotNull() {
            addCriterion("startTimeWarningEnable is not null");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableEqualTo(String value) {
            addCriterion("startTimeWarningEnable =", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableNotEqualTo(String value) {
            addCriterion("startTimeWarningEnable <>", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableGreaterThan(String value) {
            addCriterion("startTimeWarningEnable >", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableGreaterThanOrEqualTo(String value) {
            addCriterion("startTimeWarningEnable >=", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableLessThan(String value) {
            addCriterion("startTimeWarningEnable <", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableLessThanOrEqualTo(String value) {
            addCriterion("startTimeWarningEnable <=", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableLike(String value) {
            addCriterion("startTimeWarningEnable like", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableNotLike(String value) {
            addCriterion("startTimeWarningEnable not like", value, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableIn(List<String> values) {
            addCriterion("startTimeWarningEnable in", values, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableNotIn(List<String> values) {
            addCriterion("startTimeWarningEnable not in", values, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableBetween(String value1, String value2) {
            addCriterion("startTimeWarningEnable between", value1, value2, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andStarttimewarningenableNotBetween(String value1, String value2) {
            addCriterion("startTimeWarningEnable not between", value1, value2, "starttimewarningenable");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
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

        public Criteria andSchemaidEqualTo(Byte value) {
            addCriterion("schemaID =", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotEqualTo(Byte value) {
            addCriterion("schemaID <>", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidGreaterThan(Byte value) {
            addCriterion("schemaID >", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidGreaterThanOrEqualTo(Byte value) {
            addCriterion("schemaID >=", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidLessThan(Byte value) {
            addCriterion("schemaID <", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidLessThanOrEqualTo(Byte value) {
            addCriterion("schemaID <=", value, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidIn(List<Byte> values) {
            addCriterion("schemaID in", values, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotIn(List<Byte> values) {
            addCriterion("schemaID not in", values, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidBetween(Byte value1, Byte value2) {
            addCriterion("schemaID between", value1, value2, "schemaid");
            return (Criteria) this;
        }

        public Criteria andSchemaidNotBetween(Byte value1, Byte value2) {
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