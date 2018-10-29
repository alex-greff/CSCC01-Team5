# Team5

## Team Members
* Alexander Greff
* Saad Syed Ali
* Mohammed Osumah
* Gyeongwon Choi

## Setting Up

### Production
(TODO...)

### Development Environment
- Java 1.8.0 or above installed
- Install [Maven 3.5.4](https://maven.apache.org/download.cgi)
- Compile the project
``` $ mvn compile ```


### Testing
``` $ mvn test ```

## Deliverables

### Deliverable 1: Team Setup
* [Setup Report](team_setup/Team_Information.pdf)
* [Team Expectation Agreement](team_setup/Team_Expectation_Agreement.pdf)

### Deliverable 2: Project Requirements
* [Personas v0](product_backlog/v0/personas_0.pdf)
* [User Stories v0](product_backlog/v0/user_stories_0.pdf)

### Deliverable 3: Project Planning and Execution
#### Product Backlog
* [Personas v1](product_backlog/v1/personas_1.pdf)
* [User Stories v1](product_backlog/v1/user_stories_1.pdf)
* [Tasks v1](product_backlog/v1/tasks_1.pdf)

#### Sprint Backlog
##### Sprint 1
* [plan.xlsx](sprint_backlog/sprint1/plan.xlsx)
* [execution.xlsx](sprint_backlog/sprint1/execution.xlsx)
* [burndown.xlsx](sprint_backlog/sprint1/burndown.xlsx)

##### Sprint 2
* [plan.xlsx](sprint_backlog/sprint2/plan.xlsx)
* [execution.xlsx](sprint_backlog/sprint2/execution.xlsx)
* [burndown.xlsx](sprint_backlog/sprint2/burndown.xlsx)

##### Trello Board
<p align="left">
    <img src="product_backlog/trello_screenshot.png" width="1000" title="Trello Screenshot">
</p>

#### Running the Code

As of sprint 2 our systems are still indepentent of each other so they must all be compiled separately.

For the convenience of the grader we have compiled small demos of each system in their respective main functions. 

Run these commands from the project root:

``` $ mvn compile ```

* **Uploader GUI Interface:** 

    ``` $ mvn exec:java@uploader-interface-demo ```

* **Admin GUI Interface:**

    ``` $ mvn exec:java@admin-interface-demo ```

* **Database Driver:**

    ``` $ mvn exec:java@database-driver-demo ```

* **Configuration Loader:** 

    ``` $ mvn exec:java@config-loader-demo ```

* **JSON Loader:** 

    ``` $ mvn exec:java@json-loader-demo ```

<!-- * **Template System:** TODO -->

### Deliverable 4: Product Validation
(In-Progress...)

### Deliverable 5: Final Deliverable
(In-Progress...)
