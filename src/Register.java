import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.scene.image.*;
import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register {

	private static String picturePath = "";
	public static TitledPane passwordLable = new TitledPane();


public static Scene userRegister(Stage stage){
		
		BorderPane borderPane = new BorderPane();
		Register reg = new Register();
				Scene userDetailsScene = new Scene(borderPane);
				      borderPane.setPadding(new Insets(10));
				      borderPane.setId("pane");
				      borderPane.getStylesheets().addAll("addLeaugesButtons.css","RegisterPane.css");
					
				    Util.OnMousePressed(borderPane, stage);
					Util.OnMouseDragged(borderPane, stage);
						
					borderPane.setTop(Util.toolBar(stage, true));
				    reg.userDetailsPanel(borderPane, stage);

				    return userDetailsScene;
	}
	
private void userDetailsPanel(BorderPane pane, Stage stage) {
		
		GridPane gridPane = new GridPane();
				 gridPane.setAlignment(Pos.CENTER);
				 gridPane.setVgap(15);	
				 gridPane.setHgap(10);
				 gridPane.setPadding(new Insets(0,50,0,100));
				  gridPane.getStylesheets().addAll("Accordion.css");
		    final String detailsLabels[] = {"Name","Second Name", "User Name","Email", "Date of Birth","Password", "Confrim Password", "Select User Image"};
			final String displayDetailsLabels[] = {"Name","Second Name", "User Name","Email", "Date of Birth"};
			final String[] passwordFieldPrompt = {"Eneter Password", "Confirm Password"};
							
			DatePicker datePicker = new DatePicker();
					   datePicker.setMinWidth(250);
					   datePicker.setPromptText("Select Date of Birth");
					   gridPane.add(datePicker, 1, 6);					  
													   
			ComboBox<String> userPicPath = new ComboBox<>();
				  			
			ImageView userPic = new ImageView("user.png");
					GridPane.setHalignment(userPic, HPos.CENTER);

		    Separator sep = new Separator();
					  sep.setOrientation(Orientation.VERTICAL);
					  sep.setPadding(new Insets(10,60,10,50));
					  sep.setVisible(false);
				
			Label[] label = new Label[detailsLabels.length];
					TextField[] textFieldsInput = new TextField[4];
					PasswordField[] password = new PasswordField[2];
			 
			String passwordFieldTooltip = "\nPass word needs to contain\n\n * At Least 1 uppercase\n * "
											+ "At Least 1 special character\n * Needs to be minimum 8 characters in length\n "
											+ "* Cannont contain either first or secondname\n * More than 3 digits can`t be in sucession\n";
					   
				   	
			for(int i = 0; i < label.length; i++) {
					
			   label[i] = new Label(detailsLabels[i] + " :");
					if(i < (textFieldsInput.length)) {
						
							textFieldsInput[i] = new TextField();
							textFieldsInput[i].setPromptText(detailsLabels[i]);
							if(i < 4) {
								gridPane.add(textFieldsInput[i],1, i+2);
								
							}
					}
					if(i > 4 && i <  7) {
						    password[i-5] = new PasswordField();
						    password[i-5].setPromptText(passwordFieldPrompt[i-5]);
							password[i-5].setTooltip(new Tooltip(passwordFieldTooltip));
					}
					gridPane.add(label[i], 0, i+2);
			}
			
					   passwordLable.setExpanded(false);
					   passwordLable.setContent(new Label(passwordFieldTooltip));
			
			
		    StackPane stackPane = new StackPane();
		    	      StackPane.setAlignment(passwordLable, Pos.BOTTOM_CENTER);
		    	      StackPane.setAlignment(password[0], Pos.TOP_CENTER);
		   		      stackPane.getChildren().addAll(passwordLable,password[0]);
		    	     		   
				 
				 gridPane.add(stackPane, 1, 7);
				 gridPane.add(password[1], 1,8);
				
			textFieldsInput[3].setTooltip(new Tooltip("Email must be of the format name@domain.com"));
			
			Label[] displayDetailLabels = new Label[(displayDetailsLabels.length*2)+1];

			for(int i = 0; i < displayDetailLabels.length; i++) {
						if(i < displayDetailLabels.length/2) {
							
							displayDetailLabels[i] = new Label(displayDetailsLabels[i] + " :");
							gridPane.add(displayDetailLabels[i], 3, i+6);
						
						}
						else if(i < displayDetailLabels.length) {
							
							displayDetailLabels[i] = new Label();
							displayDetailLabels[i].setMinWidth(200);
							displayDetailLabels[i].setMaxWidth(200);
							gridPane.add(displayDetailLabels[i], 4, i);
							
			}}

					userPicPath.setPrefWidth(250);
					userPicPath.setOnMousePressed(event -> {
						String image = openPicFile(stage,userPicPath);
						if(!image.isEmpty()) {
							userPic.setImage(new Image(image));
							setPicturePath(image);
						}
					});

					textFieldsInput[0].textProperty().addListener((observable, oldValue, NewValue) -> {
						displayDetailLabels[6].setText(NewValue);
					});
				
					textFieldsInput[1].textProperty().addListener((observable, oldValue, NewValue) -> {
						displayDetailLabels[7].setText(NewValue);
					});
					
					textFieldsInput[2].textProperty().addListener((observable, oldValue, NewValue) -> {
						displayDetailLabels[8].setText(NewValue);
						if(!addUser.users.isEmpty()) {
							if(userNameCheck(NewValue)) {
								textFieldsInput[2].setStyle("-fx-border-color: red;\n");
								displayDetailLabels[8].setText("User name already exists");
								displayDetailLabels[8].setStyle("-fx-text-fill:red;\n");
								
							}else {
								textFieldsInput[2].setStyle("-fx-border-color: green;\n");
								displayDetailLabels[8].setText(textFieldsInput[2].getText());
							}}else {
								textFieldsInput[2].setStyle("-fx-border-color: green;\n");
								displayDetailLabels[8].setText(textFieldsInput[2].getText());
							}
					});
			
					if(textFieldsInput[3].getText().isEmpty()) {
						textFieldsInput[3].setStyle("-fx-background-color: white;\n");
					}
					
					StackPane _stackPane = new StackPane();
							  TitledPane titlePane = new TitledPane();
							  			 Label _label = new Label();
							  			 titlePane.setContent(_label);
							  			 titlePane.setExpanded(false);
							  			 _stackPane.getChildren().addAll(titlePane,textFieldsInput[3]);
							  			 StackPane.setAlignment(textFieldsInput[3], Pos.TOP_CENTER);
							  			 StackPane.setAlignment(titlePane, Pos.BOTTOM_CENTER);
							  			 
							  			 gridPane.getChildren().remove(textFieldsInput[3]);
							  			 gridPane.add(_stackPane, 1, 5);
					
				 textFieldsInput[3].textProperty().addListener((observable, oldValue, NewValue) -> {
						displayDetailLabels[9].setText(NewValue);
						if(!NewValue.contains("@")) {
				  			 titlePane.setExpanded(true);
							textFieldsInput[3].setStyle("-fx-background-color: red;\n");
							_label.setText(" \n- Email must be of the format name@domain.com\n");
						}else {
							textFieldsInput[3].setStyle("-fx-background-color: green;\n");
				  			 titlePane.setExpanded(false);
				  			 _label.setText("");

						}
					});
					
				
					HBox box = new HBox(10);
						 box.setAlignment(Pos.BASELINE_RIGHT);
					
				    String[] buttonsLabels = {"Return","Finish"};
						Button[] buttons = new Button[buttonsLabels.length];
					
					for(int i = 0; i < buttonsLabels.length; i++){
						buttons[i] = new Button(buttonsLabels[i]);
						box.getChildren().addAll(buttons[i]);
					}
					
					password[0].setOnMousePressed(event -> {
						passwordLable.setExpanded(true);	
					});
					
					password[0].textProperty().addListener((observable, oldValue, NewValue) -> {
						passwordLable.setExpanded(true);	
						if(passwordCheck(password[0].getText(),textFieldsInput[0].getText(),textFieldsInput[1].getText())) {
							password[0].setStyle("-fx-background-color: green;\n");
						}else {
							password[0].setStyle("-fx-background-color: red;\n");
						}
					});
								
					password[1].textProperty().addListener((observable, oldValue, NewValue) -> {
						passwordLable.setExpanded(true);	
						if(NewValue.equals(password[0].getText()) && passwordCheck(password[0].getText(),textFieldsInput[0].getText(),textFieldsInput[1].getText())) {
							password[1].setStyle("-fx-background-color: green;\n");
							password[0].setStyle("-fx-background-color: green;\n");
						}else if(NewValue.length() == password[0].getText().length() && !NewValue.equals(password[0].getText())) {
							password[1].setStyle("-fx-background-color: red;\n");
							password[0].setStyle("-fx-background-color: red;\n");
							passwordLable.setContent(new Label("\n* Passwords dont match !!"));
						}
					});
				
					buttons[0].setOnMouseClicked(event -> {
						stage.setScene(adminLogin.adminLoginScene(stage));						
					});
					
					buttons[1].setOnMousePressed(event -> {
						if(!userNameCheck(textFieldsInput[2].getText()) && passwordCheck(password[0].getText(),textFieldsInput[0].getText(),textFieldsInput[1].getText())) {
							userLogin.setCurrentUser(textFieldsInput[2].getText());
							addUser.admins.put(textFieldsInput[2].getText(), new User(textFieldsInput[2].getText(),textFieldsInput[0].getText(),textFieldsInput[1].getText(),textFieldsInput[3].getText(),datePicker.getValue().toString(),getPicturePath(),password[0].getText(),"0","0","null"));
							addUser.writeCsvFile( new User(textFieldsInput[2].getText(),textFieldsInput[0].getText(),textFieldsInput[1].getText(),textFieldsInput[3].getText(),datePicker.getValue().toString(),getPicturePath(),password[0].getText(),"0","0","null"), "admin");
							stage.setScene(tableLeauge.mainScene(stage,true));
					}});
				
				
					gridPane.add(sep, 2, 0);
					gridPane.add(userPic, 4,1,1,4);
					gridPane.add(userPicPath, 1, 9);
					gridPane.add(new Separator(),3,5,2,1);
					pane.setBottom(box);
					pane.setCenter(gridPane);
					
	}

public static boolean userNameCheck(String username) {
	 
	 return addUser.admins.containsKey(username);
			 
}

public static String openPicFile(Stage stage, ComboBox<String> box)
	{
		
		FileChooser fileChooser = new FileChooser();
					fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
					fileChooser.getExtensionFilters().addAll(
																new FileChooser.ExtensionFilter("PNG", "*png"),
																new FileChooser.ExtensionFilter("JPG", "*jpg"));
		
		File file = null;
		Image image = null;
		
		String size = "";
					
		file = fileChooser.showOpenDialog(stage);
		
		try {
		
			size = file.toURI().toString();
			box.setValue(size);
			image = new Image(file.toURI().toString());
		if(image.getHeight() > 130 || image.getWidth() > 130)errorMessage("Picture Size to Big !!"); 
		
		}
		catch(NullPointerException exception){
			
			}
		return size;
	};
	
public static void errorMessage(String error) {
		
		Stage errorStage = new Stage();
	          errorStage.initStyle(StageStyle.UNDECORATED);
			  errorStage.initModality(Modality.WINDOW_MODAL);
			  errorStage.setAlwaysOnTop(true);

				
		BorderPane vbox = new BorderPane();
			       vbox.setPadding(new Insets(10,10,10,10));
			       vbox.getStylesheets().addAll("Dark.css","addLeaugesButtons.css");
				   vbox.setStyle("-fx-font-fill: red;\n");
				   
        Scene erroScene = new Scene(vbox, 300, 150);
			
		Button ok = new Button("Ok");
			   ok.setAlignment(Pos.BOTTOM_RIGHT);
			   ok.setOnAction(event -> {
				   errorStage.close();
				});
			
				Label errorLabel = new Label(error);

				vbox.setCenter(errorLabel);
				vbox.setBottom(ok);
				
				errorStage.setScene(erroScene);
				errorStage.sizeToScene();
				errorStage.show();
				
	}
		
public static boolean passwordCheck(String password, String name, String surname)
	{

		if(password.length() < 8 && password.length() > 1 && !Character.isUpperCase(password.charAt(0))){
			passwordLable.setContent(new Label("\n *Password needs to be minimum 8 \ncharachters long starting with a UpperCase"));
			return false; 
		}
		else if(password.contains(name) || password.contains(surname)) {
			passwordLable.setContent(new Label("\n *Password can't contain Name or Surname"));
			return false;}
		else if(password.length() >= 8 && !Character.isUpperCase(password.charAt(0))) {
			passwordLable.setContent(new Label("\n *Password needs to start and \ncontain at least 1 Upper Case"));
			return false;
		}
		else if(password.length() >= 8 && Character.isUpperCase(password.charAt(0))) {	
			String specialCaracters = "!£$%^&?>*<@~#:;";
			int count = 0;
			for(int i = 0; i < password.length(); i++) {
				if(Character.isDigit(password.charAt(i)))count++;
				}
			
			if(count > 2) {
				int[] digits = new int[count];
					int i = 0;
					while(i < count) {
					for(int j = 0; j < password.length(); j++) {
						if(Character.isDigit(password.charAt(j)))
						{
							digits[i] = Integer.valueOf(Character.toString(password.charAt(j)));
						}
					}
					i++;
					}
				
			if((digits[0+1] - digits[0]) == 1 && (digits[0+2] - digits[0+1]) == 1){
					passwordLable.setContent(new Label("\n *more than 3 digits can't be in succession"));
					return false;
			}
				
			Pattern pattern = Pattern.compile("[^a-z0-9]",Pattern.CASE_INSENSITIVE);
			Matcher match = pattern.matcher(password);
			boolean matched = match.find();

			if(matched !=  true)
			{
					passwordLable.setContent(new Label("\n *Password needs to contain at \nleast 1 special character" + specialCaracters));
					return false ;
					}
			}
		}
		 
		passwordLable.setContent(new Label(""));
		passwordLable.setExpanded(false);
		return true;
	}

public static boolean passwordMatch(String password, String confirmedPassword)
	{
		if(!password.matches("(?i:.*"+confirmedPassword+".*)") || (password.length() != confirmedPassword.length())) //Function returns true if password match with password confirmation
		{
			errorMessage("Password dont match !!"); //String.matches is case insensitive therefore this (?i:String) checks the string case-sensitively;
			return false;
		}
		return true;
	}

public static String autoPasswordGenerater() {
	
	StringBuilder builder = new StringBuilder();
	Random rand = new Random();
	while(builder.length() != 8) {
		builder.append(Character.toChars((rand.nextInt(126))+33));
	}
	return builder.toString();
}

private static void setPicturePath(String path) {
	picturePath = path;
}

private static String getPicturePath()
{
	return picturePath;
}

}
