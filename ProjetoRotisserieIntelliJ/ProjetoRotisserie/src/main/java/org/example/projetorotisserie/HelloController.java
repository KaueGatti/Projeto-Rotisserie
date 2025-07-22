package org.example.projetorotisserie;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.w3c.dom.CDATASection;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private DatePicker data;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(data.getValue().toString());
    }
}