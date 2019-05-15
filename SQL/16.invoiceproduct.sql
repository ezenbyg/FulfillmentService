CREATE TABLE invoiceproduct (
  	pInvoiceId varchar(20) NOT NULL DEFAULT "", 
	ipProductId int(5) unsigned NOT NULL,
	ipProductName varchar(50) NOT NULL DEFAULT "",
	ipQuantity int(255) unsigned NOT NULL,
	ipDate datetime NOT NULL DEFAULT current_timestamp, 
  	PRIMARY KEY (ipProductId),
  	FOREIGN KEY (pInvoiceId) REFERENCES invoice(vId),
	FOREIGN KEY (ipProductId) REFERENCES storage(pId)
  ) DEFAULT CHARSET=utf8;

desc invoiceproduct;