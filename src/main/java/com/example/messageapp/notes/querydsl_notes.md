## Query DSL
***
One of the most widely used Java ORM frameworks, Hibernate (and also closely related JPA standard), proposes a string-based query language HQL (JPQL) very similar to SQL. The obvious drawbacks of this approach are the lack of type safety and absence of static query checking.
Also, in more complex cases (for instance, when the query needs to be constructed at runtime depending on some conditions), building an HQL query typically involves concatenation of strings which is usually very unsafe and error-prone.

The JPA 2.0 standard brought an improvement in the form of Criteria Query API — a new and type-safe method of building queries that took advantage of metamodel classes generated during annotation preprocessing. Unfortunately, being groundbreaking in its essence, Criteria Query API ended up very verbose and practically unreadable. Here’s an example from Jakarta EE tutorial for generating a query as simple as SELECT p FROM Pet p:
```java
EntityManager em = ...;
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
Root<Pet> pet = cq.from(Pet.class);
cq.select(pet);
TypedQuery<Pet> q = em.createQuery(cq);
List<Pet> allPets = q.getResultList();
```

*Adding to project*
```xml
<properties>
    <querydsl.version>4.1.3</querydsl.version>
</properties>

<!--Next, add the following dependencies to the <project><dependencies> section of your pom.xml file:-->
<dependencies>

    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-apt</artifactId>
        <version>${querydsl.version}</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-jpa</artifactId>
        <version>${querydsl.version}</version>
    </dependency>
</dependencies>
```

The querydsl-apt dependency is an annotation processing tool (APT) — implementation of corresponding Java API that allows processing of annotations in source files before they move on to the compilation stage. This tool generates the so called Q-types — classes that directly relate to the entity classes of your application, but are prefixed with letter Q. For instance, if you have a User class marked with the @Entity annotation in your application, then the generated Q-type will reside in a QUser.java source file.
The provided scope of the querydsl-apt dependency means that this jar should be made available only at build time, but not included into the application artifact.

The querydsl-jpa library is the Querydsl itself, designed to be used together with a JPA application.

Adding Annotation Processing: \
To configure annotation processing plugin that takes advantage of querydsl-apt, add the following plugin configuration to your
pom – inside the <project><build><plugins> element:

```xml
<plugin>
    <groupId>com.mysema.maven</groupId>
    <artifactId>apt-maven-plugin</artifactId>
    <version>1.1.3</version>
    <executions>
        <execution>
            <goals>
                <goal>process</goal>
            </goals>
            <configuration>
                <outputDirectory>target/generated-sources/java</outputDirectory>
                <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
        </execution>
    </executions>
</plugin>
```
This plugin makes sure that the Q-types are generated during the process goal of Maven build. The outputDirectory configuration property points to the directory where the Q-type source files will be generated. The value of this property will be useful later on, when you’ll go exploring the Q-files.
You should also add this directory to the source folders of the project.

*run `maven compile` to generate the QType classes, which can be found at `outputDirectory` specified under pom.xml inside plugins.


re-import maven project to resolve for the QType classes.

### Exploring Generated Classes

Now go to the directory specified in the outputDirectory property of apt-maven-plugin (target/generated-sources/java in our example). You will see a package and class structure that directly mirrors your domain model, except all the classes start with letter Q (QUser and QBlogPost in our case).

Open the file QUser.java. This is your entry point to building all queries that have User as a root entity. First thing you’ll notice is the @Generated annotation which means that this file was automatically generated and should not be edited manually. Should you change any of your domain model classes, you will have to run mvn compile again to regenerate all of the corresponding Q-types.

Aside from several QUser constructors present in this file, you should also take notice of a public static final instance of the QUser class:

public static final QUser user = new QUser("user");
This is the instance that you can use in most of your Querydsl queries to this entity, except when you need to write some more complex queries, like joining several different instances of a table in a single query.

The last thing that should be noted is that for every field of the entity class there is a corresponding *Path field in the Q-type, like NumberPath id, StringPath login and SetPath blogPosts in the QUser class (notice that the name of the field corresponding to Set is pluralized). These fields are used as parts of fluent query API that we will encounter later on.

### Querying With Querydsl

Simple Querying and Filtering \
To build a query, first we’ll need an instance of a JPAQueryFactory, which is a preferred way of starting the building process. The only thing that JPAQueryFactory needs is an EntityManager, which should already be available in your JPA application via EntityManagerFactory.createEntityManager() call or @PersistenceContext injection.

