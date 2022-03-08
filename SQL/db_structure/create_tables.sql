##########################################################################
drop database if exists Lendit_Book_Kiosk;
CREATE DATABASE Lendit_Book_Kiosk;
# Syntax:
# CREATE USER
#  user_name IDENTIFIED BY [ PASSWORD ] 'password_value';
# CREATE USER 'admin'@'localhost' IDENTIFIED BY 'developer';
# GRANT ALL PRIVILEGES ON admin.* TO 'admin'@'localhost' WITH GRANT OPTION;

use Lendit_Book_Kiosk;
#-- ----------------------------------------------------------------------
# SETTINGS
# set GLOBAL storage_engine='InnoDb';
# SET GLOBAL default_storage_engine = 'InnoDB';
SET NAMES utf8;
SET time_zone = '-05:00';
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
set @private_key = ucase(sha2('Lendit_Book_Kiosk', @key_len));
select @private_key as 'Private Key';
# -- Create public key
# set @public_key = hex(aes_encrypt('Lendit_Book_Kiosk', @private_key));
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
SHOW VARIABLES LIKE 'secure_file_priv'; -- Directory of imported files
#-- ----------------------------------------------------------------------
set @special_char_regex = '[\-\+\(\)\\[\\]\_\=\*\%\^\#\!\&\/\~\'\"\?\>\<\.\,\:\;\@\`]+';
#-- ----------------------------------------------------------------------
#-- ----------------------------------------------------------------------
##########################################################################
# Create FUNCTIONS
##########################################################################
#-- ----------------------------------------------------------------------
drop
    function if exists Lendit_Book_Kiosk.Gen_Uniq_Int;
create
    function Lendit_Book_Kiosk.Gen_Uniq_Int(__text varchar(64))
    returns bigint
    comment '@param: __text => text to be converted'
    deterministic
BEGIN
    return conv(substring(cast(sha2(__text) as char), 1, 16), 16, 10);
END;


#------------------------------------------------------------------------
drop
    function if exists Lendit_Book_Kiosk.Encrypt_Vals;
create
    function Lendit_Book_Kiosk.Encrypt_Vals(object varchar(512), pub_key varchar(255))
    returns text
    comment '@param: object longtext => object must be of string format;'
    comment '@param: pub_key varchar(255) => pub_key must be hex formatted key;'
    deterministic
BEGIN
    if (object is null) then
        return null;
    elseif (object is not null) then
        return hex(aes_encrypt(object, pub_key));
    end if;

END;

# set @encrypt_key = Encrypt_Vals(concat('Jane ', 'Doe ','janedoe@gmail.com [',  1, '] 1979-01-04'),@private_key);
# select length(@encrypt_key) as 'length', @encrypt_key as 'KEY';
#
# select Decrypt_Vals(@encrypt_key,@private_key) as 'original';

#-- ----------------------------------------------------------------------
drop
    function if exists Lendit_Book_Kiosk.Decrypt_Vals;
create

    function Lendit_Book_Kiosk.Decrypt_Vals(object text, pub_key varchar(255))
    returns text
    comment '@param: object text => object must be of hex format;'
    comment '@param: pub_key varchar(255) => pub_key must be hex formatted key;'
    deterministic
BEGIN
    if (object is null) then
        return null;
    end if;
    return aes_decrypt(unhex(object), pub_key);
END;
#-- ----------------------------------------------------------------------
drop function if exists Lendit_Book_Kiosk.get_age;
create
    
    function Lendit_Book_Kiosk.get_age(DOB date)
    returns int
    deterministic
    comment 'DOB Format: [YYYY-mm-dd]'
get_age:
begin
    return year(substr(current_timestamp, 1, 10)) - year(DOB) -
           (right(substr(current_timestamp, 1, 10), 5) < right(DOB, 5));
end get_age;

#-- ----------------------------------------------------------------------
drop function if exists Lendit_Book_Kiosk.no_of_years;
create

    function Lendit_Book_Kiosk.no_of_years(DOB date)
    returns int
    deterministic
