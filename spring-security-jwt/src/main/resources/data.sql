INSERT INTO 
	USER (user_name, password, active, roles) 
VALUES
  	('user', 'user', true, 'ROLE_USER'),
  	('admin', 'admin', true, 'ROLE_USER,ROLE_ADMIN'),
  	('inactive', 'inactive', false, 'ROLE_USER'),
  	('noroles', 'noroles', true, '');