module com.example.aaap {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens view to javafx.fxml;
    opens model to com.google.gson;
    exports view;
}