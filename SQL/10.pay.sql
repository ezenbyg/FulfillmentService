CREATE TABLE pay (
  	yId int(8) unsigned NOT NULL AUTO_INCREMENT,
	yAdminId int(5) unsigned NOT NULL,
  	yPrice int(10) unsigned NOT NULL,
	yDate datetime NOT NULL DEFAULT current_timestamp,
  	PRIMARY KEY (yId),
	FOREIGN KEY (yAdminId) REFERENCES admins(aId)
) AUTO_INCREMENT=20000001 DEFAULT CHARSET=utf8;