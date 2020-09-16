--liquibase formatted sql
--changeset matthias:karaoke-init

create table karaokes
(
    id serial not null,
    expired boolean default false,
    name varchar not null
);

create unique index karaokes_id_uindex
    on karaokes (id);

alter table karaokes
    add constraint karaokes_pk
        primary key (id);

alter table karaokes
    owner to ckb;

--attendees

create table attendees
(
    id serial not null,
    karaoke_id int not null
        constraint karaokes_fk
            references karaokes (id),
    name varchar not null,
    password varchar not null,
    title varchar,
    link varchar,
    artist varchar
);

create unique index attendees_id_uindex
    on attendees (id);

alter table attendees
    add constraint attendees_pk
        unique (id);

alter table attendees
    owner to ckb;

