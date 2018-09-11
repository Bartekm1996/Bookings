import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.*;


public class tableLeauge {

	public final static TableView<User> UsersTableView = new TableView<User>(); //displays users on admins account
	public final static TableView<Booking> bookingsView = new TableView<Booking>(); //display facility's bookings
	public final static TableView<Booking> usersBookingView = new TableView<Booking>(); //displays user bookings
	private final static TableView<date> avibilityTableView = new TableView<date>(); // displays facilities avibility
	public final static TableView<userActivity> userActivityTableView = new TableView<>(); //displays users statements
	public static ObservableList<Booking> observable;
	public static ObservableList<User> user;
	public static FlowPane flowPane = new FlowPane();
	private static String picutrePath;
	private static String facility;
	private static DatePicker datePicker = new DatePicker();
	private static CheckBox checkBox = new CheckBox("View bookings between to dates");

	
public static Scene mainScene(Stage primaryStage,boolean admin)
{
		

			Label label = new Label();
 			      label.setStyle("-fx-font-size: 18px;" + "-fx-text-fill: black;");
 			      label.setText("Seleact a facility to view its bookings");
 			      userBookingsDisplay();
 			      bookingDisplay(admin);
 			      avibilityTableView.getColumns().clear();
 			      avibilityTableView.getItems().clear();
 			      avibilityTableView();
 			      
		BorderPane pane = new BorderPane();
	      		   pane.setPadding(new Insets(10,0,0,10));

			Scene tableScene = new Scene(pane,Util.screenSize.getWidth()/1.5,Util.screenSize.getHeight()/1.5);
			      tableScene.getStylesheets().addAll("Accordion.css","RegisterButtons.css","vbox.css","Dark.css");
			     
				  Util.OnMousePressed(pane, primaryStage);
			      Util.OnMouseDragged(pane, primaryStage);

			      Label currentUserLabel = new Label();
			      		if(admin) {
			      			pane.setLeft(adminSelectionPannel(pane,primaryStage));
			      			currentUserLabel.setText("Admin: " + userLogin.getCurrentUser());
			      		}else {
			      			pane.setLeft(userSelectionPannel(pane,primaryStage));
			      			
				      			currentUserLabel.setText("User: " + userLogin.getCurrentUser());
			      		
			      		}
				        currentUserLabel.setAlignment(Pos.TOP_RIGHT);
				   	        
				   StackPane stackPane = new StackPane();
				   			 stackPane.setPadding(new Insets(0,10,0,0));
				   StackPane.setAlignment(currentUserLabel, Pos.TOP_RIGHT);
				   StackPane.setAlignment(top(primaryStage), Pos.TOP_LEFT);
				   stackPane.getChildren().addAll(top(primaryStage),currentUserLabel);
				   pane.setTop(stackPane);
			
			return tableScene;
		
}
	
private static HBox top(Stage stage)
	{
		HBox box = new HBox(10);
			 box.setId("hbox");
			 box.getStylesheets().add("userTableView.css");
			 box.getChildren().addAll(Util.toolBar(stage, true), Util.toolBarMenu(stage));
		return box;
	}
	
private static Accordion adminSelectionPannel(BorderPane pane,Stage stage) {
	
	final String[] adminLabels = {"Bookings / Manage Facilites","Manage Users"};
	final String[] adminSubLabels = {"View Bookings /\n Manage Facilites","View Users"};
	final TitledPane[] titledPanes = new TitledPane[adminLabels.length]; 
	final Accordion accordion = new Accordion (); 
					accordion.setMinWidth(300);
					accordion.setMaxWidth(300);
		
					
	final ToggleGroup toggle = new ToggleGroup();
	
	RadioButton[] label = new RadioButton[adminSubLabels.length];
	
	for(int i = 0; i < adminSubLabels.length; i++) {
		
		label[i] = new RadioButton(adminSubLabels[i]);
		label[i].setMaxWidth(150);
		label[i].setMinWidth(150);
		if(i < 3) {
			titledPanes[i] = new TitledPane();
			titledPanes[i].setText(adminLabels[i]);
			titledPanes[i].setStyle("-fx-background-color: transparent;\n");
		}
		label[i].setToggleGroup(toggle);
	}
	
	toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	   @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            if(toggle.getSelectedToggle() != null){
            	if(label[0].isSelected()) {
            		
            		bookingDisplay(addUser.admins.contains(userLogin.getCurrentUser()));
            		pane.setCenter(viewFacilitiesBookings(stage));
					bookingsView.setPlaceholder(new Label("Select a facility to view bookings"));

            	}
            	else if(label[1].isSelected()) {
            		UsersTableView.getColumns().clear();
            		bookingsView.getColumns().clear();
            		UsersTableView.setItems(FXCollections.observableArrayList(addUser.userListView));
					tableLeauge.bookingsView.setPlaceholder(new Label("Select user from usertable to view bookings"));
            		pane.setCenter(editUsers(stage,true));
     }}}});
	

	VBox togglVbox[] = new VBox[2];
		
		 togglVbox[0] = new VBox(10);
		 togglVbox[0].getChildren().addAll(label[0],label[1]);
		 togglVbox[0].setId("Vbox");
		 togglVbox[1] = new VBox(10);
		 togglVbox[1].getChildren().addAll(label[1]);
		 
		 titledPanes[0].setContent(togglVbox[0]);
		 titledPanes[1].setContent(togglVbox[1]);
		 
		 accordion.setMaxHeight(150);
		 accordion.getPanes().addAll(titledPanes[0],titledPanes[1]);
	     
	return accordion;
}

private static void addUser() {
	
	Stage addUserStage = new Stage();
		  addUserStage.initModality(Modality.WINDOW_MODAL);
		  addUserStage.initStyle(StageStyle.UTILITY);
	
	GridPane gridPane = new GridPane();
			 gridPane.setHgap(15);
			 gridPane.setVgap(15);
			 gridPane.setPadding(new Insets(10));
			 gridPane.getStylesheets().addAll("Dark.css","addLeaugesButtons.css");
			 
			Scene addUserScene = new Scene(gridPane,425,125);
		  
					String[] labels = {"Enter Email: ","Generated Password: ",""};
						Label[] label = new Label[labels.length];
							TextField textFields = new TextField();
									   textFields.setMinWidth(225);
		  
		  Button button = new Button("Add User");
		  	     button.setAlignment(Pos.BOTTOM_RIGHT);
		 
		for(int i = 0; i < labels.length; i++) {
			  if(i < 2) {
				  label[i] = new Label(labels[i]);
				  gridPane.add(label[i], 0, i);
			  }else if(i > 1) {
				  label[i] = new Label();
				  gridPane.add(label[i], 1, i-1);
			  }
		  }
		  
		  GridPane.setHalignment(button, HPos.RIGHT);
		  button.setOnMousePressed(event -> {
			  String password = Register.autoPasswordGenerater();
			  if(!password.isEmpty()) {
				  if(!textFields.getText().contains("@")) { 
					  Register.errorMessage("Wrong email format needs to\n be in name@domain.com");}
					  else if(addUser.usersEmails.contains(textFields.getText())){
						  Register.errorMessage("User already exists !!");
					  }
				  
				  else {
					
					    label[2].setText(password);
					  	addUser.writeCsvFile(new User("null","null","null",textFields.getText(),"null","null",password,"0","0","null"), "user");
					  	addUser.users.put(textFields.getText(),new User("null","null","null",textFields.getText(),"null","null",password,"0","0","null"));
					  	addUser.userListView.add(new User("null","null","null",textFields.getText(),"null","null",password,"0","0","null"));
					  	Register.errorMessage("User succesfully created");
					  	addUser.initEverything();
					  	UsersTableView.getItems().clear();
					  	UsersTableView.getColumns().clear();
					  	displayUsers();
					  	UsersTableView.setItems(FXCollections.observableArrayList(addUser.userListView));
				  }}
		  });

		  gridPane.add(textFields, 1, 0);
		  gridPane.add(button, 1, 2);
		  addUserStage.setResizable(false);
		  addUserStage.sizeToScene();
		  addUserStage.setScene(addUserScene);
		  addUserStage.show();
	
}

