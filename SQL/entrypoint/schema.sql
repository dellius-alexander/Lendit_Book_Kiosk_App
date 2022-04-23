
    create table book (
       isbn VARCHAR(224) not null,
        authors varchar(255),
        cover_img varchar(255),
        description varchar(255),
        genres varchar(255),
        language varchar(255),
        num_of_pages bigint,
        publication_date date,
        publisher varchar(255),
        rating double precision,
        series varchar(255),
        title varchar(255),
        primary key (isbn)
    ) engine=InnoDB;

    create table book_copy (
       copy_id bigint not null,
        isbn VARCHAR(224) not null,
        primary key (copy_id)
    ) engine=InnoDB;

    create table borrow_book (
       borrow_id bigint not null,
        checkin_timestamp datetime(6),
        checkout_timestamp datetime(6),
        copy_id bigint,
        duration double precision,
        isbn varchar(255),
        slot_id bigint,
        student_id bigint,
        primary key (borrow_id)
    ) engine=InnoDB;

    create table donated_book (
       id bigint not null,
        doner_name varchar(255),
        estimated_value double precision,
        isbn VARCHAR(224) not null,
        primary key (id)
    ) engine=InnoDB;

    create table email_notification (
       email_id bigint not null,
        student_id bigint,
        isbn varchar(255),
        borrow_id bigint,
        slot_id bigint,
        copy_id bigint,
        notification_id bigint,
        message varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (email_id)
    ) engine=InnoDB;

    create table employee (
       employee_id bigint not null,
        job_title varchar(255),
        primary key (employee_id)
    ) engine=InnoDB;

    create table fines (
       fine_id bigint not null,
        amount_paid_year_to_date double precision,
        balance double precision,
        fine double precision,
        paid_in_full bit not null,
        borrow_id bigint,
        primary key (fine_id)
    ) engine=InnoDB;

    create table kiosk (
       slot_id bigint not null,
        copy_id bigint,
        primary key (slot_id)
    ) engine=InnoDB;

    create table lendit_book_kiosk_db_sequence_generator (
       next_val bigint
    ) engine=InnoDB;

    insert into lendit_book_kiosk_db_sequence_generator values ( 1 );

    create table major (
       major_id bigint not null,
        major varchar(224),
        primary key (major_id)
    ) engine=InnoDB;

    create table notification (
       notification_id bigint not null,
        message varchar(255),
        type varchar(255),
        primary key (notification_id)
    ) engine=InnoDB;

    create table reserve_book (
       id bigint not null,
        copy_id bigint,
        student_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table role (
       role_id bigint not null,
        description varchar(255),
        name varchar(224),
        primary key (role_id)
    ) engine=InnoDB;

    create table secret (
       user_id bigint not null,
        is_initialized bit(1),
        password varchar(255),
        secret_key varbinary(2048),
        primary key (user_id)
    ) engine=InnoDB;

    create table student (
       student_id bigint not null,
        enrolled bit not null,
        primary key (student_id)
    ) engine=InnoDB;

    create table student_majors (
       student_id_fk bigint not null,
        major_id_fk bigint not null,
        primary key (student_id_fk, major_id_fk)
    ) engine=InnoDB;

    create table user (
       user_id bigint not null,
        dob date,
        email varchar(224) not null,
        gender varchar(8),
        name varchar(255),
        profession varchar(255),
        password varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_employee (
       user_id bigint not null,
        employee_id bigint not null,
        primary key (employee_id, user_id)
    ) engine=InnoDB;

    create table user_roles (
       user_id_fk bigint not null,
        role_id_fk bigint not null,
        primary key (user_id_fk, role_id_fk)
    ) engine=InnoDB;

    create table user_student (
       user_id bigint not null,
        student_id bigint not null,
        primary key (student_id, user_id)
    ) engine=InnoDB;

    alter table borrow_book 
       add constraint UK_708yjrehwaq3lskwm0q7fwdx6 unique (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table notification 
       add constraint UK_b0burp8mj0fsxw5qyf4mgqln8 unique (notification_id, message, type);

    alter table role 
       add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

    alter table secret 
       add constraint UK_rt6t6ftywcvilh3tg2tr2cfy unique (password);

    alter table user 
       add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table book_copy 
       add constraint FK97ooaf6exwl2n7hfx40gt3mjk 
       foreign key (isbn) 
       references book (isbn);

    alter table borrow_book 
       add constraint FKeqkom0kiex3pcen6meu2o0o16 
       foreign key (slot_id) 
       references kiosk (slot_id);

    alter table borrow_book 
       add constraint FKt7r6tjmuksiv3ec3wkvuveusd 
       foreign key (student_id) 
       references student (student_id);

    alter table donated_book 
       add constraint FK2v6ch7nfj0m4xn9aut8ux63ik 
       foreign key (isbn) 
       references book (isbn);

    alter table email_notification 
       add constraint FKtbbh2uqadw1avmcushtcwrqqk 
       foreign key (student_id, isbn, borrow_id, slot_id, copy_id) 
       references borrow_book (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table email_notification 
       add constraint FKg9k02sov1hj8emgasdbtjriol 
       foreign key (notification_id, message, type) 
       references notification (notification_id, message, type);

    alter table email_notification 
       add constraint FKjf5e3oto951b3tbs6f258cttl 
       foreign key (user_id) 
       references user (user_id);

    alter table fines 
       add constraint FK1144txfgfmf85khhw3ggqiedn 
       foreign key (borrow_id) 
       references borrow_book (borrow_id);

    alter table kiosk 
       add constraint FKtm3fs6jbuiyycl3mvccgcic7x 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK1rhjxgmp4r5m5tbpm80asj76y 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK94ouboffq9ex50su72d2jol5x 
       foreign key (student_id) 
       references student (student_id);

    alter table student_majors 
       add constraint FKrd5wa8yarsol7r2wy7xj7i2xu 
       foreign key (major_id_fk) 
       references major (major_id);

    alter table student_majors 
       add constraint FKo092rklfgjsxbxj39wyjuj69t 
       foreign key (student_id_fk) 
       references student (student_id);

    alter table user 
       add constraint FKmjbs7hnl0lfdc8a1726o3gk42 
       foreign key (password) 
       references secret (password);

    alter table user_employee 
       add constraint FK2ggb0ns4j04r0v1lvvmsyif2g 
       foreign key (employee_id) 
       references employee (employee_id);

    alter table user_employee 
       add constraint FK935rscnqblbm9v9emac8a8h7t 
       foreign key (user_id) 
       references user (user_id);

    alter table user_roles 
       add constraint FKrrs4hf2akix4fah7g6fgx5c3b 
       foreign key (role_id_fk) 
       references role (role_id);

    alter table user_roles 
       add constraint FKjcqwtqywj0ny5jfwhqt028826 
       foreign key (user_id_fk) 
       references user (user_id);

    alter table user_student 
       add constraint FKdhtkb6gflgexqi53tu0tyh18m 
       foreign key (student_id) 
       references student (student_id);

    alter table user_student 
       add constraint FK7guxh0ti8xeodx27r9svim5fk 
       foreign key (user_id) 
       references user (user_id);

    create table book (
       isbn VARCHAR(224) not null,
        authors varchar(255),
        cover_img varchar(255),
        description varchar(255),
        genres varchar(255),
        language varchar(255),
        num_of_pages bigint,
        publication_date date,
        publisher varchar(255),
        rating double precision,
        series varchar(255),
        title varchar(255),
        primary key (isbn)
    ) engine=InnoDB;

    create table book_copy (
       copy_id bigint not null,
        isbn VARCHAR(224) not null,
        primary key (copy_id)
    ) engine=InnoDB;

    create table borrow_book (
       borrow_id bigint not null,
        checkin_timestamp datetime(6),
        checkout_timestamp datetime(6),
        copy_id bigint,
        duration double precision,
        isbn varchar(255),
        slot_id bigint,
        student_id bigint,
        primary key (borrow_id)
    ) engine=InnoDB;

    create table donated_book (
       id bigint not null,
        doner_name varchar(255),
        estimated_value double precision,
        isbn VARCHAR(224) not null,
        primary key (id)
    ) engine=InnoDB;

    create table email_notification (
       email_id bigint not null,
        student_id bigint,
        isbn varchar(255),
        borrow_id bigint,
        slot_id bigint,
        copy_id bigint,
        notification_id bigint,
        message varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (email_id)
    ) engine=InnoDB;

    create table employee (
       employee_id bigint not null,
        job_title varchar(255),
        primary key (employee_id)
    ) engine=InnoDB;

    create table fines (
       fine_id bigint not null,
        amount_paid_year_to_date double precision,
        balance double precision,
        fine double precision,
        paid_in_full bit not null,
        borrow_id bigint,
        primary key (fine_id)
    ) engine=InnoDB;

    create table kiosk (
       slot_id bigint not null,
        copy_id bigint,
        primary key (slot_id)
    ) engine=InnoDB;

    create table lendit_book_kiosk_db_sequence_generator (
       next_val bigint
    ) engine=InnoDB;

    insert into lendit_book_kiosk_db_sequence_generator values ( 1 );

    create table major (
       major_id bigint not null,
        major varchar(224),
        primary key (major_id)
    ) engine=InnoDB;

    create table notification (
       notification_id bigint not null,
        message varchar(255),
        type varchar(255),
        primary key (notification_id)
    ) engine=InnoDB;

    create table reserve_book (
       id bigint not null,
        copy_id bigint,
        student_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table role (
       role_id bigint not null,
        description varchar(255),
        name varchar(224),
        primary key (role_id)
    ) engine=InnoDB;

    create table secret (
       user_id bigint not null,
        is_initialized bit(1),
        password varchar(255),
        secret_key varbinary(2048),
        primary key (user_id)
    ) engine=InnoDB;

    create table student (
       student_id bigint not null,
        enrolled bit not null,
        primary key (student_id)
    ) engine=InnoDB;

    create table student_majors (
       student_id_fk bigint not null,
        major_id_fk bigint not null,
        primary key (student_id_fk, major_id_fk)
    ) engine=InnoDB;

    create table user (
       user_id bigint not null,
        dob date,
        email varchar(224) not null,
        gender varchar(8),
        name varchar(255),
        profession varchar(255),
        password varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_employee (
       user_id bigint not null,
        employee_id bigint not null,
        primary key (employee_id, user_id)
    ) engine=InnoDB;

    create table user_roles (
       user_id_fk bigint not null,
        role_id_fk bigint not null,
        primary key (user_id_fk, role_id_fk)
    ) engine=InnoDB;

    create table user_student (
       user_id bigint not null,
        student_id bigint not null,
        primary key (student_id, user_id)
    ) engine=InnoDB;

    alter table borrow_book 
       add constraint UK_708yjrehwaq3lskwm0q7fwdx6 unique (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table notification 
       add constraint UK_b0burp8mj0fsxw5qyf4mgqln8 unique (notification_id, message, type);

    alter table role 
       add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

    alter table secret 
       add constraint UK_rt6t6ftywcvilh3tg2tr2cfy unique (password);

    alter table user 
       add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table book_copy 
       add constraint FK97ooaf6exwl2n7hfx40gt3mjk 
       foreign key (isbn) 
       references book (isbn);

    alter table borrow_book 
       add constraint FKeqkom0kiex3pcen6meu2o0o16 
       foreign key (slot_id) 
       references kiosk (slot_id);

    alter table borrow_book 
       add constraint FKt7r6tjmuksiv3ec3wkvuveusd 
       foreign key (student_id) 
       references student (student_id);

    alter table donated_book 
       add constraint FK2v6ch7nfj0m4xn9aut8ux63ik 
       foreign key (isbn) 
       references book (isbn);

    alter table email_notification 
       add constraint FKtbbh2uqadw1avmcushtcwrqqk 
       foreign key (student_id, isbn, borrow_id, slot_id, copy_id) 
       references borrow_book (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table email_notification 
       add constraint FKg9k02sov1hj8emgasdbtjriol 
       foreign key (notification_id, message, type) 
       references notification (notification_id, message, type);

    alter table email_notification 
       add constraint FKjf5e3oto951b3tbs6f258cttl 
       foreign key (user_id) 
       references user (user_id);

    alter table fines 
       add constraint FK1144txfgfmf85khhw3ggqiedn 
       foreign key (borrow_id) 
       references borrow_book (borrow_id);

    alter table kiosk 
       add constraint FKtm3fs6jbuiyycl3mvccgcic7x 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK1rhjxgmp4r5m5tbpm80asj76y 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK94ouboffq9ex50su72d2jol5x 
       foreign key (student_id) 
       references student (student_id);

    alter table student_majors 
       add constraint FKrd5wa8yarsol7r2wy7xj7i2xu 
       foreign key (major_id_fk) 
       references major (major_id);

    alter table student_majors 
       add constraint FKo092rklfgjsxbxj39wyjuj69t 
       foreign key (student_id_fk) 
       references student (student_id);

    alter table user 
       add constraint FKmjbs7hnl0lfdc8a1726o3gk42 
       foreign key (password) 
       references secret (password);

    alter table user_employee 
       add constraint FK2ggb0ns4j04r0v1lvvmsyif2g 
       foreign key (employee_id) 
       references employee (employee_id);

    alter table user_employee 
       add constraint FK935rscnqblbm9v9emac8a8h7t 
       foreign key (user_id) 
       references user (user_id);

    alter table user_roles 
       add constraint FKrrs4hf2akix4fah7g6fgx5c3b 
       foreign key (role_id_fk) 
       references role (role_id);

    alter table user_roles 
       add constraint FKjcqwtqywj0ny5jfwhqt028826 
       foreign key (user_id_fk) 
       references user (user_id);

    alter table user_student 
       add constraint FKdhtkb6gflgexqi53tu0tyh18m 
       foreign key (student_id) 
       references student (student_id);

    alter table user_student 
       add constraint FK7guxh0ti8xeodx27r9svim5fk 
       foreign key (user_id) 
       references user (user_id);

    create table book (
       isbn VARCHAR(224) not null,
        authors varchar(255),
        cover_img varchar(255),
        description varchar(255),
        genres varchar(255),
        language varchar(255),
        num_of_pages bigint,
        publication_date date,
        publisher varchar(255),
        rating double precision,
        series varchar(255),
        title varchar(255),
        primary key (isbn)
    ) engine=InnoDB;

    create table book_copy (
       copy_id bigint not null,
        isbn VARCHAR(224) not null,
        primary key (copy_id)
    ) engine=InnoDB;

    create table borrow_book (
       borrow_id bigint not null,
        checkin_timestamp datetime(6),
        checkout_timestamp datetime(6),
        copy_id bigint,
        duration double precision,
        isbn varchar(255),
        slot_id bigint,
        student_id bigint,
        primary key (borrow_id)
    ) engine=InnoDB;

    create table donated_book (
       id bigint not null,
        doner_name varchar(255),
        estimated_value double precision,
        isbn VARCHAR(224) not null,
        primary key (id)
    ) engine=InnoDB;

    create table email_notification (
       email_id bigint not null,
        student_id bigint,
        isbn varchar(255),
        borrow_id bigint,
        slot_id bigint,
        copy_id bigint,
        notification_id bigint,
        message varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (email_id)
    ) engine=InnoDB;

    create table employee (
       employee_id bigint not null,
        job_title varchar(255),
        primary key (employee_id)
    ) engine=InnoDB;

    create table fines (
       fine_id bigint not null,
        amount_paid_year_to_date double precision,
        balance double precision,
        fine double precision,
        paid_in_full bit not null,
        borrow_id bigint,
        primary key (fine_id)
    ) engine=InnoDB;

    create table kiosk (
       slot_id bigint not null,
        copy_id bigint,
        primary key (slot_id)
    ) engine=InnoDB;

    create table lendit_book_kiosk_db_sequence_generator (
       next_val bigint
    ) engine=InnoDB;

    insert into lendit_book_kiosk_db_sequence_generator values ( 1 );

    create table major (
       major_id bigint not null,
        major varchar(224),
        primary key (major_id)
    ) engine=InnoDB;

    create table notification (
       notification_id bigint not null,
        message varchar(255),
        type varchar(255),
        primary key (notification_id)
    ) engine=InnoDB;

    create table reserve_book (
       id bigint not null,
        copy_id bigint,
        student_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table role (
       role_id bigint not null,
        description varchar(255),
        name varchar(224),
        primary key (role_id)
    ) engine=InnoDB;

    create table secret (
       user_id bigint not null,
        is_initialized bit(1),
        password varchar(255),
        secret_key varbinary(2048),
        primary key (user_id)
    ) engine=InnoDB;

    create table student (
       student_id bigint not null,
        enrolled bit not null,
        primary key (student_id)
    ) engine=InnoDB;

    create table student_majors (
       student_id_fk bigint not null,
        major_id_fk bigint not null,
        primary key (student_id_fk, major_id_fk)
    ) engine=InnoDB;

    create table user (
       user_id bigint not null,
        dob date,
        email varchar(224) not null,
        gender varchar(8),
        name varchar(255),
        profession varchar(255),
        password varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_employee (
       user_id bigint not null,
        employee_id bigint not null,
        primary key (employee_id, user_id)
    ) engine=InnoDB;

    create table user_roles (
       user_id_fk bigint not null,
        role_id_fk bigint not null,
        primary key (user_id_fk, role_id_fk)
    ) engine=InnoDB;

    create table user_student (
       user_id bigint not null,
        student_id bigint not null,
        primary key (student_id, user_id)
    ) engine=InnoDB;

    alter table borrow_book 
       add constraint UK_708yjrehwaq3lskwm0q7fwdx6 unique (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table notification 
       add constraint UK_b0burp8mj0fsxw5qyf4mgqln8 unique (notification_id, message, type);

    alter table role 
       add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

    alter table secret 
       add constraint UK_rt6t6ftywcvilh3tg2tr2cfy unique (password);

    alter table user 
       add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table book_copy 
       add constraint FK97ooaf6exwl2n7hfx40gt3mjk 
       foreign key (isbn) 
       references book (isbn);

    alter table borrow_book 
       add constraint FKeqkom0kiex3pcen6meu2o0o16 
       foreign key (slot_id) 
       references kiosk (slot_id);

    alter table borrow_book 
       add constraint FKt7r6tjmuksiv3ec3wkvuveusd 
       foreign key (student_id) 
       references student (student_id);

    alter table donated_book 
       add constraint FK2v6ch7nfj0m4xn9aut8ux63ik 
       foreign key (isbn) 
       references book (isbn);

    alter table email_notification 
       add constraint FKtbbh2uqadw1avmcushtcwrqqk 
       foreign key (student_id, isbn, borrow_id, slot_id, copy_id) 
       references borrow_book (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table email_notification 
       add constraint FKg9k02sov1hj8emgasdbtjriol 
       foreign key (notification_id, message, type) 
       references notification (notification_id, message, type);

    alter table email_notification 
       add constraint FKjf5e3oto951b3tbs6f258cttl 
       foreign key (user_id) 
       references user (user_id);

    alter table fines 
       add constraint FK1144txfgfmf85khhw3ggqiedn 
       foreign key (borrow_id) 
       references borrow_book (borrow_id);

    alter table kiosk 
       add constraint FKtm3fs6jbuiyycl3mvccgcic7x 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK1rhjxgmp4r5m5tbpm80asj76y 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK94ouboffq9ex50su72d2jol5x 
       foreign key (student_id) 
       references student (student_id);

    alter table student_majors 
       add constraint FKrd5wa8yarsol7r2wy7xj7i2xu 
       foreign key (major_id_fk) 
       references major (major_id);

    alter table student_majors 
       add constraint FKo092rklfgjsxbxj39wyjuj69t 
       foreign key (student_id_fk) 
       references student (student_id);

    alter table user 
       add constraint FKmjbs7hnl0lfdc8a1726o3gk42 
       foreign key (password) 
       references secret (password);

    alter table user_employee 
       add constraint FK2ggb0ns4j04r0v1lvvmsyif2g 
       foreign key (employee_id) 
       references employee (employee_id);

    alter table user_employee 
       add constraint FK935rscnqblbm9v9emac8a8h7t 
       foreign key (user_id) 
       references user (user_id);

    alter table user_roles 
       add constraint FKrrs4hf2akix4fah7g6fgx5c3b 
       foreign key (role_id_fk) 
       references role (role_id);

    alter table user_roles 
       add constraint FKjcqwtqywj0ny5jfwhqt028826 
       foreign key (user_id_fk) 
       references user (user_id);

    alter table user_student 
       add constraint FKdhtkb6gflgexqi53tu0tyh18m 
       foreign key (student_id) 
       references student (student_id);

    alter table user_student 
       add constraint FK7guxh0ti8xeodx27r9svim5fk 
       foreign key (user_id) 
       references user (user_id);

    create table book (
       isbn VARCHAR(224) not null,
        authors varchar(255),
        cover_img varchar(255),
        description varchar(255),
        genres varchar(255),
        language varchar(255),
        num_of_pages bigint,
        publication_date date,
        publisher varchar(255),
        rating double precision,
        series varchar(255),
        title varchar(255),
        primary key (isbn)
    ) engine=InnoDB;

    create table book_copy (
       copy_id bigint not null,
        isbn VARCHAR(224) not null,
        primary key (copy_id)
    ) engine=InnoDB;

    create table borrow_book (
       borrow_id bigint not null,
        checkin_timestamp datetime(6),
        checkout_timestamp datetime(6),
        copy_id bigint,
        duration double precision,
        isbn varchar(255),
        slot_id bigint,
        student_id bigint,
        primary key (borrow_id)
    ) engine=InnoDB;

    create table donated_book (
       id bigint not null,
        doner_name varchar(255),
        estimated_value double precision,
        isbn VARCHAR(224) not null,
        primary key (id)
    ) engine=InnoDB;

    create table email_notification (
       email_id bigint not null,
        student_id bigint,
        isbn varchar(255),
        borrow_id bigint,
        slot_id bigint,
        copy_id bigint,
        notification_id bigint,
        message varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (email_id)
    ) engine=InnoDB;

    create table employee (
       employee_id bigint not null,
        job_title varchar(255),
        primary key (employee_id)
    ) engine=InnoDB;

    create table fines (
       fine_id bigint not null,
        amount_paid_year_to_date double precision,
        balance double precision,
        fine double precision,
        paid_in_full bit not null,
        borrow_id bigint,
        primary key (fine_id)
    ) engine=InnoDB;

    create table kiosk (
       slot_id bigint not null,
        copy_id bigint,
        primary key (slot_id)
    ) engine=InnoDB;

    create table lendit_book_kiosk_db_sequence_generator (
       next_val bigint
    ) engine=InnoDB;

    insert into lendit_book_kiosk_db_sequence_generator values ( 1 );

    create table major (
       major_id bigint not null,
        major varchar(224),
        primary key (major_id)
    ) engine=InnoDB;

    create table notification (
       notification_id bigint not null,
        message varchar(255),
        type varchar(255),
        primary key (notification_id)
    ) engine=InnoDB;

    create table reserve_book (
       id bigint not null,
        copy_id bigint,
        student_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table role (
       role_id bigint not null,
        description varchar(255),
        name varchar(224),
        primary key (role_id)
    ) engine=InnoDB;

    create table secret (
       user_id bigint not null,
        is_initialized bit(1),
        password varchar(255),
        secret_key varbinary(2048),
        primary key (user_id)
    ) engine=InnoDB;

    create table student (
       student_id bigint not null,
        enrolled bit not null,
        primary key (student_id)
    ) engine=InnoDB;

    create table student_majors (
       student_id_fk bigint not null,
        major_id_fk bigint not null,
        primary key (student_id_fk, major_id_fk)
    ) engine=InnoDB;

    create table user (
       user_id bigint not null,
        dob date,
        email varchar(224) not null,
        gender varchar(8),
        name varchar(255),
        profession varchar(255),
        password varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_employee (
       user_id bigint not null,
        employee_id bigint not null,
        primary key (employee_id, user_id)
    ) engine=InnoDB;

    create table user_roles (
       user_id_fk bigint not null,
        role_id_fk bigint not null,
        primary key (user_id_fk, role_id_fk)
    ) engine=InnoDB;

    create table user_student (
       user_id bigint not null,
        student_id bigint not null,
        primary key (student_id, user_id)
    ) engine=InnoDB;

    alter table borrow_book 
       add constraint UK_708yjrehwaq3lskwm0q7fwdx6 unique (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table notification 
       add constraint UK_b0burp8mj0fsxw5qyf4mgqln8 unique (notification_id, message, type);

    alter table role 
       add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

    alter table secret 
       add constraint UK_rt6t6ftywcvilh3tg2tr2cfy unique (password);

    alter table user 
       add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table book_copy 
       add constraint FK97ooaf6exwl2n7hfx40gt3mjk 
       foreign key (isbn) 
       references book (isbn);

    alter table borrow_book 
       add constraint FKeqkom0kiex3pcen6meu2o0o16 
       foreign key (slot_id) 
       references kiosk (slot_id);

    alter table borrow_book 
       add constraint FKt7r6tjmuksiv3ec3wkvuveusd 
       foreign key (student_id) 
       references student (student_id);

    alter table donated_book 
       add constraint FK2v6ch7nfj0m4xn9aut8ux63ik 
       foreign key (isbn) 
       references book (isbn);

    alter table email_notification 
       add constraint FKtbbh2uqadw1avmcushtcwrqqk 
       foreign key (student_id, isbn, borrow_id, slot_id, copy_id) 
       references borrow_book (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table email_notification 
       add constraint FKg9k02sov1hj8emgasdbtjriol 
       foreign key (notification_id, message, type) 
       references notification (notification_id, message, type);

    alter table email_notification 
       add constraint FKjf5e3oto951b3tbs6f258cttl 
       foreign key (user_id) 
       references user (user_id);

    alter table fines 
       add constraint FK1144txfgfmf85khhw3ggqiedn 
       foreign key (borrow_id) 
       references borrow_book (borrow_id);

    alter table kiosk 
       add constraint FKtm3fs6jbuiyycl3mvccgcic7x 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK1rhjxgmp4r5m5tbpm80asj76y 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK94ouboffq9ex50su72d2jol5x 
       foreign key (student_id) 
       references student (student_id);

    alter table student_majors 
       add constraint FKrd5wa8yarsol7r2wy7xj7i2xu 
       foreign key (major_id_fk) 
       references major (major_id);

    alter table student_majors 
       add constraint FKo092rklfgjsxbxj39wyjuj69t 
       foreign key (student_id_fk) 
       references student (student_id);

    alter table user 
       add constraint FKmjbs7hnl0lfdc8a1726o3gk42 
       foreign key (password) 
       references secret (password);

    alter table user_employee 
       add constraint FK2ggb0ns4j04r0v1lvvmsyif2g 
       foreign key (employee_id) 
       references employee (employee_id);

    alter table user_employee 
       add constraint FK935rscnqblbm9v9emac8a8h7t 
       foreign key (user_id) 
       references user (user_id);

    alter table user_roles 
       add constraint FKrrs4hf2akix4fah7g6fgx5c3b 
       foreign key (role_id_fk) 
       references role (role_id);

    alter table user_roles 
       add constraint FKjcqwtqywj0ny5jfwhqt028826 
       foreign key (user_id_fk) 
       references user (user_id);

    alter table user_student 
       add constraint FKdhtkb6gflgexqi53tu0tyh18m 
       foreign key (student_id) 
       references student (student_id);

    alter table user_student 
       add constraint FK7guxh0ti8xeodx27r9svim5fk 
       foreign key (user_id) 
       references user (user_id);

    create table book (
       isbn VARCHAR(224) not null,
        authors varchar(255),
        cover_img varchar(255),
        description varchar(255),
        genres varchar(255),
        language varchar(255),
        num_of_pages bigint,
        publication_date date,
        publisher varchar(255),
        rating double precision,
        series varchar(255),
        title varchar(255),
        primary key (isbn)
    ) engine=InnoDB;

    create table book_copy (
       copy_id bigint not null,
        isbn VARCHAR(224) not null,
        primary key (copy_id)
    ) engine=InnoDB;

    create table borrow_book (
       borrow_id bigint not null,
        checkin_timestamp datetime(6),
        checkout_timestamp datetime(6),
        copy_id bigint,
        duration double precision,
        isbn varchar(255),
        slot_id bigint,
        student_id bigint,
        primary key (borrow_id)
    ) engine=InnoDB;

    create table donated_book (
       id bigint not null,
        doner_name varchar(255),
        estimated_value double precision,
        isbn VARCHAR(224) not null,
        primary key (id)
    ) engine=InnoDB;

    create table email_notification (
       email_id bigint not null,
        student_id bigint,
        isbn varchar(255),
        borrow_id bigint,
        slot_id bigint,
        copy_id bigint,
        notification_id bigint,
        message varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (email_id)
    ) engine=InnoDB;

    create table employee (
       employee_id bigint not null,
        job_title varchar(255),
        primary key (employee_id)
    ) engine=InnoDB;

    create table fines (
       fine_id bigint not null,
        amount_paid_year_to_date double precision,
        balance double precision,
        fine double precision,
        paid_in_full bit not null,
        borrow_id bigint,
        primary key (fine_id)
    ) engine=InnoDB;

    create table kiosk (
       slot_id bigint not null,
        copy_id bigint,
        primary key (slot_id)
    ) engine=InnoDB;

    create table lendit_book_kiosk_db_sequence_generator (
       next_val bigint
    ) engine=InnoDB;

    insert into lendit_book_kiosk_db_sequence_generator values ( 1 );

    create table major (
       major_id bigint not null,
        major varchar(224),
        primary key (major_id)
    ) engine=InnoDB;

    create table notification (
       notification_id bigint not null,
        message varchar(255),
        type varchar(255),
        primary key (notification_id)
    ) engine=InnoDB;

    create table reserve_book (
       id bigint not null,
        copy_id bigint,
        student_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table role (
       role_id bigint not null,
        description varchar(255),
        name varchar(224),
        primary key (role_id)
    ) engine=InnoDB;

    create table secret (
       user_id bigint not null,
        is_initialized bit(1),
        password varchar(255),
        secret_key varbinary(2048),
        primary key (user_id)
    ) engine=InnoDB;

    create table student (
       student_id bigint not null,
        enrolled bit not null,
        primary key (student_id)
    ) engine=InnoDB;

    create table student_majors (
       student_id_fk bigint not null,
        major_id_fk bigint not null,
        primary key (student_id_fk, major_id_fk)
    ) engine=InnoDB;

    create table user (
       user_id bigint not null,
        dob date,
        email varchar(224) not null,
        gender varchar(8),
        name varchar(255),
        profession varchar(255),
        password varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_employee (
       user_id bigint not null,
        employee_id bigint not null,
        primary key (employee_id, user_id)
    ) engine=InnoDB;

    create table user_roles (
       user_id_fk bigint not null,
        role_id_fk bigint not null,
        primary key (user_id_fk, role_id_fk)
    ) engine=InnoDB;

    create table user_student (
       user_id bigint not null,
        student_id bigint not null,
        primary key (student_id, user_id)
    ) engine=InnoDB;

    alter table borrow_book 
       add constraint UK_708yjrehwaq3lskwm0q7fwdx6 unique (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table notification 
       add constraint UK_b0burp8mj0fsxw5qyf4mgqln8 unique (notification_id, message, type);

    alter table role 
       add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

    alter table secret 
       add constraint UK_rt6t6ftywcvilh3tg2tr2cfy unique (password);

    alter table user 
       add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table book_copy 
       add constraint FK97ooaf6exwl2n7hfx40gt3mjk 
       foreign key (isbn) 
       references book (isbn);

    alter table borrow_book 
       add constraint FKeqkom0kiex3pcen6meu2o0o16 
       foreign key (slot_id) 
       references kiosk (slot_id);

    alter table borrow_book 
       add constraint FKt7r6tjmuksiv3ec3wkvuveusd 
       foreign key (student_id) 
       references student (student_id);

    alter table donated_book 
       add constraint FK2v6ch7nfj0m4xn9aut8ux63ik 
       foreign key (isbn) 
       references book (isbn);

    alter table email_notification 
       add constraint FKtbbh2uqadw1avmcushtcwrqqk 
       foreign key (student_id, isbn, borrow_id, slot_id, copy_id) 
       references borrow_book (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table email_notification 
       add constraint FKg9k02sov1hj8emgasdbtjriol 
       foreign key (notification_id, message, type) 
       references notification (notification_id, message, type);

    alter table email_notification 
       add constraint FKjf5e3oto951b3tbs6f258cttl 
       foreign key (user_id) 
       references user (user_id);

    alter table fines 
       add constraint FK1144txfgfmf85khhw3ggqiedn 
       foreign key (borrow_id) 
       references borrow_book (borrow_id);

    alter table kiosk 
       add constraint FKtm3fs6jbuiyycl3mvccgcic7x 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK1rhjxgmp4r5m5tbpm80asj76y 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK94ouboffq9ex50su72d2jol5x 
       foreign key (student_id) 
       references student (student_id);

    alter table student_majors 
       add constraint FKrd5wa8yarsol7r2wy7xj7i2xu 
       foreign key (major_id_fk) 
       references major (major_id);

    alter table student_majors 
       add constraint FKo092rklfgjsxbxj39wyjuj69t 
       foreign key (student_id_fk) 
       references student (student_id);

    alter table user 
       add constraint FKmjbs7hnl0lfdc8a1726o3gk42 
       foreign key (password) 
       references secret (password);

    alter table user_employee 
       add constraint FK2ggb0ns4j04r0v1lvvmsyif2g 
       foreign key (employee_id) 
       references employee (employee_id);

    alter table user_employee 
       add constraint FK935rscnqblbm9v9emac8a8h7t 
       foreign key (user_id) 
       references user (user_id);

    alter table user_roles 
       add constraint FKrrs4hf2akix4fah7g6fgx5c3b 
       foreign key (role_id_fk) 
       references role (role_id);

    alter table user_roles 
       add constraint FKjcqwtqywj0ny5jfwhqt028826 
       foreign key (user_id_fk) 
       references user (user_id);

    alter table user_student 
       add constraint FKdhtkb6gflgexqi53tu0tyh18m 
       foreign key (student_id) 
       references student (student_id);

    alter table user_student 
       add constraint FK7guxh0ti8xeodx27r9svim5fk 
       foreign key (user_id) 
       references user (user_id);

    create table book (
       isbn VARCHAR(224) not null,
        authors varchar(255),
        cover_img varchar(255),
        description varchar(255),
        genres varchar(255),
        language varchar(255),
        num_of_pages bigint,
        publication_date date,
        publisher varchar(255),
        rating double precision,
        series varchar(255),
        title varchar(255),
        primary key (isbn)
    ) engine=InnoDB;

    create table book_copy (
       copy_id bigint not null,
        isbn VARCHAR(224) not null,
        primary key (copy_id)
    ) engine=InnoDB;

    create table borrow_book (
       borrow_id bigint not null,
        checkin_timestamp datetime(6),
        checkout_timestamp datetime(6),
        copy_id bigint,
        duration double precision,
        isbn varchar(255),
        slot_id bigint,
        student_id bigint,
        primary key (borrow_id)
    ) engine=InnoDB;

    create table donated_book (
       id bigint not null,
        doner_name varchar(255),
        estimated_value double precision,
        isbn VARCHAR(224) not null,
        primary key (id)
    ) engine=InnoDB;

    create table email_notification (
       email_id bigint not null,
        student_id bigint,
        isbn varchar(255),
        borrow_id bigint,
        slot_id bigint,
        copy_id bigint,
        notification_id bigint,
        message varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (email_id)
    ) engine=InnoDB;

    create table employee (
       employee_id bigint not null,
        job_title varchar(255),
        primary key (employee_id)
    ) engine=InnoDB;

    create table fines (
       fine_id bigint not null,
        amount_paid_year_to_date double precision,
        balance double precision,
        fine double precision,
        paid_in_full bit not null,
        borrow_id bigint,
        primary key (fine_id)
    ) engine=InnoDB;

    create table kiosk (
       slot_id bigint not null,
        copy_id bigint,
        primary key (slot_id)
    ) engine=InnoDB;

    create table lendit_book_kiosk_db_sequence_generator (
       next_val bigint
    ) engine=InnoDB;

    insert into lendit_book_kiosk_db_sequence_generator values ( 1 );

    create table major (
       major_id bigint not null,
        major varchar(224),
        primary key (major_id)
    ) engine=InnoDB;

    create table notification (
       notification_id bigint not null,
        message varchar(255),
        type varchar(255),
        primary key (notification_id)
    ) engine=InnoDB;

    create table reserve_book (
       id bigint not null,
        copy_id bigint,
        student_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table role (
       role_id bigint not null,
        description varchar(255),
        name varchar(224),
        primary key (role_id)
    ) engine=InnoDB;

    create table secret (
       user_id bigint not null,
        is_initialized bit(1),
        password varchar(255),
        secret_key varbinary(2048),
        primary key (user_id)
    ) engine=InnoDB;

    create table student (
       student_id bigint not null,
        enrolled bit not null,
        primary key (student_id)
    ) engine=InnoDB;

    create table student_majors (
       student_id_fk bigint not null,
        major_id_fk bigint not null,
        primary key (student_id_fk, major_id_fk)
    ) engine=InnoDB;

    create table user (
       user_id bigint not null,
        dob date,
        email varchar(224) not null,
        gender varchar(8),
        name varchar(255),
        profession varchar(255),
        password varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_employee (
       user_id bigint not null,
        employee_id bigint not null,
        primary key (employee_id, user_id)
    ) engine=InnoDB;

    create table user_roles (
       user_id_fk bigint not null,
        role_id_fk bigint not null,
        primary key (user_id_fk, role_id_fk)
    ) engine=InnoDB;

    create table user_student (
       user_id bigint not null,
        student_id bigint not null,
        primary key (student_id, user_id)
    ) engine=InnoDB;

    alter table borrow_book 
       add constraint UK_708yjrehwaq3lskwm0q7fwdx6 unique (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table notification 
       add constraint UK_b0burp8mj0fsxw5qyf4mgqln8 unique (notification_id, message, type);

    alter table role 
       add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

    alter table secret 
       add constraint UK_rt6t6ftywcvilh3tg2tr2cfy unique (password);

    alter table user 
       add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table book_copy 
       add constraint FK97ooaf6exwl2n7hfx40gt3mjk 
       foreign key (isbn) 
       references book (isbn);

    alter table borrow_book 
       add constraint FKeqkom0kiex3pcen6meu2o0o16 
       foreign key (slot_id) 
       references kiosk (slot_id);

    alter table borrow_book 
       add constraint FKt7r6tjmuksiv3ec3wkvuveusd 
       foreign key (student_id) 
       references student (student_id);

    alter table donated_book 
       add constraint FK2v6ch7nfj0m4xn9aut8ux63ik 
       foreign key (isbn) 
       references book (isbn);

    alter table email_notification 
       add constraint FKtbbh2uqadw1avmcushtcwrqqk 
       foreign key (student_id, isbn, borrow_id, slot_id, copy_id) 
       references borrow_book (student_id, isbn, borrow_id, slot_id, copy_id);

    alter table email_notification 
       add constraint FKg9k02sov1hj8emgasdbtjriol 
       foreign key (notification_id, message, type) 
       references notification (notification_id, message, type);

    alter table email_notification 
       add constraint FKjf5e3oto951b3tbs6f258cttl 
       foreign key (user_id) 
       references user (user_id);

    alter table fines 
       add constraint FK1144txfgfmf85khhw3ggqiedn 
       foreign key (borrow_id) 
       references borrow_book (borrow_id);

    alter table kiosk 
       add constraint FKtm3fs6jbuiyycl3mvccgcic7x 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK1rhjxgmp4r5m5tbpm80asj76y 
       foreign key (copy_id) 
       references book_copy (copy_id);

    alter table reserve_book 
       add constraint FK94ouboffq9ex50su72d2jol5x 
       foreign key (student_id) 
       references student (student_id);

    alter table student_majors 
       add constraint FKrd5wa8yarsol7r2wy7xj7i2xu 
       foreign key (major_id_fk) 
       references major (major_id);

    alter table student_majors 
       add constraint FKo092rklfgjsxbxj39wyjuj69t 
       foreign key (student_id_fk) 
       references student (student_id);

    alter table user 
       add constraint FKmjbs7hnl0lfdc8a1726o3gk42 
       foreign key (password) 
       references secret (password);

    alter table user_employee 
       add constraint FK2ggb0ns4j04r0v1lvvmsyif2g 
       foreign key (employee_id) 
       references employee (employee_id);

    alter table user_employee 
       add constraint FK935rscnqblbm9v9emac8a8h7t 
       foreign key (user_id) 
       references user (user_id);

    alter table user_roles 
       add constraint FKrrs4hf2akix4fah7g6fgx5c3b 
       foreign key (role_id_fk) 
       references role (role_id);

    alter table user_roles 
       add constraint FKjcqwtqywj0ny5jfwhqt028826 
       foreign key (user_id_fk) 
       references user (user_id);

    alter table user_student 
       add constraint FKdhtkb6gflgexqi53tu0tyh18m 
       foreign key (student_id) 
       references student (student_id);

    alter table user_student 
       add constraint FK7guxh0ti8xeodx27r9svim5fk 
       foreign key (user_id) 
       references user (user_id);
