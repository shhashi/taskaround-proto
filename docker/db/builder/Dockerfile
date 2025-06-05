FROM ubuntu:latest

RUN apt-get update && apt-get install -y postgresql-client

COPY ./sql/grant_user.sql ./

CMD psql -h postgresql -U service -d taskaround -f grant_user.sql
