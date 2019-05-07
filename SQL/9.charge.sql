CREATE TABLE charge (
  	gId int(8) unsigned NOT NULL AUTO_INCREMENT,
	gAdminId int(5) unsigned NOT NULL,
  	gInvoiceId int(5) unsigned NOT NULL,
	gProductName varchar(30) NOT NULL DEFAULT "",
	gDate datetime NOT NULL DEFAULT current_timestamp,
  	PRIMARY KEY (gId),
	FOREIGN KEY (gAdminId) REFERENCES admins(aId)
) AUTO_INCREMENT=10000001 DEFAULT CHARSET=utf8;