create table log
(
    id            int auto_increment
        primary key,
    method        varchar(255) null,
    start_inf     varchar(255) null,
    sccessu_inf   varchar(255) null,
    exception_inf varchar(255) null
);

