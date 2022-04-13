create table books
(
    id                 bigint       not null
        primary key,
    authors            varchar(255) null,
    average_rating     double       null,
    isbn               varchar(255) null,
    isbn13             varchar(255) null,
    language_code      varchar(255) null,
    number_of_pages    bigint       null,
    publication_date   date         null,
    publisher          varchar(255) null,
    ratings_count      bigint       null,
    text_reviews_count bigint       null,
    title              varchar(255) null,
    constraint books_id_uindex
        unique (id)
);

create table hibernate_sequences
(
    sequence_name varchar(255) not null
        primary key,
    next_val      bigint       null
);

create table major
(
    major_id bigint       not null
        primary key,
    major    varchar(255) null
);

create table userRole
(
    role_id     bigint       not null
        primary key,
    description varchar(255) null,
    name        varchar(255) null
);

create table role_sequence
(
    next_val bigint null
);

create table student
(
    student_id bigint not null
        primary key,
    enrolled   bit    not null
);

create table student_majors
(
    student_id bigint not null,
    major_id   bigint not null,
    primary key (student_id, major_id),
    constraint FK78ng9jeou05niff8i91eaeq5o
        foreign key (student_id) references student (student_id),
    constraint FKefxryt1rfpnx3ey1hvicjudm
        foreign key (major_id) references major (major_id)
);

create table student_sequence
(
    next_val bigint null
);

create table user
(
    user_id    bigint       not null
        primary key,
    dob        date         null,
    email      varchar(255) null,
    gender     varchar(255) null,
    name       varchar(255) null,
    secret   varchar(255) null,
    profession varchar(255) null
);

create table user_roles
(
    user_id_fk bigint not null,
    role_id_fk bigint not null,
    primary key (user_id_fk, role_id_fk),
    constraint FKjcqwtqywj0ny5jfwhqt028826
        foreign key (user_id_fk) references user (user_id),
    constraint FKrrs4hf2akix4fah7g6fgx5c3b
        foreign key (role_id_fk) references userRole (role_id)
);

create table user_sequence
(
    next_val bigint null
);

create table user_students
(
    user_id_fk    bigint not null,
    student_id_fk bigint not null,
    primary key (user_id_fk, student_id_fk),
    constraint FK2fq81v74emo6h3e15e0anbmkf
        foreign key (student_id_fk) references student (student_id)
            on delete cascade,
    constraint FKg2wjgwsm59lulb3flncqd9hxv
        foreign key (user_id_fk) references user (user_id)
);

