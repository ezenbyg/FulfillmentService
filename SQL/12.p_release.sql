CREATE TABLE p_release (
  	rId int(8) unsigned NOT NULL AUTO_INCREMENT,
	rInvoiceId varchar(20) NOT NULL DEFAULT "", 
	rTransportName varchar(20) NOT NULL DEFAULT "",
	rDate datetime NOT NULL DEFAULT current_timestamp,
	rState varchar(10) DEFAULT "배송요청",
  	PRIMARY KEY (rId),
	FOREIGN KEY (rInvoiceId) REFERENCES invoice(vId)
) AUTO_INCREMENT=40000001 DEFAULT CHARSET=utf8;