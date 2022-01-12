import javafx.application.Application;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.io.FileNotFoundException;

public class App extends Application {
    TableView<Rumahsakit> tableView = new TableView<Rumahsakit>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("UAS OOP");
        TableColumn<Rumahsakit, String> columnId = new TableColumn<>("No");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rumahsakit, String> columnNama= new TableColumn<>("Nama Pasien");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Rumahsakit, String> columnPenyakit = new TableColumn<>("Penyakit");
        columnPenyakit.setCellValueFactory(new PropertyValueFactory<>("penyakit"));

        tableView.getColumns().add(columnId);
        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnPenyakit);

        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("ADD");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> add());

        Button button2 = new Button("DELETE");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> delete());

        Button button3 = new Button("EDIT");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> edit());

        Button button4 = new Button("Refresh");
        toolBar.getItems().add(button4);
        button4.setOnAction(e -> re());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pasien");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Rumahsakit(rs.getInt("id"), rs.getString("nama"), rs.getString("penyakit")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("simpan");

        addStage.setTitle("Tambahkan nama pasien");

        TextField namaField = new TextField();
        TextField penyakitField = new TextField();
        Label labelNama = new Label("Tambahkan nama pasien");
        Label labelPenyakit = new Label("Penyakit");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelPenyakit, penyakitField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "INSERT into pasien SET nama='%s', penyakit='%s'";
                sql = String.format(sql, namaField.getText(), penyakitField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField idField = new TextField();
        Label labelId= new Label("Nama");

        VBox hbox1 = new VBox(5, labelId, idField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from pasien WHERE nama='%s'";
                sql = String.format(sql, idField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Pasien");

        TextField namaField = new TextField();
        TextField penyakitField = new TextField();
        Label labelNama = new Label("Pasien");
        Label labelPenyakit = new Label("Penyakit");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelPenyakit, penyakitField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE pasien SET penyakit ='%s' WHERE nama='%s'";
                sql = String.format(sql, penyakitField.getText(), namaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pasien");
            while (rs.next()) {
                tableView.getItems().addAll(new Rumahsakit(rs.getInt("id"), rs.getString("nama"), rs.getString("penyakit")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE pasien DROP id";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE pasien ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}