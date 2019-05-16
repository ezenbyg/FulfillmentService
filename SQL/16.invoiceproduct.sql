CREATE TABLE invoiceproduct (
  	pInvoiceId varchar(20) NOT NULL DEFAULT "", 
	ipProductId int(5) unsigned NOT NULL,
	ipQuantity int(255) unsigned NOT NULL,
  	PRIMARY KEY (pInvoiceId, ipProductId),
  	FOREIGN KEY (pInvoiceId) REFERENCES invoice(vId),
	FOREIGN KEY (ipProductId) REFERENCES storage(pId)
  ) AUTO_INCREMENT=5000001 DEFAULT CHARSET=utf8;

desc invoiceproduct;