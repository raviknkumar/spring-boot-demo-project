
#### Setting the Repository Detection Strategy

| Name      | Description |
| :---        |    :----   |
| `DEFAULT`      | Exposes all public repository interfaces but considers the exported flag of @(Repository)RestResource.       |
| `ALL`   | Exposes all repositories independently of type visibility and annotations.|
| `ANNOTATED` | Only repositories annotated with @(Repository)RestResource are exposed, unless their exported flag is set to false. |
| `VISIBILITY` | Only public repositories annotated are exposed. |

#### base path for spring data rest
> spring.data.rest.basePath=/api 

add this snippet in `application.properties`

changing through code
```java
@Configuration
class CustomRestMvcConfiguration {

  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {

    return new RepositoryRestConfigurer() {

      @Override
      public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setBasePath("/api");
      }
    };
  }
}
```

(or)

```java
@Component
public class CustomizedRestMvcConfiguration extends RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    config.setBasePath("/api");
  }
}
```

### Changing Other Spring Data REST Properties

| Property | Description |
| :------ |-------------|
|  `basePath` | the root URI for Spring Data REST |
|`defaultPageSize` |change the default for the number of items served in a single page |
|`maxPageSize` | change the maximum number of items in a single page|
|`pageParamName`| change the name of the query parameter for selecting pages|
|`limitParamName` | change the name of the query parameter for the number of items to show in a page|
| `sortParamName` |change the name of the query parameter for sorting
| `defaultMediaType` | change the default media type to use when none is specified|
| `returnBodyOnCreate` | change whether a body should be returned when creating a new entity|
| `returnBodyOnUpdate` | change whether a body should be returned when updating an entity |

### Repository methods exposure
Which HTTP resources are exposed for a certain repository is mostly driven by the structure of the repository. In other words, the resource exposure will follow which methods you have exposed on the repository. If you extend CrudRepository you usually expose all methods required to expose all HTTP resources we can register by default. Each of the resources listed below will define which of the methods need to be present
so that a particular HTTP method can be exposed for each of the resources. 
That means, that repositories that are not exposing those methods either by not declaring them at all or explicitly using 
`@RestResource(exported = false)` won’t expose those HTTP methods on those resources.


###  Default Status Codes
For the resources exposed, we use a set of default status codes:

* 200 OK: For plain GET requests.
* 201 Created: For POST and PUT requests that create new resources.
* 204 No Content: For PUT, PATCH, and DELETE requests when the configuration is set to not return response bodies for resource updates (RepositoryRestConfiguration.returnBodyOnUpdate). If the configuration value is set to include responses for PUT, 200 OK is returned for updates, and 201 Created is returned for resource created through PUT.

If the configuration values (RepositoryRestConfiguration.returnBodyOnUpdate and RepositoryRestConfiguration.returnBodyCreate) are explicitly set to null, the presence of the HTTP Accept header is used to determine the response code.


### The Collection Resource
Spring Data REST exposes a collection resource named after the uncapitalized, pluralized version of the domain class the exported repository is handling. Both the name of the resource and the path can be customized by using @RepositoryRestResource on the repository interface.

#### Supported HTTP Methods
Collections resources support both GET and POST. All other HTTP methods cause a 405 Method Not Allowed.

`GET` \
Returns all entities the repository servers through its findAll(…) method. 
If the repository is a paging repository we include the pagination links if necessary and additional page metadata.

Methods used for invocation
The following methods are used if present (decending order):
`findAll(Pageable)` \
`findAll(Sort)` \
`findAll()`

For more information on the default exposure of methods, see Repository methods exposure.
Parameters
If the repository has pagination capabilities, the resource takes the following parameters:

`page`: The page number to access (0 indexed, defaults to 0).\
`size`: The page size requested (defaults to 20).\
`sort`: A collection of sort directives in the format ($propertyname,)+[asc|desc]?.

Custom Status Codes \
The GET method has only one custom status code:
405 Method Not Allowed: If the findAll(…) methods were not exported (through @RestResource(exported = false)) or are not present in the repository.

Supported Media Types
The GET method supports the following media types:
> application/hal+json \
application/json


Related Resources \
The GET method supports a single link for discovering related resources \
`search` **A search resource is exposed if the backing repository exposes query methods.**

#### `HEAD`
The HEAD method returns whether the collection resource is available. It has no status codes, media types, or related resources.

Methods used for invocation
The following methods are used if present (decending order):
> findAll(Pageable) \
findAll(Sort) \
findAll()

For more information on the default exposure of methods, see Repository methods exposure.

`POST`  
The POST method creates a new entity from the given request body.

Methods used for invocation
The following methods are used if present (decending order):

`save(…)`

For more information on the default exposure of methods, see Repository methods exposure.

Custom Status Codes
The POST method has only one custom status code:

405 Method Not Allowed: If the save(…) methods were not exported (through @RestResource(exported = false)) or are not present in the repository at all.
Supported Media Types
The POST method supports the following media types:
```
application/hal+json
application/json
```

SAMPLE OF GET
```json5
{
  "_links" : { // 1
    "self" : {
      "href" : "http://localhost:8080/persons{&sort,page,size}", 
      "templated" : true
    },
    "next" : { //2
      "href" : "http://localhost:8080/persons?page=1&size=5{&sort}", 
      "templated" : true
    }
  },
  "_embedded" : {
  	"data" : []
  },
  "page" : { // 3 
    "size" : 5,
    "totalElements" : 50,
    "totalPages" : 10,
    "number" : 0
  }
}
```

At the top, we see _links:

1. The self link serves up the whole collection with some options.\
2. The next link points to the next page, assuming the same page size.
3. At the bottom is extra data about the page settings, including the size of a page, total elements, total pages, and the page number you are currently viewing.

`curl "http://localhost:8080/persons?page=1&size=5"`

```json5
{
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/persons{&sort,projection,page,size}",
      "templated" : true
    },
    "next" : {
      "href" : "http://localhost:8080/persons?page=2&size=5{&sort,projection}", 
      "templated" : true
    },
    "prev" : {
      "href" : "http://localhost:8080/persons?page=0&size=5{&sort,projection}", 
      "templated" : true
    }
  },
  "_embedded" : {
	... data ...
  },
  "page" : {
    "size" : 5,
    "totalElements" : 50,
    "totalPages" : 10,
    "number" : 1 
  }
}
```

#### sorting
`curl -v "http://localhost:8080/people/search/nameStartsWith?name=K&sort=name,desc"`

***
#### `Projections`
* Avoid unwanted information
* reveal hidden information
* excerpts

***
#### Validation

***
#### Events


***
### LINKS
[Spring IO Docs on Spring Data Rest](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#conditional) `PRIMARY`
