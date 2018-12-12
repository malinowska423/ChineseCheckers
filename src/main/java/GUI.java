import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;


public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("connect");
        button.setStyle("-fx-min-width: 400;-fx-min-height: 50;-fx-font-size: 20;");
        BorderPane pane = new BorderPane();
        pane.setTop(button);
        pane.setStyle("-fx-background-color: #0c05f4");
        Scene scene = new Scene(pane,400,200);
        primaryStage.setScene(scene);
        primaryStage.show();


        button.setOnAction(actionEvent -> {
            BufferedReader in;
            PrintWriter out;
            try {
                Socket socket = new Socket("localhost", 2137);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connected");
                String line = "";

                out.println("//end");

                while (!(line.equals("//end"))) {
                    line = in.readLine();
                    System.out.println(line);
                }


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Do you want to create a room?");

                Optional<ButtonType> result = alert.showAndWait();

                TextInputDialog dialog = new TextInputDialog("Number of players?");
                dialog.showAndWait();
                String s = dialog.getEditor().getText();
//                System.out.println(s);
                if (result.get() == ButtonType.OK){
                    out.println("Y");
                    out.println(s);
                } else {
                    out.println("Y");
                    out.println(s);
                }
                out.println("//end");
                System.out.println("control");

                line = "";

                while(!(line.equals("//end"))){
                    line = in.readLine();
                    System.out.println(line);
                }
                out.println("//end");


            } catch (IOException ex){
                System.out.printf("connection error");
            }

        });
        


    }
}
