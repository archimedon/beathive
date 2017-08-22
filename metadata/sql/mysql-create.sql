drop database if exists @DB-NAME@;
create database if not exists @DB-NAME@;
grant all privileges on @DB-NAME@.* to '@DB-USERNAME@'@'localhost' identified by '@DB-USERNAME@';
ALTER DATABASE `@DB-NAME@` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- CREATE {DATABASE | SCHEMA} [IF NOT EXISTS] db_name
--    [create_specification] ...

-- create_specification:
--    [DEFAULT] CHARACTER SET [=] charset_name
--  | [DEFAULT] COLLATE [=] collation_name

-- grant all privileges on @DB-NAME@.* to @DB-USERNAME@@localhost identified by "@DB-PASSWORD@";
-- grant all privileges on @DB-NAME@.* to @DB-USERNAME@@beathive.com identified by "@DB-PASSWORD@";

-- Extra user/host privilege added to account for Fedora Core default hostname quirk.
-- grant all privileges on @DB-NAME@.* to @DB-USERNAME@@s identified by "@DB-PASSWORD@";

-- You may have to explicitly define your hostname in order for things
-- to work correctly.  For example:
-- grant all privileges on @DB-NAME@.* to @DB-USERNAME@@host.domain.com identified by "@DB-PASSWORD@";
