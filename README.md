# Electro Urn API

A simple API written with Spring for voting candidates to various different roles then calculates most voted candidates per role, made as part of a software architecture assignment, from a course involving system analysis and development offered by [IFSul](http://www.ifsul.edu.br).<br/>
This project isn't intended to be an actual product and is meant to be used for educative purposes, for people looking to learn about [Spring](https://spring.io/).

> :warning: The API is currently in development and only a few basic calls do work, documentation and assignment specification is being gradually added as more features are added and tested to work.

## What are the specifications of the assignment?

* Voter can vote a candidate for a specific role, the assignment dictates there must be 5 roles one may choose from: "president", "governor", "senator", "federal representative" and "intrastate representative".
* There's two turns to vote: the first begins automatically, and the second via an API call (manually), but only if president and/or governor weren't chosen yet (the rest of the roles must have a "winner").
* Both candidates and turns needed to be loaded automatically upon system start-up: for that end, the structure is created via the Spring MVC framework and the sample data is then, loaded from the queries at [`/src/main/resources/data.sql`](/src/main/resources/data.sql).
* Send a request to vote for a candidate, and being able to submit a blank vote: to achieve this, the user sends data via POST as described in the next section. If the user skipped sending a candidate ID and/or role ID, the vote is automatically blank. ID of the voter is a must, otherwise the vote is rejected.
* As described before, calculate winners per role and end turn. Start second turn if there's at least a president or governor to be voted, the second turn will only end when there's a winner for these two missing roles.

## Calls to API

Below are some examples of calls you can do with [Postman](https://www.postman.com/) or [CURL](https://curl.se/) you can do, more will be added later:

* **GET:** `http://localhost:9999/candidate/1`: find and get data of a candidate that does exist.
* **GET:** `http://localhost:9999/candidate/1000`: get a response for a candidate that doesn't exist, since their ID is out of bounds from the range of IDs within supplied sample data.
* **POST:** `http://localhost:9999/candidate/reg`: register a sample candidate with generic information.
  * **Header:** `Content-Type: application/json`
  * **Body:** `{"first_name": "Test","last_name": "Candidate","date_birth": "1993-06-21"}`

If you're making your requests via Postman, please check the [`/postman_collections`](/postman_collections) folder on this repository for more information.
In this folder, you can find collections that can be downloaded and imported to Postman, these contain various examples of requests you can make to the API while it's working, they're labeled and have sample data.

## How to use

The API is set-up to be ran locally, I used [XAMPP](https://www.apachefriends.org/) with Apache and MySQL services running.
The files of this project can be unpacked anywhere, and with the IDE of preference, open the folder as a project and run the main executable, located at [`/src/main/java/com/afa1997/electrournapi/ElectroUrnApiApplication.java`](/src/main/java/com/afa1997/electrournapi/ElectroUrnApiApplication.java).

Upon first initiation, database is created automatically with all the tables and relevant relationships, as well as some sample data to help us test code quickly, so you won't need to do much tinkering.

## Plans

This project is planned to be updated post course completion to add new functionality and to keep practising and experimenting with Spring, I hope it'll be useful to you in your learning!

> :information_source: A wiki is going to be written later on, with resources that could help setting up things and learning Spring, it won't be a full-fledged course but it's planned to be a starting point anyone could use.