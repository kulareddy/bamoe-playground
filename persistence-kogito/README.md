# persistence-9.0.1.Final

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


# infinispan
```
ddocker run --name infinispan \
    -p 11222:11222 \
    -e USER="admin" \
    -e PASS="changeme" \
    -d infinispan/server
```

# mongodb
```
docker run --name mongodb \
    -p 27017:27017 \
    -e MONGO_INITDB_ROOT_USERNAME=admin \
    -e MONGO_INITDB_ROOT_PASSWORD=changeme \
    -d mongo
```
# postgres persistence
```
docker run --name postgres \
    -e POSTGRES_USER=quarkus \
    -e POSTGRES_PASSWORD=Quarkus123 \
    -e POSTGRES_DB=kogito \
    -p 5432:5432 \
    -d postgres:14
```
# mssql persistence
```
docker run --name sql_server_2022 \
    -e 'ACCEPT_EULA=Y' \
    -e 'SA_PASSWORD=Quarkus123' \
    -p 1433:1433  \
    -d mcr.microsoft.com/mssql/server:2022-latest

CREATE DATABASE kogito;
GO
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/persistence-9.0.1.Final-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