private static BorderPane userDetailsPanel(Stage stage,boolean user) {

	BorderPane pane = new BorderPane();
	
	GridPane gridPane = new GridPane();
			 gridPane.setAlignment(Pos.CENTER);
			 gridPane.setVgap(15);	
			 gridPane.setHgap(10);
			 gridPane.setPadding(new Insets(-20,20,20,-70));
			 
	    final String detailsLabels[] = {"Name","Second Name", "User Name","Email", "Date of Birth","Select User Image"};
		final String displayDetailsLabels[] = {"Name","Second Name", "User Name","Date of Birth","Email"};
		final String[] passwordFieldPrompt = {"Password", "Confirm Password"};
						
		DatePicker dateOfBirth = new DatePicker();
		           dateOfBirth.setMinWidth(200);
		           dateOfBirth.setMaxWidth(200);
		           dateOfBirth.setPromptText("Select Date of Birth");
				   gridPane.add(dateOfBirth, 1, 6);					  
												   
		ComboBox<String> userPicPath = new ComboBox<>();
						 userPicPath.setMinWidth(200);
						 userPicPath.setMaxWidth(200);
			  			
		ImageView userPic = new ImageView("user.png");
				 GridPane.setHalignment(userPic, HPos.CENTER);

	    Separator sep = new Separator();
				  sep.setOrientation(Orientation.VERTICAL);
				  sep.setPadding(new Insets(10,30,10,30));
				  sep.setVisible(false);
			
		Label[] label = new Label[detailsLabels.length];
				TextField[] textFieldsInput = new TextField[4];
				PasswordField[] password = new PasswordField[2];
		 
		
		for(int i = 0; i < label.length; i++) {
				
		   label[i] = new Label(detailsLabels[i] + " :");
					if(i < (textFieldsInput.length)) {
						textFieldsInput[i] = new TextField();
						textFieldsInput[i].setMaxWidth(200);
						textFieldsInput[i].setMinWidth(200);
						textFieldsInput[i].setPromptText(detailsLabels[i]);
						if(i < 4) {
							gridPane.add(textFieldsInput[i],1, i+2);
						}
		}
				
				gridPane.add(label[i], 0, i+2);
		}
		
		TitledPane passwordPane = new TitledPane();
		CheckBox checkBox = new CheckBox();
		HBox vbox = new HBox(200);
	
		if(user) {
		 
			String passwordFieldTooltip = "Pass word needs to contain\n * At Least 1 uppercase\n * "
					+ "At Least 1 special character\n * Needs to be minimum 8 characters in length\n "
					+ "* Cannont contain either first or secondname\n * More than 3 digits can`t be in sucession\n";

			GridPane passwordGridPane = new GridPane();
			     	 passwordGridPane.setHgap(10);
			         passwordGridPane.setVgap(15);
			     	textFieldsInput[3].setDisable(true);
					textFieldsInput[3].setEditable(false);
					
		
			         for(int i = 0; i < 2; i++) {
			        	 password[i] = new PasswordField();
			        	 password[i].setPromptText("Enter" + passwordFieldPrompt[i]);
			        	 password[i].setTooltip(new Tooltip(passwordFieldTooltip));	
			        	 passwordGridPane.add(new Label(passwordFieldPrompt[i]), 0, i);
			        	
		}
		       passwordGridPane.add(password[1],1,1);

			  if(!textFieldsInput[3].getText().isEmpty())
			  {
				     textFieldsInput[3].setEditable(false);  
			  }
			  	   passwordPane.setContent(passwordGridPane);
				   passwordPane.setExpanded(false);
				   passwordPane.setId("titlePane");
				   passwordPane.getStylesheets().add("Accordion.css");
				
				   checkBox.setOnAction(event -> {
		   		    	if(checkBox.isSelected()) {
		   		    		passwordPane.setExpanded(true);
		   		    	}else {
		   		    		passwordPane.setExpanded(false);
		   		    	}
		   		    });
				   
				   
			        vbox.getChildren().addAll(new Label("Change Password:"),checkBox);
			             gridPane.add(vbox, 0,8,2,1);
			             gridPane.add(passwordPane, 0, 9,2,1);
			          
			        StackPane stackPane = new StackPane();
			     
			        		   stackPane.getChildren().addAll(Register.passwordLable,password[0]);
			        		   StackPane.setAlignment(Register.passwordLable, Pos.BOTTOM_CENTER);
			        		   StackPane.setAlignment(password[0], Pos.TOP_CENTER);
					
			        password[0].setOnAction(event -> {
			        	Register.passwordLable.setExpanded(true);
			        });
			        password[0].textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> observablePassword, String oldPassword, String newPassword) {
 							if(!newPassword.isEmpty() && Register.passwordCheck(newPassword,textFieldsInput[0].getText(),textFieldsInput[1].getText()))
 								password[0].setStyle("-fx-background-color: green;\n");
 							else password[0].setStyle("-fx-background-color: red;\n");	
						}
			        });
				     password[1].textProperty().addListener((observable, oldValue, NewValue) -> {
								if(NewValue.equals(password[0].getText())) {
									password[1].setStyle("-fx-background-color: green;\n");
								}else if(NewValue.length() == password[0].getText().length() && !NewValue.equals(password[0].getText())) {
									password[1].setStyle("-fx-background-color: red;\n");
									Register.errorMessage("Passwords dont match");
								}
							});
				     
				     passwordGridPane.add(stackPane, 1, 0);
		}else {
		    
			for(int i = 0; i < 4;i++) {
				textFieldsInput[i].setDisable(true);
			}
			textFieldsInput[3].setDisable(true);
			textFieldsInput[3].setEditable(false);
			dateOfBirth.setDisable(true);
			userPicPath.setDisable(true);
			
			
		}
		
		Label[] displayDetailLabels = new Label[(displayDetailsLabels.length*2)+1];
		for(int i = 0; i <= displayDetailLabels.length; i++) {
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
		 	
	                  
		textFieldsInput[3].setTooltip(new Tooltip("Email must be of the format name@domain.com"));
		
		userPicPath.setOnMousePressed(event -> {
					String image = Register.openPicFile(stage,userPicPath);
					if(!image.isEmpty()) {
						userPic.setImage(new Image(image));
						setPicPath(image);
		}});

		if(addUser.users.containsKey(userLogin.getCurrentUser())) {
		
			displayDetailLabels[6].setText(addUser.users.get(userLogin.getCurrentUser()).getName());
			displayDetailLabels[7].setText(addUser.users.get(userLogin.getCurrentUser()).getSurName());
			displayDetailLabels[8].setText(addUser.users.get(userLogin.getCurrentUser()).getUserName());
			displayDetailLabels[10].setText(addUser.users.get(userLogin.getCurrentUser()).getEmail());
			displayDetailLabels[9].setText(addUser.users.get(userLogin.getCurrentUser()).getDateOfBirth());
			
			bookingsView.setItems(FXCollections.observableArrayList(addUser.userBookings.get(userLogin.getCurrentUser())));
		}
			
		
		textFieldsInput[0].textProperty().addListener((observable, oldValue, NewValue) -> {
					displayDetailLabels[6].setText(NewValue);
		});
			
		textFieldsInput[1].textProperty().addListener((observable, oldValue, NewValue) -> {
					displayDetailLabels[7].setText(NewValue);
		});
				
		textFieldsInput[2].textProperty().addListener((observable, oldValue, NewValue) -> {
					displayDetailLabels[8].setText(NewValue);
		});
				
		textFieldsInput[3].setOnMouseClicked(event -> {
					userLogin.setCurrentUser(textFieldsInput[2].getText());
					if(!addUser.users.isEmpty()) {
					if(userLogin.userNameCheck(textFieldsInput[2].getText())) {
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
				
		textFieldsInput[3].textProperty().addListener((observable, oldValue, NewValue) -> {
					displayDetailLabels[10].setText(NewValue);
		});
		
		dateOfBirth.setOnAction(event -> {
			displayDetailLabels[9].setText(dateOfBirth.getValue().toString());
		});
		
		try {
			
		UsersTableView.getSelectionModel().selectedItemProperty().addListener(  
				  (ObservableValue<? extends User> ov, User old_val, User new_val) -> {
		        	   
					   usersBookingView.getColumns().clear();
					   userBookingsDisplay(); 
				   		for(int i = 0; i < 4;i++) {
				   			textFieldsInput[i].setEditable(true);
				   		}
				   		
				   	if(new_val != null) {	
				   	if(addUser.userBookings.containsKey(new_val.getEmail()) && addUser.users.containsKey(new_val.getEmail())){
				   		if(!addUser.userBookings.get(new_val.getEmail()).isEmpty()) {
				   		 usersBookingView.getItems().clear();
				   		 usersBookingView.setItems(FXCollections.observableArrayList(addUser.userBookings.get(new_val.getEmail())));
				   	}else {
						   tableLeauge. usersBookingView.setPlaceholder(new Label("No Bookings yet made by this user"));

				   	}
				   	
				   	}
				   	 
				       textFieldsInput[0].setText(new_val.getName());
				       textFieldsInput[1].setText(new_val.getSurName());
				       textFieldsInput[2].setText(new_val.getUserName());
				       textFieldsInput[3].setText(new_val.getEmail());
				       dateOfBirth.setPromptText(new_val.getDateOfBirth());
		        	   displayDetailLabels[6].setText(new_val.getName());
		        	   displayDetailLabels[7].setText(new_val.getSurName());
		        	   displayDetailLabels[8].setText(new_val.getUserName());
		        	   displayDetailLabels[10].setText(new_val.getEmail());   
		        	   displayDetailLabels[9].setText(new_val.getDateOfBirth());
		        }}
		);}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		FlowPane flowPane = new FlowPane();
		         flowPane.setPadding(new Insets(10));
		         flowPane.setHgap(10);
				
				String[] buttonLabels = {"Add User","Save Changes","View Stament"};
					Button[] button = new Button[buttonLabels.length];
				
					String[] colourKey = {"TableView Color Key : ","Red : ","User Blocked","|","Green :"," User Unlocked","|","Gray","&","Red :","User requested Password reset"};
					Label[] colourKeyLabel = new Label[colourKey.length];
					
				for(int i = 0; i < button.length; i++) {
					button[i] = new Button(buttonLabels[i]);
		
				}
				for(int i = 0; i < colourKey.length;i++) {
					colourKeyLabel[i] = new Label(colourKey[i]);
				}
				
				colourKeyLabel[1].setStyle("-fx-text-fill: red;");
				colourKeyLabel[4].setStyle("-fx-text-fill: green;");
				colourKeyLabel[9].setStyle("-fx-text-fill: red;");
				
				if(addUser.admins.containsKey(userLogin.getCurrentUser())) {
					flowPane.getChildren().addAll(button[0],colourKeyLabel[0],colourKeyLabel[1],colourKeyLabel[2],colourKeyLabel[3],colourKeyLabel[4],colourKeyLabel[5],colourKeyLabel[6],colourKeyLabel[7],colourKeyLabel[8],
							colourKeyLabel[9],colourKeyLabel[10]);
				}else {
					flowPane.getChildren().addAll(button[1],button[2]);
				}
 
				button[0].setOnAction(event -> addUser());

				button[1].setOnMousePressed(event -> {
					try {
						changeUserDetails(textFieldsInput[0].getText(),textFieldsInput[2].getText(),textFieldsInput[1].getText(),displayDetailLabels[9].getText(),getPicPath(),password[1].getText());
	    				addUser.saveEditedAccounts();
	    				Register.errorMessage("Details updated");
					}catch(NullPointerException exception) {
						exception.printStackTrace();
					}});
				
				button[2].setOnAction(event -> {
					userActivityTableView.getColumns().clear();
					viewStatement(userLogin.getCurrentUser());
				});
		
				gridPane.add(sep, 2, 0);
				gridPane.add(userPic, 4,1,1,4);
				gridPane.add(userPicPath, 1, 7);
				FlowPane box = new FlowPane();
						 box.setHgap(10);
					
			    Separator separator = new Separator();
			    Label labele = new Label("User Details");
			    	  labele.setStyle("-fx-font-size: 16px;");
			    	  labele.setId("UserDetailsLabel");
			    	  separator.setMinWidth(200);
			    	  box.getChildren().addAll(labele,separator);
				gridPane.add(box,3,5,2,1);
				pane.setTop(flowPane);
				pane.setCenter(gridPane);
				return pane;
				
}

private static void changeUserDetails(String name,String username,String date,String surname,String userPic,String password) {
			 addUser.userListView.clear();
	
			 if(username.length() != 0) {
				 addUser.users.get(userLogin.getCurrentUser()).setUserName(username);
			 }
			 if(username.length() != 0){
				 addUser.users.get(userLogin.getCurrentUser()).setSurName(surname);
			 }
			 if(username.length() != 0) {
				 addUser.users.get(userLogin.getCurrentUser()).setName(name);
			 }
			if(username.length() != 0) {
				 addUser.users.get(userLogin.getCurrentUser()).setDateOfBirth(date);
			}
			if(!password.isEmpty()) {
				 addUser.users.get(userLogin.getCurrentUser()).setPassword(password);
			}
			for(String userEmail : addUser.usersEmails) {
				 addUser.userListView.add(addUser.users.get(userEmail));
			 }
			 
}

private static BorderPane editUsers(Stage stage,boolean admin) {
	
	BorderPane borderPane = new BorderPane();
	
			   if(admin) {
			       displayUsers();
				   borderPane.setLeft(UsersTableView);
			       borderPane.setCenter(userDetailsPanel(stage,false));
			   }else {
				   
				   bookingsView.getColumns().clear();
			       bookingDisplay(true);
			       borderPane.setCenter(userDetailsPanel(stage,true));

			   }
			  
			   borderPane.setBottom(usersBookingView);
			 
			   
		return borderPane;
}

private static BorderPane viewFacilitiesBookings(Stage stage) {
	
	bookingsView.getColumns().clear();
	BorderPane borderPane = new BorderPane();

			 flowPane.setVgap(10);
			 flowPane.setOrientation(Orientation.VERTICAL);
			 flowPane.setPadding(new Insets(10));
			 
    FlowPane flowPaneHorizontal = new FlowPane();
    		 flowPaneHorizontal.getStylesheets().add("userTableView.css");
    		 flowPaneHorizontal.setHgap(10);
    		 flowPaneHorizontal.setPadding(new Insets(10));

				   			  
    DatePicker datePickerUntil = new DatePicker();
    		   datePickerUntil.setPromptText("Enter until date");
	   	       datePickerUntil.setDisable(true);
	   	       datePicker.setPromptText("Enter from date");
	   	       
	   	       if(flowPane.getChildren().isEmpty()) {
	   	    	   datePicker.setDisable(true);
	   	    	   checkBox.setDisable(true);
	   	       }else {
	   	    	   checkBox.setDisable(false);
	   	    	   datePicker.setDisable(false);
	   	       }
	   	 
    boolean selected = false;
    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
	   	       if(arg2 == true) {
	   	    	   datePickerUntil.setDisable(false);
	   	       }else {
	   	    	   datePickerUntil.setDisable(true);

	   	       }
		}
    	
    });
    		   datePicker.setOnAction(event -> {
    			   if(selected == false) {
    		
    				String[] _date1 = datePicker.getValue().toString().replaceAll("/", "-").split("-");
    				String fomratedString1 = _date1[0] + "-" + _date1[1] + "-" + _date1[2];
    				bookingsView.getItems().removeIf(booking -> !booking.getDates().equals(fomratedString1));
    				if(getFacility() == null) {
    					Register.errorMessage("Select a facility to view bookings by dates");

    				}else {
    				
    					if(getFacility() != null) {
    					if(!addUser.facilityDateBookings.get(getFacility()).containsKey(fomratedString1)) {
  						   bookingsView.setPlaceholder(new Label("No Bookings yet made for this facility on this date"));
    					}
     					else {
     							bookingsView.getItems().clear();
     							bookingsView.getColumns().clear();
     							bookingDisplay(addUser.admins.containsKey(userLogin.getCurrentUser()));
     	    					bookingsView.setItems(FXCollections.observableArrayList(addUser.facilityDateBookings.get(getFacility()).get(fomratedString1)));
     	    			}}	
    				}}
    		   });
    		   
    		   datePickerUntil.setOnAction(event -> {
    			   if(datePicker.getValue() == null) {
    				   Register.errorMessage("Select from date to view bookings between to dates");
    			   }else {
    				   
    				   String[] _date1 =  datePickerUntil.getValue().toString().replaceAll("/", "-").split("-");
       				   String fomratedString1 = _date1[0] + "-" + _date1[1] + "-" + _date1[2];
       				   String[] _date0= datePicker.getValue().toString().replaceAll("/", "-").split("-");
       				   String fomratedString0 = _date0[0] + "-" + _date0[1] + "-" + _date0[2];
    				   LocalDate fromDate = LocalDate.parse(fomratedString0);
    				   LocalDate toDate = LocalDate.parse(fomratedString1);
    				   if(fromDate.isAfter(toDate)){
    					   	Register.errorMessage("From Date cannont be after to date");
    				   }else if(toDate.isBefore(fromDate)){
   					   		Register.errorMessage("To Date cannont be before from date");
    				   }else {
    					   TreeSet<Booking> betweenTwoDates = new TreeSet<>(new BookingCompare());
    					   ArrayList<String> datess = new ArrayList<>();
    					   while(!fromDate.isEqual(toDate)) {
    					   datess.add(fromDate.toString());
    					   fromDate = fromDate.plusDays(1);
    				   }
    				   
    				   ArrayList<Booking> tempe = new ArrayList<>();
    				   for(String facility : addUser.facilitesList) {
    					   for(Booking booking : addUser.facilityBooking.get(facility)) {
    						   tempe.add(booking);
    					   }
    				   }
    				   int i = 0;
    				   
    				   while(i < datess.size()){
    					   for(int j = 0; j < tempe.size(); j++) {
    					   if(tempe.get(j).getDates().equals(datess.get(i))) {
    						   betweenTwoDates.add(tempe.get(j));
    					   }}
    					   i++;
    				   }
    				   
    			  bookingsView.setItems(FXCollections.observableArrayList(betweenTwoDates));
    		   }}});
    /*TextField searchBookingId = new TextField();
    		  searchBookingId.setPromptText("Search Booking Id");
    		  
    		  
    		  searchBookingId.textProperty().addListener((observabl, oldValue, NewValue) -> {
    
    			  searchBookingId.setOnKeyPressed((KeyEvent event) -> {
    				  if(event.getCode() != KeyCode.BACK_SPACE) {
						  for(Booking booking : observable) {
							  if(booking.getBookingId().contains(NewValue)) {
								  
							  }
						  }
    				  }else if(event.getCode() == KeyCode.BACK_SPACE) {
    					  for(Booking booking : addUser.facilityBooking.get(getFacility())) {
    						  if(!observable.contains(booking) && booking.getBookingId().contains(NewValue)) {
    							  observable.add(booking);
    						  }
    					  }
    				  }
    			  });
    				
    			  	
     });*/ 			
    		
    			  			
    		Button viewMyBookings = new Button("View my Bookings");
    			   viewMyBookings.setOnAction(event -> {
    				
    				   bookingsView.getColumns().clear();
    				   bookingDisplay(true);
    				   bookingsView.setItems(FXCollections.observableArrayList(addUser.userBookings.get(userLogin.getCurrentUser())));
    				   
    			   });
    			  
    		 
        if(addUser.admins.containsKey(userLogin.getCurrentUser()) || userLogin.getCurrentUser().equals("Default Admin"))flowPaneHorizontal.getChildren().addAll(manageBookings(stage),magaeFacilites(stage),datePicker, datePickerUntil,checkBox);
        else{
        	flowPaneHorizontal.getChildren().addAll(manageBookings(stage),viewMyBookings,datePicker, datePickerUntil,checkBox);
        }

		borderPane.setLeft(flowPane);
		borderPane.setTop(flowPaneHorizontal);
		borderPane.setCenter(bookingsView);
		borderPane.setBottom(avibilityPane());
		return borderPane;
}

private static void addFacility(Stage stage) {
	
	Stage addFacilityStage = new Stage();
		  addFacilityStage.initStyle(StageStyle.UNDECORATED);
		  addFacilityStage.initModality(Modality.APPLICATION_MODAL);
		  addFacilityStage.setResizable(false);
		  
		BorderPane borderPane = new BorderPane();
				   borderPane.setPadding(new Insets(10));
		GridPane pane = new GridPane();
				 pane.setVgap(10);
				 pane.setHgap(10);
				 pane.setPadding(new Insets(10));
				 
				 Button closeButton = Util.closeButton(addFacilityStage);
				 		closeButton.setAlignment(Pos.TOP_LEFT);
				 		
				 		borderPane.setTop(closeButton);
				 		borderPane.setCenter(pane);
				 		borderPane.getStylesheets().addAll("Dark.css","addLeaugesButtons.css");
		Scene addFacilityScene = new Scene(borderPane,300,200);
		String[] addFacilityLabel = {"Facility Name","Facility Price","Facility Id"};
		TextField[] textFields = new TextField[2];
		Label[] labels = new Label[addFacilityLabel.length + 2];
	
		for(int i = 0; i < addFacilityLabel.length; i++) {
			if(i < 2) {
				textFields[i] = new TextField();
				textFields[i].setPromptText("Enter" + addFacilityLabel[i]);
				pane.add(textFields[i], 1, i);
			}
			labels[i] = new Label(addFacilityLabel[i]);
			pane.add(labels[i], 0, i);
		}
		
		labels[3] = new Label();
		labels[4] = new Label();
		labels[4].setStyle("-fx-text-fill: red;");
	
		textFields[1].textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				for(int i = 0; i < arg2.length(); i++) {
					if(!Character.isDigit(arg2.charAt(i))) {
						labels[4].setText("Price cannont contain letters");
					}
				}
					
			}
			
		});
		Button _addButton = new Button("Add");
		_addButton.setAlignment(Pos.BASELINE_RIGHT);
		_addButton.setOnAction(event -> {
				
			
			
					if(textFields[0].getText().isEmpty()) {
						labels[4].setText("Please enter facility name");
					
					}
					else if(textFields[1].getText().isEmpty()) {
						labels[4].setText("Please enter facility Price");
					}
					else if(addUser.facilites.stream().anyMatch(facility -> facility.getFacility().equals(textFields[0].getText()))){
						
						labels[4].setText("Facility already exists");
						
					}
					else {
						
							
							String id = leaugeIdAutoAssign();
							labels[3].setText(id);
							

							addUser.facilites.add(new facility(id,textFields[0].getText(),null,null));
							addUser.facilitesId.put(id, new facility(id,textFields[0].getText(),null,null));
							addUser.facilityPrices.put(textFields[0].getText(), leaugeIdAutoAssign());
							addUser.addFacility(textFields[0].getText(),id);
							addUser.writeFacilityPrice(textFields[0].getText(),textFields[1].getText());
							addUser.facilitesList.add(textFields[0].getText());
							addUser.facilityDateBookings.put(textFields[0].getText(), new Hashtable<>());
							
							for(facility facility : addUser.facilites) {
								if(!flowPane.getChildren().contains(facility.getButton())) {
									flowPane.getChildren().addAll(facility.getButton());}						
							}
						  
							labels[4].setText("Facility created successfully");
							

					   	       if(flowPane.getChildren().isEmpty()) {
					   	    	   datePicker.setDisable(true);
					   	    	   checkBox.setDisable(true);
					   	       }else {
					   	    	   datePicker.setDisable(false);
					   	    	   checkBox.setDisable(false);
					   	       }
					   	       
			}});
			
	
		GridPane.setHalignment(_addButton,HPos.RIGHT);
		pane.add(_addButton, 1, 4);
		pane.add(labels[3], 1, 2);
		pane.add(labels[4], 0, 3,2,1);
		
		addFacilityStage.sizeToScene();
		addFacilityStage.setScene(addFacilityScene);
		addFacilityStage.show();
		
}

