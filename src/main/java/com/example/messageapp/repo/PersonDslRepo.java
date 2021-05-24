package com.example.messageapp.repo;

import java.util.List;
import java.util.Map;
import com.example.messageapp.entity.Person;

public interface PersonDslRepo {

    Person save(Person person);

    List<Person> findPersonsByFirstnameQueryDSL(String firstname);

    List<Person> findPersonsByFirstnameAndLastnameQueryDSL(String firstname, String surname);

    List<Person> findPersonsByFirstnameInDescendingOrderQueryDSL(String firstname);

    int findMaxAge();

    Map<String, Integer> findMaxAgeByName();
}
