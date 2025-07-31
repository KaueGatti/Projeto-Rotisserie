module my.company.projetorotisseriejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens my.company.projetorotisseriejavafx to javafx.fxml;
    opens my.company.projetorotisseriejavafx.Controller to javafx.fxml;
    
    exports my.company.projetorotisseriejavafx;
    
}
