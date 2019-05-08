  CREATE TABLE bank (
  	 bId varchar(30) NOT NULL DEFAULT "",
 	 bAdminId int(5) unsigned NOT NULL,
  	 bBalance int(10) unsigned NOT NULL,
  	PRIMARY KEY (bId),
  	FOREIGN KEY (bAdminId) REFERENCES admins(bId)
  ) DEFAULT CHARSET=utf8;

desc bank;