private static MenuBar magaeFacilites(Stage stage) {

	final MenuBar _menu = new MenuBar();
	final Menu _menuBar = new Menu("Manage Facilites");
			   _menu.setMinWidth(150);
				
	MenuItem _addFacility = new MenuItem("Add Facility");
	MenuItem _deleteFacilty = new MenuItem("Delete Facility");
	MenuItem _suspendFacility = new MenuItem("Suspend Facility");
	MenuItem _removeSuspendedFacility = new MenuItem("Re - Comission");
			
			_addFacility.setOnAction(event -> addFacility(stage));
			_deleteFacilty.setOnAction(event -> {
				if(!addUser.facilites.isEmpty())delteFacility();
				else Register.errorMessage("No facilites to delete");
			});
			_suspendFacility.setOnAction(event -> {
			
				if(!addUser.facilites.isEmpty())susspendBooking();
				else Register.errorMessage("No facilities to suspend");
				
			});
			_removeSuspendedFacility.setOnAction(event -> {
				if(addUser.decomisionedFacilites.isEmpty()) {
					Register.errorMessage("There is no facilites to Re - comission");
				}else {
					recommisionFacility();
				}
			});

			_menuBar.getItems().addAll(_addFacility,_deleteFacilty,_suspendFacility,_removeSuspendedFacility);
	
			_menu.getMenus().add(_menuBar);
	return _menu;
}

