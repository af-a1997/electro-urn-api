# Electro Urn API

A simple API for voting candidates to various different roles, made as part of a software architecture assignment, from a course involving system analysis and development offered by [IFSul](http://www.ifsul.edu.br).

> :warning: The API is currently in development and only a few basic calls do work, documentation and assignment specification will be added later but here's some examples of the requests you can make (recommended to use [Postman](https://www.postman.com/)). Host files on a folder, run project with your preference IDE and [XAMPP](https://www.apachefriends.org/download.html) installed and running Apache and MYSQL.

* **GET:** `http://localhost:9999/candidate/1` (find and get data of a candidate that does exist)
* **GET:** `http://localhost:9999/candidate/1000` (get a response for a candidate that doesn't exist)
* **POST:** `http://localhost:9999/candidate/reg` (register a sample candidate)
  * **Header:** `Content-Type: application/json`
  * **Body:** `{"first_name": "Test","last_name": "Candidate","date_birth": "1993-06-21"}`