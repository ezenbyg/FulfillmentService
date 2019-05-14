CREATE TABLE invoicehuman (
  	hInvoiceId int(12) unsigned NOT NULL, 
	hName varchar(10) NOT NULL DEFAULT "",
	hTel varchar(20) NOT NULL DEFAULT "",
	hAddress varchar(50) NOT NULL DEFAULT "",
	hDate datetime NOT NULL DEFAULT current_timestamp, 
  	PRIMARY KEY (hTel),
  	FOREIGN KEY (hInvoiceId) REFERENCES invoice(vId)
  ) DEFAULT CHARSET=utf8;

desc invoicehuman;