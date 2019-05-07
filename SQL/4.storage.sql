  CREATE TABLE storage (
  	 pId int(5) unsigned NOT NULL AUTO_INCREMENT,
 	 pName varchar(50) NOT NULL DEFAULT "",
  	 pImgName varchar(100) NOT NULL DEFAULT "",
	 pPrice int(5) unsigned NOT NULL,
	 pQuantity int(255) unsigned NOT NULL,
	 pAdminId int(5) unsigned NOT NULL,
  	PRIMARY KEY (pId),
  	FOREIGN KEY (pAdminId) REFERENCES admins(aId)
  ) AUTO_INCREMENT=50001 DEFAULT CHARSET=utf8;

desc storage;