CREATE TABLE invoice (
  	vId varchar(20) NOT NULL DEFAULT "", 
  	vAdminId int(5) unsigned NOT NULL,
	vShopName varchar(20) NOT NULL DEFAULT "",
	vName varchar(10) NOT NULL DEFAULT "",
	vTel varchar(20) NOT NULL DEFAULT "",
	vAddress varchar(50) NOT NULL DEFAULT "",
	vDate date NOT NULL,
	vState varchar(10) DEFAULT "출고대기",
  	PRIMARY KEY (vId),
  	FOREIGN KEY (vAdminId) REFERENCES admins(aId)
  ) DEFAULT CHARSET=utf8;

desc invoice;