# Instant Messaging Backend Service - REST API

The project aims to emulate the functionality of an instant messaging service (e.g. Discord, Google Workspace), i.e.
being able to create public and private discussions channels, sending and receiving instant messages, organizing
meetings in channels, sending attachements, notifying users of a new message or meeting in one of the channels 
they subscribed to.

The service is designed as REST API built with Jakarta EE, which will eventually be consumed by an Angular app.

## Use case

This project is part of Metz Numeric School's curriculum for Software Engineering, and the School acts as the customer,
i.e. expressing needs and specifications.

Metz Numeric School is interested in the benefits of already available instant messaging solutions but wishes for its 
data to remain closed to giants like Google or Discord.
The project is designed to help students apply design and programming knowledge on a product that could eventually
be used in production by the school.

## Features

### Planned features
* Creating Users and User Groups
* Creating Public and Private Channels
* Allow access to Users or User Groups to specific Channels
* Send and receiving instant Messages through WebSocket
* Adding File attachements to Messages
* Creating Meetings and inviting Users to partake in a specific Channel 
* Getting notified of new messages and meetings through WebSocket
* Managing User Authentication and Authorization via JWT

## Built With

* [Jakarta EE](https://jakarta.ee/)
* [MariaDB](https://mariadb.org/)

## Authors

* **AmbryN** - *Initial work* - [AmbryN](https://github.com/AmbryN)
