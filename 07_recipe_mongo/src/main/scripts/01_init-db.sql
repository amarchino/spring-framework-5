CREATE USER IF NOT EXISTS 'sfg_dev_user'@'%' IDENTIFIED BY 'guru';
CREATE USER IF NOT EXISTS 'sfg_prod_user'@'%' IDENTIFIED BY 'guru';

CREATE DATABASE IF NOT EXISTS sfg_dev;
CREATE DATABASE IF NOT EXISTS sfg_prod;

GRANT SELECT ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT INSERT ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT UPDATE ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT DELETE ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT SELECT ON sfg_prod.* TO 'sfg_prod_user'@'%';
GRANT INSERT ON sfg_prod.* TO 'sfg_prod_user'@'%';
GRANT UPDATE ON sfg_prod.* TO 'sfg_prod_user'@'%';
GRANT DELETE ON sfg_prod.* TO 'sfg_prod_user'@'%';