private static Button manageBookings(Stage stage) {
	
	Button _addBooking = new Button("Add Booking");
		   _addBooking.setOnAction(event -> {
				if(!addUser.facilites.isEmpty()) {
					addBooking();}
				else {Register.errorMessage("There are no facilites to add Bookings to ");}});

			return _addBooking;
}

private static Accordion userSelectionPannel(BorderPane pane,Stage stage) {
		
	final String[] userLabels = {"Bookings","My Profile"};
	final String[] userSubLabels = {"View Bookings","View Profile"};
	final TitledPane[] titledPanes = new TitledPane[userLabels.length]; 
	final Accordion accordion = new Accordion (); 
					accordion.setMinWidth(250);
					accordion.setMaxWidth(250);
				
	final ToggleGroup toggle = new ToggleGroup();
	
	RadioButton[] label = new RadioButton[userSubLabels.length];
	
	for(int i = 0; i < userSubLabels.length; i++) {
		
		label[i] = new RadioButton(userSubLabels[i]);
		label[i].setPrefSize(150, 60);
		if(i < 2) {
			titledPanes[i] = new TitledPane();
			titledPanes[i].setText(userLabels[i]);
			titledPanes[i].setStyle("-fx-background-color: transparent;\n");
		}
		label[i].setToggleGroup(toggle);
	}
	
	toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	   @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            if(toggle.getSelectedToggle() != null){
            	if(label[0].isSelected()) {
            		pane.setCenter(viewFacilitiesBookings(stage));
            	}
            	else if(label[1].isSelected()) {
            		usersBookingView.getColumns().clear();
            		usersBookingView.getItems().clear();
            		tableLeauge.userBookingsDisplay();
            		pane.setCenter(editUsers(stage,false));
            		usersBookingView.setItems(FXCollections.observableArrayList(addUser.userBookings.get(userLogin.getCurrentUser())));
            	}}}});


	VBox togglVbox[] = new VBox[2];
		 togglVbox[0] = new VBox(10);
		 togglVbox[0].getChildren().addAll(label[0]);
		 togglVbox[0].setId("Vbox");
		 titledPanes[0].setContent(togglVbox[0]);
		 
		 togglVbox[1] = new VBox(10);
		 togglVbox[1].getChildren().addAll(label[1]);
		 titledPanes[1].setContent(togglVbox[1]);
		 accordion.setMaxHeight(100);
		 accordion.getPanes().addAll(titledPanes[0],titledPanes[1]);
	     
	return accordion;
}			            
            		
@SuppressWarnings("unchecked")
public static void displayUsers() {
	
				
			 	TableColumn<User, String> Name = new TableColumn<User, String>("Name");
			 							  Name.setMinWidth(75);
			 							  Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		 
			    TableColumn<User, String> SurName = new TableColumn<User, String>("SurName");
			 							  SurName.setMinWidth(100);
			 							  SurName.setCellValueFactory(new PropertyValueFactory<>("surName"));
			 							  
			 	TableColumn<User, String> Email = new TableColumn<User, String>("Email");
				 						  Email.setMinWidth(175);
				 						  Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
			 						     
			 	TableColumn<User,Button> actionCol = new TableColumn<User,Button>("Statement");
			 						     						   actionCol.setMinWidth(200);
			 						     						   actionCol.setCellValueFactory(new PropertyValueFactory<>("Options"));
			 							  
			 	user = FXCollections.observableArrayList(addUser.userListView);
			 	UsersTableView.setItems(user);
			 	
			 	UsersTableView.setRowFactory(user -> {
			 			TableRow<User> row = new TableRow<User>() {
			 				@Override
			 				public void updateItem(User person, boolean empty) {
			 					super.updateItem(person, empty);
			 					if (person != null && addUser.blockedUsers.contains(person.getEmail())) {
			 						setStyle("-fx-border-color: red;\n" + "-fx-background-color: red;\n" );
			 					} 
			 					else if (person != null && !addUser.blockedUsers.contains(person.getEmail()) && !addUser.passwordRequest.contains(person.getEmail())){
			 						setStyle("-fx-border-color: green;\n" + "-fx-background-color: green;\n" );	}
			 					else if(person != null && addUser.passwordRequest.contains(person.getEmail())) {
			 						setStyle("-fx-border-color: red;\n" + "-fx-background-color: lightgray;\n" );
			 					}
			 					else if(empty) {
			 						setStyle("-fx-background-color: null;\n" );
			 					}
            }};
            	return row ;
			});
			 	
			 	UsersTableView.getStylesheets().add("userTableView.css");
			 	UsersTableView.getColumns().addAll(Name,SurName,Email,actionCol);
		       
}

private static void delteFacility() {
	
	Stage deleteFacility = new Stage();
		  deleteFacility.initStyle(StageStyle.UNDECORATED);
		  deleteFacility.initModality(Modality.APPLICATION_MODAL);
		  deleteFacility.setResizable(false);
		
		  Button closeButton = Util.closeButton(deleteFacility);
		         closeButton.setAlignment(Pos.TOP_LEFT);
		  BorderPane borderPane = new BorderPane();
		  			 borderPane.setPadding(new Insets(10));
		  GridPane pane = new GridPane();
				   pane.setVgap(10);
				   pane.setHgap(10);
				   pane.setPadding(new Insets(10));
				   
				   borderPane.setTop(closeButton);
				   borderPane.setCenter(pane);
				   Scene deleteFacilityScene = new Scene(borderPane,Util.screenSize.getWidth()/2.5,Util.screenSize.getHeight()/3.5);
				 	     deleteFacilityScene.getStylesheets().addAll("Accordion.css","userTableView.css","Dark.css","addLeaugesButtons.css");
				 
				   TitledPane titlePane = new TitledPane();
				 			  titlePane.setExpanded(false);
				 			  Label error = new Label();
	    
				   StackPane stackPane = new StackPane();
				 		     stackPane.getChildren().addAll(error,titlePane);
				 		     StackPane.setAlignment(error, Pos.TOP_CENTER);
				 		     StackPane.setAlignment(titlePane, Pos.BOTTOM_CENTER);
				 		    
	    Button _delete = new Button("Delete Facility");
	    ComboBox<String> bookingsComboBox = new ComboBox<>();
	    				 bookingsComboBox.setPromptText("Select Facility");
	    				 ArrayList<String> facilites = new ArrayList<>();
	    				 for(facility faci : addUser.facilites) {
	    					 StringBuilder builder = new StringBuilder();
	    					 			   builder.append(faci.getFacility() + "-" +  faci.getFacilityId());
	    					 			   facilites.add(builder.toString());
	    				 }
	    				 ObservableList<String> facilities = FXCollections.observableArrayList(facilites);
	    			     bookingsComboBox.setItems(facilities);
	
	    			     bookingsComboBox.valueProperty().addListener(new ChangeListener<String>() {
	    			            @Override 
	    			            public void changed(@SuppressWarnings("rawtypes") ObservableValue _observableBooking, String _oldBooking, String _newBooking) {                
	    			            	
	    			            		String[] split = _newBooking.split("-");
	    			            
		    			     			if(!addUser.facilityBooking.get(split[0]).isEmpty()) {
	    			            			
	    			            			titlePane.setContent(bookingDisplay(addUser.facilityBooking.get(split[0])));
	    			            			titlePane.setExpanded(true);
	    			            			
	    			            			int _amountDue = 0;
	    			            			for(Booking booking : addUser.facilityBooking.get(split[0])) {
	    			            				
	    			            				_amountDue += Integer.parseInt(booking.getDue().replace("€", ""));
	    			            			}
	    			            			
	    			            			error.setText("Cannot delete facility as it has pending bookings and an outsanding amount of " + _amountDue);
	    			            			error.setStyle("-fx-text-fill: white;\n -fx-font-size: 14px;");
	    			            		}
	    			            		else {
	    			            			
	    			            			titlePane.setExpanded(false);
	    			            			_delete.setOnAction(event -> {
	    			            				
	    			            				facilities.remove(split[0]);
	    			            				addUser.facilitesList.remove(split[0]);
	    			            				addUser.facilites.removeIf(facility -> facility.getFacility().equals(split[0]));
	    			            				addUser.facilityBooking.remove(split[0]);
	    			            				addUser.Bookigns.remove(split[0]);
	    			            				addUser.facilityPrices.remove(split[0]);
	    			            				bookingsView.getItems().clear();
	  
	    			            				for(User user : addUser.userListView) {
	    			            					if(addUser.userBookings.containsKey(user.getUserName())) {
	    			            					addUser.userBookings.get(user.getUserName()).removeIf(booking -> _newBooking.equals(booking.getFacility()));
	    			            				}}
	    			            		
	    			            				if(addUser.deleteFacility(new String(_newBooking + ".csv"))) {
	    			            					error.setText("Facility successfuly deleted");
	    			            				}
	    			            				flowPane.getChildren().clear();
		    			            			for(facility facil : addUser.facilites) {
		    			            				flowPane.getChildren().add(facil.getButton());

		    			            			}
		    			            			for(facility facilities : addUser.decomisionedFacilites) {
		    			            				if(facilities.getFacility().equals(split[0])) {
		    			            					addUser.decomisionedFacilites.remove(facilities);
		    			            				}
		    			            			}
		    			            			

		    			         	   	       if(flowPane.getChildren().isEmpty()) {
		    			         	   	    	   datePicker.setDisable(true);
		    			         	   	    	   checkBox.setDisable(true);
		    			         	   	       }else {
		    			         	   	    	   datePicker.setDisable(false);
		    			         	   	    	   checkBox.setDisable(false);
		    			         	   	       }
		    			            		
	    			            		});
	    			            		}
	    			            	
	    			            }    
	    			        });
	    			 
	    			     		
	    			     
	    			     pane.add(new Label("Select Facility : "), 0, 1);
	    			     pane.add(bookingsComboBox, 1, 1);
	    			     pane.add(stackPane, 0, 2,2,1);
	    			     pane.add(_delete, 2, 4);
	    			     
	    			     deleteFacility.sizeToScene();
	    			     deleteFacility.setScene(deleteFacilityScene);
	    			     deleteFacility.show();
	    				  		
}

