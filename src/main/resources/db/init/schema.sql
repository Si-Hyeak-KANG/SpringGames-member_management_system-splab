DROP TABLE IF EXISTS game_card;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS member;

create table game
(
    game_id bigint auto_increment
        primary key,
    name    varchar(255) null
);

create table member
(
    card_total_count int                               not null,
    card_total_price double                            not null,
    join_at          date                              not null,
    member_id        bigint auto_increment
        primary key,
    name             varchar(100)                      not null,
    email            varchar(255)                      not null,
    level            enum ('GOLD', 'SILVER', 'BRONZE') not null,
    constraint UK_mbmcqelty0fbrvxp1q58dn57t
        unique (email)
);

create table game_card
(
    price         double       not null,
    game_card_id  bigint auto_increment
        primary key,
    game_id       bigint       null,
    member_id     bigint       null,
    serial_number bigint       null,
    title         varchar(255) null,
    constraint FK6jtk60jlm6mhyp1dhvxoef5y9
        foreign key (member_id) references member (member_id),
    constraint FKj5am0ld0cyuv6qgsxjdulm3hp
        foreign key (game_id) references game (game_id)
);

create index IDX1l8w2wh5dgpgwo6dhf7fnbd8c
    on member (level, name);

create index IDX6m44i8n7q3285nkq28f57i911
    on member (level);

create index IDX9esvgikrmti1v7ci6v453imdc
    on member (name);

