module ca.georgiancollege.assignment2gc2506 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens ca.georgiancollege.assignment2gc2506 to javafx.fxml;
    exports ca.georgiancollege.assignment2gc2506;
}