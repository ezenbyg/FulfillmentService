CREATE TABLE pay (
  	yId int(8) unsigned NOT NULL AUTO_INCREMENT,
	yBankId varchar(30) NOT NULL DEFAULT "",
	yAdminId int(5) unsigned NOT NULL,
  	yPrice int(10) unsigned NOT NULL,
	yDate datetime NOT NULL DEFAULT current_timestamp,
	yState varchar(10) DEFAULT "ЙЬСіБо",
  	PRIMARY KEY (yId),
	FOREIGN KEY (yAdminId) REFERENCES admins(aId),
	FOREIGN KEY (yBankId) REFERENCES bank(bId)
) AUTO_INCREMENT=20000001 DEFAULT CHARSET=utf8;