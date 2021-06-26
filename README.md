# AddressBookService

JSON service for getting information by regular expression.
Which get information about person names except symbols mashed in regular expression.

#### Start application

Apache Tomcat v8.5 <br>
Change full path for parameters file to csv data file (contains in resource directory)

```text
main.properties
dataFile.path=~/Workspace/AddressBookService/src/test/resources/AddressNameTest.csv
```
And will start war file in Tomcat server

#### Query example

```text
GET
http://localhost:8080/AddressBook/hello/contacts?nameFilter=^A.*$
```

#### Request example

```json
{"contacts":
  [
    {"id":1,"name":"Jacob"},
    {"id":2,"name":"Michael"},
    {"id":3,"name":"Ethan"},
    {"id":4,"name":"Joshua"},
    {"id":5,"name":"Daniel"},
    {"id":8,"name":"William"},
    {"id":9,"name":"Christopher"},
    {"id":10,"name":"Matthew"}
  ]
}
```

#### Query example

```text
GET
http://localhost:8080/AddressBook/hello/contacts?nameFilter=^.*[aey].*$
```

#### Request example

```json
{"contacts":
    [
        {"id":20,"name":"John"},
        {"id":45,"name":"Justin"},
        {"id":55,"name":"Austin"},
        {"id":57,"name":"Connor"},
        {"id":64,"name":"Luis"},
        {"id":86,"name":"Eric"},
        {"id":93,"name":"Dominic"},
        {"id":98,"name":"Colton"},
        {"id":100,"name":"Eli"}
    ]
}
```

---

## DataBase

[H2 Engine](https://www.h2database.com/html/main.html) <br>
Application use in memory H2 database