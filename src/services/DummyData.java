package services;

import application.Person;

import java.util.ArrayList;

/**
 * Created by johan on 2017-05-01.
 */

/*This class loads dummy data into the database. Should be run before "Desktopinlamning".*/
public class DummyData {
    public static void main(String[] args){
        DatabaseServiceClass dataBaseComponent = new DatabaseServiceClass();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person("Sten", "Nilsson", "Electrician", 20000, 28, "Handstand"));
        personList.add(new Person("Richard", "Smith", "Wizard", 30000, 38, "Great voice"));
        personList.add(new Person("Ylva", "Eriksson", "Busdriver", 25000, 35, "Parallel parking"));

        for(Person p: personList){
            dataBaseComponent.create(p);
        }
        System.out.println("Ready");

    }
}
