CREATE TABLE p_order (
  	oId int(8) unsigned NOT NULL AUTO_INCREMENT,
	oAdminId int(5) unsigned NOT NULL,
	oProductId int(5) unsigned NOT NULL,
  	oQuantity int(200) unsigned NOT NULL,
	oPrice int(10) unsigned NOT NULL,
	oTotalPrice int(10) unsigned NOT NULL,
	oDate datetime NOT NULL DEFAULT current_timestamp,
	oState varchar(10) DEFAULT "πﬂ¡÷",
  	PRIMARY KEY (oId),
	FOREIGN KEY (oAdminId) REFERENCES admins(aId),
	FOREIGN KEY (oProductId) REFERENCES storage(pId)
) AUTO_INCREMENT=30000001 DEFAULT CHARSET=utf8;