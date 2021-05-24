package com.example.messageapp.repo;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.example.messageapp.entity.Person;
import com.example.messageapp.entity.QPerson;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class PersonDslRepoImpl implements PersonDslRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person save(final Person person) {
        em.persist(person);
        return person;
    }

    @Override
    public List<Person> findPersonsByFirstnameQueryDSL(final String firstname) {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstName.eq(firstname)).fetch();
    }

    @Override
    public List<Person> findPersonsByFirstnameAndLastnameQueryDSL(final String firstname, final String surname) {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstName.eq(firstname).and(person.lastName.eq(surname))).fetch();
    }

    @Override
    public List<Person> findPersonsByFirstnameInDescendingOrderQueryDSL(final String firstname) {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstName.eq(firstname)).orderBy(person.firstName.desc()).fetch();
    }

    @Override
    public int findMaxAge() {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).select(person.age.max()).fetchFirst();
    }

    @Override
    public Map<String, Integer> findMaxAgeByName() {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).transform(GroupBy.groupBy(person.firstName).as(GroupBy.max(person.age)));
    }
}