@SuppressWarnings("unchecked")
private static void addBooking() {
	
	Stage addBookingStage = new Stage();
		  addBookingStage.initStyle(StageStyle.UNDECORATED);
		  addBookingStage.initModality(Modality.APPLICATION_MODAL);
		  addBookingStage.setResizable(false);
		  
		  GridPane gridPane = new GridPane();
		  		   gridPane.setPadding(new Insets(10));
		  		   gridPane.setVgap(20);
		  		   gridPane.setHgap(20);
		  		   
		  BorderPane borderPane = new BorderPane();
		  			 Button closeButton = Util.closeButton(addBookingStage);
		  			 		closeButton.setAlignment(Pos.TOP_CENTER);
		  			 		borderPane.setTop(closeButton);
		  			 		borderPane.setPadding(new Insets(10));
		  			 		borderPane.setCenter(gridPane);
		  			 		borderPane.getStylesheets().addAll("userTableView.css","Dark.css","addLeaugesButtons.css");
		  Scene addBookingScene = new Scene(borderPane,660,340);
		  
		  ComboBox<String> facilitesSelection = new ComboBox<>();
		  				   facilitesSelection.setPromptText("Select afcility");
		  			       facilitesSelection.setItems(FXCollections.observableArrayList(addUser.facilitesList));
		  			       gridPane.add(facilitesSelection,1,0);
		  			       
		  ArrayList<String> hours = new ArrayList<>();
		  
		  for(int i = 9; i <= 18; i++) {
			 hours.add(Integer.toString(i));
		  }

		  HBox[] timeVbox = new HBox[3];
		  
		  ComboBox<String>[] hoursSelection = new ComboBox[2];
		  			 hoursSelection[0] = new ComboBox<String>(FXCollections.observableArrayList(hours));
		  			 hoursSelection[0].setPromptText("Select Start Hour");
		  			 hoursSelection[1] = new ComboBox<String>(FXCollections.observableArrayList(hours));
		  			 hoursSelection[1].setPromptText("Select End Hour");
		  			        
		  DatePicker datePicker = new DatePicker();
		  			 datePicker.setPromptText("Select Booking date");
		             gridPane.add(datePicker, 1,3);
		  
		  String[] labels = {"Select Facility: ","Booking Purpose: ","UserName: ","Date: ","BookingId: ","StartTime: ","EndTime: ","Duration: ","Booking Cost: "};
		  		   Label[] bookingLables = new Label[labels.length];
		  for(int i = 0; i < labels.length; i++) {
			  bookingLables[i] = new Label(labels[i]);
			  if(i < 5) {
				  gridPane.add(bookingLables[i], 0, i);
			  }else if(i >= 4) {
				  gridPane.add(bookingLables[i], 2, i-5);
			  }
		  }
		
		  			 gridPane.add(hoursSelection[0], 3, 0);
		  			 gridPane.add(hoursSelection[1], 3, 1);
		  
		  TextField bookingPurposeTextField = new TextField();
		  			bookingPurposeTextField.setPromptText("Eneter booking purpose");
		            gridPane.add(bookingPurposeTextField, 1, 1);
		  
		  Label[] displayLabel = new Label[4];
		  for(int i = 0; i < displayLabel.length;i++) {
			  displayLabel[i] = new Label();
		  }
		  		   displayLabel[0].setText(userLogin.getCurrentUser());
		  gridPane.add(displayLabel[0],1,2);
		  gridPane.add(displayLabel[1], 1,4);
		  gridPane.add(displayLabel[2],3,2);
		  gridPane.add(displayLabel[3], 3,3);
		  ArrayList<String> temp = new ArrayList<>();
		  
		  AtomicInteger _dif = new AtomicInteger(0);
		  AtomicInteger _dif1 = new AtomicInteger(0);
		  AtomicInteger _length = new AtomicInteger(0);
		  
		  hoursSelection[0].valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String _time1, String _time2) {     
	            	  _dif.set(Integer.parseInt(_time2));
	            	  if(!_time2.isEmpty() && !Integer.toString(_dif1.get()).isEmpty()) {
	            		  _length.set(_dif1.get() - _dif.get());
	            	  }
	            	  
  	    			  if(_dif.get() > _dif1.get() && _dif1.get() != 0) {
  	    				 Register.errorMessage("Start Time cannot be after Finish Time");
  	    			  }
  	    			  else if(_length.get() > 1 ) {
  	    				  Register.errorMessage("Bookings can only be made for an hour");
  	    			  }
  	    			  else if(_dif.get() == _dif1.get()) {
  	    				 Register.errorMessage("Start time and finish time cannot be the same");
  	    			  }
  	    			  else {
  	    				displayLabel[2].setText(Integer.toString(_length.get()));
  	    			  }}
	            
	        });
		  			  

		  hoursSelection[1].valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String _time1, String _time2) {     
	            	
	            	 _dif1.set(Integer.parseInt(_time2));
	            	 
	            	  if(!_time2.isEmpty() && !Integer.toString(_dif.get()).isEmpty()) {
	            		  _length.set(_dif1.get() - _dif.get());
	            	  }
	            	  
  	    			  if(_dif.get() > _dif1.get() && _dif.get() != 0) {
  	    				 Register.errorMessage("Finish Time cannot be before Start Time");
  	    			  }
  	    			  else if(_length.get() > 1) {
  	    				  Register.errorMessage("Bookings can only be made for an hour");
  	    			  }
  	    			  else if(_dif.get() == _dif1.get()) {
   	    				 Register.errorMessage("Start time and finish time cannot be the same");
   	    			  }
  	    			  else {
  	    				displayLabel[2].setText(Integer.toString(_length.get()));
  	    			  }}
  	    			  
	             
	        });
	
	
		  facilitesSelection.valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String t, String t1) {     
	            	  temp.clear();
	            	  displayLabel[3].setText(addUser.facilityPrices.get(t1));
			  		  displayLabel[1].setText(leaugeIdAutoAssign()); 
			  		  temp.add(t1);
		
	            }});
		  
		  String bookingId = leaugeIdAutoAssign();
	
		  Label label = new Label();
		 
		  String[] buttonLabels = {"Create Booking"};
		  Button[] buttons = new Button[buttonLabels.length];
		  	       for(int i = 0; i < buttons.length; i++) {
		  	    	   buttons[i] = new Button(buttonLabels[i]);
		  	       }
		  	       buttons[0].setMinWidth(150);
		  	       buttons[0].setMaxWidth(150);
	  	  SimpleBooleanProperty makeBooking = new SimpleBooleanProperty(true);
	       
		  datePicker.valueProperty().addListener((ov,oldValue,newValue) -> {
		  	    	 
			  if(datePicker.getValue() != null) {
				  		
		  				  if(facilitesSelection.getSelectionModel() != null) {
		  					  for(facility facilit : addUser.decomisionedFacilites) {
		  						  if(facilit.getFacility().equals(facilitesSelection.getSelectionModel().getSelectedItem())) {
		  					
		  					  ArrayList<String> dateSelected = new ArrayList<>();
		  					  LocalDate facilityDateFrom = LocalDate.parse(facilit.getDateFrom());
		  					  LocalDate facilityDateUntil = LocalDate.parse(facilit.getDateUntil());
		  					  LocalDate facilityDateFromCopy = LocalDate.parse(facilit.getDateUntil());
		  					  while(!facilityDateUntil.equals(facilityDateFrom.plusDays(1))) {
		  						  dateSelected.add(facilityDateUntil.toString());
		  						  facilityDateUntil = facilityDateUntil.plusDays(1);
		  						  
		  					  }
		  					  
		  					      if(dateSelected.contains(datePicker.getValue().toString())) {
		  					    	  Register.errorMessage("Booking is suspended from\n" +  facilityDateFromCopy.toString() + " to " +  facilityDateUntil.toString());
		  					    	  makeBooking.set(false);
		  					    	  
			  					  }
		  					      else {
	  					    		makeBooking.set(true);  
	  					    	  }
			  				  
		  		}}}}});

		  	       buttons[0].setOnAction(event -> {

			  				  if(makeBooking.get() == true) {
			  					  
					  	    	  if(_dif.get() > _dif1.get() && _dif.get() != 0) {
				    				 Register.errorMessage("Finish Time cannot be before Start Time");
				    			  }
					  	    	  else if(_dif.get() > _dif1.get() && _dif1.get() != 0) {
				  	    		     Register.errorMessage("Start Time cannot be after Finish Time");
					  	    	  }
				    			  else if(_length.get() > 1) {
				    				  Register.errorMessage("Bookings can only be made for an hour");
				    			  }
				    			  else if(_dif.get() == _dif1.get()) {
				    				 Register.errorMessage("Start time and finish time cannot be the same");
				    			  }
				    			  else {
				    				
					  	    				 
					  	    				Instant instant = Instant.now();
					  	    				Date dates = Date.from(instant);
					  	    				SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
					  	    				String formattedDate = formatter.format(dates);
					  	    				 
					  	    			
					  	    				if(dateCheck(_dif.get(),datePicker.getValue().toString())) {
					  	    					try {
				    							for(facility fac : addUser.facilites) {
				    								if(facilitesSelection.getSelectionModel() != null && fac.getFacility().equals(facilitesSelection.getSelectionModel().getSelectedItem())) {
														if(fac.getFacility() != null && fac.getFacilityId() != null) {
				    									addUser.writeUserActivity(new userActivity(formattedDate,"Booking made for " + temp.get(0) + " on the " + datePicker.getValue().toString().replaceAll("/", "-") + " for 1 hour","Booking","0",addUser.facilityPrices.get(temp.get(0))),userLogin.getCurrentUser());
															addUser.facilityDateBookings.get(fac.getFacility()).put(datePicker.getValue().toString().replaceAll("/", "-"),new TreeSet<Booking>(new BookingCompare()));
															addUser.facilityDateBookings.get(fac.getFacility()).get(datePicker.getValue().toString().replaceAll("/", "-")).add(new Booking(temp.get(0),bookingPurposeTextField.getText(),userLogin.getCurrentUser(),datePicker.getValue().toString().replaceAll("/", "-"),bookingId,_dif.get(),_dif1.get(),Integer.toString(_length.get()) ,addUser.facilityPrices.get(temp.get(0)),formattedDate));
															addUser.facilityBooking.get(fac.getFacility()).add(new Booking(temp.get(0),bookingPurposeTextField.getText(),userLogin.getCurrentUser(),datePicker.getValue().toString().replaceAll("/", "-"),bookingId,_dif.get(),_dif1.get(),Integer.toString(_length.get()) ,addUser.facilityPrices.get(temp.get(0)),formattedDate));
															label.setText("Booking created !");
														
														}}}}catch(ConcurrentModificationException e) {
															e.printStackTrace();
														}
					  	    					addUser.initEverything();
					  	    					}
				    							else {
				    								Register.errorMessage("Booking already exits on " + datePicker.getValue().toString() + "\n\t\t at " + _dif.get());
				    			  }
				    							}}
			  	    	  else {
			  	    		label.setText("Booking failed to create");  
			  	    	  }}
			  	    	 );
		  timeVbox[2] = new HBox(10);
		  timeVbox[2].getChildren().addAll(buttons[0]);
		  
		  buttons[0].setAlignment(Pos.BOTTOM_RIGHT);
		  gridPane.add(label, 0, 5,3,1);
		  gridPane.add(timeVbox[2], 3, 6);
		 
		  addBookingStage.sizeToScene();
		  addBookingStage.setScene(addBookingScene);
		  addBookingStage.show();
}

