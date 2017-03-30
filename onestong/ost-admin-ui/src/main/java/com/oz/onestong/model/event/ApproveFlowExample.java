package com.oz.onestong.model.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApproveFlowExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public ApproveFlowExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
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

        public Criteria andAeIdIsNull() {
            addCriterion("ae_id is null");
            return (Criteria) this;
        }

        public Criteria andAeIdIsNotNull() {
            addCriterion("ae_id is not null");
            return (Criteria) this;
        }

        public Criteria andAeIdEqualTo(String value) {
            addCriterion("ae_id =", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdNotEqualTo(String value) {
            addCriterion("ae_id <>", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdGreaterThan(String value) {
            addCriterion("ae_id >", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdGreaterThanOrEqualTo(String value) {
            addCriterion("ae_id >=", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdLessThan(String value) {
            addCriterion("ae_id <", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdLessThanOrEqualTo(String value) {
            addCriterion("ae_id <=", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdLike(String value) {
            addCriterion("ae_id like", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdNotLike(String value) {
            addCriterion("ae_id not like", value, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdIn(List<String> values) {
            addCriterion("ae_id in", values, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdNotIn(List<String> values) {
            addCriterion("ae_id not in", values, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdBetween(String value1, String value2) {
            addCriterion("ae_id between", value1, value2, "aeId");
            return (Criteria) this;
        }

        public Criteria andAeIdNotBetween(String value1, String value2) {
            addCriterion("ae_id not between", value1, value2, "aeId");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorIsNull() {
            addCriterion("approval_excutor is null");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorIsNotNull() {
            addCriterion("approval_excutor is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorEqualTo(Integer value) {
            addCriterion("approval_excutor =", value, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorNotEqualTo(Integer value) {
            addCriterion("approval_excutor <>", value, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorGreaterThan(Integer value) {
            addCriterion("approval_excutor >", value, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorGreaterThanOrEqualTo(Integer value) {
            addCriterion("approval_excutor >=", value, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorLessThan(Integer value) {
            addCriterion("approval_excutor <", value, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorLessThanOrEqualTo(Integer value) {
            addCriterion("approval_excutor <=", value, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorIn(List<Integer> values) {
            addCriterion("approval_excutor in", values, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorNotIn(List<Integer> values) {
            addCriterion("approval_excutor not in", values, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorBetween(Integer value1, Integer value2) {
            addCriterion("approval_excutor between", value1, value2, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalExcutorNotBetween(Integer value1, Integer value2) {
            addCriterion("approval_excutor not between", value1, value2, "approvalExcutor");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNull() {
            addCriterion("approval_time is null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNotNull() {
            addCriterion("approval_time is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeEqualTo(Date value) {
            addCriterion("approval_time =", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotEqualTo(Date value) {
            addCriterion("approval_time <>", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThan(Date value) {
            addCriterion("approval_time >", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approval_time >=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThan(Date value) {
            addCriterion("approval_time <", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThanOrEqualTo(Date value) {
            addCriterion("approval_time <=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIn(List<Date> values) {
            addCriterion("approval_time in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotIn(List<Date> values) {
            addCriterion("approval_time not in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeBetween(Date value1, Date value2) {
            addCriterion("approval_time between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotBetween(Date value1, Date value2) {
            addCriterion("approval_time not between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionIsNull() {
            addCriterion("approval_suggestion is null");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionIsNotNull() {
            addCriterion("approval_suggestion is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionEqualTo(String value) {
            addCriterion("approval_suggestion =", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionNotEqualTo(String value) {
            addCriterion("approval_suggestion <>", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionGreaterThan(String value) {
            addCriterion("approval_suggestion >", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionGreaterThanOrEqualTo(String value) {
            addCriterion("approval_suggestion >=", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionLessThan(String value) {
            addCriterion("approval_suggestion <", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionLessThanOrEqualTo(String value) {
            addCriterion("approval_suggestion <=", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionLike(String value) {
            addCriterion("approval_suggestion like", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionNotLike(String value) {
            addCriterion("approval_suggestion not like", value, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionIn(List<String> values) {
            addCriterion("approval_suggestion in", values, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionNotIn(List<String> values) {
            addCriterion("approval_suggestion not in", values, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionBetween(String value1, String value2) {
            addCriterion("approval_suggestion between", value1, value2, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalSuggestionNotBetween(String value1, String value2) {
            addCriterion("approval_suggestion not between", value1, value2, "approvalSuggestion");
            return (Criteria) this;
        }

        public Criteria andApprovalStateIsNull() {
            addCriterion("approval_state is null");
            return (Criteria) this;
        }

        public Criteria andApprovalStateIsNotNull() {
            addCriterion("approval_state is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalStateEqualTo(Byte value) {
            addCriterion("approval_state =", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotEqualTo(Byte value) {
            addCriterion("approval_state <>", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateGreaterThan(Byte value) {
            addCriterion("approval_state >", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("approval_state >=", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateLessThan(Byte value) {
            addCriterion("approval_state <", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateLessThanOrEqualTo(Byte value) {
            addCriterion("approval_state <=", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateIn(List<Byte> values) {
            addCriterion("approval_state in", values, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotIn(List<Byte> values) {
            addCriterion("approval_state not in", values, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateBetween(Byte value1, Byte value2) {
            addCriterion("approval_state between", value1, value2, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotBetween(Byte value1, Byte value2) {
            addCriterion("approval_state not between", value1, value2, "approvalState");
            return (Criteria) this;
        }

        public Criteria andOptional1IsNull() {
            addCriterion("optional1 is null");
            return (Criteria) this;
        }

        public Criteria andOptional1IsNotNull() {
            addCriterion("optional1 is not null");
            return (Criteria) this;
        }

        public Criteria andOptional1EqualTo(String value) {
            addCriterion("optional1 =", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1NotEqualTo(String value) {
            addCriterion("optional1 <>", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1GreaterThan(String value) {
            addCriterion("optional1 >", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1GreaterThanOrEqualTo(String value) {
            addCriterion("optional1 >=", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1LessThan(String value) {
            addCriterion("optional1 <", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1LessThanOrEqualTo(String value) {
            addCriterion("optional1 <=", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1Like(String value) {
            addCriterion("optional1 like", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1NotLike(String value) {
            addCriterion("optional1 not like", value, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1In(List<String> values) {
            addCriterion("optional1 in", values, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1NotIn(List<String> values) {
            addCriterion("optional1 not in", values, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1Between(String value1, String value2) {
            addCriterion("optional1 between", value1, value2, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional1NotBetween(String value1, String value2) {
            addCriterion("optional1 not between", value1, value2, "optional1");
            return (Criteria) this;
        }

        public Criteria andOptional2IsNull() {
            addCriterion("optional2 is null");
            return (Criteria) this;
        }

        public Criteria andOptional2IsNotNull() {
            addCriterion("optional2 is not null");
            return (Criteria) this;
        }

        public Criteria andOptional2EqualTo(String value) {
            addCriterion("optional2 =", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2NotEqualTo(String value) {
            addCriterion("optional2 <>", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2GreaterThan(String value) {
            addCriterion("optional2 >", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2GreaterThanOrEqualTo(String value) {
            addCriterion("optional2 >=", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2LessThan(String value) {
            addCriterion("optional2 <", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2LessThanOrEqualTo(String value) {
            addCriterion("optional2 <=", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2Like(String value) {
            addCriterion("optional2 like", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2NotLike(String value) {
            addCriterion("optional2 not like", value, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2In(List<String> values) {
            addCriterion("optional2 in", values, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2NotIn(List<String> values) {
            addCriterion("optional2 not in", values, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2Between(String value1, String value2) {
            addCriterion("optional2 between", value1, value2, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional2NotBetween(String value1, String value2) {
            addCriterion("optional2 not between", value1, value2, "optional2");
            return (Criteria) this;
        }

        public Criteria andOptional3IsNull() {
            addCriterion("optional3 is null");
            return (Criteria) this;
        }

        public Criteria andOptional3IsNotNull() {
            addCriterion("optional3 is not null");
            return (Criteria) this;
        }

        public Criteria andOptional3EqualTo(String value) {
            addCriterion("optional3 =", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3NotEqualTo(String value) {
            addCriterion("optional3 <>", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3GreaterThan(String value) {
            addCriterion("optional3 >", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3GreaterThanOrEqualTo(String value) {
            addCriterion("optional3 >=", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3LessThan(String value) {
            addCriterion("optional3 <", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3LessThanOrEqualTo(String value) {
            addCriterion("optional3 <=", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3Like(String value) {
            addCriterion("optional3 like", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3NotLike(String value) {
            addCriterion("optional3 not like", value, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3In(List<String> values) {
            addCriterion("optional3 in", values, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3NotIn(List<String> values) {
            addCriterion("optional3 not in", values, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3Between(String value1, String value2) {
            addCriterion("optional3 between", value1, value2, "optional3");
            return (Criteria) this;
        }

        public Criteria andOptional3NotBetween(String value1, String value2) {
            addCriterion("optional3 not between", value1, value2, "optional3");
            return (Criteria) this;
        }

        public Criteria andApprovalNameIsNull() {
            addCriterion("approval_name is null");
            return (Criteria) this;
        }

        public Criteria andApprovalNameIsNotNull() {
            addCriterion("approval_name is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalNameEqualTo(String value) {
            addCriterion("approval_name =", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotEqualTo(String value) {
            addCriterion("approval_name <>", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameGreaterThan(String value) {
            addCriterion("approval_name >", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameGreaterThanOrEqualTo(String value) {
            addCriterion("approval_name >=", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameLessThan(String value) {
            addCriterion("approval_name <", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameLessThanOrEqualTo(String value) {
            addCriterion("approval_name <=", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameLike(String value) {
            addCriterion("approval_name like", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotLike(String value) {
            addCriterion("approval_name not like", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameIn(List<String> values) {
            addCriterion("approval_name in", values, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotIn(List<String> values) {
            addCriterion("approval_name not in", values, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameBetween(String value1, String value2) {
            addCriterion("approval_name between", value1, value2, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotBetween(String value1, String value2) {
            addCriterion("approval_name not between", value1, value2, "approvalName");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andValidIsNull() {
            addCriterion("valid is null");
            return (Criteria) this;
        }

        public Criteria andValidIsNotNull() {
            addCriterion("valid is not null");
            return (Criteria) this;
        }

        public Criteria andValidEqualTo(Byte value) {
            addCriterion("valid =", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotEqualTo(Byte value) {
            addCriterion("valid <>", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThan(Byte value) {
            addCriterion("valid >", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThanOrEqualTo(Byte value) {
            addCriterion("valid >=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThan(Byte value) {
            addCriterion("valid <", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThanOrEqualTo(Byte value) {
            addCriterion("valid <=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidIn(List<Byte> values) {
            addCriterion("valid in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotIn(List<Byte> values) {
            addCriterion("valid not in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidBetween(Byte value1, Byte value2) {
            addCriterion("valid between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotBetween(Byte value1, Byte value2) {
            addCriterion("valid not between", value1, value2, "valid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table approve_flows
     *
     * @mbggenerated do_not_delete_during_merge Tue Jan 20 13:36:44 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
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