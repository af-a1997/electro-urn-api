# Electro Urn API

A simple API that uses Spring Boot for voting candidates to various different roles then calculates most voted candidates per role, made as part of a software architecture assignment, from a course involving system analysis and development offered by [IFSul](http://www.ifsul.edu.br).<br/>
This project isn't intended to be an actual product and is meant to be used for educative purposes, for people looking to learn about [Spring](https://spring.io/).

> :warning: The API is currently in development and only a few basic calls do work, documentation and assignment specification is being gradually added as more features are added and tested to work.

## Calls to API

Below, some examples of calls you can do with [Postman](https://www.postman.com/) or [CURL](https://curl.se/) you can do:

* **GET:** `http://localhost:9999/candidate/1`: find and get data of a candidate that does exist.
* **GET:** `http://localhost:9999/candidate/1000`: get a response for a candidate that doesn't exist, since their ID is out of bounds from the range of IDs within supplied sample data.
* **POST:** `http://localhost:9999/candidate/reg`: register a sample candidate with generic information.
  * **Header:** `Content-Type: application/json`
  * **Body:** `{"first_name": "Test","last_name": "Candidate","date_birth": "1993-06-21"}`

If you're making your requests via Postman, please check the [`/postman_collections`](/postman_collections) folder on this repository, in which you can find collections that can be downloaded and imported, these contain various examples of requests you can make to the API while it's working, they're labeled and have sample data.

## How to use

The API is set-up to be ran locally, I used [XAMPP](https://www.apachefriends.org/) with Apache and MySQL services running.
The files of this project can be unpacked anywhere, and with the IDE of preference, open the folder as a project and run the main executable, located at [`/src/main/java/com/afa1997/electrournapi/ElectroUrnApiApplication.java`](/src/main/java/com/afa1997/electrournapi/ElectroUrnApiApplication.java).

Upon first initiation, database is created automatically with all the tables and relevant relationships, as well as some sample data to help us test code quickly, so you won't need to do much tinkering.

## Plans

This project is planned to be updated post course completion to add new functionality and to keep practising and experimenting with Spring, I hope it'll be useful to you in your learning!

> :information_source: A wiki is going to be written later on, with resources that could help setting up things and learning Spring, it won't be a full-fledged course but it's planned to be a starting point anyone could use. 