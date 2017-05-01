/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;



import java.util.Comparator;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.DatabaseServiceClass;

/**
 *
 * @author johan
 * Class with general logic
 */
public class Main extends Application{
    
    private int windowHeight = 500;
    private int windowWidth = 1000;
    private GridPane root = new GridPane();
    private Scene scene = new Scene(root, windowWidth, windowHeight);
    private TextArea outputTextArea  = new TextArea();

    private DatabaseServiceClass dataBaseComponent = new DatabaseServiceClass();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        // TODO code application logic here
    }
    
    @Override
    public void start(Stage primaryStage){

        /*Load data from mysql into observable list and bind to tableView*/
        ObservableList<Person> data;
        data = DataLoader.getData();
        TableView<Person> tableView;
        tableView = new TableView<>();
        tableView.setItems(data);
        tableView.setMaxHeight(300);
        

        /*Tableview setup columns*/
            //First name
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
            //Last name
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
            //Profession
        TableColumn<Person, String> professionColumn = new TableColumn<>("Profession");
        professionColumn.setMinWidth(200);
        professionColumn.setCellValueFactory(new PropertyValueFactory<>("profession"));
        
            //Wage
        TableColumn<Person, Double> wageColumn = new TableColumn<>("Wage");
        wageColumn.setMinWidth(100);
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));
        
            //Age
        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(100);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        
            //Skill
        TableColumn<Person, String> skillColumn = new TableColumn("Skill");
        skillColumn.setMinWidth(200);
        skillColumn.setCellValueFactory(new PropertyValueFactory<>("skills"));
        
            //Empty
        TableColumn emptyColumn = new TableColumn();
        emptyColumn.setMinWidth(185);
        
            // add columns to tableview
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, professionColumn, wageColumn, ageColumn, skillColumn, emptyColumn);

        
        /*Find oldest*/
        Button oldestButton = new Button();
        oldestButton.setText("Find oldest");
        oldestButton.setOnAction(e->{
            Comparator<Person> byAge = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
            data.stream()
                .sorted(byAge)
                .forEach(em->outputTextArea.setText(em.getFirstName()));
        });
        
        /*Find youngest*/
        Button youngestButton = new Button();
        youngestButton.setText("Find youngest");
        youngestButton.setOnAction(e->{
            Comparator<Person> byAge = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
            data.stream()
                .sorted(byAge.reversed())
                .forEach(em->outputTextArea.setText(em.getFirstName()));   
        });
        
        /*Find richest*/
        Button richestButton = new Button();
        richestButton.setText("Find richest");
        richestButton.setOnAction(e->{
            Comparator<Person> byWage = (p1, p2) -> Double.compare(p1.getWage(), p2.getWage());
            data.stream()
                .sorted(byWage)
                .forEach(em->outputTextArea.setText(em.getFirstName()));
        });
        
        /*Sort*/
        Button sortButton = new Button();
        sortButton.setText("Sort by first name");
        sortButton.setOnAction(e->{
            outputTextArea.setText("");
            
            data.stream()
                    .sorted((e1, e2) -> e1.getFirstName()
                    .compareTo(e2.getFirstName()))
                    .forEach(em -> outputTextArea.appendText(em.getFirstName()+"\n"));
        });


        /*Setup filter*/
        TextField filterTextField = new TextField();
        
        filterTextField.setOnMouseClicked(e->{
            outputTextArea.setText("");
        });
            

        FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);
        
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                //Convert wage and age to strings
                String wage = person.getWage()+" ";
                String age = person.getAge()+" ";

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                

                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getSkills().toLowerCase().contains(lowerCaseFilter)){
                    return true; //Filter matches skills.
                } else if (person.getProfession().toLowerCase().contains(lowerCaseFilter)){
                    return true; //Filter matches profession
                } else if (age.contains(lowerCaseFilter)){
                    return true; //Filter matches age.
                } else if(wage.contains(lowerCaseFilter)){
                    return true; //Filter matches wage.
                }  
                return false; // Does not match.
            });
        });

        SortedList<Person> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

        /*Add person*/
        GridPane addPersonLayout = new GridPane();

            //Create labels
        Label fNameLabel = new Label("First name");
        Label eNameLabel = new Label("Last name");
        Label professionLabel = new Label("Profession");
        Label wageLabel = new Label("Wage");
        Label ageLabel = new Label("Age");
        Label skillLabel = new Label("Skill");

            //Create textfield and set their widths.
        TextField fNameTextField = new TextField();
        fNameTextField.setMaxWidth(120);
        TextField eNameTextField = new TextField();
        eNameTextField.setMaxWidth(120);
        TextField professionTextField = new TextField();
        professionTextField.setMaxWidth(120);
        TextField wageTextField = new TextField();
        wageTextField.setMaxWidth(100);
        TextField ageTextField = new TextField();
        ageTextField.setMaxWidth(100);
        TextField skillTextField = new TextField();
        skillTextField.setMaxWidth(120);

        Button addPerson = new Button("Add person");

            //Add components to addPersonLayout
        addPersonLayout.add(fNameLabel,0,0);
        addPersonLayout.add(eNameLabel,1,0);
        addPersonLayout.add(professionLabel,2,0);
        addPersonLayout.add(wageLabel,3,0);
        addPersonLayout.add(ageLabel,4,0);
        addPersonLayout.add(skillLabel,5,0);

        addPersonLayout.add(fNameTextField,0,1);
        addPersonLayout.add(eNameTextField,1,1);
        addPersonLayout.add(professionTextField,2,1);
        addPersonLayout.add(wageTextField,3,1);
        addPersonLayout.add(ageTextField,4,1);
        addPersonLayout.add(skillTextField,5,1);

        addPersonLayout.add(addPerson, 6,1);

            //Styling for addPerson layout
        addPersonLayout.setHgap(5);
        addPerson.setPadding(new Insets(5));
        addPersonLayout.setMargin(fNameLabel, new Insets(0,0,3,0));
        addPersonLayout.setMargin(eNameLabel, new Insets(0,0,3,0));
        addPersonLayout.setMargin(professionLabel, new Insets(0,0,10,0));
        addPersonLayout.setMargin(wageLabel, new Insets(0,0,3,0));
        addPersonLayout.setMargin(ageLabel, new Insets(0,0,3,0));
        addPersonLayout.setMargin(skillLabel, new Insets(0,0,3,0));

            //Method for adding user defined person to observable list as well as to database
        addPerson.setOnAction(e->{

            String forName = fNameTextField.getText();
            String lastName = eNameTextField.getText();
            String profession = professionTextField.getText();
            Double wage = Double.parseDouble(wageTextField.getText());
            int age = Integer.parseInt(ageTextField.getText());
            String skill = skillTextField.getText();

            fNameTextField.setText("");
            eNameTextField.setText("");
            professionTextField.setText("");
            wageTextField.setText("");
            ageTextField.setText("");
            skillTextField.setText("");

            Person person = new Person(forName, lastName, profession, wage, age, skill);

            dataBaseComponent.create(person);
            data.add(person);

        });

        /*Style root layout*/
        root.setHgap(5);
        root.setPadding(new Insets(5));
        GridPane.setMargin(oldestButton, new Insets(0,0,10,0));
        GridPane.setMargin(youngestButton, new Insets(0,0,10,0));
        GridPane.setMargin(richestButton, new Insets(0,0,10,0));
        GridPane.setMargin(sortButton, new Insets(0,0,10,0));
        GridPane.setMargin(filterTextField, new Insets(0,0,10,0));
        GridPane.setMargin(outputTextArea, new Insets(10,0,10,0));
        
        /*Add components to layout*/
        root.add(oldestButton, 0, 0);
        root.add(youngestButton, 1, 0);
        root.add(richestButton, 2, 0);
        root.add(sortButton, 3, 0);

        root.add(filterTextField, 0, 1, 4, 1);

        root.add(tableView, 0, 2, 4, 1);

        root.add(outputTextArea, 0, 3, 4, 1);

        root.add(addPersonLayout, 0,4,4,1);

        /*Setup stage*/
        primaryStage.setTitle("Inlamning");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
