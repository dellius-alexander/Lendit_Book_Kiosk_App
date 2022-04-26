-- ########################################################
# create root/admin/other users and grant privileges
# CREATE USER 'root'@'localhost' IDENTIFIED BY 'developer';
# GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
# Syntax:
# CREATE USER
#  user_name IDENTIFIED BY [ PASSWORD ] 'password_value';
# GRANT ALL PRIVILEGES ON user_name.database TO 'user_name'@'database' WITH GRANT OPTION;
-- ########################################################
# Selecting  query log output destinations
# You can log the queries to either FILE or TABLE in MySQL itself by
# specifying the log_output variable, which can be either FILE or TABLE,
# or both FILE and TABLE.
# If you specify log_output as FILE, the general query log and the slow
# query log will be written to the files specified by general_log_file and
# slow_query_log_file, respectively.
# If you specify log_output as TABLE, the general query log and the slow
# query log will be written to the mysql.general_log and mysql.slow_log
# tables respectively. Log contents are accessible through SQL statements.
-- # Set log_output FILE
set global log_output = 'FILE';
set global general_log = 'ON';
-- # Set global general_log_file
set global  general_log_file = '/var/log/mysql/general.log';
set global slow_query_log = 'ON';
-- # Log details for queries expected to retrieve all rows instead of using an index:
set global log_queries_not_using_indexes = 'ON';
-- # Set the path to the slow query log:
set global slow_query_log_file ='/var/log/mysql/slow_query.log';
-- # Set the amount of time a query needs to run before being logged:
set global long_query_time = 20;

-- # Set 'secure_file_priv=/mysql_files' must be set in my.cnf file [mysqld] property
# create databases
CREATE DATABASE IF NOT EXISTS `Lendit_Book_Kiosk`;
CREATE DATABASE IF NOT EXISTS `Lendit_Book_Kiosk_Test`;
-- # create users and grant privileges
# CREATE USER 'admin'@'%' IDENTIFIED BY 'developer';
# GRANT ALL PRIVILEGES ON *.* TO '`admin`'@'%';
-- #
CREATE USER 'austin'@'%' IDENTIFIED BY 'developer';
GRANT ALL PRIVILEGES ON *.* TO 'austin'@'%';
-- #
CREATE USER 'jarvis'@'%' IDENTIFIED BY 'developer';
GRANT ALL PRIVILEGES ON *.* TO 'jarvis'@'%';
-- #
CREATE USER 'preston'@'%' IDENTIFIED BY 'developer';
GRANT ALL PRIVILEGES ON *.* TO 'preston'@'%';
-- #
CREATE USER 'dellius'@'%' IDENTIFIED BY 'developer';
GRANT ALL PRIVILEGES ON *.* TO 'dellius'@'%';
-- #
CREATE USER 'annmarie'@'%' IDENTIFIED BY 'developer';
GRANT ALL PRIVILEGES ON *.* TO 'annmarie'@'%';
#-- ----------------------------------------------------------------------
# SETTINGS
# set GLOBAL storage_engine='InnoDb';
# SET GLOBAL default_storage_engine = 'InnoDB';
SET NAMES utf8;
SET global time_zone = '-05:00';
select current_timestamp() as 'Current Time';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';
#-- ----------------------------------------------------------------------
##########################################################################
##########################################################################
# SETUP ENCRYPTION ALGORITHM AND PRIVATE/PUBLIC KEYS
-- Encryption algorithm; can be 'DSA' or 'DH' instead
SET @algo = 'RSA';
-- Key length in bits; make larger for stronger keys
SET @key_len = 224;
# select @key_len;
-- Create private key
set @private_key = ucase(sha2('Lendit_Book_Kiosk_Test', @key_len));
select @private_key as 'Private Key';
# -- Create public key
# set @public_key = hex(aes_encrypt('Lendit_Book_Kiosk_Test', @private_key));
# select @public_key;
#-- ----------------------------------------------------------------------
# # Use the private key to encrypt data and the public key to decrypt it
# # This requires that the members of the key pair be RSA keys.
#
# SET @ciphertext = asymmetric_encrypt(@algo, 'My secret text', @private);
# SET @plaintext = asymmetric_decrypt(@algo, @ciphertext, @public);

