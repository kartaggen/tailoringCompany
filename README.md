# Tailoring Company Management Application

[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

The project utilizes JavaFX, MySQL, Hibernate.

It features:
*	Company details (Tax/Salaries)
*   CRUD operations for Employees (sortable)
*   CRUD operations for Work Orders (sortable)
*   CRUD operations for Parts (sortable)
*   CRUD operations for Materials (sortable)
*	Accounting using preset tax and salaries
*	Total parts manufactured and parts manufactured by a specific employee

## Prerequisites
* Java 8 or higher.
* JavaFX
* Maven
* MySQL
* Hibernate

## Installation

Navigate to a new folder of your choice. Clone this repository.
```git
git clone https://github.com/kartaggen/tailoringCompany.git
```

Run "db/taloring_company.sql" script in MySQL. Initialize a user to access that database and update the hibernate config with the "hibernate.connection.url", "hibernate.connection.username" & "hibernate.connection.password".