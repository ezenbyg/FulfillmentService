CREATE TABLE p_order (
  	oId int(8) unsigned NOT NULL AUTO_INCREMENT,
	oAdminId int(5) unsigned NOT NULL,
  	oQuantity int(200) unsigned NOT NULL,
	oPrice int(10) unsigned NOT NULL,
	oTotalPrice int(10) unsigned NOT NULL,
	oDate datetime NOT NULL DEFAULT current_timestamp,
  	PRIMARY KEY (oId),
	FOREIGN KEY (oAdminId) REFERENCES admins(aId)
) AUTO_INCREMENT=30000001 DEFAULT CHARSET=utf8;