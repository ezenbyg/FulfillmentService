CREATE TABLE customers (
  	cId int(4) unsigned NOT NULL AUTO_INCREMENT,
	cUserId varchar(20) NOT NULL DEFAULT "",
 	cName varchar(20) NOT NULL DEFAULT "",
  	cPassword varchar(20) NOT NULL DEFAULT "",
	PRIMARY KEY(cId),
	UNIQUE KEY(cUserId)
) AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

desc customers;