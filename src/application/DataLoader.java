/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.DatabaseServiceClass;

import java.util.List;

/**
 *
 * @author johan
 * Class for creating dummy data
 */
public class DataLoader {

    public static ObservableList<Person> getData(){

        ObservableList<Person> persons = FXCollections.observableArrayList();
        DatabaseServiceClass cRUD = new DatabaseServiceClass();
        List<Person> list = cRUD.readAll();
        for(Person p: list){
            persons.add(p);
        }

        return persons;
    }

}
