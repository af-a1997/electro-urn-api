# Electro Urn API

A simple API that uses Spring Boot for voting candidates to various different roles and gets most voted candidates, made as part of a software architecture assignment, from a course involving system analysis and development offered by [IFSul](http://www.ifsul.edu.br).

> :warning: The API is currently in development and only a few basic calls do work, documentation and assignment specification is being gradually added.

Below, some examples of calls you can do with [Postman](https://www.postman.com/) or [CURL](https://curl.se/) you can do.

* **GET:** `http://localhost:9999/candidate/1` (find and get data of a candidate that does exist)
* **GET:** `http://localhost:9999/candidate/1000` (get a response for a candidate that doesn't exist)
* **POST:** `http://localhost:9999/candidate/reg` (register a sample candidate)
  * **Header:** `Content-Type: application/json`
  * **Body:** `{"first_name": "Test","last_name": "Candidate","date_birth": "1993-06-21"}`

If you're making your requests via Postman, please check the [`/postman_collections`](/postman_collections) folder on this repository, in which you can find collections that can be downloaded and imported, these contain various examples of requests you can make to the API while it's working, they're labeled and have sample data.

The API is set to be ran locally, I used XAMPP with Apache and MySQL enabled, the files can be unpacked anywhere, and with the IDE of preference, open the folder as a project and run it.