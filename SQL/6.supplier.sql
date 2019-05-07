CREATE TABLE supplier (
  	sAdminId int(5) unsigned NOT NULL,
  	sProductId int(5) unsigned NOT NULL,
 	sProductName varchar(50) NOT NULL,
  	PRIMARY KEY (sAdminId, sProductId),
  	FOREIGN KEY (sAdminId) REFERENCES admins(aId),
  	FOREIGN KEY (sProductId) REFERENCES storage(pId)
  ) DEFAULT CHARSET=utf8;
  
desc supplier;