no_of_years:
BEGIN
    DECLARE date2 DATE;
    Select current_date() into date2;
    RETURN date(date2) - date(DOB);
END no_of_years;
#-- ----------------------------------------------------------------------

drop function if exists Lendit_Book_Kiosk.format_phone;
create
    function Lendit_Book_Kiosk.format_phone(phone char(32))
    returns char(17)
    deterministic
format_phone:
begin
    -- Phone Number Format: {<country_code>####}-{<area_code>###}-{<local phone number>###-####}
    declare temp_phone varchar(14);
    declare temp_country_code char(4) default '1';
    declare temp_area_code char(3);
    declare temp_local_prefix char(3);
    declare temp_local_suffix char(4);
    -- break phone number into segments
    split_phone_number:
    begin
        -- remove any special characters
        set temp_phone = lpad(regexp_replace(trim(phone), @special_char_regex, ''), 14, 0);
        -- remove leading zeroes
        set temp_country_code = regexp_replace(substr(temp_phone, -14, 4), '^[0]+', '');
        set temp_area_code = substr(temp_phone, -10, 3);
        set temp_local_prefix = substr(temp_phone, -7, 3);
        set temp_local_suffix = substr(temp_phone, -4, 4);
    end split_phone_number;
    -- check if country code is not entered
    check_country_code:
    begin
        if (length(temp_country_code) = 0) then
            set temp_country_code = '1';
        end if;
    end check_country_code;
    return concat(temp_country_code, '-', temp_area_code, '-', temp_local_prefix, '-', temp_local_suffix);
end format_phone;

# select format_phone('[+=12]-(206)-356_5206') as `Formated Phone`;
#-- ----------------------------------------------------------------------
drop function if exists Lendit_Book_Kiosk.hexToBin;
create

    function Lendit_Book_Kiosk.hexToBin(obj varchar(512))
    returns longblob
    deterministic -- return datatype is constant or fits within the return datatype
#non deterministic -- return datatype can change; from, e.g. int to char, etc...
begin
    declare cntr varchar(16);
    declare len int;
    declare hexToBin varbinary(512) default '';
    declare start int default 1;
    declare chunks int;
    declare position int default 1;
    declare bin_sum bigint default 0;
    -- set values
    set chunks = LENGTH(obj) / 16;
    #     set hexToBin =  conv(substr(obj,start,16),16,2);
    -- loop through each chunk and aggregate sum of each chunk
    while chunks >= 1
        do
            -- start loop
            set cntr = substr(obj, start, 16);
#         set hexToBin = concat(hexToBin,'0000',cast(cntr as unsigned ));
            set hexToBin = concat(hexToBin, conv(cntr, 16, 2));
            set start = start + 16;
            set chunks = chunks - 1;
        end while;
    -- end of loop
    return hexToBin;

end;
# #-- ----------------------------------------------------------------------
# set @object = '8E1F000B331DD75AA926DDB1000CE0F67787E06E75DE19E0F30FD1250286B626041FEA29057BAB85F2FCF2BA437E7AC73AC537FB63D648929B717B33F06FFD1A8F';
# #-- ----------------------------------------------------------------------
# select length(@object) as 'length of object';
# select length(hexToBin(@object)) as 'length of hexToBin';
# #-- ----------------------------------------------------------------------
##########################################################################
##########################################################################
# Create Tables
##########################################################################
SET foreign_key_checks = 0;
#-- ----------------------------------------------------------------------

drop table if exists Lendit_Book_Kiosk.User cascade;
create table if not exists Lendit_Book_Kiosk.User
(
    Id               bigint                         not null,
    User_Name         varchar(255)                           not null comment 'dataType: Email Address',
    Name                varchar(255)                           not null,
    DOB                   date                                   not null comment 'Date format: YYYY-mm-dd',
    Majors                text                  null comment 'AKA Organization Name, Group Name',
    Age                   char(3)                             null comment 'char: ###',
    Department            varchar(255) default 'Student'    null comment 'only applies if USER is a faculty member',
    Last_Update_TimeStamp timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint user_pk
        primary key (Id),
    constraint user_username_uindex
        unique (Id,User_Name)

);

verify
drop trigger if exists Lendit_Book_Kiosk.insert_user;
create trigger Lendit_Book_Kiosk.insert_user
    before insert
    on Lendit_Book_Kiosk.User
    for each row
insert_user:
begin
#     set foreign_key_checks = 1;
    set @temp_user_id = ifnull(new.Id,null);
    set @timestamp = current_timestamp();
    set @age = Lendit_Book_Kiosk.get_age(new.DOB);
    -- create hash
    create_hash:
        begin
        select Lendit_Book_Kiosk.Encrypt_Vals(concat(new.User_Name,'-',new.DOB), @private_key)
                        into @temp_user_id;
    end create_hash;
    set new.Id = @temp_user_id;
    set new.Last_Update_TimeStamp = @timestamp;
    set new.Age = @age;
end insert_user;


drop trigger if exists Lendit_Book_Kiosk.update_user_email;
create trigger Lendit_Book_Kiosk.update_user_email
    after insert
    on Lendit_Book_Kiosk.User
    for each row
update_user:
begin
    declare temp_email varchar(255);
    insert_email_user_name:
    begin
        -- check Lendit_Book_Kiosk.Email
        select Email
        from Lendit_Book_Kiosk.Email
        where Lendit_Book_Kiosk.Email.Email like new.User_Name
        into temp_email;
        if (ifnull(temp_email, null) is null) then
            insert into Lendit_Book_Kiosk.Email(Email) values (new.User_Name);
        end if;
        -- check Lendit_Book_Kiosk.User_Email
        select Email
        from Lendit_Book_Kiosk.User_Email
        where Lendit_Book_Kiosk.User_Email.Email like new.User_Name
        into temp_email;
        if (ifnull(temp_email, null) is null) then -- the email does not exist in the db
            insert into Lendit_Book_Kiosk.User_Email(User_ID, Email) values (new.User_ID, new.User_Name);
        end if;
    end insert_email_user_name;
end update_user;


# INSERT INTO Lendit_Book_Kiosk.User(User_Name, FirstName, LastName, MailingList, DOB)
#      VALUES ('janedoe@gmail.com', 'Jane', 'Doe', '1',
#              '1979-01-03');
#-- ----------------------------------------------------------------------
# drop table if exists Lendit_Book_Kiosk.Type cascade;
# create table if not exists Lendit_Book_Kiosk.Type
# (
#     Type_Name   varchar(128) not null,
#     Description varchar(255) null,
#     constraint type_pk
#         primary key (Type_Name),
#     constraint type_uindex
#         unique index (Type_Name)
#
# ) comment 'Used for Phone and Email; e.g. Home, Work';
#
# drop trigger if exists Lendit_Book_Kiosk.insert_type;
# create trigger Lendit_Book_Kiosk.insert_type
#     before insert
#     on Lendit_Book_Kiosk.Type
#     for each row
# insert_type:
# begin
#     declare temp_type varchar(128);
#     set new.Type_Name = ucase(trim(new.Type_Name));
#     set new.Description = ucase(trim(new.Description));
#     select Type_Name
#     from Lendit_Book_Kiosk.Type
#     where Lendit_Book_Kiosk.Type.Type_Name like new.Type_Name
#     into temp_type;
#     if (ifnull(temp_type, null) is not null) then
#         leave insert_type;
#     end if;
# end insert_type;

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Email cascade;
create table if not exists Lendit_Book_Kiosk.Email
(
    Email varchar(255) not null,
    constraint email_pk
        primary key (Email),
    constraint email_uindex
        unique index (Email)
);

drop trigger if exists Lendit_Book_Kiosk.insert_email;
create
    trigger Lendit_Book_Kiosk.insert_email
    before insert
    on Lendit_Book_Kiosk.Email
    for each row
insert_email:
begin
    declare temp_email varchar(255);
    set new.Email = trim(new.Email);
end insert_email;
#-- ----------------------------------------------------------------------
# drop table if exists Lendit_Book_Kiosk.Zipcode;
# create table if not exists Lendit_Book_Kiosk.Zipcode
# (
#     Zipcode            char(10)     not null,
#     Latitude           double       null,
#     Longitude          double       null,
#     City               varchar(128) not null,
#     State_ID           char(2)      not null,
#     State_Name         varchar(64)  not null,
#     County_Fips        char(5)      not null,
#     County_Name        varchar(64)  not null,
#     All_County_Weights varchar(255) null,
#     Timezone           varchar(45)  not null,
#     constraint zipcode_pk
#     primary key (Zipcode),
#     constraint zipcode_uindex
#         unique (Zipcode, City, State_ID, State_Name, Timezone, County_Name)
# );

#-- ----------------------------------------------------------------------
# drop table if exists Lendit_Book_Kiosk.Addresses;
# create table if not exists Lendit_Book_Kiosk.Addresses
# (
#     Address_ID varchar(255)                        not null,
#     Type       char(16)  default 'HOME'            not null,
#     Line_1     varchar(255)                        not null,
#     Line_2     varchar(255)                        null,
#     Zipcode    char(10)                            not null,
#     City       varchar(64)                         null,
#     State_ID   char(2)                             null,
#     State_Name varchar(64)                         null,
#     Timezone   varchar(45)                         null,
#     TimeStamp  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
#     constraint addresses_pk
#         primary key (Address_ID),
#     constraint addresses_uindex_address
#         unique (Type, Line_1, Line_2, Zipcode),
#     constraint addresses_zipcode_fk
#         foreign key (Zipcode, City, State_ID, State_Name, Timezone)
#             references Lendit_Book_Kiosk.Zipcode (Zipcode, City, State_Id, State_Name, Timezone)
#             on update no action on delete no action
# #     constraint addresses_type_fk
# #         foreign key (Type)
# #             references Lendit_Book_Kiosk.Type (Type_Name)
# #             on update no action on delete no action
# );
#
#
# drop trigger if exists Lendit_Book_Kiosk.insert_addresses_address_id;
# create trigger Lendit_Book_Kiosk.insert_addresses_address_id
#     before insert
#     on Lendit_Book_Kiosk.Addresses
#     for each row
# insert_addresses_address_id:
# begin
#     set @temp_city = ifnull(new.City, '0');
#     set @temp_state_id = ifnull(new.State_ID, '0');
#     set @temp_state_name = ifnull(new.State_Name, '0');
#     set @temp_timezone = ifnull(new.Timezone, '0');
#     set @temp_address_id = ifnull(new.Address_ID, null);
#     set new.Type = ucase(trim(new.Type));
#     get_zipcode_info: -- check zipcode and get city, state, and timezone
#     begin
#         select City, State_ID, State_Name, Timezone
#         from Lendit_Book_Kiosk.Zipcode
#         where Lendit_Book_Kiosk.Zipcode.Zipcode like new.Zipcode
#         into @temp_city, @temp_state_id, @temp_state_name, @temp_timezone;
#     end get_zipcode_info;
#     -- In case line_2 is null assign N/A
#     if (new.Line_2 is null) then
#         set new.Line_2 = 'N/A';
#     end if;
#     set new.City = @temp_city;
#     set new.State_ID = @temp_state_id;
#     set new.State_Name = @temp_state_name;
#     set new.Timezone = @temp_timezone;
#     set new.TimeStamp = current_timestamp();
#     -- Create hash
#     select Lendit_Book_Kiosk.Encrypt_Vals(concat(new.Type,';', new.Line_1,';', new.Line_2,';', new.Zipcode,';', new.Timezone),
#                    @private_key) into @temp_address_id;
#     set new.Address_ID = @temp_address_id;
#     insert_type: -- check and add new type if needed
#     begin
#         set @temp_type = (select Type_Name
#                           from Lendit_Book_Kiosk.Type
#                           where Lendit_Book_Kiosk.Type.Type_Name like new.Type);
#         if (ifnull(@temp_type, null) is null) then
#             insert into Lendit_Book_Kiosk.Type(Type_Name, Description)
#             values (new.Type, new.Type);
#         end if;
#     end insert_type;
# end insert_addresses_address_id;
#
# #
# # drop trigger if exists Lendit_Book_Kiosk.insert_addresses_address_id_hash;
# # create
# #     trigger Lendit_Book_Kiosk.insert_addresses_address_id_hash
# #     after insert
# #     on Lendit_Book_Kiosk.Addresses
# #     for each row
# # insert_addresses_address_id_hash:
# # begin
# #     set @temp_address_id = ifnull(Lendit_Book_Kiosk.Addresses.Address_ID, null);
# #     if (@temp_address_id is null) then
# #         set @temp_address_id = Lendit_Book_Kiosk.Encrypt_Vals(concat(Lendit_Book_Kiosk.Addresses.Type, '-',
# #             Lendit_Book_Kiosk.Addresses.Line_1, '-', Lendit_Book_Kiosk.Addresses.Line_2, '-',
# #             Lendit_Book_Kiosk.Addresses.Zipcode, '-', Lendit_Book_Kiosk.Addresses.Timezone, '-', Lendit_Book_Kiosk.Addresses.TimeStamp), @private_key);
# # #         set @temp_address_id = Lendit_Book_Kiosk.Encrypt_Vals(concat(Type, '-',
# # #             Line_1, '-', Line_2, '-',
# # #             Zipcode, '-', Timezone, '-', TimeStamp), @private_key);
# #         update Lendit_Book_Kiosk.Addresses
# #             set Lendit_Book_Kiosk.Addresses.Address_ID = @temp_address_id
# #             where Lendit_Book_Kiosk.Addresses.ID = new.ID;
# #     end if;
# # end insert_addresses_address_id_hash;
# # set @hash_val = (select Lendit_Book_Kiosk.Encrypt_Vals(concat('Home '), @private_key));
# # select @hash_val;
# # INSERT INTO Lendit_Book_Kiosk.Addresses(Address_ID,Type, Line_1, Line_2, Zipcode, City, State_ID, State_Name)
# #     VALUES (null,'Home', '2650 Johns Creek Rd', null, 30094, null, null, null);

#-- ----------------------------------------------------------------------


drop table if exists Lendit_Book_Kiosk.User_Email cascade;
create table if not exists Lendit_Book_Kiosk.User_Email
(
    User_ID long                not null,
    Email   varchar(255)                not null,
#     primary key (User_ID, Email),
    constraint User_Email_uindex
        unique (User_ID, Email),
    constraint email_user_email_fk
        foreign key (Email) references Lendit_Book_Kiosk.Email (Email)
            on update cascade on delete restrict,
    constraint user_user_email_fk
        foreign key (User_ID, Email) references Lendit_Book_Kiosk.User (User_ID, User_Name)
            on update cascade on delete cascade
);


drop trigger if exists Lendit_Book_Kiosk.insert_user_email;
create trigger Lendit_Book_Kiosk.insert_user_email
    after insert
    on Lendit_Book_Kiosk.User_Email
    for each row
insert_user_email:
begin
    declare temp_email varchar(255) default '0';
    select Email from Lendit_Book_Kiosk.Email where Lendit_Book_Kiosk.Email.Email like new.Email into temp_email;

    if (ifnull(temp_email, null) is null) then
        insert into Lendit_Book_Kiosk.Email(Email) VALUES (new.Email);
    end if;
end insert_user_email;

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.User_Phone cascade;
create table if not exists Lendit_Book_Kiosk.User_Phone
(
    User_ID     long  not null,
    PhoneNumber char(17)                    not null comment '{<country_code>####}-{<area_code>###}-{<local phone number>###-####}',
    Type        varchar(128) default 'Home' not null,
    primary key (User_ID, PhoneNumber),
    constraint User_Phone_uindex
        unique (User_ID, PhoneNumber),
    constraint phone_user_phone_fk
        foreign key (PhoneNumber, Type)
            references Lendit_Book_Kiosk.Phone (PhoneNumber, Type)
            on update cascade on delete cascade,
    constraint user_user_phone_fk
        foreign key (User_ID)
            references Lendit_Book_Kiosk.User (User_ID)
            on update no action on delete no action
);

drop trigger if exists Lendit_Book_Kiosk.insert_user_phone;
create trigger Lendit_Book_Kiosk.insert_user_phone
    before insert
    on Lendit_Book_Kiosk.User_Phone
    for each row
insert_user_phone:
begin
    declare temp_type varchar(128);
    declare temp_phone varchar(17);
    declare temp_user_id varchar(255);
    set new.Type = ucase(trim(new.Type));
    set new.PhoneNumber = Lendit_Book_Kiosk.format_phone(new.PhoneNumber);
    -- check User_ID constraint: user must exist in order to add user phone number
    select User_ID
    from Lendit_Book_Kiosk.User
    where Lendit_Book_Kiosk.User.User_ID like new.User_ID
    into temp_user_id;
    if (ifnull(temp_user_id, null) is null) then
        insert_phone:
        begin
            select PhoneNumber
            from Lendit_Book_Kiosk.Phone
            where Lendit_Book_Kiosk.Phone.PhoneNumber like new.PhoneNumber
            into temp_phone;
            if (ifnull(temp_phone, null) is null) then
                insert into Lendit_Book_Kiosk.Phone(PhoneNumber, Type) values (new.PhoneNumber, new.Type);
            end if;
            #         leave insert_phone;
        end insert_phone;
    end if;

end insert_user_phone;

# select ifnull((select PhoneNumber from Lendit_Book_Kiosk.Phone
# where Lendit_Book_Kiosk.Phone.PhoneNumber like '678-650-9414'), 0) as 'Results';

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Day cascade;
create table if not exists Lendit_Book_Kiosk.Day
(
    Date                  varchar(16)    not null,
    Day_Number            int            null,
    Day_Name              char(16)       null,
    Day_Num_In_Year       int            null,
    Total_Available_Hours decimal(19, 2) null,
    Week_Start_Day        char(16)       null,
    Hours_Scheduled       decimal(19, 2) null,
    primary key (Date)
);

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Month cascade;
create table if not exists Lendit_Book_Kiosk.Month
(
    Month           int            not null,
    Total_Hours     decimal(19, 2) not null,
    Hours_Scheduled decimal(19, 2) not null,
    Month_Name      char(16)       not null,
    primary key (Month)
);
#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Services cascade;
create table if not exists Lendit_Book_Kiosk.Services
(
    Service_Type varchar(255) not null,
    Description  varchar(255) not null,
    primary key (Service_Type),
    constraint Service_Type
        unique (Service_Type)
);

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Time cascade;
create table if not exists Lendit_Book_Kiosk.Time
(
    Time time default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    TimeZone  char(32)                            null,
    primary key (Time)
);

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Year;
create table if not exists Lendit_Book_Kiosk.Year
(
    Year int not null,
    primary key (Year)
);
#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Calendar;
create table if not exists Lendit_Book_Kiosk.Calendar
(
    Year  int          not null,
    Month int          not null,
    Day   varchar(255) not null,
    Time  time          not null,
    primary key (Year, Month, Day, Time),
    constraint Day_Calendar_FK
        foreign key (Day) references Lendit_Book_Kiosk.Day (Date),
    constraint Month_Calendar_FK
        foreign key (Month) references Lendit_Book_Kiosk.Month (Month),
    constraint Time_Calendar_FK
        foreign key (Time) references Lendit_Book_Kiosk.Time (Time) ,
    constraint Year_Calendar_FK
        foreign key (Year) references Lendit_Book_Kiosk.Year (Year)
);

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Phone cascade;
create table if not exists Lendit_Book_Kiosk.Phone
(
    PhoneNumber char(17)                    not null comment '{<country_code>####}-{<area_code>###}-{<local phone number>###-####}',
    Type        varchar(128) default 'HOME' null,
    primary key (PhoneNumber),
    constraint PhoneNumber
        unique (PhoneNumber)
)
    comment ' {<country_code>####}-{<area_code>###}-{<local phone number>###-####}'
;

drop trigger if exists Lendit_Book_Kiosk.insert_phonenumber;
create
    trigger Lendit_Book_Kiosk.insert_phonenumber
    before insert
    on Lendit_Book_Kiosk.Phone
    for each row
insert_phonenumber:
begin
    # Phone Number Format: {<country_code>####}-{<area_code>###}-{<local phone number>###-####}
    declare temp_phone char(17);
    declare temp_type varchar(128);
    set new.PhoneNumber = Lendit_Book_Kiosk.format_phone(new.PhoneNumber);
    set new.Type = ucase(trim(new.Type));
    #
    insert_phone:
    begin
        select PhoneNumber
        from Lendit_Book_Kiosk.Phone
        where Lendit_Book_Kiosk.Phone.PhoneNumber like new.PhoneNumber
        into temp_phone;
        if (ifnull(temp_phone, null) is not null) then -- if phone exists leave trigger insert_phonenumber
            leave insert_phonenumber;
        end if;
    end insert_phone;
    #
end insert_phonenumber;

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Blog cascade;
create table if not exists Lendit_Book_Kiosk.Blog
(
    Blog_Id   varchar(255) default '00'              not null,
    User_ID   varchar(255) default '00'              not null,
    Date      date                                   not null,
    Time      time                                   not null,
    Message   blob                                   not null,
    TimeStamp datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (Blog_Id),
    constraint Blog_uindex
        unique key (Blog_Id),
    constraint Blog_User_ID_FK
        foreign key (User_ID) references Lendit_Book_Kiosk.User (User_ID)
);

drop trigger if exists Lendit_Book_Kiosk.insert_blog_id;
create trigger Lendit_Book_Kiosk.insert_blog_id
    before insert
    on Lendit_Book_Kiosk.Blog
    for each row
insert_blog_id:
begin
    set new.TimeStamp = now();
    set new.Date = substr(current_timestamp, 1, 10);
    set new.Time = substr(current_timestamp, 12, 8);
    set new.Blog_Id = Encrypt_Vals(concat(new.User_ID, ' ', new.TimeStamp), @private_key);
end insert_blog_id;

#-- ----------------------------------------------------------------------
drop table if exists Lendit_Book_Kiosk.Password cascade;
create table if not exists Lendit_Book_Kiosk.Password
(
    Passwd_ID varchar(255) default '00' not null,
    User_ID   varchar(255) default '00' not null,
    Password  varchar(255) default '00' not null,
    primary key (Passwd_ID),
    constraint Passwd_ID
        unique (Passwd_ID),
    constraint User_ID_FK
        foreign key (User_ID) references Lendit_Book_Kiosk.User (User_ID)
);


drop trigger if exists Lendit_Book_Kiosk.insert_passwd_id;
create trigger Lendit_Book_Kiosk.insert_passwd_id
    before insert
    on Lendit_Book_Kiosk.Password
    for each row
insert_passwd_id:
begin
    set new.Passwd_ID = Encrypt_Vals(concat(new.User_ID, ' ', new.Password), @private_key);
end insert_passwd_id;
