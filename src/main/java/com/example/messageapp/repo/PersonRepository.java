package com.example.messageapp.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import com.example.messageapp.entity.Person;


// not needed for exposing it to rest
// but for customising end point
//@RepositoryRestResource(collectionResourceRel = "people", path = "people")

/**
 * all methods exposed in this repository
 * are exposed as rest end points found under search
 * with methodName as path variable and query parameters as @Param(name is exposed to rest)
 *
 * http://localhost:9005/persons/search/findByFirstName?name=test2
 * {{url}}/{{resource}}/search/{methodName}?{queryParams}
 *
 * sample json":
	 "_links": {
	 "findByFirstNameAndLastName": {
	 "href": "http://localhost:9005/persons/search/findByFirstNameAndLastName{?name,last}",
	 "templated": true
	 },
	 "find-by-last-name": {
	 "href": "http://localhost:9005/persons/search/title-contains{?name}",
	 "templated": true
	 },
	 "findByFirstNameLike": {
	 "href": "http://localhost:9005/persons/search/findByFirstNameLike{?name}",
	 "templated": true
	 },
	 "self": {
	 "href": "http://localhost:9005/persons/search"
	 }
	 }
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

	/**
	 * rel is key used inside json for the _links
	 * path is the Path param used after search
	 * exported to tell HAL whether to export this or not
	 * @param name - parameter
	 * @return - list
	 */
	@RestResource (rel = "find-by-last-name", path="title-contains")
	List<Person> findByLastName(@Param("name") String name);

	@RestResource (rel = "find-by-first-name", path="title-contains", exported = false)
	List<Person> findByFirstName(@Param("name") String name);

	List<Person> findByFirstNameAndLastName(@Param("name") String name, @Param("last") String lastName);

	List<Person> findByFirstNameLike(@Param("name") String name);

	/**
	 * these are not exported
	 */
	@Override
	@RestResource (exported = false)
	void delete(Person entity);

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends Person> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();
}
