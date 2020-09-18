--liquibase formatted sql
--changeset matthias:karaoke-init

create table karaokes
(
    id      serial  not null,
    expired boolean default false,
    name    varchar not null
);

create unique index karaokes_id_uindex
    on karaokes (id);

alter table karaokes
    add constraint karaokes_pk
        primary key (id);


--songs
create table songs
(
    id     serial not null,
    title  varchar,
    artist varchar,
    link   varchar
);

create unique index songs_id_uindex
    on songs (id);

alter table songs
    add constraint songs_pk
        primary key (id);


--votes
create table votes
(
    id          serial not null,
    attendee_id int    not null,
    percentage  int    not null
);

create unique index votes_id_uindex
    on votes (id);

alter table votes
    add constraint votes_pk
        primary key (id);

--attendees

create table attendees
(
    id         serial  not null,
    karaoke_id int     not null
        constraint attendees_fk
            references karaokes (id),
    song_id    int
        constraint songs_fk
            references songs (id),
    vote_id    int
        constraint votes_fk
            references votes (id),
    name       varchar not null,
    password   varchar not null
);

create unique index attendees_id_uindex
    on attendees (id);

alter table attendees
    add constraint attendees_pk
        primary key (id);

alter table votes
    add constraint attendee_fk
        foreign key (attendee_id) references attendees;
