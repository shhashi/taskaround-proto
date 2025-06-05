-- 登録コード
-- * RestoreFromTempTable
create table registration_codes
(
    registration_code_id serial not null,
    registration_code    varchar(32),
    create_account_id    integer,
    account_id           integer,
    expired_in           timestamp with time zone,
    created_at           timestamp with time zone,
    constraint registration_codes_PKC primary key (registration_code_id)
);

-- 工数
-- * RestoreFromTempTable
create table person_hours
(
    person_hour_id bigserial not null,
    account_id     integer,
    task_id        varchar(100),
    person_hour    decimal(3, 2),
    working_day    date,
    created_at     timestamp with time zone,
    constraint person_hours_PKC primary key (person_hour_id)
);

-- ステータス
-- * RestoreFromTempTable
create table status
(
    status_id   serial not null,
    status_name varchar(100),
    created_at  timestamp with time zone,
    deleted_at  timestamp with time zone,
    constraint status_PKC primary key (status_id)
);

-- タスク
-- * RestoreFromTempTable
create table tasks
(
    task_id     varchar(100) not null,
    task_name   varchar(100),
    description text,
    finish_date timestamp with time zone,
    account_id  integer,
    project_id  varchar(100),
    status_id   integer,
    created_at  timestamp with time zone,
    deleted_at  timestamp with time zone,
    constraint tasks_PKC primary key (task_id)
);

-- 担当
-- * RestoreFromTempTable
create table roles
(
    role_id    varchar(100) not null,
    role_name  varchar(100) not null,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    constraint roles_PKC primary key (role_id)
);

-- プロジェクト内担当
-- * RestoreFromTempTable
create table account_roles
(
    account_role_id serial       not null,
    account_id      integer      not null,
    project_id      varchar(100) not null,
    role_id         varchar(100) not null,
    created_at      timestamp with time zone,
    deleted_at      timestamp with time zone,
    constraint account_roles_PKC primary key (account_role_id)
);

-- プロジェクト
-- * RestoreFromTempTable
create table projects
(
    project_id   varchar(100) not null,
    project_name varchar(100) not null,
    created_at   timestamp with time zone,
    deleted_at   timestamp with time zone,
    constraint projects_PKC primary key (project_id)
);

-- アカウント
-- * RestoreFromTempTable
create table accounts
(
    account_id   serial       not null,
    login_id     varchar(100) not null,
    account_name varchar(20)  not null,
    password     varchar(100) not null,
    created_at   timestamp with time zone,
    deleted_at   timestamp with time zone,
    constraint accounts_PKC primary key (account_id)
);

create unique index unique_login_id
    on accounts (login_id);

alter table registration_codes
    add constraint registration_codes_FK1 foreign key (account_id, create_account_id) references accounts (account_id, account_id)
        on delete cascade
        on update cascade;

alter table person_hours
    add constraint person_hours_FK1 foreign key (task_id) references tasks (task_id)
        on delete cascade
        on update cascade;

alter table person_hours
    add constraint person_hours_FK2 foreign key (account_id) references accounts (account_id)
        on delete cascade
        on update cascade;

alter table tasks
    add constraint tasks_FK1 foreign key (status_id) references status (status_id)
        on delete cascade
        on update cascade;

alter table tasks
    add constraint tasks_FK2 foreign key (account_id) references accounts (account_id)
        on delete cascade
        on update cascade;

alter table tasks
    add constraint tasks_FK3 foreign key (project_id) references projects (project_id)
        on delete cascade
        on update cascade;

alter table account_roles
    add constraint account_roles_FK1 foreign key (role_id) references roles (role_id)
        on delete cascade
        on update cascade;

alter table account_roles
    add constraint account_roles_FK2 foreign key (project_id) references projects (project_id)
        on delete cascade
        on update cascade;

alter table account_roles
    add constraint account_roles_FK3 foreign key (account_id) references accounts (account_id)
        on delete cascade
        on update cascade;
