insert into Lendit_Book_Kiosk.user (user_id, account_verified, dob, email, enabled, failed_login_attempts, gender, login_disabled, name, password, profession, secret, token)
values  (1, false, '1989-01-06', 'jane@gmail.com', true, 0, 'FEMALE', false, 'Jane Doe', 'password', 'Student:Senior', null, null),
        (152, false, '1989-01-05', 'john@gmail.com', true, 0, 'MALE', false, 'John Doe', 'password', 'Faculty:Professor', null, null),
        (153, false, '1979-01-12', 'bob@gmail.com', true, 0, 'MALE', false, 'Bob Doe', 'password', 'Student:Junior', null, null);