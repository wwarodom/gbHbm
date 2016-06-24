CREATE TABLE `user` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(50) NULL DEFAULT '0',
	`password` VARCHAR(50) NULL DEFAULT '0',
	`email` VARCHAR(50) NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO `guestbook`.`user` (`username`, `password`, `email`) VALUES ('user', 'pass', 'wwarodom@gmail.com');


CREATE TABLE `message` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`message` VARCHAR(500) NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `FK_message_user` (`user_id`),
	CONSTRAINT `FK_message_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO `guestbook`.`message` (`user_id`, `message`) VALUES (1, 'Hello world');

CREATE TABLE `tag` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(10) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO `guestbook`.`tag` (`name`) VALUES ('java');
INSERT INTO `guestbook`.`tag` (`name`) VALUES ('php');


CREATE TABLE `message_tag` (
	`message_id` INT UNSIGNED NOT NULL,
	`tag_id` INT UNSIGNED NOT NULL,
	PRIMARY KEY (`message_id`, `tag_id`),
	INDEX `fk_tag` (`tag_id`),
	CONSTRAINT `fk_message` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `fk_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE=InnoDB
;

INSERT INTO `guestbook`.`message_tag` (`message_id`, `tag_id`) VALUES (1, 1);
INSERT INTO `guestbook`.`message_tag` (`message_id`, `tag_id`) VALUES (2, 1);
INSERT INTO `guestbook`.`message_tag` (`message_id`, `tag_id`) VALUES (3, 2);
