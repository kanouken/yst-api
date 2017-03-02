CREATE TABLE `onestong`.`approval_users` (
	`id` int NOT NULL AUTO_INCREMENT,
	`approval_event_id` char(50) NOT NULL COMMENT '审批事件id',
	`user_id` int NOT NULL COMMENT '审批人id',
	`state` tinyint NOT NULL DEFAULT 0 COMMENT '0 未审批 1 通过 2 不通过',
	`create_time` datetime NOT NULL,
	`update_time` datetime NOT NULL,
	`create_by` varchar(50) NOT NULL,
	`update_by` varchar(50) NOT NULL,
	`is_delete` tinyint(4) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`)
) COMMENT='';