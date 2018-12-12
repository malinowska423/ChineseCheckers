import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class BasicBoardBuilder extends BoardBuilder {


    @Override
    public GridPane buildBoard(String s) {
        System.out.println(s);
        GridPane grid = new GridPane();
        int radius = 12;
        int row = 0;
        int column = 0;
        for(int i=0;i<s.length();i++){
            char sign = s.charAt(i);
            if(sign == ' ' || sign == '-') {
                Label hspacing = new Label();
                hspacing.setStyle("-fx-min-height: " + 2*radius  + ";-fx-min-width: " + 2*radius + ";");
                grid.add(hspacing,column,row);
                column++;
//                System.out.println("SPACE");
            }
            else if(sign == '\n') {
//                System.out.println("JUMP");
                row++;
                Label vspacing = new Label();
                vspacing.setStyle("-fx-min-height: 5;-fx-max-height: 10;-fx-min-width: 20;");
                grid.add(vspacing,0,row);
                row++;
                column = 0;
            }
            else {
                Circle circle = new Circle();
                circle.setOnMouseClicked(mouseEvent -> {
                    int y = grid.getRowIndex(circle)/2;
                    int x = grid.getColumnIndex(circle)/2;
                    System.out.println("Y: " + y + " X: " + x);

                });
                switch (sign) {
                    case 'o':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: black;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND o");
                    }break;
                    case '1':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: red;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND 1");
                    }break;
                    case '2':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: green;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND 2");
                    }break;
                    case '3':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: yellow;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND 3");
                    }break;
                    case '4':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: purple;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND 4");
                    }break;
                    case '5':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: brown;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND 5");
                    }break;
                    case '6':
                    {
//                        Circle circle = new Circle();
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: blue;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND 6");
                    }break;
                }
            }
        }
        return grid;
    }
}
