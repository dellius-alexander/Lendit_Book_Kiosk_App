drop database if exists Lendit_Book_Kiosk;
CREATE DATABASE Lendit_Book_Kiosk;
# Syntax:
# CREATE USER
#  user_name IDENTIFIED BY [ PASSWORD ] 'password_value';
CREATE USER 'admin'@'Lendit_Book_Kiosk-db' IDENTIFIED BY 'developer';
GRANT ALL PRIVILEGES ON admin.* TO 'admin'@'Lendit_Book_Kiosk-db' WITH GRANT OPTION;
use Lendit_Book_Kiosk;