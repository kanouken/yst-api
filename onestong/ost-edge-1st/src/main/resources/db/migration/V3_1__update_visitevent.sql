ALTER TABLE `onestong`.`visit_events` ADD COLUMN `contacts_id` int COMMENT '客户下联系人id' AFTER `customer_id`, ADD COLUMN `contacts_name` varchar(50) COMMENT '客户下联系人名字' AFTER `contacts_id`;