import javafx.scene.input.*;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class userLogin{

  private static String logedinUser;
  private static AtomicInteger count = new AtomicInteger(0);
  private static HBox hbox = new HBox(10);
  
  public static Scene userLoginScene(Stage primaryStage){

	  
        BorderPane mainPane = new BorderPane();
        Scene main_scene = new Scene(mainPane,Util.screenWidth,Util.screenHeight);
              mainPane.setPadding(new Insets(10));
              mainPane.getStylesheets().addAll("Dark.css","addLeaugesButtons.css");

              Util.OnMousePressed(mainPane,primaryStage); //two classes OnMouseDragged & OnMousePressed being implemented from the abstract class Util,
              Util.OnMouseDragged(mainPane,primaryStage); // used in order to move the undecorated stage style by getting and setting y and x values

            	   
        mainPane.setTop(Util.toolBar(primaryStage, true));
        mainPane.setCenter(userLoginPane(primaryStage));
        mainPane.setBottom(optionsHBox(primaryStage));


        return main_scene;

    }
   
  private static GridPane userLoginPane(Stage stage){
     
 		GridPane gridPane = new GridPane();
 				 gridPane.setVgap(20);
 				 gridPane.setHgap(10);
 				 gridPane.setAlignment(Pos.CENTER);
 				 

 		Label label = new Label();			
 	
 		TextField textField = new TextField();
     	          textField.setMinWidth(400);
     	          textField.setPromptText("Enter Username / Email");
    
        PasswordField passwordField = new PasswordField();
                      passwordField.setMinWidth(400);
                      passwordField.setPromptText("Enter Password");

     	String[] labelText = {"Enter User Name / Email","Enter Password"};
     	Label[] labels = new Label[labelText.length];
     	
    
     for(int i = 0; i < labels.length; i++){
         labels[i] = new Label(labelText[i]);
         if((i*2)%2 == 0)gridPane.add(labels[i],0, i*2);
         GridPane.setHalignment(labels[i], HPos.CENTER);
     }

     Button loginButton = new Button("Login");
     	    loginButton.setMinWidth(400);

		 	
			loginButton.setOnAction(event ->  {	
		    	 verify(stage,textField.getText(),passwordField.getText(),label); 	
			});
		  
		     gridPane.setOnKeyPressed((KeyEvent key) -> {
		    	 if(key.getCode() == KeyCode.ENTER) {
		    		verify(stage,textField.getText(),passwordField.getText(),label); 	 
		     		}});
		     
     	   textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				passwordField.clear();
				count.set(0);
				  label.setText("");
				  hbox.getChildren().clear();
				  hbox.getChildren().add(label);
			} 
     	   });
     	   
     	 

     
     hbox.getChildren().add(label);
     gridPane.add(textField,0,1);
     gridPane.add(passwordField,0,3);
     gridPane.add(loginButton,0,5);
     gridPane.add(hbox, 0, 4);
     GridPane.setHalignment(label, HPos.CENTER);
     return gridPane;
 }
 
  public static void verify(Stage stage,String username, String password, Label label) {
	   	String name = string(username);
	  
	  	if(loginVer(password,username,label)) {
	  		
	  		 if(passwordConfrimation(password,name,addUser.users,label)) {
	  			  hbox.getChildren().clear();
	  			  setCurrentUser(name); 
		     	  addUser.initEverything();
	     		  stage.setScene(tableLeauge.mainScene(stage,false));
	     		  stage.setFullScreen(true);
	  		 }else if(passwordConfrimation(password,name,addUser.admins,label)) {
	  			 hbox.getChildren().clear();
	  			 setCurrentUser(name);
	     		 addUser.initEverything();
				 stage.setScene(tableLeauge.mainScene(stage,true));
				 stage.setFullScreen(true);	
	  		 }else if(addUser.admins.containsKey(username) && password.equals("Password")) {
	  			 hbox.getChildren().clear();
	  			 setCurrentUser(name);
	  			 addUser.initEverything();
				 stage.setScene(tableLeauge.mainScene(stage,true));
				 stage.setFullScreen(true);
	  		 }
	  	}
   
	  	 	 
  }
  /**
   * Takes username | email entered by user / admin and returns users | admins email as a login details are verified
   * using emails, if a users enters his username when logging in the corresponding value from the hash map
   * will be returned 
   * @param username
   * @return
   */
  private static String string(String username) {
		
	  	if(addUser.users.containsKey(username)) {
	  	    return username;
	  	}else if(addUser.admins.containsKey(username)) {
	  		return username;
	  	}
	  	else if(!addUser.users.containsKey(username) && !addUser.admins.containsKey(username) && addUser.userNameEmail.containsKey(username)) {
  		 	 return addUser.userNameEmail.get(username);
  	 	}
	  	else if(!addUser.users.containsKey(username) && !addUser.admins.containsKey(username) && !addUser.userNameEmail.containsKey(username) && addUser.adminsEmails.containsKey(username)) {
	  		return addUser.adminsEmails.get(username);
	  	}
	  	return "";
  }
  
  private static void passwordReset(String username) {
	  Stage passwordRestStage = new Stage();
	  		passwordRestStage.initStyle(StageStyle.UNDECORATED);
	  		passwordRestStage.initModality(Modality.APPLICATION_MODAL);
	  		passwordRestStage.setResizable(false);
	  		
	  		GridPane gridPane = new GridPane();
	  				 gridPane.setVgap(10);
	  				 gridPane.setHgap(10);
	  				 gridPane.setPadding(new Insets(10));
	  				 gridPane.getStylesheets().addAll("Dark.css");
	  				 
	  				 Scene resetPasswordScene = new Scene(gridPane,300,200);
	  				 
	  String[] labels = {"New Password","Confirm Password"};
	  Label[] label = new Label[labels.length];
	  PasswordField[] passwordFields = new PasswordField[label.length];
	  
	  for(int i = 0; i < label.length; i++) {
		  label[i] = new Label(labels[i]);
		  passwordFields[i] = new PasswordField();
		  passwordFields[i].setPromptText(labels[i]);
		  gridPane.add(label[i], 0, i+1);
	  }
	  
	  gridPane.add(passwordFields[0], 1, 1);

	  Button button = new Button("Reset Password");
	  Button closeButton = Util.closeButton(passwordRestStage);
	  		 GridPane.setHalignment(closeButton, HPos.LEFT);
	  		 gridPane.add(closeButton, 0, 0);
	  		 GridPane.setHalignment(button, HPos.RIGHT);
	  		 gridPane.add(button, 1, 4);
	  		 
	  		 Label labell = new Label();
	  		 
	  		 TitledPane titlePane = new TitledPane();
	  		 			titlePane.setExpanded(false);
	  		 			StackPane stackPane = new StackPane();
	  		 					  stackPane.getChildren().addAll(labell,passwordFields[1]);
	  		 					  StackPane.setAlignment(passwordFields[0], Pos.TOP_CENTER);
	  		 					  StackPane.setAlignment(labell, Pos.BOTTOM_CENTER);
	  		 					  gridPane.add(stackPane, 1, 2);
	  		 passwordFields[0].textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					
				}
	  			 
	  		 });
	  		 
	  		 passwordFields[1].textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						if(!passwordFields[0].getText().equals(arg2)) {
							 passwordFields[1].setStyle("-fx-background-color:red");
							 labell.setText("Password dont match");
							 titlePane.setExpanded(true);
						}else {
							 passwordFields[1].setStyle("-fx-background-color:green");
							 labell.setText("");
							 titlePane.setExpanded(false);
							 
						}
					}
		  			 
		  		 });
	  		 
	  		 
	  		 button.setOnAction(event -> {
	  			 if(passwordFields[0].getText().equals(passwordFields[1].getText())) {
	  				 String user = string(username);
	  				 addUser.users.get(user).setPassword(passwordFields[1].getText());
	  				 for(User uer : addUser.userListView) {
	  					 if(uer.getEmail().equals(username)) {
	  						 uer.setPassword(passwordFields[1].getText());
	  					 }
	  				 }
	  				 addUser.saveEditedAccounts();
	  				 addUser.initEverything();
	  				 Register.errorMessage("Your password has been reset");
	  			 }
	  		 });
	  		 
	  		passwordRestStage.setScene(resetPasswordScene);
	  		passwordRestStage.sizeToScene();
	  		passwordRestStage.show();
  }
  
  public static boolean loginVer(String password, String username, Label label) {
	  
	  if(addUser.users.containsKey(string(username))) {
	  if(!username.isEmpty() && addUser.users.get(string(username)).getPassword().contains("Reset")) {
			 
		  	 Hyperlink reset = new Hyperlink("Click here to reset your password");
		  	 		   label.setText("\t\tYour Password was reset by your admin :");
		  	  hbox.getChildren().clear();
		  	  hbox.getChildren().addAll(label, reset);
		  	 reset.setOnAction(event -> {
		  		passwordReset(string(username));
		  	 });
			 return false;
	  }}else {
	
		  hbox.getChildren().clear();
		  hbox.getChildren().add(label);
	  if(password.isEmpty() && username.isEmpty())
      {
      	label.setText("Enter Username and Password"); 
      	return false;
      } 
      else if(!password.isEmpty() && username.isEmpty()) {
    	  label.setText("Enter Username");
    	  return false;
      }
      else if(!userNameCheck(username)  && !username.isEmpty()) {
    	    
			label.setText("Username doesnt exist !!");
			return false;
      }
      else if(password.isEmpty() && userNameCheck(username)  && !username.isEmpty()) {
      		label.setText("Enter Password");
      		return false;
      }}
      	
      return true;
 }
  /**
   * passwordConfrimation checks and count using atomic integer users failed login attempts and
   * checks users email against the blockedUsers array list. Atomic Integer count is a global variable
   * as a local variable would be reset after each call as local variables life span is the methods execution
   * time, therefore failed login attempts wouldn't be recorded
   * 
   * @param password
   * @param username
   * @param map
   * @param label
   * @return
   */
  
  public static boolean passwordConfrimation(String password,String username,Hashtable<String,User> map,Label label) {


	  	 int value = count.get();

	  	 if(!map.containsKey(username)) {
	  		 return false;
	  	 }
	  	 else if(checkBlockedUSer(username)){
	  	 	Hyperlink hyper = new Hyperlink("Click here to request password reset");
		 	label.setText("You account is blocked ");
		 	hbox.getChildren().clear();
		 	hbox.getChildren().addAll(label,hyper);
		 	hyper.setOnAction(event -> {
		 		addUser.forgotPasswords.put(username, "Yes");
		 		addUser.passwordRequest.add(username);
		 		addUser.updateBlockedUser();
		 		Register.errorMessage("Password reset request sent");
		 	});
			label.setStyle("-fx-text-fill:red;\n");
	 		return false;
	     }
	  	 else if(!password.equals(map.get(username).getPassword()) && value < 3)
		 {	
			    label.setText("Wrong Username or Password Entered");
			    count.getAndIncrement(); //Every failed login attempt increments the atomic integer
			    return false;
		 }
		 else if((!password.equals(map.get(username).getPassword()) || password.equals(map.get(username).getPassword())) && value == 3)
		 {
			 if(addUser.admins.containsKey(username)) {
				 label.setText("Wrong admin Password : Use" + " Password " + "ass your password");
				 return false;
			 }else {
			 	
				label.setText("You account is blocked");
			 	label.setStyle("-fx-text-fill:red;\n");
			 	addUser.addblockedUserFile(username,"No");
			 	addUser.readBlockedUsers();
			 	count.getAndIncrement();
			 	return false;
			 	
			 }
		 }
		 else if((!password.equals(map.get(username).getPassword()) || password.equals(map.get(username).getPassword()))  && checkBlockedUSer(username)  && value > 3) {

			 	Hyperlink hyper = new Hyperlink("Click here to request password reset");
			 	label.setText("You account is blocked ");
			 	hbox.getChildren().clear();
			 	hbox.getChildren().addAll(label,hyper);
			 	hyper.setOnAction(event -> {
			 		addUser.forgotPasswords.put(username, "Yes");
			 		addUser.passwordRequest.add(username);
			 		addUser.updateBlockedUser();
			 		Register.errorMessage("Password reset request sent");
			 	});
			 	
				label.setStyle("-fx-text-fill:red;\n");
		 		return false;
		 }
	  	 
	  return true;
  }
  
  private static HBox optionsHBox(Stage stage) {
  	HBox optionsHbox = new HBox(10);
  		 optionsHbox.getStylesheets().add("Buttons.css");
  	String[] images = {"Admin","Settings"};
  	Button[] buttons = new Button[images.length];
  	
  	for(int i = 0; i < buttons.length; i++) {
  		buttons[i] = new Button();
  		buttons[i].setId(images[i]);
  		optionsHbox.getChildren().add(buttons[i]);
  	}
  	
  	buttons[0].setOnAction(event -> adminAccess(stage));
  	buttons[1].setOnAction(event -> {});
  	
  	optionsHbox.setAlignment(Pos.BASELINE_RIGHT);
  	return optionsHbox;
  }
  
  private static void adminAccess(Stage stage) {
	  
	  Stage adminAccessStage = new Stage();
	  		adminAccessStage.initStyle(StageStyle.UNDECORATED);
	  		adminAccessStage.initModality(Modality.APPLICATION_MODAL);
	  
	  		 GridPane gridPane = new GridPane();
	  		 		  gridPane.setVgap(10);
	  		 		  gridPane.setPadding(new Insets(10));
	  		 		  gridPane.getStylesheets().addAll("Dark.css","addLeaugesButtons.css");
	  		 Scene adminAccessScene = new Scene(gridPane,320,230);
	  		 	  
	  		 
	  String genUserName = "Admin", genUserPassword = "Password";
	  
	 
	  Label label = new Label("\t   To Access Admin Login Page \nEnter The login Creditentials you recieved \n\t\tfrom Administartor");
	  GridPane.setHalignment(label, HPos.CENTER);
	  Button cancelButton = new Button("Cancel");
	  		 cancelButton.setOnMousePressed(event -> adminAccessStage.close());
	  Button okButton = new Button("Login");
	  String[] textFieldsPromptText = {"Enter Admin Access UserName","Enter Admin Access Password"};
	  
	  		 
	  TextField[] textFiedls = new TextField[2];
	  for(int i = 0; i < textFiedls.length;i++) {
		  textFiedls[i] = new TextField();
		  textFiedls[i].setPromptText(textFieldsPromptText[i]);
		  gridPane.add(textFiedls[i], 0, i+1);
		  GridPane.setHalignment(textFiedls[i], HPos.CENTER);
	  }
	  
	  Label errorLabel = new Label();
	  	    errorLabel.setVisible(false);
	  	    errorLabel.setStyle("-fx-text-fill: red;\n -fx-font-size: 14px;\n -fx-font-weight: bold;\n");
	 
	  
	  okButton.setOnMousePressed(event -> {
		  if(textFiedls[0].getText().equals(genUserName) && textFiedls[1].getText().equals(genUserPassword)) {
			  stage.setScene(adminLogin.adminLoginScene(stage));
			  adminAccessStage.close();
		  }else if(textFiedls[0].getText().isEmpty() && textFiedls[1].getText().isEmpty()){
   			  errorLabel.setText("TextFields are Empty !!");
			  errorLabel.setVisible(true);
   		  }else {
   			 errorLabel.setText("Logons dont match !!");
			 errorLabel.setVisible(true);
   		  }
	  });
	  
	  gridPane.setOnKeyPressed((KeyEvent key) -> {
	    	 if(key.getCode() == KeyCode.ENTER) {
	    		 if(textFiedls[0].getText().equals(genUserName) && textFiedls[1].getText().equals(genUserPassword)) {
	   			  stage.setScene(adminLogin.adminLoginScene(stage));
	   			  adminAccessStage.close();
	   		  }else if(textFiedls[0].getText().isEmpty() && textFiedls[0].getText().isEmpty()){
	   			  errorLabel.setText("TextFields are Empty !!");
				  errorLabel.setVisible(true);
	   		  }else {
	   			 errorLabel.setText("Logons dont match !!");
				 errorLabel.setVisible(true);
	   		  }
	    	 }});
	  
	  HBox box = new HBox(10);
	  	   box.setAlignment(Pos.BOTTOM_RIGHT);
	       box.getChildren().addAll(cancelButton,okButton);
	  
	  GridPane.setHalignment(errorLabel, HPos.CENTER);
	  gridPane.add(label, 0, 0);
	  gridPane.add(box, 0, 4);
	  gridPane.add(errorLabel, 0, 3);
	  
	  adminAccessStage.sizeToScene();
	  adminAccessStage.setScene(adminAccessScene);
	  adminAccessStage.show();
	  
	  
  }
  
  /**
  * method checkeBlcokedUser check is userName is contained in 
   * the blocked user ArrayList
   * 
   * @param userName
   * @return boolean value
   */

  private static boolean checkBlockedUSer(String userName) {
		
	 return addUser.blockedUsers.contains(userName);
	 
}
  
  public static boolean userNameCheck(String username) {
			
	 		return addUser.users.containsKey(username) || addUser.admins.containsKey(username) || 
	 			   addUser.userNameEmail.containsKey(username) || addUser.adminsEmails.containsKey(username);
	 									
 }
 
  public static void setCurrentUser(String user) {
	  logedinUser = user;
  }
  
  public static String getCurrentUser() {
	  return logedinUser;
  }

}
