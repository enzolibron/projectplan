# Project Plan

## Description

Calculates schedules for project task.

## Getting Started

- Clone project: https://github.com/enzolibron/projectplan
- run 'mvn clean install'
- run 'mvn spring-boot:run'
    - app will run on localhost:8080

### Prerequisites

- Java 17
- Maven

# Endpoints

- ## Create a project

#### POST *localhost:8080/api/projects*

__Request Body__
  ```
  {
      "name": "Project One",
      "startDate":"11-14-2024"
  }
  ```
__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One",
      "tasks": [],
      "startDate": "2024-11-14",
      "endDate": "2024-11-14"
  }
  ```

- ## Create a task inside a project

#### POST *localhost:8080/api/projects/{projectId}/tasks*

__Request Body__
  ```
  {
      "name":"Requirements Gathering",
      "duration":4,
      "dependencies": []
  }
  ```
- duration field value is in days
- dependencies field value is a list of task that are pre-requisite to the new task

__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One",
      "tasks": [
          {
              "id": 1,
              "name": "Requirements Gathering",
              "duration": 4,
              "dependencies": [],
              "startDate": "11-14-2024",
              "endDate": "11-17-2024"
          }
      ],
      "startDate": "11-14-2024",
      "endDate": "11-17-2024"
  }
  ```

another add task sample w/ dependency

__Request Body__
  ```
  {
      "name":"Requirements Analysis",
      "duration":4,
      "dependencies": [1]
  }
  ```

__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One",
      "tasks": [
          {
              "id": 1,
              "name": "Requirements Gathering",
              "duration": 4,
              "dependencies": [],
              "startDate": "11-14-2024",
              "endDate": "11-17-2024"
          },
          {
              "id": 2,
              "name": "Requirements Analysis",
              "duration": 4,
              "dependencies": [
                  1
              ],
              "startDate": "11-18-2024",
              "endDate": "11-21-2024"
          }
      ],
      "startDate": "11-14-2024",
      "endDate": "11-21-2024"
  }
  ```

- ## Update a task inside a project

#### PUT *localhost:8080/api/projects/{projectId}/tasks*

__Request Body__
  ```
  {
      "name": "REQ ANALYSIS",
      "duration": 2,
      "dependencies": [
          1
      ]
  }
  ```

__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One",
      "tasks": [
          {
              "id": 1,
              "name": "REQ GATHERING",
              "duration": 2,
              "dependencies": [],
              "startDate": "11-14-2024",
              "endDate": "11-15-2024"
          },
          {
              "id": 2,
              "name": "REQ ANALYSIS",
              "duration": 2,
              "dependencies": [
                  1
              ],
              "startDate": "11-16-2024",
              "endDate": "11-17-2024"
          },
          {
              "id": 3,
              "name": "DEV SETUP",
              "duration": 2,
              "dependencies": [
                  1
              ],
              "startDate": "11-18-2024",
              "endDate": "11-19-2024"
          }
      ],
      "startDate": "11-14-2024",
      "endDate": "11-19-2024"
  }
  ```

- ## Get a project by id

#### GET *localhost:8080/api/projects/{id}*

__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One",
      "tasks": [
          {
              "id": 1,
              "name": "Requirements Gathering",
              "duration": 4,
              "dependencies": [],
              "startDate": "11-14-2024",
              "endDate": "11-17-2024"
          },
          {
              "id": 2,
              "name": "Requirements Analysis",
              "duration": 4,
              "dependencies": [
                  1
              ],
              "startDate": "11-18-2024",
              "endDate": "11-21-2024"
          }
      ],
      "startDate": "11-14-2024",
      "endDate": "11-21-2024"
  }
  ```


- ## Get all projects

#### GET *localhost:8080/api/projects*

__Response Body__
  ```
  [
      {
          "id": 1,
          "name": "Project One",
          "tasks": [
              {
                  "id": 1,
                  "name": "Requirements Gathering",
                  "duration": 4,
                  "dependencies": [],
                  "startDate": "11-14-2024",
                  "endDate": "11-17-2024"
              },
              {
                  "id": 2,
                  "name": "Requirements Analysis",
                  "duration": 4,
                  "dependencies": [
                      1
                  ],
                  "startDate": "11-18-2024",
                  "endDate": "11-21-2024"
              }
          ],
          "startDate": "11-14-2024",
          "endDate": "11-21-2024"
      },
      {
          "id": 2,
          "name": "Project Two",
          "tasks": [
              {
                  "id": 3,
                  "name": "Dev Environment Setup",
                  "duration": 4,
                  "dependencies": [],
                  "startDate": "11-14-2024",
                  "endDate": "11-17-2024"
              }
          ],
          "startDate": "11-14-2024",
          "endDate": "11-17-2024"
      }
  ]
  ```

- ## Delete a project by id

#### DELETE *localhost:8080/api/projects/{id}*


__Response Body__
  ```
  deleted successfully
  ```

- ## Delete all tasks project by projectId

#### DELETE *localhost:8080/api/projects/{id}/tasks*


__Response Body__
  ```
  deleted successfully
  ```


   



