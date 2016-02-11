create table borrowabook.users (
    id bigserial,
    user_name varchar(256) not null,
    email varchar(256) not null,
    created_at timestamp not null,
    constraint pk_users primary key(id)
);

create table borrowabook.login_infos(
    id bigserial,
    provider_id varchar not null,
    provider_key varchar not null,
    constraint pk_login_infos primary key(id)
);

create table borrowabook.user_login_infos(
    id bigserial,
    user_id bigint not null,
    login_info_id bigint not null,
    constraint pk_user_login_infos primary key(id),
    foreign key (user_id) references borrowabook.users(id),
    foreign key (login_info_id) references borrowabook.login_infos(id)
);

create table borrowabook.password_infos (
    id bigserial,
    hasher varchar not null,
    password varchar(256) not null,
    salt varchar(256) not null,
    login_info_id bigint not null,
    created_at timestamp not null,
    constraint pk_passwords primary key(id),
    foreign key (login_info_id) references borrowabook.login_infos(id)
);

insert into borrowabook.users(user_name, email, created_at) values('rwalerow','rwalerow@home.pl', now());
insert into borrowabook.login_infos(provider_id, provider_key) values ('mybook', '13rfdt34tgfaswe5234');
