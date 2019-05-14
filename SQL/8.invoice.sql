CREATE TABLE invoice (
  	vId int(12) unsigned NOT NULL, 
	vAdminId int(5) unsigned NOT NULL,
	vDate datetime NOT NULL DEFAULT current_timestamp, 
	vState varchar(10) DEFAULT "출고대기",
  	PRIMARY KEY (vId),
  	FOREIGN KEY (vAdminId) REFERENCES admins(aId)
  ) DEFAULT CHARSET=utf8;

desc invoice;