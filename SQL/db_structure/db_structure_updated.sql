drop table if exists books cascade;

drop table if exists student_majors cascade;

drop table if exists major cascade;

drop table if exists tgvuzeluiejvb2sgs2lvc2s_sequence cascade;

drop table if exists user_roles cascade;

drop table if exists role cascade;

drop table if exists user_student cascade;

drop table if exists student cascade;

drop table if exists user cascade;


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
    name        varchar(224) null,
    constraint `PRIMARY`
    primary key (role_id),
    constraint UK_8sewwnpamngi6b1dwaa88askk
    unique (name)
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
    foreign key (student_id_fk) references Lendit_Book_Kiosk.student (student_id)
    on delete cascade,
    constraint FKrd5wa8yarsol7r2wy7xj7i2xu
    foreign key (major_id_fk) references Lendit_Book_Kiosk.major (major_id)
    );

create table if not exists Lendit_Book_Kiosk.tgvuzeluiejvb2sgs2lvc2s_sequence
(
    next_val bigint null
);

create table if not exists Lendit_Book_Kiosk.user
(
    user_id    bigint       not null,
    dob        date         null,
    email      varchar(224) not null,
    gender     varchar(8)   null,
    name       varchar(255) null,
    password   varchar(255) null,
    profession varchar(255) null,
    constraint `PRIMARY`
    primary key (user_id),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
    unique (email)
    );

create table if not exists Lendit_Book_Kiosk.user_roles
(
    user_id_fk bigint not null,
    role_id_fk bigint not null,
    constraint `PRIMARY`
    primary key (user_id_fk, role_id_fk),
    constraint FKjcqwtqywj0ny5jfwhqt028826
    foreign key (user_id_fk) references Lendit_Book_Kiosk.user (user_id)
    on delete cascade,
    constraint FKrrs4hf2akix4fah7g6fgx5c3b
    foreign key (role_id_fk) references Lendit_Book_Kiosk.role (role_id)
    );

create table if not exists Lendit_Book_Kiosk.user_student
(
    user_id_fk    bigint not null,
    student_id_fk bigint not null,
    constraint `PRIMARY`
    primary key (user_id_fk, student_id_fk),
    constraint FK2g3x1whyf91yc956y8vq5xh40
    foreign key (student_id_fk) references Lendit_Book_Kiosk.student (student_id),
    constraint FK4crg7xk4rq4s4jgeh7slpbev6
    foreign key (user_id_fk) references Lendit_Book_Kiosk.user (user_id)
    on delete cascade
    );

