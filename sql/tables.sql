
drop table sms_code ;
CREATE TABLE sms_code ( 
    id         	bigint(20) AUTO_INCREMENT COMMENT 'primary key as messageId'  NOT NULL,
    mobile     	varchar(11) COMMENT 'mobile phone'  NOT NULL,
    code       	varchar(8) COMMENT 'verify code'  NOT NULL,
    state       int(1) COMMENT '0:created; 1:verified; 2:unavailable' NOT NULL default 0,
    create_time	datetime COMMENT 'create time'  NOT NULL,
    PRIMARY KEY(id)
)
GO
