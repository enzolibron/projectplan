# Project Plan

## Description

Calculates project task schedules.

## Getting Started

- Clone project: https://github.com/enzolibron/projectplan
- run 'mvn clean install'
- run 'mvn spring-boot:run'
    - app will run on localhost:8080

### Prerequisites

- Java 17
- Maven

# Endpoints

 ## 1. Create a project

#### POST *localhost:8080/api/projects*

__Request Body__
  ```
  {
      "name": "Project One",
      "startDate":"11-17-2025"
  }
  ```
__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One",
      "tasks": [],
      "startDate": "11-17-2025",
      "endDate": "11-17-2025"
  }
  ```


## 2. Create a task inside a project

#### POST *localhost:8080/api/projects/{projectId}/tasks*

__Request Body__
  - duration field value is in days
  ```
  {
      "name":"Requirements Gathering",
      "duration": 3
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
              "duration": 3,
              "dependencies": [],
              "startDate": "11-17-2025",
              "endDate": "11-19-2025"
          }
      ],
      "startDate": "11-17-2025",
      "endDate": "11-19-2025"
  }
  ```

another add task sample w/ dependency

__Request Body__
  - dependencies field value is a list of task that are pre-requisite to the new task
  ```
  {
      "name":"Requirements Analysis",
      "duration": 4,
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
              "duration": 3,
              "dependencies": [],
              "startDate": "11-17-2025",
              "endDate": "11-19-2025"
          },
          {
              "id": 2,
              "name": "Requirements Analysis",
              "duration": 4,
              "dependencies": [
                  1
              ],
              "startDate": "11-20-2025",
              "endDate": "11-23-2025"
          }
      ],
      "startDate": "11-17-2025",
      "endDate": "11-23-2025"
  }
  ```

## 3. Update a task inside a project

#### PATCH *localhost:8080/api/projects/{projectId}/tasks/{taskId}*

__Request Body__
  - you can update fields name, duration, and dependencies
  ```
  {
      "duration": 2
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
              "duration": 2,
              "dependencies": [],
              "startDate": "11-17-2025",
              "endDate": "11-18-2025"
          },
          {
              "id": 2,
              "name": "Requirements Analysis",
              "duration": 4,
              "dependencies": [
                  1
              ],
              "startDate": "11-19-2025",
              "endDate": "11-22-2025"
          }
      ],
      "startDate": "11-17-2025",
      "endDate": "11-22-2025"
  }
  ```

## 4. Update a project

#### PATCH *localhost:8080/api/projects/{projectId}*

__Request Body__
  ```
  {
      "startDate": "11-12-2024",
      "name": "Project One v2.0"
  }
  ```
__Response Body__
  ```
  {
      "id": 1,
      "name": "Project One v2.0",
      "tasks": [
          {
              "id": 1,
              "name": "Requirements Gathering",
              "duration": 2,
              "dependencies": [],
              "startDate": "11-12-2024",
              "endDate": "11-13-2024"
          },
          {
              "id": 2,
              "name": "Requirements Analysis",
              "duration": 4,
              "dependencies": [
                  1
              ],
              "startDate": "11-14-2024",
              "endDate": "11-17-2024"
          }
      ],
      "startDate": "11-12-2024",
      "endDate": "11-17-2024"
  }
  ```

## 5. Get a project by id

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
              "duration": 2,
              "dependencies": [],
              "startDate": "11-17-2025",
              "endDate": "11-18-2025"
          },
          {
              "id": 2,
              "name": "Requirements Analysis",
              "duration": 4,
              "dependencies": [
                  1
              ],
              "startDate": "11-19-2025",
              "endDate": "11-22-2025"
          }
      ],
      "startDate": "11-17-2025",
      "endDate": "11-22-2025"
  }
  ```


## 6. Get all projects

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
                  "duration": 2,
                  "dependencies": [],
                  "startDate": "11-17-2025",
                  "endDate": "11-18-2025"
              },
              {
                  "id": 2,
                  "name": "Requirements Analysis",
                  "duration": 4,
                  "dependencies": [
                      1
                  ],
                  "startDate": "11-19-2025",
                  "endDate": "11-22-2025"
              }
          ],
          "startDate": "11-17-2025",
          "endDate": "11-22-2025"
      },
      {
          "id": 2,
          "name": "Project Two",
          "tasks": [],
          "startDate": "11-17-2025",
          "endDate": "11-17-2025"
      },
      {
          "id": 3,
          "name": "Project Three",
          "tasks": [],
          "startDate": "11-25-2025",
          "endDate": "11-25-2025"
      }
  ]
  ```

## 7. Delete a project by id

#### DELETE *localhost:8080/api/projects/{id}*


__Response Body__
  ```
  deleted successfully
  ```

## 8. Delete all tasks project by projectId

#### DELETE *localhost:8080/api/projects/{id}/tasks*


__Response Body__
  ```
  deleted successfully
  ```

## 9. Delete a task inside a project

#### DELETE *localhost:8080/api/projects/{projectId}/tasks/{taskId}*


__Response Body__
  ```
  deleted successfully
  ```


   



