# LikeMinded app service [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-green.svg)](https://github.com/sikumiku/likeminded-server/pulls)

![LikeMinded logo](https://i.imgur.com/KfMHj4c.png)

LikeMinded is an appication for tabletop enthusiasts that would like to find other tabletop gamers with similar tastes and organize groups as well as ongoing events.

This repository is for the service portion of it that houses business logic and data.
Frontend repository can be found [here](https://github.com/sikumiku/likeminded-client).

- [Used technologies](#used-technologies) – Used technologies in service
- [Contributing](#contributing) – How to set up local development environment for making any changes
- [Major endpoints](#major-endpoints) – Major endpoints (controllers)
- [Authors](#authors) - Contributing authors

If you see anything not working, feel free to report [issues here](https://github.com/sikumiku/likeminded-server/issues/new).

## Quick Overview

### Used technologies

- Sprint Boot
- DB: PostgreSQL
- Hibernate
- Flyway
- Gradle
- REST API
- Docker
- Spring Security
- Hosting platform: Heroku

### Contributing

Everybody is welcome to contribute to the project. Some tips on getting started:

1. git clone git@github.com:sikumiku/likeminded-server.git
2. cd docker
3. docker-compose up // setup local DB with basic user roles
4. Migrations: add new file to migrations folder following format of `V000__name_of_your_migration_change.sql`
5. Use gradle Bootrun task with VM option `-Dspring.profiles.active=development`

For setting up frontend, follow the guide at the client repository, but for testing API endpoints, the service will be hosted on http://localhost:8080

### Major endpoints

- **Authorization**: `/api/auth`
- **Events**: `/api/v1/events`
- **Groups**: `/api/v1/groups`
- **People**: `/api/v1/people`
- **Users**: `/api/v1/users`

## Authors

LikeMinded thanks the following people for contribution:
- [sikumiku](https://github.com/sikumiku)
