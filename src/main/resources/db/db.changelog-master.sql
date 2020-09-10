--liquibase formatted sql
--changeset matthias:karaoke-init

create table karaokes
(
    id         serial not null
        constraint karaokes_pk
            primary key,
    name       varchar,
    expired    boolean   default false,
    updated_at timestamp default CURRENT_TIMESTAMP,
    created_at timestamp default CURRENT_TIMESTAMP,
    deleted_at timestamp
);

alter table karaokes
    owner to ckb;

create unique index karaokes_id_uindex
    on karaokes (id);