public static boolean dateCheck(int diferene,String date) {
	 
	  

		 for(facility facili : addUser.facilites) {
			 if(addUser.facilityBooking.containsKey(facili.getFacility())){
				 for(Booking booking : addUser.facilityBooking.get(facili.getFacility())) {
					 if(booking.getDates().equals(date) && booking.getStartTime() == diferene) {
						 return false;
					 }
				 }
			 }
			 
		 }
	 

		return true;
}

/**
 * generated bookingId and returns it as a string
 * @return
 */

public static String leaugeIdAutoAssign() {
	Random rand = new Random();
	return "2" + rand.nextInt(50) + addUser.facilites.size();
}

public static void recommisionFacility() {
	
	Stage recommison = new Stage();
		  recommison.initStyle(StageStyle.UNDECORATED);
		  recommison.initModality(Modality.APPLICATION_MODAL);
		  BorderPane borderPane = new BorderPane();
		  			 borderPane.setPadding(new Insets(10));
		  			 borderPane.getStylesheets().addAll("userTableView.css","Dark.css","addLeaugesButtons.css");
		  GridPane gridPane = new GridPane();
		  		   gridPane.setHgap(10);
		  
		  Scene recommisonScene = new Scene(borderPane,350,150);
		  
		  Label selectFacilityLabel = new Label("Select Facility :");
		  Label labelText = new Label();
		  	    labelText.setStyle("-fx-text-fill: red;");
		  
		  ComboBox<String> decomiSionedFacility = new ComboBox<>();
		  				   decomiSionedFacility.setPromptText("Select facility to recomission");
		  ArrayList<String> susspendedFacilite = new ArrayList<>();
		  
		  for(facility facilities : addUser.decomisionedFacilites) {
			  susspendedFacilite.add(facilities.getFacility());
		  }
		  
		  decomiSionedFacility.setItems(FXCollections.observableArrayList(susspendedFacilite));
		  
		  Button closeButton = Util.closeButton(recommison);
		  Button decomisiion = new Button("Decomision Faciltiy");
		  		decomisiion.setOnAction(event -> {
		  			if(decomiSionedFacility.getSelectionModel() != null) {
		  						if(addUser.decomisionedFacilites.removeIf(facility -> facility.getFacility().equals(decomiSionedFacility.getSelectionModel().getSelectedItem()))) {
				  					labelText.setText("Facility recomisioned ");
				  					susspendedFacilite.removeIf(string -> string.equals(decomiSionedFacility.getSelectionModel().getSelectedItem()));
				  					Register.errorMessage("Facility Re - comissioned");
		  						}
				  				addUser.updateDecomisionedFacilites();
		  				}
		  			else {
		  				labelText.setText("Selecet a facility");
		  			}
		  		});	
			
		  closeButton.setAlignment(Pos.TOP_LEFT);
		  borderPane.setTop(closeButton);
		  borderPane.setCenter(gridPane);
		  
		  gridPane.add(selectFacilityLabel, 0, 1);
		  gridPane.add(labelText,0,2,2,1);
		  gridPane.add(decomiSionedFacility, 1, 1);
		  gridPane.add(decomisiion, 1, 3);
		  GridPane.setHalignment(decomisiion, HPos.RIGHT);
		  
		  recommison.setScene(recommisonScene);
		  recommison.sizeToScene();
		  recommison.show();
		 
}

private static void susspendBooking() {
	
	Stage susspendStage = new Stage();
		  susspendStage.initStyle(StageStyle.UNDECORATED);
		  susspendStage.initModality(Modality.APPLICATION_MODAL);
		  susspendStage.setResizable(false);
		  
		  BorderPane borderPane = new BorderPane();
		  			 borderPane.getStylesheets().addAll("userTableView.css","Dark.css","addLeaugesButtons.css");
		  			 borderPane.setPadding(new Insets(10));
		  
		  GridPane gridPane = new GridPane();
		  		   gridPane.setHgap(10);
		  		   gridPane.setVgap(10);
		  		   gridPane.setPadding(new Insets(10));
		  		  
		  Scene susspendScene = new Scene(borderPane,Util.screenSize.getWidth()/4.5,Util.screenSize.getHeight()/4);
		  		
		  DatePicker datePicker1 = new DatePicker();
		  			 datePicker1.setPromptText("Select from date");
		  DatePicker datePicker2 = new DatePicker();
		  			 datePicker2.setPromptText("Select until date");
		  
		  TitledPane titlePane = new TitledPane();
		  			 titlePane.setExpanded(false);
		  			 titlePane.setVisible(false);
		  			 
		  			 Label errorLabel = new Label();
		  			 	   errorLabel.setStyle("-fx-text-fill: red;\n");
		  			 	   
		  			 	   StackPane stackPane = new StackPane();
		  			 	   			 stackPane.getChildren().addAll(titlePane,errorLabel);
		  			 	   			 StackPane.setAlignment(errorLabel, Pos.TOP_CENTER);
		  			 	   			 StackPane.setAlignment(titlePane, Pos.BOTTOM_CENTER);
		  			 	   			 
		  ComboBox<String> facilityPick = new ComboBox<>();
		  				   facilityPick.setPromptText("Select");
		  				   facilityPick.setItems(FXCollections.observableArrayList(addUser.facilitesList));
		  				   
		  				   
		  				   String[] labels = {"Select facility to suspend : ","From : ","Until : "};
		  				   Label[] label = new Label[labels.length];
		  				   
		  				   for(int i = 0; i< label.length; i++) {
		  					   label[i] = new Label(labels[i]);
		  				   }
	
		  				  Button susspendButton = new Button("Susspend");
		  				   
		  				   			  facilityPick.valueProperty().addListener(new ChangeListener<String>() {
		  					            @Override 
		  					            public void changed(ObservableValue ov, String _time1, String _time2) {     
		  					            	if(!addUser.facilityBooking.get(_time2).isEmpty()) {
		  					            		
		  					            		susspendStage.setWidth(Util.screenSize.getWidth()/3.5);
		  					            		susspendStage.setHeight(Util.screenSize.getHeight()/2.5);
		  					            		datePicker1.setDisable(true);
		  					            		datePicker1.getEditor().setDisable(true);
		  					            		datePicker2.setDisable(true);
		  					            		datePicker2.getEditor().setDisable(true);
		  					            		titlePane.setVisible(true);
		  			  			 	   			errorLabel.setText("Cannont suspend facility as there is current bookings");
		  					            		titlePane.setContent(bookingDisplay(addUser.facilityBooking.get(_time2)));
		  					            		datePicker1.setEditable(false);
		  					            		titlePane.setExpanded(true);
		  					            	}
		  					            	else {

		  					            
		  					            		if(addUser.decomisionedFacilites.stream().anyMatch(facility -> facility.getFacility().equals(_time2))) {
		  					            			Register.errorMessage("Facility can be susspended once");
		  					            		}else {
		  					            			
		  					            		
		  					            			titlePane.setVisible(false);
		  					            			susspendStage.setWidth(Util.screenSize.getWidth()/4.5);
		  					            			susspendStage.setHeight(Util.screenSize.getHeight()/4);
		  					            			datePicker1.setDisable(false);
		  					            			datePicker1.getEditor().setDisable(false);
		  					            			datePicker2.setDisable(false);
		  					            			datePicker2.getEditor().setDisable(false);
		  					            			errorLabel.setText("");
		  					            			titlePane.setExpanded(false);
		  					            		
		  					            		
		  					            		susspendButton.setOnAction(event -> {
		  			  				   				if(addUser.facilityBooking.get(facilityPick.getSelectionModel().getSelectedItem()).isEmpty()) {
		  				            				if(datePicker1.getValue() != null) {
		  				            					for(facility faci : addUser.facilites) {
		  				            						
		  				            						if(addUser.facilitesId.get(faci.getFacilityId()).getFacility().equals(_time2)) {
		  				            							addUser.facilitesId.get(faci.getFacilityId()).setDateFrom(datePicker1.getValue().toString());
		  		  			  			 	   					addUser.facilitesId.get(faci.getFacilityId()).setDateUntil(datePicker2.getValue().toString());
				  				            					addUser.writeDecomisionedFacilites(faci.getFacilityId(),facilityPick.getSelectionModel().getSelectedItem(),datePicker1.getValue().toString(),datePicker2.getValue().toString());
				  		  			  			 	   			addUser.readDecomisionedFacilites();

				  				            					}
		  				            						}
		  				            					}
		  		  			  			 	   			errorLabel.setText("Facility suspend form " + datePicker1.getValue().toString() + " until " + datePicker2.getValue().toString());
		  				            				}else if(datePicker1.getValue() == null && datePicker2.getValue() != null) {
		  				            					
		  				            					DateFormat date = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
		  				            					Date dtae = Date.from(Instant.now());
		  				            						 String formatedDate = date.format(dtae);
		  		  			  			 	   			errorLabel.setText("Facility suspend form " +  formatedDate + " until " + datePicker2.getValue().toString());
		  		  			  			 	   			for(facility faci : addUser.facilites) {
		  		  			  			 	   				if(addUser.facilitesId.get(faci.getFacilityId()).getFacility().equals(_time2)) {
		  		  			  			 	   					addUser.facilitesId.get(faci.getFacilityId()).setDateFrom(formatedDate);
		  		  			  			 	   					addUser.facilitesId.get(faci.getFacilityId()).setDateUntil(datePicker2.getValue().toString());
				  		  			  			 	   			addUser.writeDecomisionedFacilites(faci.getFacilityId(),facilityPick.getSelectionModel().getSelectedItem(),formatedDate,datePicker2.getValue().toString());
				  		  			  			 	   			addUser.readDecomisionedFacilites();
			  				            					}
		  		  			  			 	   								
		  		  			  			 	   				}
		  		  			  			 	   			}

		  				            				}
		  			  				   				
		  					            		);}}}});

		  				  gridPane.add(label[0],0,1);
		  				  gridPane.add(facilityPick, 1, 1);
		  				  gridPane.add(label[1], 0, 2);
		  				  gridPane.add(label[2], 0, 3);
		  				  gridPane.add(datePicker1, 1, 2);
		  				  gridPane.add(datePicker2, 1, 3);
		  				  gridPane.add(stackPane, 0, 4,2,1);
		  				 
		  				  GridPane.setHalignment(susspendButton, HPos.RIGHT);
		  				  gridPane.add(susspendButton, 1, 5);
		  				   			  
		  				  borderPane.setCenter(gridPane);
		  				  Button closeButton = Util.closeButton(susspendStage);
		  				  BorderPane.setAlignment(closeButton, Pos.TOP_LEFT);
		  				  borderPane.setTop(closeButton);
		  				  susspendStage.sizeToScene();
		  				  susspendStage.setScene(susspendScene);
		  				  susspendStage.show();
		  				   			  
}
                                   
