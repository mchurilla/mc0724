## Building

This project was developed using IntelliJ Community Edition version 2022.3.2. It is built using Java 17 and uses Maven as a dependency manager.<br>

## Test Suites
All JUnit 5 test suites are located under the `src/test/java/*` directory. While there are multiple test suites included, the primary test suite containing the project's proofs is found in the `RentalProcessorTests` class.

Each test case used for the proofs is named according to its test number (e.g., test1_**) and is accompanied by Javadocs that provide all relevant information.

## Maven dependencies

This project uses the following dependencies:
- **JUnit (v5.10.3)**: Used to build and run the unit tests.
- **Jackson FasterXML (v2.17.2)**: This library is used to serialize and deserialize JSON.

```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.17.2</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.17.2</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.2</version>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.10.3</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```


