CREATE TABLE invoice (
  	vId varchar(20) NOT NULL DEFAULT "",
  	vAdminId int(5) unsigned NOT NULL,
	vShopName varchar(20) NOT NULL DEFAULT "",
	vName varchar(10) NOT NULL DEFAULT "",
	vTel varchar(20) NOT NULL DEFAULT "",
	vAddress varchar(50) NOT NULL DEFAULT "",
	vDate datetime NOT NULL DEFAULT current_timestamp,
	vPrice int(10) DEFAULT 0,
	vState varchar(10) DEFAULT "출고대기",
	vlogisId int unsigned default 0,
  	PRIMARY KEY (vId),
  	FOREIGN KEY (vAdminId) REFERENCES admins(aId)
  ) DEFAULT CHARSET=utf8;

desc invoice;