@SuppressWarnings("unchecked")
public static TableView<Booking> bookingDisplay(TreeSet<Booking> arrayList) {

		 final TableView<Booking> bookings = new TableView<Booking>();
	
    	 TableColumn<Booking, Integer> BookingIdCol = new TableColumn<Booking, Integer>("Booking Id");
                                       BookingIdCol.setMinWidth(100);
                                       BookingIdCol.setCellValueFactory(new PropertyValueFactory<>("BookingId"));
	
		 TableColumn<Booking,String> FacilityCol = new TableColumn<Booking, String>("Facility");
		 							 FacilityCol.setMinWidth(200);
		 							 FacilityCol.setCellValueFactory(new PropertyValueFactory<>("Facility"));

	     TableColumn<Booking, String> DateCol = new TableColumn<Booking, String>("Date");
	        					      DateCol.setMinWidth(120);
	        					      DateCol.setCellValueFactory(new PropertyValueFactory<>("Dates"));
	        					   
	     TableColumn<Booking, Integer> AmountDueCol = new TableColumn<Booking, Integer>("Amount Owned");
	        					       AmountDueCol.setMinWidth(100);
	        					       AmountDueCol.setCellValueFactory(new PropertyValueFactory<>("Due"));   			
	        					       
	        					       bookings.getColumns().addAll(BookingIdCol,FacilityCol,DateCol,AmountDueCol);
	        					       bookings.setItems(FXCollections.observableArrayList(arrayList));
	        					       
	        					       return bookings;
}

@SuppressWarnings("unchecked")
public static void userBookingsDisplay() {

 	
	 TableColumn<Booking, Integer> BookingIdCol = new TableColumn<Booking, Integer>("Booking Id");
                                   BookingIdCol.setMinWidth(200);
                                   BookingIdCol.setCellValueFactory(new PropertyValueFactory<>("BookingId"));
                            						                           
    TableColumn<Booking, String> BookingPuproseCol = new TableColumn<Booking, String>("Activity / Class");
    						     BookingPuproseCol.setMinWidth(350);
    						     BookingPuproseCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingPurpose"));

    TableColumn<Booking, String> DateCol = new TableColumn<Booking, String>("Date");
       					   DateCol.setMinWidth(250);
       					   DateCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("Dates"));
       
    TableColumn<Booking, Integer> startTimeCol = new TableColumn<Booking, Integer>("Start Time");
    							startTimeCol.setMinWidth(150);
    							startTimeCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("StartTime"));
       
    TableColumn<Booking, Integer> endTimeCol = new TableColumn<Booking, Integer>("End Time");
    							endTimeCol.setMinWidth(150);
    							endTimeCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("EndTime"));

    TableColumn<Booking, Integer> DurationCol = new TableColumn<Booking, Integer>("Duration");
    						    DurationCol.setMinWidth(75);
    						    DurationCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("Duration"));
    
    TableColumn<Booking, Integer> AmountDueCol = new TableColumn<Booking, Integer>("Amount Owned");
                                  AmountDueCol.setMinWidth(200);
                                  AmountDueCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("Due"));
    
    TableColumn<Booking,Menu> actionCol = new TableColumn<Booking,Menu>("Actions");
    						   actionCol.setMinWidth(300);
    						   actionCol.setCellValueFactory(new PropertyValueFactory<>("Menu"));
    						   
    						   
    						   usersBookingView.getColumns().addAll(BookingIdCol,BookingPuproseCol,DateCol,startTimeCol,endTimeCol,DurationCol,AmountDueCol,actionCol);
    						   if(usersBookingView.getItems().isEmpty()) {
    							   usersBookingView.setPlaceholder(new Label("Select user to view his/her bookings"));
    						   }
    						   usersBookingView.setId("BookingsTableView");
    						   usersBookingView.getStylesheets().add("userTableView.css");
    						   
    						   
      
}

/**
 * bookingDisplay method adds columns to booking display tableView 
 * @param admin
 */

@SuppressWarnings("unchecked")
public static void bookingDisplay(boolean admin) {

		 	
		 TableColumn<Booking, Integer> BookingIdCol = new TableColumn<Booking, Integer>("Booking Id");
                                       BookingIdCol.setCellValueFactory(new PropertyValueFactory<>("BookingId"));
                                 
         TableColumn<Booking, String> BookingUserCol = new TableColumn<Booking, String>("Booking Made By");
         							  BookingUserCol.setCellValueFactory(new PropertyValueFactory<>("UserName"));
         							                           
	     TableColumn<Booking, String> BookingPuproseCol = new TableColumn<Booking, String>("Activity / Class");
	     						      BookingPuproseCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingPurpose"));
	 
	     TableColumn<Booking, String> DateCol = new TableColumn<Booking, String>("Date");
	        					      DateCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("Dates"));
	        
	     TableColumn<Booking, Integer> startTimeCol = new TableColumn<Booking, Integer>("Start Time");
	     	    					   startTimeCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("StartTime"));
	        
	     TableColumn<Booking, Integer> endTimeCol = new TableColumn<Booking, Integer>("End Time");
	     							   endTimeCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("EndTime"));
	 
	     TableColumn<Booking, Integer> DurationCol = new TableColumn<Booking, Integer>("Duration");
	     						       DurationCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("Duration"));
	     
	     TableColumn<Booking, Integer> AmountDueCol = new TableColumn<Booking, Integer>("Amount Owned");
	                                   AmountDueCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("Due"));
	     
	     TableColumn<Booking,Menu> actionCol = new TableColumn<Booking,Menu>("Actions");
	     						   actionCol.setCellValueFactory(new PropertyValueFactory<>("Menu"));
	     						   
	     						   if(admin) {
		     							endTimeCol.setMinWidth(150);
			        				    DateCol.setMinWidth(200);
			     						BookingPuproseCol.setMinWidth(250);
		         					    BookingUserCol.setMinWidth(250);
		                                BookingIdCol.setMinWidth(150);
		   	     					    startTimeCol.setMinWidth(150);
		     						    DurationCol.setMinWidth(75);
	                                    AmountDueCol.setMinWidth(150);
		     						    actionCol.setMinWidth(250);

	     							   bookingsView.getColumns().addAll(BookingIdCol,BookingUserCol,BookingPuproseCol,DateCol,startTimeCol,endTimeCol,DurationCol,AmountDueCol,actionCol);
	     						   }else {
	     							   
	     								endTimeCol.setMinWidth(200);
			        				    DateCol.setMinWidth(300);
			     						BookingPuproseCol.setMinWidth(440);
		                                BookingIdCol.setMinWidth(200);
		   	     					    startTimeCol.setMinWidth(200);
		     						    DurationCol.setMinWidth(150);
	                                    AmountDueCol.setMinWidth(200);
	     							   
		     						      bookingsView.getColumns().addAll(BookingIdCol,BookingPuproseCol,DateCol,startTimeCol,endTimeCol,DurationCol);
	     						   }
	     						
	      bookingsView.setId("BookingsTableView");
	      bookingsView.getStylesheets().add("userTableView.css");
	       
}

/**
 * getPicPath() getter and setter is used to set the picture file path
 * as a variable needs to be electively final or final in lambdas expressions
**/

private static void setPicPath(String path) {
	picutrePath = path;
}

private static String getPicPath() {
	return picutrePath;
}

public static void setFacility(String faci) {
	facility = faci;
}

public static String getFacility() {
	return facility;
}

/**
 * avibilityDisplay iterates through dates and then hour from 9 - 18; to display the avibility of a facility for a 
 * particular date or dates inclusive
 * 
 * As it iterates through each booking for each facility, when there a two bookings it adds each booking to the arraylist
 * I tried couple of ways to prevent duplicates eg. using a treeSet and custom comparator comparing the hours when a time slot is not available, 
 * but nothing I tried worked for me yet, I will try and solve this after the exam period 
 * 
 * @param dateFrom
 * @param dateUntil
 * @param facility
 * @return
 */

private static ArrayList<date> avibilityDisplay(String dateFrom, String dateUntil,String facility){
	

					LocalDate localDateFrom = null;
					LocalDate localDateUntil = null;
					
					try {
						 localDateFrom = LocalDate.parse(dateFrom);
						 if(dateUntil != null) {
						 localDateUntil = LocalDate.parse(dateUntil);
						 }
					}catch(DateTimeParseException exception) {
						exception.printStackTrace();
					}
					
					ArrayList<date> avibilityView = new ArrayList<>();
					ArrayList<String> hour = new ArrayList<>();
					
					for(int i = 9; i <= 18;i++) {
						hour.add(Integer.toString(i));
					}

					if(dateUntil != null) {
						ArrayList<String> dates = new ArrayList<>();
					
						while(!localDateFrom.equals(localDateUntil.plusDays(1))) {
							dates.add(localDateFrom.toString());
							localDateFrom = localDateFrom.plusDays(1);
					}
					int i = 0;
					while(i < dates.size()) {
						for(Booking booking : addUser.facilityBooking.get(facility)) {
							for(int j = 0; j < hour.size(); j++) {
								if(booking.getStartTime() == Integer.parseInt(hour.get(j)) && booking.getDates().equals(dates.get(i))) {
									avibilityView.add(new date(dates.get(i),hour.get(j),"Not available : Booked for " + booking.getBookingPurpose()));
								}else if(booking.getStartTime() != Integer.parseInt(hour.get(j)) && booking.getDates().equals(dates.get(i))){
									avibilityView.add(new date(dates.get(i),hour.get(j),"Available"));
								}else if(booking.getStartTime() != Integer.parseInt(hour.get(j)) && !booking.getDates().equals(dates.get(i))){
									avibilityView.add(new date(dates.get(i),hour.get(j),"Available"));
								}
							}
				}i++;
				}}else {
					for(int i = 0; i < hour.size(); i++) {
						for(Booking booking : addUser.facilityBooking.get(facility)) {
						if(booking.getStartTime() == Integer.parseInt(hour.get(i)) && booking.getDates().equals(dateFrom)) {
									avibilityView.add(new date(dateFrom,hour.get(i),"Not available : Booked for " + booking.getBookingPurpose()));
						}else if(booking.getStartTime() != Integer.parseInt(hour.get(i)) && booking.getDates().equals(dateFrom)){
									avibilityView.add(new date(dateFrom,hour.get(i),"Available"));
						}	
				}}}
				return avibilityView;
	
}