# # Conversely, you can encrypt using the public key and decrypt using the private key.
#
# SET @ciphertext = asymmetric_encrypt(@algo, 'My secret text', @pub);
# SET @plaintext = asymmetric_decrypt(@algo, @ciphertext, @private);
#-- ----------------------------------------------------------------------
##########################################################################
# SPECIAL VARIABLES LISTED HERE
-- # Confirm the changes are active by entering the MySQL shell and
-- # running the following command:
show variables;
show privileges;
-- # Directory to imported files from on LOAD Command
SHOW VARIABLES LIKE 'secure_file_priv';
#-- ----------------------------------------------------------------------
set @special_char_regex = '[\-\+\(\)\\[\\]\_\=\*\%\^\#\!\&\/\~\'\"\?\>\<\.\,\:\;\@\`]+';
#-- ----------------------------------------------------------------------
#-- ----------------------------------------------------------------------
##########################################################################
# Create FUNCTIONS
##########################################################################
#-- ----------------------------------------------------------------------
# drop
#     function if exists Lendit_Book_Kiosk_Test.Encrypt_Vals;
# create
# #     definer = alphatoo_dalex@`76.105.120.24`
#     function Lendit_Book_Kiosk_Test.Encrypt_Vals(object varchar(512), pub_key varchar(255))
#     returns text
#     comment '@param: object longtext => object must be of string format;'
#     comment '@param: pub_key varchar(255) => pub_key must be hex formatted key;'
#     deterministic
# BEGIN
#     if (object is null) then
#         return null;
#     elseif (object is not null) then
#         return hex(aes_encrypt(object, pub_key));
#     end if;
#
# END;
#
# # set @encrypt_key = Encrypt_Vals(concat('Jane ', 'Doe ','janedoe@gmail.com [',  1, '] 1979-01-04'),@private_key);
# # select length(@encrypt_key) as 'length', @encrypt_key as 'KEY';
# #
# # select Decrypt_Vals(@encrypt_key,@private_key) as 'original';
#
# #-- ----------------------------------------------------------------------
# drop
#     function if exists Lendit_Book_Kiosk_Test.Decrypt_Vals;
# create
# #     definer = alphatoo_dalex@`76.105.120.24`
#     function Lendit_Book_Kiosk_Test.Decrypt_Vals(object text, pub_key varchar(255))
#     returns text
#     comment '@param: object text => object must be of hex format;'
#     comment '@param: pub_key varchar(255) => pub_key must be hex formatted key;'
#     deterministic
# BEGIN
#     if (object is null) then
#         return null;
#     end if;
#     return aes_decrypt(unhex(object), pub_key);
# END;
# #-- ----------------------------------------------------------------------
# drop function if exists Lendit_Book_Kiosk_Test.get_age;
# create
#     #     definer = alphatoo_dalex@`76.105.120.24`
#     function Lendit_Book_Kiosk_Test.get_age(DOB date)
#     returns int
#     deterministic
#     comment 'DOB Format: [YYYY-mm-dd]'
# get_age:
# begin
#     return year(substr(current_timestamp, 1, 10)) - year(DOB) -
#            (right(substr(current_timestamp, 1, 10), 5) < right(DOB, 5));
# end get_age;
#
# #-- ----------------------------------------------------------------------
# drop function if exists Lendit_Book_Kiosk_Test.no_of_years;
# create
# #     definer = alphatoo_dalex@`76.105.120.24`
#     function Lendit_Book_Kiosk_Test.no_of_years(DOB date)
#     returns int
#     deterministic
# no_of_years:
# BEGIN
#     DECLARE date2 DATE;
#     Select current_date() into date2;
#     RETURN date(date2) - date(DOB);
# END no_of_years;
# #-- ----------------------------------------------------------------------
#
# drop function if exists Lendit_Book_Kiosk_Test.format_phone;
# create
#     function Lendit_Book_Kiosk_Test.format_phone(phone char(32))
#     returns char(17)
#     deterministic
# format_phone:
# begin
#     -- Phone Number Format: {<country_code>####}-{<area_code>###}-{<local phone number>###-####}
#     declare temp_phone varchar(14);
#     declare temp_country_code char(4) default '1';
#     declare temp_area_code char(3);
#     declare temp_local_prefix char(3);
#     declare temp_local_suffix char(4);
#     -- break phone number into segments
#     split_phone_number:
#     begin
#         -- remove any special characters
#         set temp_phone = lpad(regexp_replace(trim(phone), @special_char_regex, ''), 14, 0);
#         -- remove leading zeroes
#         set temp_country_code = regexp_replace(substr(temp_phone, -14, 4), '^[0]+', '');
#         set temp_area_code = substr(temp_phone, -10, 3);
#         set temp_local_prefix = substr(temp_phone, -7, 3);
#         set temp_local_suffix = substr(temp_phone, -4, 4);
#     end split_phone_number;
#     -- check if country code is not entered
#     check_country_code:
#     begin
#         if (length(temp_country_code) = 0) then
#             set temp_country_code = '1';
#         end if;
#     end check_country_code;
#     return concat(temp_country_code, '-', temp_area_code, '-', temp_local_prefix, '-', temp_local_suffix);
# end format_phone;
#
# # select format_phone('[+=12]-(206)-356_5206') as `Formated Phone`;
# #-- ----------------------------------------------------------------------
# drop function if exists Lendit_Book_Kiosk_Test.hexToBin;
# create
# #     definer = alphatoo_dalex@`76.105.120.24`
#     function Lendit_Book_Kiosk_Test.hexToBin(obj varchar(512))
#     returns longblob
#     deterministic -- return datatype is constant or fits within the return datatype
# #non deterministic -- return datatype can change; from, e.g. int to char, etc...
# begin
#     declare cntr varchar(16);
#     declare len int;
#     declare hexToBin varbinary(512) default '';
#     declare start int default 1;
#     declare chunks int;
#     declare position int default 1;
#     declare bin_sum bigint default 0;
#     -- set values
#     set chunks = LENGTH(obj) / 16;
#     #     set hexToBin =  conv(substr(obj,start,16),16,2);
#     -- loop through each chunk and aggregate sum of each chunk
#     while chunks >= 1
#         do
#             -- start loop
#             set cntr = substr(obj, start, 16);
# #         set hexToBin = concat(hexToBin,'0000',cast(cntr as unsigned ));
#             set hexToBin = concat(hexToBin, conv(cntr, 16, 2));
#             set start = start + 16;
#             set chunks = chunks - 1;
#         end while;
#     -- end of loop
#     return hexToBin;
#
# end;
# #-- ----------------------------------------------------------------------
# set @object = '8E1F000B331DD75AA926DDB1000CE0F67787E06E75DE19E0F30FD1250286B626041FEA29057BAB85F2FCF2BA437E7AC73AC537FB63D648929B717B33F06FFD1A8F';
# #-- ----------------------------------------------------------------------
# select length(@object) as 'length of object';
# select length(hexToBin(@object)) as 'length of hexToBin';
# #-- ----------------------------------------------------------------------
##########################################################################
-- #
LOAD DATA INFILE '/mysql_files/Lendit_Book_Kiosk_books.csv'
    INTO TABLE Lendit_Book_Kiosk_Test.book
    FIELDS TERMINATED BY ','
    ENCLOSED BY  '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES (isbn,authors,cover_img,description,genres,language,num_of_pages,publication_date,publisher,rating,series,title)
;
-- #