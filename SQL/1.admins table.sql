CREATE TABLE admins (
  	aId int(5) unsigned NOT NULL,
	aUserId varchar(20) NOT NULL DEFAULT "",
 	aName varchar(20) NOT NULL DEFAULT "",
  	aPassword varchar(20) NOT NULL DEFAULT "",
  	PRIMARY KEY (aId),
	UNIQUE KEY(aUserId)
)  DEFAULT CHARSET=utf8;