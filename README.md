This an exploration for extending and beautifying the Spring Boot's Passkey integration.

**Some config done on Backend layer.**

At the time of POC the Spring Security team did not introduce the JDBC or JPA support for the Passkeys, using MySQL for the same.

For mysql, if db is available, it will setup the tables automatically.

SQL queries to set up two users is as below:-

```sql
-- create database
create database spring_passkeys_demo;

-- insert two users, one named user & another one is admin, both with password = test
insert into spring_passkeys_demo.users (id, created, deleted, email, email_verified, enabled, first_name, last_login, last_name, middle_name, modified, password, phone, username)
values  ('0193d2b3-2fb6-7e3d-829a-a97e463db956', '2024-12-23 14:23:25.000000', 0, null, 0, 1, null, '2024-12-23 14:24:02.000000', null, null, '2024-12-23 14:24:07.000000', '$2a$10$GC9kHwD3PvHrtcSBA7TAPe8j92nGFJmQKW5IKxe6bBEf8ouZjuS2u', null, 'user'),
('0193d2bc-ecb8-700d-bfeb-7a2c0f7424c3', '2024-12-23 14:23:25.000000', 0, null, 0, 1, null, '2024-12-23 14:24:02.000000', null, null, '2024-12-23 14:24:07.000000', '$2a$10$GC9kHwD3PvHrtcSBA7TAPe8j92nGFJmQKW5IKxe6bBEf8ouZjuS2u', null, 'admin');
```


**Beautification done on front layer - kinda obvious.**

➫ JTE for templates.
➫ HTMX for an interaction and loading
➫ For front end used Tailwind. (⚠️ I am a tailwind newbie)

➫ Raw Tailwind Styles [tw.styles.css](src/main/resources/static/css/tw.styles.css)

Run the commands:-

```shell
# install the node packages
npm i
# generate output styles.
npx tailwindcss -i tw.styles.css -o styles.css
# continuous watch changes in the tw.styles.css during development. 
npx tailwindcss -i tw.styles.css -o styles.css --watch
```
