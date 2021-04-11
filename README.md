# api-company
A project to learning Spring Boot

## Goals 

Practice of training in Java, Spring Boot, Hibernate, Spring Data with main objectives:

* Create an API using Spring Boot and Spring Data technology.
* Develop RESTful APIs.
* Practice the use of good practices in the development of APIs.
* Test the API with the POSTMAN tool.
* Build unit test.

## Implementation of the Company API

For this practical work, we want to develop an API for the data model below. 

* [Data model](https://drive.google.com/file/d/1mxjh0n0QdoO2f9w6qo44cWuvBFYnE3Xi/view?usp=sharing)

The API must be built in Java, that is, using the Spring Boot framework with integration with the MySQL database.

Entities must be stored, searched, changed and removed from that bank.

Docker must be used.

## Problem

Consider the physical model of the database.

* [Data model](https://drive.google.com/file/d/1mxjh0n0QdoO2f9w6qo44cWuvBFYnE3Xi/view?usp=sharing)

This model includes the entities of a company where the entities are: Employee, Address, Project, Project-Employee and Department.

As can be seen in the model, an employee has a supervisor who is also an employee of the company and the employee has information such as name, age, sex and the address which is a separate entity.

Consider that an employee has only one address and an address is associated with only one employee.

An employee can be on several projects within the company and the project has a department.

And a department can have several projects.

## ToDo
- Development of CRUD operations for the Employee, Address, Project, and Department entities.
 - API to retrieve all employees in a specific department;
 - API to retrieve employees according to their name;
 - API to retrieve all the projects that an employee worked on;
 - API to retrieve employees who are under the supervision of a specific supervisor;
 - Develop an API that returns the status of the department's budget:
    - GREEN - The cost of the projects is equal to or below the value of the department's budget;
    - YELLOW - The cost of the projects is above the budget by up to 10%.
    - RED - The cost of the projects is over budget exceeding 10%.
 - The department has the budget information which is a value and the period with a start date and an end date.
 - The project has the value information and start date and end date.
 - Employee has the salary data.
 - Remember that the cost of a project is given by the value of the project plus the salary of the employees who will participate in the project.

# Install and running

Run maven clean/install comands:

```sh
mvn clean install -DskipTests -U
```

Execute the shell script in the root of the project:

* `run.sh` (run application)
* `stop.sh` (stop application)


# Docs

## /api/v1/company/employee
* [GET] /
    * Get a employee list
* [GET] /{name}
    * Get employee info by name
* [POST] /
    * Store a new employee
* [PUT] /{id}
    * Update employee
* [DELETE] /{id}
    * Remove employee
* [GET] /{id}/projects
    * Get a employee projects list
* [GET] /{id}/supervisedEmployees
    * Get a supervised employee list
## /api/v1/company/address
* [GET] /
    * Get a address list
* [GET] /{id}
    * Get address info
* [PUT] /{id}
    * Update address
## /api/v1/company/department
* [GET] /
    * Get a department list
* [GET] /{id}
    * Get department info
* [POST] /
    * Store a new department
* [PUT] /{id}
    * Update department
* [DELETE] /{id}
    * Remove department
* [GET] /{id}/employees
    * Get a department employee list
* [GET] /{id}/budgetStatus
    * Get a department budget list
* [GET] /{id}/projects
    * Get a department project list
## /api/v1/company/project
* [GET] /
    * Get a project list
* [GET] /{id}
    * Get project info
* [POST] /
    * Store a new project
* [PUT] /{id}
    * Update project
* [DELETE] /{id}
    * Remove project
* [GET] /{id}/registerEmployee/{employeeId}
    * Register a employee in a project
* [GET] /{id}/unregisterEmployee/{employeeId}
    * Unregister a employee in a project
## /api/v1/company/budget
* [GET] /{id}
    * Get budget info
* [POST] /
    * Store a new budget
* [PUT] /{id}
    * Update budget
* [DELETE] /{id}
    * Remove budget
