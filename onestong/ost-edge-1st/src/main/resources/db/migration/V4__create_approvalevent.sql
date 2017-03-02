CREATE TABLE `onestong`.`approval_events` (
	`ae_id` char(50) NOT NULL,
	`user_id` int NOT NULL COMMENT '用户id',
	`start_time` datetime NOT NULL,
	`end_time` datetime NOT NULL,
	`leave_type` varchar(20) NOT NULL,
	`approval_type` varchar(20) NOT NULL,
	`content` varchar(300) NOT NULL,
	`schedules` varchar(300),
	`approval_tip` varchar(100) NOT NULL,
	`create_time` datetime NOT NULL,
	`update_time` datetime NOT NULL,
	`create_by` varchar(50) NOT NULL,
	`update_by` varchar(50) NOT NULL,
	`is_delete` tinyint(4) NOT NULL DEFAULT 0,
	`state` tinyint(4) NOT NULL COMMENT '0 未审批  1 审批 未通过  2 审批通过',
	PRIMARY KEY (`ae_id`)
) COMMENT='';