```java
EntityManagerFactory emf =
Persistence.createEntityManagerFactory("com.baeldung.querydsl.intro");
EntityManager em = entityManagerFactory.createEntityManager();
JPAQueryFactory queryFactory = new JPAQueryFactory(em);
```
or using 
> @PersistanceContext

Now let’s create our first query:
```java
QUser user = QUser.user;

User c = queryFactory.selectFrom(user)
.where(user.login.eq("David"))
.fetchOne();
```
Notice we’ve defined a local variable QUser user and initialized it with QUser.user static instance. This is done purely for brevity, alternatively you may import the static QUser.user field.

The selectFrom method of the JPAQueryFactory starts building a query. We pass it the QUser instance and continue building the conditional clause of the query with the .where() method. The user.login is a reference to a StringPath field of the QUser class that we’ve seen before. The StringPath object also has the .eq() method that allows to fluently continue building the query by specifying the field equality condition.

Finally, to fetch the value from the database into persistence context, we end the building chain with the call to the fetchOne() method. This method returns null if the object can’t be found, but throws a NonUniqueResultException if there are multiple entities satisfying the .where() condition.

### Ordering and Grouping
Now let’s fetch all users in a list, sorted by their login in ascension order.

```java
List<User> c = queryFactory.selectFrom(user)
.orderBy(user.login.asc())
.fetch();
```

This syntax is possible because the *Path classes have the .asc() and .desc() methods. You can also specify several arguments for the .orderBy() method to sort by multiple fields.

Suppose we need to group all posts by title and count duplicating titles. This is done with the .groupBy() clause. We’ll also want to order the titles by resulting occurrence count.

```java
NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

List<Tuple> userTitleCounts = queryFactory.select(
        blogPost.title,blogPost.id.count().as(count))
        .from(blogPost)
        .groupBy(blogPost.title)
        .orderBy(count.desc())
        .fetch();
```

We selected the blog post title and count of duplicates, grouping by title and then ordering by aggregated count. Notice we first created an alias for the count() field in the .select() clause, because we needed to reference it in the .orderBy() clause.

### Complex Queries With Joins and Subqueries
Let’s find all users that wrote a post titled “Hello World!” For such query we could use an inner join. Notice we’ve created an alias blogPost for the joined table to reference it in the .on() clause:


```java
QBlogPost blogPost=QBlogPost.blogPost;
        List<User> users=queryFactory.selectFrom(user)
        .innerJoin(user.blogPosts,blogPost)
        .on(blogPost.title.eq("Hello World!"))
        .fetch();
```

Now let’s try to achieve the same with subquery:

```java
List<User> users = queryFactory.selectFrom(user)
.where(user.id.in(
JPAExpressions.select(blogPost.user.id)
.from(blogPost)
.where(blogPost.title.eq("Hello World!"))))
.fetch();
```
As we can see, subqueries are very similar to queries, and they are also quite readable, but they start with JPAExpressions factory methods. To connect subqueries with the main query, as always, we reference the aliases defined and used earlier.

### Modifying Data
JPAQueryFactory allows not only constructing queries, but also modifying and deleting records. Let’s change the user's login and disable the account:
```java
queryFactory.update(user)
        .where(user.login.eq("Ash"))
        .set(user.login,"Ash2")
        .set(user.disabled,true)
        .execute();
```
We can have any number of .set() clauses we want for different fields. The .where() clause is not necessary, so we can update all the records at once.

#### Deleting
To delete the records matching a certain condition, we can use a similar syntax:

```java
queryFactory.delete(user)
        .where(user.login.eq("David"))
        .execute();
```

**The .where() clause is also not necessary, but be careful, because omitting the .where() clause results in deleting all the entities of a certain type.**\
You may wonder, why JPAQueryFactory doesn’t have the .insert() method. This is a limitation of JPA Query interface. The underlying javax.persistence.Query.executeUpdate() method is capable of executing update and delete but not insert statements. To insert data, you should simply persist the entities with EntityManager.

If you still want to take advantage of a similar Querydsl syntax for inserting data, you should use SQLQueryFactory class that resides in the querydsl-sql library.


### LINKS
[Intro to Query DSL Baeldung](https://www.baeldung.com/intro-to-querydsl)
[Controller Generic Filter method ](https://www.baeldung.com/spring-data-web-support)