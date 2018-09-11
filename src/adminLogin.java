import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class adminLogin {

	  
public static Scene adminLoginScene(Stage primaryStage) {
	
	        BorderPane mainPane = new BorderPane();
	        Scene main_scene = new Scene(mainPane,Util.screenWidth,Util.screenHeight);
	              main_scene.getStylesheets().addAll("Buttons.css","Dark.css","addLeaugesButtons.css");
	              mainPane.setPadding(new Insets(10));

	              Util.OnMousePressed(mainPane,primaryStage); //two classes OnMouseDragged & OnMousePressed being implemented from the abstract class Util,
	              Util.OnMouseDragged(mainPane,primaryStage); // used in order to move the undecorated stage style by getting and setting y and x values

	              Button returnButton = new Button();
	              		 returnButton.setAlignment(Pos.BOTTOM_LEFT);
	              	     returnButton.setId("ReturnButton");
	              		 returnButton.setOnAction(event -> {
	              			 primaryStage.setScene(userLogin.userLoginScene(primaryStage));
	              		 });
	            	   
	        mainPane.setTop(Util.toolBar(primaryStage, true));
	        mainPane.setCenter(adminLoginPane(primaryStage));
	        mainPane.setBottom(returnButton);

	        return main_scene;
}
	    
public static GridPane adminLoginPane(Stage stage){
	     
 		GridPane gridPane = new GridPane();
 				 gridPane.setVgap(20);
 				 gridPane.setHgap(10);
 				 gridPane.setAlignment(Pos.CENTER);


 		Label label = new Label();			
 			 	
 		TextField textField = new TextField();
     	          textField.setMinWidth(400);
     	          textField.setPromptText("Enter Admin Username");
    
        PasswordField passwordField = new PasswordField();
                      passwordField.setMinWidth(400);
                      passwordField.setPromptText("Enter admin Password or default Password");

     String[] buttonsLabels = {"Register","Login"};
     	Button[] loginButton = new Button[buttonsLabels.length];
     	String[] labelText = {"Enter User Name","Enter Password"};
     	Label[] labels = new Label[labelText.length];
     	HBox loginButtonBox = new HBox(10);
    
     for(int i = 0; i < buttonsLabels.length; i++){

         loginButton[i] = new Button(buttonsLabels[i]);
         loginButtonBox.getChildren().addAll(loginButton[i]);
         loginButton[i].setMinWidth(textField.getMinWidth()/2);
         loginButton[i].setMaxHeight(25);
         labels[i] = new Label(labelText[i]);
         if((i*2)%2 == 0)gridPane.add(labels[i],0, i*2);
         GridPane.setHalignment(labels[i], HPos.CENTER);
     }
     
     
     textField.textProperty().addListener(new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			   loginButton[1].setOnAction(event ->  {
				  	 userLogin.verify(stage,arg2,passwordField.getText(),label); 	
				});

				gridPane.setOnKeyPressed((KeyEvent key) -> {
				  	if(key.getCode() == KeyCode.ENTER) {
				  		userLogin.verify(stage,arg2,passwordField.getText(),label); 	 
				}});
	}});
     
     loginButton[0].setOnAction(event -> {
     		stage.setScene(Register.userRegister(stage));
     		stage.setFullScreen(true);
     });
 	   


     gridPane.add(textField,0,1);
     gridPane.add(passwordField,0,3);
     gridPane.add(loginButtonBox,0,5);
     gridPane.add(label, 0, 4);
     GridPane.setHalignment(label, HPos.CENTER);

     return gridPane;

 }
 
	
}
