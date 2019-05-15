CREATE TABLE p_release (
  	rId int(8) unsigned NOT NULL AUTO_INCREMENT,
	rTransportId int(5) unsigned NOT NULL,
	rShoppingId int(5) unsigned NOT NULL,
	rInvoiceId varchar(20) NOT NULL DEFAULT "", 
  	rName varchar(10) NOT NULL DEFAULT "",
	rTel varchar(15) NOT NULL DEFAULT "",
	rAddress varchar(50) NOT NULL DEFAULT "",
	rProductName varchar(100) NOT NULL DEFAULT "",
	rQuantity int(10) unsigned NOT NULL,
	rDate datetime NOT NULL DEFAULT current_timestamp,
	rPrice int(10) unsigned,
	rState varchar(10) DEFAULT "배송요청",
  	PRIMARY KEY (rId),
	FOREIGN KEY (rTransportId) REFERENCES admins(aId),
	FOREIGN KEY (rShoppingId) REFERENCES admins(aId),
	FOREIGN KEY (rInvoiceId) REFERENCES invoice(vId)
) AUTO_INCREMENT=40000001 DEFAULT CHARSET=utf8;