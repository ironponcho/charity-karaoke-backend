--liquibase formatted sql
--changeset matthias:karaoke-init

create table karaokes
(
    id      serial  not null,
    name    varchar not null,
    date    timestamptz,
    expired boolean default false
);

create unique index karaokes_id_uindex
    on karaokes (id);

alter table karaokes
    add constraint karaokes_pk
        primary key (id);

--users
create table users
(
    id         serial  not null,
    username       varchar not null,
    password   varchar not null
);

create unique index users_id_uindex
    on users (id);

alter table users
    add constraint users_pk
        primary key (id);

--songs
create table songs
(
    id     serial not null,
    karaoke_id int not null ,
    user_id int    not null,
    title  varchar,
    artist varchar,
    link   varchar
);

create unique index songs_id_uindex
    on songs (id);

alter table songs
    add constraint songs_pk
        primary key (id);

alter table songs
    add constraint user_fk
        foreign key (user_id) references users;

alter table songs
    add constraint karaoke_fk
        foreign key (karaoke_id) references karaokes;

--votes
create table votes
(
    id          serial not null,
    user_id int    not null,
    recipient_id int    not null,
    karaoke_id int not null ,
    percentage  int    not null
);

create unique index votes_id_uindex
    on votes (id);

alter table votes
    add constraint votes_pk
        primary key (id);

alter table votes
    add constraint user_fk
        foreign key (user_id) references users;

alter table votes
    add constraint recipient_fk
        foreign key (recipient_id) references users;

alter table votes
    add constraint karaoke_fk
        foreign key (karaoke_id) references karaokes;


-- user karaoke mapping
create table user_karaokes
(
    user_id int not null,
    karaoke_id   int not null
);

alter table user_karaokes
    add constraint user_karaokes_pk
        primary key (user_id, karaoke_id);

create unique index user_karaokes_id_uindex
    on user_karaokes (user_id,karaoke_id);

alter table user_karaokes
    add constraint user_fk
        foreign key (user_id) references users;

alter table user_karaokes
    add constraint karaoke_fk
        foreign key (karaoke_id) references karaokes;

-- roles
create table roles
(
    id     serial not null,
    name   varchar not null unique
);

alter table roles
    add constraint roles_pk
        primary key (id);

create unique index roles_id_uindex
    on roles (id);

create table user_roles
(
    user_id int not null,
    role_id     int not null
);

alter table user_roles
    add constraint user_roles_pk
        primary key (user_id, role_id);

create unique index user_roles_id_uindex
    on user_roles (user_id,role_id);


alter table user_roles
    add constraint user_fk
        foreign key (user_id) references users;


alter table user_roles
    add constraint role_fk
        foreign key (role_id) references roles;
