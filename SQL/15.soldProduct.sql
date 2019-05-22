CREATE TABLE soldProduct (
	serial int(5) unsigned NOT NULL AUTO_INCREMENT,
	soldInvId varchar(20) NOT NULL DEFAULT "",
	soldShopId int(5) unsigned NOT NULL,
	soldTransportId int(5) unsigned NOT NULL,
  	soldId int(5) unsigned NOT NULL,
	soldQuantity int(255) unsigned NOT NULL,
	soldTotalPrice int(10) unsigned NOT NULL,
	soldDate datetime NOT NULL DEFAULT current_timestamp,
	chargeState varchar(10) DEFAULT "미청구",
	transportState varchar(10) DEFAULT "미지급",
  	PRIMARY KEY (serial),
	FOREIGN KEY (soldId) REFERENCES storage(pId),
	FOREIGN KEY (soldInvId) REFERENCES invoice(vId),
	FOREIGN KEY (soldTransportId) REFERENCES admins(aId),
	FOREIGN KEY (soldShopId) REFERENCES admins(aId)
)AUTO_INCREMENT=80000001 DEFAULT CHARSET=utf8;