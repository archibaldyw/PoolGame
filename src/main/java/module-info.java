module com.example.poolgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;



    opens com.example.poolgame to javafx.fxml;
    exports com.example.poolgame;
}

