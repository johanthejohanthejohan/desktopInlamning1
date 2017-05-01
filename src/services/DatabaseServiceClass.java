package services;

import application.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by johan on 2017-04-29.
 */
public class DatabaseServiceClass {

    //public void create(String firstName, String lastName, String profession, double wage, int age, String skills){
    public void create(Person p){
        Person person = p;
        //Person person = new Person(firstName, lastName, profession, wage, age, skills);

        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    /*Method for reading all entries*/
    public List<Person> readAll(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = emfactory.createEntityManager();
        Query query = entityManager.createNamedQuery("Person.readAll");

        List<Person> list = query.getResultList();

        entityManager.close();
        emfactory.close();

        return list;

    }

}
