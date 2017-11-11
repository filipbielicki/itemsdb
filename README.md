# itemsdb
JDBC / PostgreSQL / DAO / Mockito / AssertJ 

SUMMARY<br>
Simple CRUD application connected to a database via JDBC. Designed with DAO pattern. Used Mockito and AssertJ for unit tests.<br>

DB Configuration<br>
URL, username, password -> configure in DBSettings class.<br>
database: postgresql<br>
schema: warehouse<br>
table: items<br>
column1: id (serial) pkey not null<br>
column2: name (character varying)<br>
column3: price (numerical)<br>

Before running the application, insert some records into database.<br>
