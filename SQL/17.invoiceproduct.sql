CREATE TABLE invoiceproduct (
  	pInvoiceId int(12) unsigned NOT NULL, 
	ipProductId int(5) unsigned NOT NULL,
	ipProductName varchar(50) NOT NULL DEFAULT "",
	ipQuantity int(255) unsigned NOT NULL,
  	PRIMARY KEY (ipProductId),
  	FOREIGN KEY (pInvoiceId) REFERENCES invoice(vId),
	FOREIGN KEY (ipProductId) REFERENCES storage(pId)
  ) DEFAULT CHARSET=utf8;

desc invoiceproduct;