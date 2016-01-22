create table borrowabook.users (
    id bigserial,
    username varchar(256) not null,
    email varchar(256) not null,
    created timestamp not null,
    constraint pk_users primary key(id)
);

create table borrowabook.passwords (
    id bigserial,
    password varchar(256) not null,
    salt varchar(256),
    user_id bigint not null,
    created timestamp not null,
    constraint pk_passwords primary key(id),
    foreign key user_id references borrowabook.users(id)
);