<h1 align="center">
    <img alt="Pismo" src="/assets/logo.png" /> 
    <br>
    Transactions API
</h1>

<h4 align="center">
  REST API for financial transactions control.
</h4>
<p align="center"> 
  <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/brunovalentino-dev/transactions-api">
  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/brunovalentino-dev/transactions-api">  
  <a href="https://github.com/brunovalentino-dev/transactions-api/commits/main">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/brunovalentino-dev/transactions-api">
  </a>
  <a href="https://github.com/brunovalentino-dev/transactions-api/issues">
    <img alt="Repository issues" src="https://img.shields.io/github/issues/brunovalentino-dev/transactions-api">
  </a>
  <img alt="GitHub license" src="https://img.shields.io/github/license/brunovalentino-dev/transactions-api">
</p>

<p align="center">
  <a href="#rocket-technologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#information_source-how-to-use">How To Use</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-license">License</a>
</p>

## :rocket: Technologies

This project was developed with the following technologies:

-  [Java 17][java17]
-  [Spring Boot][spring-boot]
-  [PostgreSQL][postgresql] 
-  [pgAdmin][pgadmin] 
-  [Apache Maven][apache-maven]
-  [JUnit 5][junit5]
-  [Mockito][mockito] 
-  [Docker][docker]

## :information_source: How To Use

To clone and run this application, you'll need [Git](https://git-scm.com), [Java 17][java17], [Apache Maven][apache-maven] 3.8.6 or higher and [Docker][docker] installed on your computer. From your command line:

```bash
# Clone this repository
$ git clone https://github.com/brunovalentino-dev/transactions-api

# Go into the repository
$ cd transactions-api

# Build the project
$ mvn clean package

# Go into the configuration folder
$ cd config/docker

# Run the app with Docker
$ docker-compose up --build
```

## :gift: [Bonus] How To Config pgAdmin

To use pgAdmin with this application, you'll need [Docker][docker] installed on your computer and make some configuration. From your command line and with pgAdmin + PostgreSQL containers running:

```bash
# Inspect PostgreSQL container to grab its IP address
$ docker inspect <container-id-for-postgres> | grep "IPAddress"
```

P.S.: Save this IP Address to enter in DB configuration later.

On your browser, go to http://localhost:54321 to finish configuration. 
Enter your credentials (username and password) - same defined in .env file at config/docker directory.

![Login](/assets/login.png)

Go to the server registration area:

![Dashboard](/assets/dashboard.png)

Enter a name for your server and then go to the connection tab:

![Server](/assets/server.png)

Enter IP Address copied before and your credentials as you defined in .env file.

![Connection](/assets/connection.png)

Done! :) 

## :memo: License
This project is under the MIT license. See the [LICENSE](https://github.com/brunovalentino-dev/transactions-api/blob/main/LICENSE) for more information.

---

Made with ♥ by Bruno Valentino :wave: [Get in touch!](https://www.linkedin.com/in/bruno-valentino-31278862)

[java17]: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
[spring-boot]: https://spring.io/projects/spring-boot
[postgresql]: https://www.postgresql.org/
[pgadmin]: https://www.pgadmin.org/
[apache-maven]: https://maven.apache.org/
[junit5]: https://junit.org/junit5/
[mockito]: https://github.com/mockito/mockito
[docker]: https://www.docker.com/
