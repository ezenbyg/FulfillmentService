CREATE TABLE soldProduct (
	serial int(5) unsigned NOT NULL AUTO_INCREMENT,
	soldAdminId int(5) unsigned NOT NULL,
  	soldId int(5) unsigned NOT NULL,
	soldName varchar(20) NOT NULL DEFAULT "",
 	soldPrice int(10) unsigned NOT NULL,
	soldTotalPrice int(10) unsigned NOT NULL,
	soldQuantity int(255) unsigned NOT NULL,
	soldDate datetime NOT NULL DEFAULT current_timestamp,
  	PRIMARY KEY (serial),
	FOREIGN KEY (soldId) REFERENCES storage(pId),
	FOREIGN KEY (soldAdminId) REFERENCES admins(aId)
)AUTO_INCREMENT=80000001 DEFAULT CHARSET=utf8;