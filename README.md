# Spring Boot Starter Template
_by [PollitoDev](https://pollitodev.netlify.app/)_

Starting point for future projects, designed to embrace Component-Driven Development (CDD) practices. It encapsulates essential dependencies and best-practice boilerplates.

Let's do a run of each relevant file content.

### aspect.LoggingAspect

Centralized logging logic across the application, particularly for controller methods.

### config.LogFilterConfig

Configuration class in a Spring Boot application, dedicated to setting up a custom filter, specifically filter.**LogFilter**.

By default, it ensures that LogFilter is applied globally to all requests.

### config.WebConfig

Configuration class focusing on [Cross-Origin Resource Sharing (CORS) settings](https://www.baeldung.com/spring-cors).

By default, it allows cross-origin requests from any source, which is good for dev purposes. However this might not be suitable for a production environment.

### controller.AnimeInfoController

[Rest Controller](https://www.baeldung.com/spring-controller-vs-restcontroller) that serves as an example of how to implement a generated provider interface (what is a generated provider interface? more on that later). It calls the default methods within the implemented interface.

It is meant to be deleted and replaced with your own implementation.

### controller.advice.GlobalControllerAdvice

[Global exception handler](https://www.bezkoder.com/spring-boot-restcontrolleradvice/) designed to catch and handle various exceptions that may occur during the processing of web requests.

By default, it handles:

- **MissingServletRequestParameterException:** Caught when required request parameters are missing.
- **ConstraintViolationException:** Handled when Bean Validation API constraint violations occur.
- **MethodArgumentTypeMismatchException:** Caught when a method argument is not the expected type.
- **MethodArgumentNotValidException:** Handled when an argument annotated with @Valid fails validation.
- **Exception:** A generic catch-all for any other exceptions not explicitly handled by the other methods.

Each handler method returns a ResponseEntity\<Error\>, where Error is meant to be a schema in your provider OAS file.

By default also contains the annotation [@Order()](https://www.baeldung.com/spring-order) empty with no args. This means that the advice runs last in relation to other @ControllerAdvice components.

### filter.LogFilter

It implements a [servlet filter](https://www.geeksforgeeks.org/spring-boot-servlet-filter/) for logging request and response details.

- It initializes the [Mapped Diagnostic Context (MDC)](https://www.baeldung.com/mdc-in-log4j-2-logback) with a unique session ID for each request, facilitating easier tracing of logs related to specific requests.
- It logs details about the incoming request, including its method, URI, query string, and headers.
- After the request has been processed, it also logs the response status.

### util.Constants

Container for application-wide constants.

By default it only contains the constant SLF4J_MDC_SESSION_ID_KEY, used in **LogFilter** as a key in the SLF4J Mapped Diagnostic Context (MDC) for storing and retrieving a unique session identifier.

Feel free to add all your needed constants here.

### util.ErrorResponseBuilder

Utility for constructing error response entities, so all error responses have the same structure.

### SpringBootStarterTemplateApplication

Default entry point to a Spring Boot application.

### Resources > openapi files

This is a totally arbitrary folder inside resources where I decided is good enough to put all the OAS files.

Is important to remember that in the definition of contract I made up, we need to comply with inputs, outputs, and errors.

> Microservices must comply with a contract, which defines inputs, outputs, and errors.

So, each contract should have an Error-like schema (no need for it to be named Error), which after generation tasks, will be used for constructing standardized error responses.

Here is my recommended Error schema, and the one being used in this template

```yaml
Error:
  type: object
  properties:
    timestamp:
      type: string
      description: The date and time when the error occurred in ISO 8601 format.
      format: date-time
      example: "2024-01-04T15:30:00Z"
    session:
      type: string
      format: uuid
      description: A unique UUID for the session instance where the error happened, useful for tracking and debugging purposes.
    error:
      type: string
      description: A brief error message or identifier.
    message:
      type: string
      description: A detailed error message.
    path:
      type: string
      description: The path that resulted in the error.
```

Feel free to organize files in subfolders if necessary. Don't forget to maintain consistency in the pom.xml (more on that later).

### Resources > logback.xml

Configuration to log information to the console, with a customized pattern that includes a session ID for better traceability of log entries.

### pom.xml

- This template uses [Spring Boot 3.2.3](https://github.com/spring-projects/spring-boot/releases/tag/v3.2.3) and [Java 21](https://www.geeksforgeeks.org/java-jdk-21-new-features-of-java-21/)
- Basic dependencies: you'll find these in almost every Spring Boot 3 application out there:
  - [org.springframework.boot » spring-boot-starter-web » 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/3.2.3): Essential for building web applications using Spring Boot
  - [org.springframework.boot » spring-boot-devtools » 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools/3.2.3): Set of tools that make the development process with Spring Boot more efficient.
  - [org.springframework.boot » spring-boot-configuration-processor » 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor/3.2.3): Generates metadata for configuration properties, making them easier to work with in IDEs.
  - [org.projectlombok » lombok » 1.18.30](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.30): Reduces boilerplate code like getters, setters, and constructors through annotations.
  - [org.springframework.boot » spring-boot-starter-test » 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test/3.2.3): Includes support for JUnit, Spring Test & Spring Boot Test, AssertJ, Hamcrest, and a bunch of other libraries necessary for thorough testing.
- Dependencies required by [org.openapitools » openapi-generator-maven-plugin » 7.4.0](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin/7.4.0) when generating provider code:
  - [io.swagger.core.v3 » swagger-core-jakarta » 2.2.20](https://mvnrepository.com/artifact/io.swagger.core.v3/swagger-core-jakarta/2.2.20): Solves error package io.swagger.v3.oas.annotations does not exist.
  - [org.openapitools » jackson-databind-nullable » 0.2.6](https://mvnrepository.com/artifact/org.openapitools/jackson-databind-nullable/0.2.6): Solves error package org.openapitools.jackson.nullable does not exist.
  - [org.springframework.boot » spring-boot-starter-validation » 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/3.2.3): Solves validations being ignored.
- Dependency required by AOP practices:
  - [org.aspectj » aspectjweaver » 1.9.21.2](https://mvnrepository.com/artifact/org.aspectj/aspectjweaver/1.9.21.2): Modifies Java classes to weave in the aspects.
- Plugins:
  - [org.springframework.boot » spring-boot-maven-plugin » 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-maven-plugin/3.2.3) : Used for explicitly excluding lombok from the final packaged application, since Lombok is a compile-time only tool that helps reduce boilerplate code.
  - [com.spotify.fmt » fmt-maven-plugin » 2.23](https://mvnrepository.com/artifact/com.spotify.fmt/fmt-maven-plugin/2.23): Formats your Java source code to comply with [Google Java Format](https://google.github.io/styleguide/javaguide.html).
  - [org.openapitools » openapi-generator-maven-plugin » 7.4.0](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin/7.4.0): A Maven plugin to support the OpenAPI generator project. Is a code generator that embraces CDD practices. By default it's configured like this:

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.2.0</version>
    <executions>
        <execution>
            <id>provider generation: animeinfo.yaml</id>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/openapi/animeinfo.yaml</inputSpec>
                <generatorName>spring</generatorName>
                <output>${project.build.directory}/generated-sources/openapi/</output>
                <apiPackage>dev.pollito.springbootstartertemplate.api</apiPackage>
                <modelPackage>dev.pollito.springbootstartertemplate.models</modelPackage>
                <configOptions>
                    <interfaceOnly>true</interfaceOnly>
                    <useSpringBoot3>true</useSpringBoot3>
                    <useEnumCaseInsensitive>true</useEnumCaseInsensitive>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Let's check what's going on here.

- **Group ID and Artifact ID:** Identifies the OpenAPI Generator plugin itself.
- **Version:** Specifies the version of the OpenAPI Generator plugin.
- **Execution block:** Defines when and how the plugin's goal(s) should be executed within the build lifecycle.
  - **ID:** A unique identifier for this execution instance.
  - **Goals:** Specifies the generate goal, which tells the plugin to perform code generation.
  - **Configuration Block:** Provides detailed instructions on how the code generation should be performed.
    - **inputSpec:** Points to the location of the OpenAPI spec file that does the role of being the provider contract. In this case, animeinfo.yaml located under src/main/resources/openapi/ .
    - **generatorName:** Specifies the spring generator, indicating that the code should be generated with Spring in mind, tailoring the output for Spring-based projects.
    - **output:** The directory where the generated code should be placed. Personally I think target/generated-sources/openapi/ directory is a good place.
    - **apiPackage and modelPackage:** Define the Java package names for the generated API interfaces and model classes, respectively. These values are up to you. Personally I like to use the OAS file server url in reverse url notation. In case that info is not available, using the project groupId + artifactId is common practice
    - **configOptions:** A set of additional configuration options.
      - **interfaceOnly:** It set to true. You will need to create your own implementation anyways.
      - **useSpringBoot3:** Ensures compatibility with Spring Boot 3.
      - **useEnumCaseInsensitive:** If there are generated enums, it is configured to be case-insensitive, adding flexibility to how their values are deserialized.

### uml diagram

The base template on this branch feature/provider-gen looks like this:

![uml](./uml.jpg)

### How to run

1. maven clean + maven compile. This will generate the code in target/generated-sources/openapi/
2. run the application
3. execute this curl to check that everything is working

```shell
curl --location 'http://localhost:8080/anime?id=846'
```

Should get the default response from the interface, an empty body with status 501.

You can also see that in the console:

```
2024-03-18 19:52:42 INFO  d.p.s.filter.LogFilter [SessionID: 6ede5e72-8d98-4289-adc5-f7539275f8d9] - >>>> Method: GET; URI: /anime; QueryString: id=846; Headers: {user-agent: PostmanRuntime/7.37.0, accept: */*, cache-control: no-cache, postman-token: 2c6b8a8b-0253-4bdb-b3b2-1fae2c16191a, host: localhost:8080, accept-encoding: gzip, deflate, br, connection: keep-alive}
2024-03-18 19:52:42 INFO  d.p.s.aspect.LoggingAspect [SessionID: 6ede5e72-8d98-4289-adc5-f7539275f8d9] - [AnimeInfoController.getAnimeStatisticsViewers(..)] Args: [846]
2024-03-18 19:52:42 INFO  d.p.s.aspect.LoggingAspect [SessionID: 6ede5e72-8d98-4289-adc5-f7539275f8d9] - [AnimeInfoController.getAnimeStatisticsViewers(..)] Response: <501 NOT_IMPLEMENTED Not Implemented,[]>
2024-03-18 19:52:42 INFO  d.p.s.filter.LogFilter [SessionID: 6ede5e72-8d98-4289-adc5-f7539275f8d9] - <<<< Response Status: 501
```
