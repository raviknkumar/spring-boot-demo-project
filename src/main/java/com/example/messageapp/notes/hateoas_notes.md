Spring HATEOAS

 
* HATEOAS - Hypermedia as the Engine of Application State
* This uses HAL - Hypertext Application Language

*** 
### HAL
HAL, is a simple format that gives a consistent and easy way to hyperlink between resources in our API. Including HAL within our REST API makes it much more explorable to users as well as being essentially self-documenting.

The HAL model revolves around two simple concepts.

Resources, which contain:

Links to relevant URIs
Embedded Resources
State
Links:

A target URI
A relation, or rel, to the link
A few other optional properties to help with depreciation, content negotiation, etc
The HAL browser was created by the same person who developed HAL and provides an in-browser GUI to traverse your REST API.


Dependencies:
```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-rest-hal-explorer</artifactId>
    <version>3.4.1.RELEASE</version>
</dependency>

<!-- For Rest End points, this takes `spring-boot-hateoas` as a dependency -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>


```

***
### Links:
* [Baeldung](https://www.baeldung.com/spring-rest-hal)
* [Spring io Guildes Accessing Data Rest](https://spring.io/guides/gs/accessing-data-rest/)  
* [Spring io Rest Hateoas](https://spring.io/guides/gs/rest-hateoas/)