@SuppressWarnings("unchecked")
private static void avibilityTableView(){
	
	TableColumn<date, String> DateCol = new TableColumn<date, String>("Date");
	   								  DateCol.setMinWidth(500);
	   								  DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    TableColumn<date, String> HourCol = new TableColumn<date, String>("Hour");
    								  HourCol.setMinWidth(700);
    								  HourCol.setCellValueFactory(new PropertyValueFactory<>("hour"));
    								  
    TableColumn<date, String> avbCol = new TableColumn<date, String>("Avibility");
    						  avbCol .setMinWidth(600);
    						  avbCol .setCellValueFactory(new PropertyValueFactory<>("Avibility"));
    						
    								  avibilityTableView.getStylesheets().add("userTableView.css");
    								  avibilityTableView.getColumns().addAll(DateCol,HourCol,avbCol);
    								  
}

private static BorderPane avibilityPane() {
	
	BorderPane borderPane = new BorderPane();
	
	Label checkAvibilityLabel = new Label("CheckAvibility");
	checkAvibilityLabel.setStyle("-fx-font-size:18px;");
	Separator sep = new Separator();
			  sep.setOrientation(Orientation.HORIZONTAL);
			  sep.setStyle("-fx-width : 2;");
	
	FlowPane flowPane = new FlowPane();
			 flowPane.setHgap(20);
			 flowPane.getChildren().addAll(checkAvibilityLabel,sep);
			 sep.setMinWidth(Util.screenSize.getWidth()/1.5  );
			 
			 DatePicker dateFrom = new DatePicker();
			 DatePicker dateUntil = new DatePicker();
			 			dateUntil.setDisable(true);
			 			dateFrom.setPromptText("Select Date From");
			 			dateUntil.setPromptText("Select Date Until");
			 
			 CheckBox checkBox = new CheckBox("Select to view between two dates");
			 ComboBox<String> comboBox = new ComboBox<>();
		   		               comboBox.setPromptText("Select Facility to view Its Avibility");
		   		               ObservableList<String> observable = FXCollections.observableArrayList(addUser.facilitesList);					  
		   		               comboBox.setItems(FXCollections.observableArrayList(observable));
		   				   	  Label label = new Label();
							  label.setStyle("-fx-font-size: 18px;" + "-fx-text-fill: black;");
			if(comboBox.getSelectionModel() == null) {
				 label.setText("Select Facilit to view its avibility");
				 avibilityTableView.setPlaceholder(label);
			}
			else if(dateFrom.getValue() == null && comboBox.getSelectionModel() != null) {
		    	 label.setText("Select Date to view Facilities avibility");
				 avibilityTableView.setPlaceholder(label);
		   	 }               
	FlowPane flowPaneDatePickers = new FlowPane();
		     flowPaneDatePickers.setHgap(10);
		     flowPaneDatePickers.getChildren().addAll(comboBox,new Label("Date From :"),dateFrom,new Label("Date Until :"),dateUntil,checkBox);
		     
		     VBox vbox = new VBox(10);
		     	  vbox.getChildren().addAll(flowPane,flowPaneDatePickers);
		     	  vbox.setPadding(new Insets(10));
		     	  borderPane.setTop(vbox);
		     	  avibilityTableView.getColumns().clear();
		     	  avibilityTableView();
		     	  borderPane.setCenter(avibilityTableView);
		     	  comboBox.valueProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						dateUntil.setDisable(true);
						dateUntil.getEditor().clear();
			     		avibilityTableView.getItems().clear();
			     		dateFrom.getEditor().clear();
						dateFrom.setOnAction(event -> {
				     		avibilityTableView.getItems().clear();
							if(dateFrom.getValue() != null && comboBox.getSelectionModel() != null) {
								ObservableList<date> ob = FXCollections.observableArrayList(avibilityDisplay(dateFrom.getValue().toString(),null,comboBox.getSelectionModel().getSelectedItem()));
								if(ob.size() == 0) {
								   	 label.setText("Facility is available whole day form 9 - 18");
									avibilityTableView.setPlaceholder(label);
								}else {
									avibilityTableView.setItems(FXCollections.observableArrayList(ob));
								}
							}});
				     	checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
								if(arg2) {
									dateUntil.setDisable(false);
									dateUntil.setOnAction(event -> {
									if(dateFrom.getValue() != null && comboBox.getSelectionModel() != null) {
										LocalDate dateF = LocalDate.parse(dateFrom.getValue().toString());
										LocalDate dateU = LocalDate.parse(dateUntil.getValue().toString());
										if(dateF.isAfter(dateU)) {
											Register.errorMessage("Start date cannot be after Until date");
										}else if(dateU.isBefore(dateF)) {
											Register.errorMessage("Until date cannot be before Start date");
										}else if(dateF.equals(dateU)) {
											Register.errorMessage("Start date and Until date cannont be equal");
										}else {
											String date = dateFrom.getValue().toString();
											String date1 = dateUntil.getValue().toString();
										avibilityTableView.setItems(FXCollections.observableArrayList(avibilityDisplay(date,date1,comboBox.getSelectionModel().getSelectedItem())));
									}}	
									else if(dateFrom.getValue() == null) {
										Register.errorMessage("Select date From to view facility\n avibility between two dates");
									}else if(dateUntil.getValue() == null) {
										Register.errorMessage("Select date Until to view facility\n avibility between two dates");
									}});
									}
								else {
									dateUntil.setDisable(true);
								}	
							}});};  
		     	  });
		
	return borderPane;
}

@SuppressWarnings("unchecked")
private static void userActivityDisplay(){
	

	
	 TableColumn<userActivity, String> DateCol = new TableColumn<userActivity, String>("Date");
                                  	   DateCol.setMinWidth(100);
                                  	   DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

	 TableColumn<userActivity, String> ActivityCol = new TableColumn<userActivity, String>("Activity");
	 								   ActivityCol.setMinWidth(400);
	 								   ActivityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
	 								  
	 TableColumn<userActivity, String> ActionCol = new TableColumn<userActivity, String>("Operation");
	 								   ActionCol.setMinWidth(175);
	 								   ActionCol.setCellValueFactory(new PropertyValueFactory<>("Operation"));					
	 								   
	 TableColumn<userActivity, String> AmountCol = new TableColumn<userActivity, String>("Paid Amount");
	 								   AmountCol.setMinWidth(100);
	 								   AmountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
	 								   
	 								  userActivityTableView.getColumns().addAll(DateCol,ActivityCol,ActionCol,AmountCol);
	 								 userActivityTableView.getStylesheets().add("userTableView.css");

								
	}

/**
 * viewStatmet method returns the statement between to specific dates inclusive, 
 * @param username
 */

/*private static ArrayList<userActivity> viewStatement(LocalDate date1, LocalDate date2,String username) {
	
	ArrayList<userActivity> bookings = new ArrayList<>();
	
	ArrayList<String> dates = new ArrayList<>();
	while(!date1.equals(date2)) {
		dates.add(date1.toString());
		date1 = date1.plusDays(1);
	}
	

	int i = 0;
	while(i < dates.size()) {
		for(int j = 0; j < addUser.userAcity.get(username).size();j++) {
			if(addUser.userAcity.get(username).containsKey(dates.get(i))){
				bookings.add(addUser.userAcity.get(username).get(i).get(j));
			}
		}
		i++;
	}
	
	return bookings;

}*/
	
public static void viewStatement(String username) {
	
	Stage statmentStage = new Stage();
		  statmentStage.initStyle(StageStyle.UNDECORATED);
		  statmentStage.initModality(Modality.APPLICATION_MODAL);
		  statmentStage.setResizable(false);
		  
		  Button closeButton = Util.closeButton(statmentStage);
		  		 closeButton.setAlignment(Pos.TOP_LEFT);

	BorderPane conentPane = new BorderPane();
			   conentPane.setPadding(new Insets(10));
			   conentPane.getStylesheets().add("Dark.css");
			   Scene statmentScene = new Scene(conentPane,Util.screenSize.getWidth()/2.5,Util.screenSize.getHeight()/2.8);
	
	/*DatePicker datePicker1 = new DatePicker();
			   datePicker1.setPromptText("Select Date From");
    DatePicker datePicker2 = new DatePicker();
    		   datePicker2.setPromptText("Select Date Until");
    		   datePicker2.setDisable(true);
    		   
	CheckBox checkBox = new CheckBox("Select to view statment between two dates");
	FlowPane flowPane = new FlowPane();
			 flowPane.getChildren().addAll(closeButton,new Label("From :"),datePicker1,new Label("To :"), datePicker2,checkBox);
			 flowPane.setOrientation(Orientation.HORIZONTAL);
			 flowPane.setHgap(20);
			 flowPane.setPadding(new Insets(10));*/
			 
			 	Set<String> key = addUser.userAcity.get(username).keySet();
				double payment = 0,due = 0, accountBalance = 0,previouseBalace = 0;
				for(String date : key) {
				for(userActivity activity : addUser.userAcity.get(username).get(date)) {
					payment += Double.parseDouble(activity.getAmount());
				}}
				

				for(Booking booking : addUser.userBookings.get(username)) {
					due += Double.parseDouble(booking.getDue());
				}

				accountBalance = due;
				previouseBalace = Double.parseDouble(addUser.users.get(username).getAccountBalance());
			
				
				GridPane gridPane = new GridPane();	
						 gridPane.setHgap(10);
						 gridPane.setVgap(10);
						 gridPane.setPadding(new Insets(10));
						 
						 String[] labels = {"Total Paid :","Total due on Account :","Previouse Balance","Account Balance :",};
						 Label[] labelsDisplay = new Label[labels.length*2];
						 for(int i = 0; i < labelsDisplay.length;i++) {
							 if(i < labels.length) {
								 labelsDisplay[i] = new Label(labels[i]);
								 gridPane.add(labelsDisplay[i], 0, i);
							 }
							 else if(i >= labels.length) {
								 labelsDisplay[i] = new Label();
								 gridPane.add(labelsDisplay[i], 1, i-4);
							 }
						 }
						 
						 labelsDisplay[4].setText(Double.toString(payment));
						 labelsDisplay[5].setText(Double.toString(due));
						 labelsDisplay[6].setText(Double.toString(accountBalance));
						 labelsDisplay[7].setText(Double.toString(previouseBalace));
						 userActivityDisplay();
						 ArrayList<userActivity> temp = new ArrayList<>();
						 for(String date : key) {
							 for(userActivity acti : addUser.userAcity.get(username).get(date)) {
								 temp.add(acti);
							 }
						 }userActivityTableView.setItems(FXCollections.observableArrayList(temp));

		     gridPane.setAlignment(Pos.BOTTOM_RIGHT);
			 conentPane.setTop(closeButton);
			 conentPane.setCenter(userActivityTableView);
			 conentPane.setBottom(gridPane);
			 
			 statmentStage.setScene(statmentScene);
			 statmentStage.sizeToScene();
			 statmentStage.show();
	
}
}

