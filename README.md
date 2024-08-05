## Building

This project is built using Java 17 and uses Maven as a dependecy manager.

## Maven dependecies

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


