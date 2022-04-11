drop table if exists acl_entry cascade;

drop table if exists acl_object_identity cascade;

drop table if exists acl_class cascade;

drop table if exists acl_sid cascade;

drop table if exists books cascade;

drop table if exists secure_tokens cascade;

drop table if exists student_majors cascade;

drop table if exists major cascade;

drop table if exists tgvuzeluiejvb2sgs2lvc2s_sequence cascade;

drop table if exists user_roles cascade;

drop table if exists role cascade;

drop table if exists user_students cascade;

drop table if exists student cascade;

drop table if exists user cascade;


create table if not exists Lendit_Book_Kiosk.acl_class
(
    id    bigint default 100 not null,
    class varchar(100)       not null,
    constraint `PRIMARY`
    primary key (id),
    constraint unique_uk_2
    unique (class)
    );

create table if not exists Lendit_Book_Kiosk.acl_sid
(
    id        bigint default 100 not null,
    principal tinyint(1)         not null,
    sid       varchar(100)       not null,
    constraint `PRIMARY`
    primary key (id),
    constraint unique_uk_1
    unique (sid, principal)
    );

create table if not exists Lendit_Book_Kiosk.acl_object_identity
(
    id                 bigint default 100 not null,
    object_id_class    bigint             not null,
    object_id_identity bigint             not null,
    parent_object      bigint             null,
    owner_sid          bigint             null,
    entries_inheriting tinyint(1)         not null,
    constraint `PRIMARY`
    primary key (id),
    constraint unique_uk_3
    unique (object_id_class, object_id_identity),
    constraint foreign_fk_1
    foreign key (parent_object) references Lendit_Book_Kiosk.acl_object_identity (id),
    constraint foreign_fk_2
    foreign key (object_id_class) references Lendit_Book_Kiosk.acl_class (id),
    constraint foreign_fk_3
    foreign key (owner_sid) references Lendit_Book_Kiosk.acl_sid (id)
    );

create table if not exists Lendit_Book_Kiosk.acl_entry
(
    id                  bigint default 100 not null,
    acl_object_identity bigint             not null,
    ace_order           int                not null,
    sid                 bigint             not null,
    mask                int                not null,
    granting            tinyint(1)         not null,
    audit_success       tinyint(1)         not null,
    audit_failure       tinyint(1)         not null,
    constraint `PRIMARY`
    primary key (id),
    constraint unique_uk_4
    unique (acl_object_identity, ace_order),
    constraint foreign_fk_4
    foreign key (acl_object_identity) references Lendit_Book_Kiosk.acl_object_identity (id),
    constraint foreign_fk_5
    foreign key (sid) references Lendit_Book_Kiosk.acl_sid (id)
    );

create table if not exists Lendit_Book_Kiosk.books
(
    isbn             varchar(224) not null,
    authors          varchar(255) null,
    cover_img        varchar(255) null,
    description      varchar(255) null,
    genres           varchar(255) null,
    language         varchar(255) null,
    num_of_pages     bigint       null,
    publication_date date         null,
    publisher        varchar(255) null,
    rating           double       null,
    series           varchar(255) null,
    title            varchar(255) null,
    constraint `PRIMARY`
    primary key (isbn)
    );

create table if not exists Lendit_Book_Kiosk.major
(
    major_id bigint       not null,
    major    varchar(224) null,
    constraint `PRIMARY`
    primary key (major_id)
    );

create table if not exists Lendit_Book_Kiosk.role
(
    role_id     bigint       not null,
    description varchar(255) null,
    role        varchar(224) null,
    constraint `PRIMARY`
    primary key (role_id),
    constraint UK_bjxn5ii7v7ygwx39et0wawu0q
    unique (role)
    );

create table if not exists Lendit_Book_Kiosk.student
(
    student_id bigint not null,
    enrolled   bit    not null,
    constraint `PRIMARY`
    primary key (student_id)
    );

create table if not exists Lendit_Book_Kiosk.student_majors
(
    student_id_fk bigint not null,
    major_id_fk   bigint not null,
    constraint `PRIMARY`
    primary key (student_id_fk, major_id_fk),
    constraint FKo092rklfgjsxbxj39wyjuj69t
    foreign key (student_id_fk) references Lendit_Book_Kiosk.student (student_id),
    constraint FKrd5wa8yarsol7r2wy7xj7i2xu
    foreign key (major_id_fk) references Lendit_Book_Kiosk.major (major_id)
    );

create table if not exists Lendit_Book_Kiosk.tgvuzeluiejvb2sgs2lvc2s_sequence
(
    next_val bigint null
);

create table if not exists Lendit_Book_Kiosk.user
(
    user_id               bigint       not null,
    account_verified      bit          not null,
    dob                   date         null,
    email                 varchar(224) not null,
    enabled               bit          not null,
    failed_login_attempts int          not null,
    gender                varchar(8)   null,
    login_disabled        bit          not null,
    name                  varchar(255) null,
    password              varchar(255) null,
    profession            varchar(255) null,
    secret                varchar(255) null,
    token                 varchar(255) null,
    constraint `PRIMARY`
    primary key (user_id),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
    unique (email)
    );

create table if not exists Lendit_Book_Kiosk.secure_tokens
(
    id         bigint auto_increment,
    expire_at  datetime(6)  not null,
    time_stamp datetime(6)  null,
    token      varchar(255) null,
    user_id    bigint       null,
    constraint `PRIMARY` primary key (id),
    constraint UK_dlvilc17p76edkguw5kd16xl5
    unique (token),
    constraint FK2c6au8cb5pp8w4gnx73yrsgrr
    foreign key (user_id) references Lendit_Book_Kiosk.user (user_id)
    );

create table if not exists Lendit_Book_Kiosk.user_roles
(
    user_id_fk bigint not null,
    role_id_fk bigint not null,
    constraint `PRIMARY`
    primary key (user_id_fk, role_id_fk),
    constraint FKjcqwtqywj0ny5jfwhqt028826
    foreign key (user_id_fk) references Lendit_Book_Kiosk.user (user_id),
    constraint FKrrs4hf2akix4fah7g6fgx5c3b
    foreign key (role_id_fk) references Lendit_Book_Kiosk.role (role_id)
    );

create table if not exists Lendit_Book_Kiosk.user_students
(
    user_id_fk    bigint not null,
    student_id_fk bigint not null,
    constraint `PRIMARY`
    primary key (user_id_fk, student_id_fk),
    constraint FK2fq81v74emo6h3e15e0anbmkf
    foreign key (student_id_fk) references Lendit_Book_Kiosk.student (student_id),
    constraint FKg2wjgwsm59lulb3flncqd9hxv
    foreign key (user_id_fk) references Lendit_Book_Kiosk.user (user_id)
    );

