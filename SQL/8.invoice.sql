CREATE TABLE invoice (
  	vId int(5) unsigned NOT NULL AUTO_INCREMENT, 
	vName varchar(10) NOT NULL DEFAULT "",
	vTel varchar(15) NOT NULL DEFAULT "",
	vAddress varchar(3), 
	vProductId int(5) unsigned NOT NULL,
	vProductName varchar(100) NOT NULL DEFAULT "", 
	vQuantity int(255) NOT NULL,
	vDate datetime NOT NULL DEFAULT current_timestamp, 
	vAdminId int(5) unsigned NOT NULL,
	vState varchar(10) DEFAULT "출고대기",
  	PRIMARY KEY (vId),
  	FOREIGN KEY (vProductId) REFERENCES storage(pId),
  	FOREIGN KEY (vAdminId) REFERENCES admins(aId)
  ) DEFAULT CHARSET=utf8;

desc invoice;