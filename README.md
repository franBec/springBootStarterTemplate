# Spring Boot Starter Template
_by [PollitoDev](https://pollitodev.netlify.app/)_

## Purpose

Starting point for future projects, designed to embrace Component-Driven Development (CDD) practices. It encapsulates essential dependencies and best-practice boilerplates.

## Classes analysis

### dev.pollito.springbootstartertemplate.aspect.LoggingAspect

Centralized logging logic across the application, particularly for controller methods.

#### [RECOMENDED PRACTICE] Log APIs args and return values

If your application has client APIs, I highly recommend also logging the @Before and @AfterReturning of those API methods.

Example: Let's imagine there's a com.weatherstack.api package which contains interfaces related to getting the weather. Then I would edit the LoggingAspect file as follows:

```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Pointcut("execution(* com.weatherstack.api.*.*(..))")
  public void weatherstackApiMethodsPointcut() {}

  @Pointcut("execution(public * dev.pollito.springbootstartertemplate.controller..*.*(..))")
  public void controllerPublicMethodsPointcut() {}

  @Before("weatherstackApiMethodsPointcut() || controllerPublicMethodsPointcut()")
  public void logBefore(JoinPoint joinPoint) {
    log.info(
        "["
            + joinPoint.getSignature().toShortString()
            + "] Args: "
            + Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(
      pointcut = "weatherstackApiMethodsPointcut() || controllerPublicMethodsPointcut()",
      returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    log.info("[" + joinPoint.getSignature().toShortString() + "] Response: " + result);
  }
}
```

Changes made:

- Creating a new @Pointcut, whose excecution value is the package containing the API interfaces
- Adding the recently created pointcut to the @Before and @AfterReturning annotated methods.

Do this process to every package you consider is worth logging its public methods arguments and results.

### dev.pollito.springbootstartertemplate.config.LogFilterConfig

Configuration class in a Spring Boot application, dedicated to setting up a custom filter, specifically dev.pollito.springbootstartertemplate.filter.**LogFilter**.

By default, it ensures that LogFilter is applied globally to all requests.

### dev.pollito.springbootstartertemplate.config.WebConfig

Configuration class focusing on Cross-Origin Resource Sharing (CORS) settings.

By default, it allows cross-origin requests from any source, which is good for dev purposes. However this might not be suitable for a production environment.

### dev.pollito.springbootstartertemplate.controller.PetsController

Controller that serves as an example of how to implement a generated interface. Just for demonstration purposes. It calls the default methods within implemented interface. 


### dev.pollito.springbootstartertemplate.controller.advice.GlobalControllerAdvice

Global exception handler designed to catch and handle various exceptions that may occur during the processing of web requests.

By default, it handles:

- MissingServletRequestParameterException: Caught when required request parameters are missing.
- ConstraintViolationException: Handled when Bean Validation API constraint violations occur.
- MethodArgumentTypeMismatchException: Caught when a method argument is not the expected type.
- MethodArgumentNotValidException: Handled when an argument annotated with @Valid fails validation.
- Exception: A generic catch-all for any other exceptions not explicitly handled by the other methods.

Each handler method returns a ResponseEntity<Error>, where Error is meant to be a custom object created in your provider OAS yaml file.

By default also contains the annotation @Order() empty with no args. This means that the advice runs last in relation to other @ControllerAdvice components.

### dev.pollito.springbootstartertemplate.filter.LogFilter

It implements a servlet filter for logging request and response details.

- It initializes the Mapped Diagnostic Context (MDC) with a unique session ID for each request, facilitating easier tracing of logs related to specific requests.
- It logs details about the incoming request, including its method, URI, query string, and headers.
- After the request has been processed, it also logs the response status.

### dev.pollito.springbootstartertemplate.util.Constants

Container for application-wide constants.

By default it only contains the constant SLF4J_MDC_SESSION_ID_KEY, used in **LogFilter** as a key in the SLF4J Mapped Diagnostic Context (MDC) for storing and retrieving a unique session identifier.

Feel free to add all your needed constants here.

### dev.pollito.springbootstartertemplate.util.ControllerAdviceUtil 

Utility for handling exceptions and constructing error responses.

Feel free to add all your error responses construction logic here. Example: Let's imagine we need to build a response for a InvalidBase64ImageException. That would look something like this:

```java
public class ControllerAdviceUtil {
    //others methods
    //...
    
    public static ResponseEntity<Error> getBadRequestError(InvalidBase64ImageException e) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST, e, "image provided is not a valid Base64Image");
    }
}

```

You could also create another utility class to separate error responses construction logic.

### dev.pollito.springbootstartertemplate.SpringBootStarterTemplateApplication

Default entry point to a Spring Boot application.

## Resources > openapi files

This is a totally arbitrary folder inside resources where I decided is good enough to put all the OAS.yaml files.

Here you are expected to have:

- Zero or more OAS.yaml files that do the role of being the "clients contract".
- At least one OAS.yaml file that does the role of being the "provider contract".
  - Usually when talking about microservices, a microservice do only one thing, so it should provide only one contract.
  - Each contract should have an Error schema, which after generation tasks, will be used by @RestControllerAdvice annotated classes and their respective utilities classes for for constructing standardized error responses.

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
Feel free to organize files in subfolders if necessary. Don't forget to maintain consistency with the pom.xml org.openapitools:openapi-generator-maven-plugin plugin.

## Resources > logback.xml

Configuration to log information to the console, with a customized pattern that includes a session ID for better traceability of log entries.

## pom.xml

- This template uses [Spring Boot 3.2.3](https://github.com/spring-projects/spring-boot/releases/tag/v3.2.3) and Java 17
- Basic dependencies: you'll find these in every Spring Boot 3 application:
  - Spring Boot Starter Web: essential for building web applications using Spring Boot
  - Spring Boot DevTools: set of tools that make the development process with Spring Boot more efficient.
  - Spring Boot Configuration Processor: generates metadata for configuration properties, making them easier to work with in IDEs.
  - Lombok: reduces boilerplate code like getters, setters, and constructors through annotations.
  - Spring Boot Starter Test: includes support for JUnit, Spring Test & Spring Boot Test, AssertJ, Hamcrest, and a bunch of other libraries necessary for thorough testing.
- Dependencies required by org.openapitools:openapi-generator-maven-plugin when generating provider code:
  - io.swagger.core.v3 >> swagger-core-jakarta >> 2.2.8: solves error package io.swagger.v3.oas.annotations does not exist.
  - org.openapitools >> jackson-databind-nullable >> 0.2.6: solves error package org.openapitools.jackson.nullable does not exist.
  - org.springframework.boot >> spring-boot-starter-validation >> 3.1.2: solves validations being ignored.
- Dependencies required by AOP practices:
  - org.aspectj >> aspectjrt >> 1.9.19: enables you to define and execute aspects that can work across your application's components.
  - org.aspectj >> aspectjweaver >> 1.9.19: modifies Java classes to weave in the aspects at load time or compile time.
- Plugins:
  - Spring Boot Maven Plugin excluding lombok: since Lombok is a compile-time only tool that helps reduce boilerplate code, there's no need to include it in the final packaged application.
  - Fmt Maven Plugin (by Spotify): formats your Java source code to comply with Google Java Format.
  - openapi-generator-maven-plugin: a Maven plugin to support the OpenAPI generator project. Is a code generator that embraces CDD practices. By default it's configured like this:

```xml
<plugin>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-generator-maven-plugin</artifactId>
  <version>7.2.0</version>
  <executions>
    <execution>
      <id>provider generation: petstore.yaml</id>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <inputSpec>${project.basedir}/src/main/resources/openapi/petstore.yaml</inputSpec>
        <generatorName>spring</generatorName>
        <output>${project.build.directory}/generated-sources/openapi/</output>
        <apiPackage>io.swagger.petstore.api</apiPackage>
        <modelPackage>io.swagger.petstore.models</modelPackage>
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

- Group ID and Artifact ID: identifies the OpenAPI Generator plugin itself.
- Version: specifies the version of the OpenAPI Generator plugin.
- Execution block: defines when and how the plugin's goal(s) should be executed within the build lifecycle.
  - ID: a unique identifier for this execution instance.
  - Goals: specifies the generate goal, which tells the plugin to perform code generation.
  - Configuration Block: provides detailed instructions on how the code generation should be performed.
    - inputSpec: points to the location of the OpenAPI spec file that does the role of being the provider contract. In this case, petstore.yaml located under src/main/resources/openapi/ .
    - generatorName: specifies the spring generator, indicating that the code should be generated with Spring in mind, tailoring the output for Spring-based projects.
    - output: the directory where the generated code should be placed. Personally I think target/generated-sources/openapi/ directory is a good place.
    - apiPackage and modelPackage: define the Java package names for the generated API interfaces and model classes, respectively. These values are up to you. Personally I like to use the OAS file server url in reverse url notation. In case that info is not available, using the project groupId + artifactId is common practice
    - configOptions: a set of additional configuration options.
      - interfaceOnly: it set to true. You will need to create your own implementation anyways.
      - useSpringBoot3: ensures compatibility with Spring Boot 3.
      - useEnumCaseInsensitive: if there are generated enums, it is configured to be case-insensitive, adding flexibility to how their values are deserialized.

## UML diagram

![UML diagram](./uml.jpg)

## How to run

1. maven clean + maven compile. This will generate the code in target/generated-sources/openapi/
2. run the application
3. execute this curl to check that everything is working

```shell
curl --location 'http://localhost:8080/pets'
```

Should get the default response from the interface, an empty body with status 501.

You can also see that in the console:

```log
2024-03-14 21:25:23 INFO  d.p.s.filter.LogFilter [SessionID: 373c02ac-10e3-44b1-9a9b-dc35f807a0ab] - >>>> Method: GET; URI: /pets; QueryString: null; Headers: {user-agent: PostmanRuntime/7.37.0, accept: */*, cache-control: no-cache, postman-token: 7a238d8f-d26e-4e84-8b5d-87bc20e54557, host: localhost:8080, accept-encoding: gzip, deflate, br, connection: keep-alive}
2024-03-14 21:25:23 INFO  d.p.s.aspect.LoggingAspect [SessionID: 373c02ac-10e3-44b1-9a9b-dc35f807a0ab] - [PetsController.findPets(..)] Args: [null, null]
2024-03-14 21:25:23 INFO  d.p.s.aspect.LoggingAspect [SessionID: 373c02ac-10e3-44b1-9a9b-dc35f807a0ab] - [PetsController.findPets(..)] Response: <501 NOT_IMPLEMENTED Not Implemented,[]>
2024-03-14 21:25:23 INFO  d.p.s.filter.LogFilter [SessionID: 373c02ac-10e3-44b1-9a9b-dc35f807a0ab] - <<<< Response Status: 501
```