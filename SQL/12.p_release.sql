CREATE TABLE p_release (
  	rId int(8) unsigned NOT NULL AUTO_INCREMENT,
	rTransportId int(5) unsigned NOT NULL,
	rShoppingId int(5) unsigned NOT NULL,
	rInvoiceId int(5) unsigned NOT NULL,
  	rName varchar(10) NOT NULL DEFAULT "",
	rTel varchar(15) NOT NULL DEFAULT "",
	rAddress varchar(50) NOT NULL DEFAULT "",
	rProductName varchar(100) NOT NULL DEFAULT "",
	rQuantity int(10) unsigned NOT NULL,
	rDate datetime NOT NULL DEFAULT current_timestamp,
	rState varchar(10) DEFAULT "√‚∞Ì",
  	PRIMARY KEY (rId),
	FOREIGN KEY (rTransportId) REFERENCES admins(aId),
	FOREIGN KEY (rShoppingId) REFERENCES admins(aId),
	FOREIGN KEY (rInvoiceId) REFERENCES invoice(vId)
) AUTO_INCREMENT=40000001 DEFAULT CHARSET=utf8;