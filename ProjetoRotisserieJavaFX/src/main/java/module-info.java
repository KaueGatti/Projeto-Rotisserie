module my.company.projetorotisseriejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens my.company.projetorotisseriejavafx to javafx.fxml;
    exports my.company.projetorotisseriejavafx;
}
