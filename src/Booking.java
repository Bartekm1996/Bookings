
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;

public class Booking {

	private SimpleStringProperty Facility;
	private SimpleStringProperty bookingPurpose;
	private SimpleStringProperty UserName;
	private SimpleStringProperty Dates;
	private String BookingId;
	private SimpleIntegerProperty StartTime;
	private SimpleIntegerProperty EndTime;
	private SimpleStringProperty Duration;
	private SimpleStringProperty Due;
	private SimpleStringProperty MadeOn;
	private MenuBar Menu;

	private MenuBar menu(String facility, String bookin, String bookingId) {
		final MenuBar menuBar = new MenuBar();
		final Menu menus = new Menu("Actions");

		menuBar.getMenus().add(menus);

		MenuItem _delete = new MenuItem("Delete Booking");
		MenuItem _makePayment = new MenuItem("Make Payment");

		_delete.setOnAction(event -> {
			if(addUser.admins.containsKey(userLogin.getCurrentUser())) {
				adiminDelte();
			}
				else {
				
					deleteBooking();
				}
			
			
		});

		_makePayment.setOnAction(event -> {
			if(addUser.admins.containsKey(userLogin.getCurrentUser())) {
				
			}
			pay(this.getFacility(),this.getBookingId(),false);
		});

		menus.getItems().addAll(_delete, _makePayment);

		return menuBar;
	}
	
/**
 * admin can delete bookings without associated cost,I will implement that the booking deleted if made by a user
 * will be appended onto the users dueBalance at 25% of booking cost as it is with users deleting a booking
 */
private void adiminDelte() {
	
	   		addUser.Bookigns.remove(this.getBookingId());
	   		addUser.userBookings.get(this.getUserName()).removeIf(bookin -> bookin.getBookingId().equals(this.getBookingId()));	
	   		addUser.facilityBooking.get(this.getFacility()).removeIf(bookings -> bookings.getBookingId().equals(this.getBookingId()));
	   		tableLeauge.bookingsView.getItems().removeIf(bookin -> bookin.getBookingId().equals(this.getBookingId()));
	   		tableLeauge.usersBookingView.getItems().removeIf(bookin -> bookin.getBookingId().equals(this.getBookingId()));
	   		if(addUser.facilityBooking.get(this.getFacility()).removeIf(booking -> booking.getBookingId().equals(this.getBookingId())));
	   		{
	   				Register.errorMessage("Booking Deleted");
			   		tableLeauge.bookingsView.getItems().clear();	
			   		addUser.updateBookings();
			}
	   		
	   		
	   		/*for(String dates : addUser.dates) {
	   			if(addUser.facilityDateBookings.containsKey(dates)) {
	   				for(String facility : addUser.facilitesList) {
	   					if(addUser.facilityDateBookings.get(dates).containsKey(facility)) {
	   						for(Booking bookings : addUser.facilityDateBookings.get(dates).get(facility)) {
	   							if(bookings.getBookingId().equals(this.getBookingId())) {
	   								addUser.facilityDateBookings.get(dates).get(facility).remove(bookings);
	   							}
	   						}
	   					}
	   				}
	   			}
	   		}*/
	   		
	   		
  		
}
private void deleteBooking() {
		
		Stage deleteBooking = new Stage();
			  deleteBooking.initStyle(StageStyle.UNDECORATED);
			  deleteBooking.initModality(Modality.APPLICATION_MODAL);
			  deleteBooking.setResizable(false);
			  
			  BorderPane borderPane = new BorderPane();
 			  			 borderPane.setPadding(new Insets(10));
 			  			 
		
		GridPane pane = new GridPane();
				 pane.setVgap(20);
				 pane.setHgap(10);
				 pane.setPadding(new Insets(10));
					
					 Scene deleteBookingScene = new Scene(borderPane,500,100);
					 	   deleteBookingScene.getStylesheets().addAll("Dark.css","addLeaugesButtons.css");
					
					 	   			  Button delete = Util.closeButton(deleteBooking);
					 	   			  Button procedToPayment = new Button("Proceed to Payment");
					 	   			  		 procedToPayment.setMinWidth(150);
					 	   			  		 procedToPayment.setMaxWidth(150);
					 	   			  Button payLater = new Button("Pay Later");
					 	   			  	     payLater.setMinWidth(150);
					 	   			  	     payLater.setMaxWidth(150);
					 	   			  
					 	   			  GridPane gridPane = new GridPane();
					 	   			  		   Label priceLabel = new Label();
					 	   			  		   Label info = new Label();
					 	   			  		   GridPane.setHalignment(procedToPayment, HPos.RIGHT);
					 	   			  		   GridPane.setHalignment(payLater, HPos.RIGHT);
					 	   			  		 
									 	   if(addUser.admins.containsKey(userLogin.getCurrentUser())) {
									 		   	pane.add(delete, 1, 4);
									 		   
									 	   }else {
									 		   
									 		   SimpleStringProperty price = new SimpleStringProperty(addUser.facilityPrices.get(this.getFacility()));
									 		   double priceInt = Double.parseDouble(price.get());
									 		       priceInt = (priceInt/100)*25;
									 		       price.set(Double.toString(priceInt));
									 		   priceLabel.setText("Caution : Deleting a booking is capped at 25% of total cost " + price.get());
									 		   gridPane.add(priceLabel, 0, 1,2,1);
									 		   HBox hbox = new HBox(10);
									 		   		hbox.getChildren().addAll(procedToPayment,payLater);
									 		   		gridPane.add(hbox, 1, 3);
									 		   	
									 		   		Calendar calendar = null;
									 		   	try {
									 		   		calendar = Calendar.getInstance();
									 		   	}catch(DateTimeException e) {
									 		   		e.printStackTrace();
									 		   	}
									 		   	
									 		   	
												DateFormat format = new SimpleDateFormat("yyyy MM dd");
												String formatedDate = format.format(calendar.getTime());
									 		   		
									 		   	payLater.setOnAction(event -> {
									 		   		
									 		   
									 		   		addUser.userBookings.get(userLogin.getCurrentUser()).removeIf(booking -> booking.getBookingId().equals(getBookingId()));

									 		   			addUser.Bookigns.remove(this.getBookingId());
									 		   			addUser.writeUserActivity(new userActivity(formatedDate,"Booking " + this.getBookingId() + "deleted by user","Booking delete",price.get(),"0"), userLogin.getCurrentUser());
									 		   			
									 		   			double update = Double.parseDouble(price.get()) + Double.parseDouble(addUser.users.get(this.getUserName()).getAccountBalance());
									 		   			addUser.users.get(this.getUserName()).setPreviouseBalance(addUser.users.get(this.getUserName()).getAccountBalance());
									 		   			addUser.users.get(this.getUserName()).setAccountBalance(Double.toString(update));
									 		   	
									 		   				addUser.facilityBooking.get(this.getFacility()).removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
									 		   		
									 		   			tableLeauge.bookingsView.getItems().removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
									 		   			tableLeauge.usersBookingView.getItems().removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
									 		   			if(addUser.facilityDateBookings.containsKey(this.getFacility())) {
									 		   				if(addUser.facilityDateBookings.get(this.getFacility()).containsKey(this.getDates())) {
									 		   					addUser.facilityDateBookings.get(this.getFacility()).get(this.getDates()).removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
									 		   				}
									 		   			}
									 		   			this.setDue(price.get());
									 		   			Register.errorMessage("Booking deleted");

									 		   		
									 		   	});
									 		   	
									 		   procedToPayment.setOnAction(event -> {
										   			addUser.Bookigns.remove(this.getBookingId());
								 		   			addUser.writeUserActivity(new userActivity(formatedDate,"Booking " + this.getBookingId() + "deleted by user","Booking delete",price.get(),"0"), userLogin.getCurrentUser());
								 		   			
								 		   			double update = Double.parseDouble(price.get()) + Double.parseDouble(addUser.users.get(this.getUserName()).getAccountBalance());
								 		   			addUser.users.get(this.getUserName()).setPreviouseBalance(addUser.users.get(this.getUserName()).getAccountBalance());
								 		   			addUser.users.get(this.getUserName()).setAccountBalance(Double.toString(update));
								 		   			tableLeauge.bookingsView.getItems().removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
								 		   			tableLeauge.usersBookingView.getItems().removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
								 	
							 		   				addUser.facilityBooking.get(this.getFacility()).removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));

								 		   			if(addUser.facilityDateBookings.containsKey(this.getFacility())) {
								 		   				if(addUser.facilityDateBookings.get(this.getFacility()).containsKey(this.getDates())) {
								 		   					
								 		   					addUser.facilityDateBookings.get(this.getFacility()).get(this.getDates()).removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
								 		   				}
								 		   			}
								 		   			this.setDue(price.get());
									 			    pay(this.getFacility(), this.getBookingId(),true);
									 		   });
									 	   }
						          
				
					 		   delete.setAlignment(Pos.TOP_LEFT);
					 		   borderPane.setTop(delete);
					 		   borderPane.setCenter(gridPane);
					 		   deleteBooking.setScene(deleteBookingScene);
					 		   deleteBooking.sizeToScene();
					 		   deleteBooking.show();
	}

	public Booking() {
		
	}
	public Booking(String facility, String booking, String userName, String date, String bookingId, int startTime,
			int endTime, String length, String amountOwned, String dateMade) {

		this.Facility = new SimpleStringProperty(facility);
		this.bookingPurpose = new SimpleStringProperty(booking);
		this.UserName = new SimpleStringProperty(userName);
		this.Dates = new SimpleStringProperty(date);
		this.BookingId = bookingId;
		this.StartTime = new SimpleIntegerProperty(startTime);
		this.EndTime = new SimpleIntegerProperty(endTime);
		this.Duration = new SimpleStringProperty(length);
		this.Due = new SimpleStringProperty(amountOwned);
		this.Menu = menu(facility, booking, bookingId);
		this.MadeOn = new SimpleStringProperty(dateMade);

	}

	public void setFacility(String facility) {
		Facility.set(facility);
	}

	public String getFacility() {
		return Facility.get();
	}

	public void setBookingPurpose(String booking) {
		bookingPurpose.set(booking);
	}

	public String getBookingPurpose() {
		return bookingPurpose.get();
	}

	public String getUserName() {
		return UserName.get();
	}

	public void setDates(String date) {
		Dates.set(date);
	}

	public String getDates() {
		return Dates.get();
	}

	public void setStartTime(int startTime) {
		StartTime.set(startTime);
	}

	public String getBookingId() {
		return this.BookingId;
	}

	public int getStartTime() {
		return StartTime.get();
	}

	public void setEndTime(int endTime) {
		EndTime.set(endTime);
	}

	public int getEndTime() {
		return EndTime.get();
	}

	public void setDuration(String length) {
		Duration.set(length);
	}

	public String getDuration() {
		return Duration.get();
	}

	public void setDue(String amount) {
		Due.set(amount);
	}

	public String getDue() {
		return Due.get();
	}

	public void setMenu(MenuBar menu) {
		this.Menu = menu;
	}

	public MenuBar getMenu() {
		return this.Menu;
	}
	
	public String getMadeOn() {
		return MadeOn.get();
	}

	
	public void pay(String facility,String bookingID,boolean del) {

		Stage paymentStage = new Stage();
			  paymentStage.initStyle(StageStyle.UNDECORATED);
			  paymentStage.initModality(Modality.APPLICATION_MODAL);	
			  

		BorderPane borderPane = new BorderPane();
			       borderPane.setPadding(new Insets(10));
			       borderPane.getStylesheets().addAll("Accordion.css","Dark.css","userTableView.css","vbox.css","addLeaugesButtons.css");
			       Button closeButton = Util.closeButton(paymentStage);
			       	      closeButton.setAlignment(Pos.TOP_LEFT);
			       	      borderPane.setTop(closeButton);

		Scene paymentScene = new Scene(borderPane,325, 310);

		final ToggleGroup toggle = new ToggleGroup();
				RadioButton cashPayment = new RadioButton("Cash Payment");
							cashPayment.setToggleGroup(toggle);
				//RadioButton cardPayments = new RadioButton("Card Payment");
							//cardPayments.setToggleGroup(toggle);


		VBox vbox = new VBox(10);
			 vbox.setPadding(new Insets(20));
	         vbox.getChildren().addAll(cashPayment);

		TitledPane titlePane = new TitledPane();
				   titlePane.setExpanded(false);
				   

		GridPane gridPane = new GridPane();
		         gridPane.add(vbox, 0, 1);
		         gridPane.add(titlePane, 0, 2);

		toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				if (cashPayment.isSelected()) {
					titlePane.setContent(cashPayment(paymentStage, facility, bookingID,del));
					titlePane.setExpanded(true);
				}/* else if (cardPayments.isSelected()) {
					titlePane.setContent(cardPayments(facility));
					titlePane.setExpanded(true);
				}*/

			}
		});

		borderPane.setCenter(gridPane);
		paymentStage.sizeToScene();
		paymentStage.setScene(paymentScene);
		paymentStage.show();

	}

	private GridPane cashPayment(Stage stage, String facility, String BookingId,boolean del) {

		GridPane gridPane = new GridPane();
				 gridPane.setHgap(10);
				 gridPane.setVgap(10);
				 gridPane.getStylesheets().addAll("Dark.css","userTableView.css","vbox.css","addLeaugesButtons.css");


		String labels[] = { "Payin Amount : ", "Amount Paid : ", "Amount Owned : "};
		Label label[] = new Label[labels.length + 3];

		for (int i = 0; i < label.length; i++) {
			if (i < 3) {
				label[i] = new Label(labels[i]);
				gridPane.add(label[i], 0, i);
			} else if (i >= 3) {
				label[i] = new Label();
		
			}
			label[i].setStyle("-fx-text-fill: black;");

		}

		gridPane.add(label[3], 1, 1);
		gridPane.add(label[4], 1, 2);

		Label messageLabel = new Label();

		TextField payIn = new TextField();
				  payIn.setPromptText("Enter Paying Amount");
		Button payButton = new Button("Pay");

		double dueAmount = 0;
		if(addUser.Bookigns.containsKey(BookingId)) {
			dueAmount = Double.parseDouble(addUser.Bookigns.get(BookingId).getDue());
		}
		label[3].setText("€ " + "0");
		label[4].setText("€ " + Double.toString(dueAmount));

		GridPane.setHalignment(payButton, HPos.RIGHT);
		SimpleStringProperty update = new SimpleStringProperty();
		SimpleStringProperty paid = new SimpleStringProperty();

		SimpleStringProperty converted = new SimpleStringProperty(Double.toString(dueAmount));
		payIn.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.length() != 0) {
					label[3].setText("€ " + arg2);
					if (Double.parseDouble(arg2) > Double.parseDouble(converted.get())) {
						messageLabel.setText("Payment cant be bigger than amount due");
					} else {
						paid.set(arg2);
						double updateAmount =  Double.parseDouble(converted.get()) - Double.parseDouble(arg2);
						update.set(Double.toString(updateAmount));
						label[4].setText("€ " + Double.toString(updateAmount));
					}
				} else {
					label[3].setText("€ " + "0");
					label[4].setText("€ " +  converted.get());
				}
			}
		});

		Instant instant = Instant.now();
		Date date = Date.from(instant);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
		String formattedDate = formatter.format(date);

		payButton.setOnAction(event -> {
			if (!payIn.getText().isEmpty() && update.length() != null) {
				
				if (addUser.admins.containsKey(userLogin.getCurrentUser())) {

					this.setDue(update.get());
					if (addUser.Bookigns.containsKey(this.getUserName())) {
						addUser.Bookigns.get(this.getUserName()).setDue(update.get());
					}
					
					addUser.writeUserActivity(
							new userActivity(formattedDate,
									"Payment made by amdin " + userLogin.getCurrentUser() + " - " + BookingId
											+ " on the " + this.getDates(),
									"Payment", paid.get(),addUser.facilityPrices.get(this.getFacility())),
							this.getUserName());
					Register.errorMessage("Payment successful");
					tableLeauge.bookingsView.getItems().clear();
					tableLeauge.bookingsView.setItems(FXCollections.observableArrayList(addUser.facilityBooking.get(facility)));
				} else {

					this.setDue(update.get());
					if (addUser.Bookigns.containsKey(this.getUserName())) {
						addUser.Bookigns.get(this.getUserName()).setDue(update.get());
					}
					if(addUser.userAcity.get(this.getUserName()).containsKey(this.getDates())) {
						addUser.userAcity.get(this.getUserName())
						.get(this.getDates()).add(new userActivity(formattedDate,
								"Payment for booking " + BookingId + " on the " + this.getDates(), "Payment",
								paid.get(),addUser.facilityPrices.get(this.getFacility())));
					}
					
					addUser.writeUserActivity(new userActivity(formattedDate,
							"Payment for booking " + BookingId + " on the " + this.getDates(), "Payment", paid.get(),addUser.facilityPrices.get(this.getFacility())),
							this.getUserName());
					messageLabel.setText("Payment sucessfull");
					
					if(addUser.facilityBooking.containsKey(this.getFacility())) {
						for(Booking booking : addUser.facilityBooking.get(this.getFacility())) {
							if(booking.getBookingId().equals(this.getBookingId())) {
								booking.setDue(update.get());
							}
						}
					}
					
					Register.errorMessage("Payment successful");
					addUser.users.get(this.getUserName()).setAccountBalance(update.get());
					addUser.saveEditedAccounts();
					tableLeauge.bookingsView.getItems().clear();
					tableLeauge.bookingsView.setItems(FXCollections.observableArrayList(addUser.facilityBooking.get(facility)));
					tableLeauge.usersBookingView.getItems().clear();
					tableLeauge.usersBookingView.setItems(FXCollections.observableArrayList(addUser.userBookings.get(userLogin.getCurrentUser())));
					if(del) {
 		   				addUser.facilityBooking.get(this.getFacility()).removeIf(booking -> booking.getBookingId().equals(this.getBookingId()));
					}
				}
				stage.close();
			} else {
				messageLabel.setText("Please put in amount being paid");
			}

		});

		gridPane.add(messageLabel, 0, 3, 2, 1);
		gridPane.add(payButton, 1, 4);
		gridPane.add(payIn, 1, 0);

		return gridPane;

	}
}

/**
 *  cardPayments will be implement in later development of the application
 *  not neccessary for the purpose of the project
 */

/*
	private GridPane cardPayments(String facility) {

		GridPane gridPane = new GridPane();
			     gridPane.setHgap(10);
			     gridPane.setVgap(10);

		String[] labels = { "Card Number : ", "Expiry Date : ", "CVC : ", "CardHolder Name : ","Amount being payed : "};
		Label[] displayLabel = new Label[labels.length];
		TitledPane[] titlePane = new TitledPane[labels.length];
		StackPane[] stackPane = new StackPane[labels.length];
		Label[] label = new Label[labels.length];
		TextField[] textFields = new TextField[labels.length - 1];
		
		ArrayList<String> years = new ArrayList<String>();
		ArrayList<String> _months = new ArrayList<String>();

		for(int i = 2018; i < 2028; i++) {
			years.add(Integer.toString(i));
		}
		for(int i = 0; i <= 12;i++) {
			_months.add(Integer.toString(i));
		}

		ComboBox<String> year = new ComboBox<>(FXCollections.observableArrayList(years));
		ComboBox<String> months = new ComboBox<>(FXCollections.observableArrayList(_months));

		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(months, year);

		for (int i = 0; i < label.length; i++) {
			label[i] = new Label(labels[i]);
			titlePane[i] = new TitledPane();
			displayLabel[i] = new Label();
			titlePane[i].setStyle("-fx-background-color:transparent;");
			stackPane[i] = new StackPane();
			if (i < label.length - 1) {
				textFields[i] = new TextField();
				StackPane.setAlignment(textFields[i], Pos.TOP_CENTER);
				stackPane[i].getChildren().addAll(titlePane[i],textFields[i]);
			}
			titlePane[i].setContent(displayLabel[i]);
			titlePane[i].setExpanded(false);
			StackPane.setAlignment(titlePane[i], Pos.BOTTOM_CENTER);
			gridPane.add(label[i], 0, i+1);
			
		}
		
		stackPane[4].getChildren().addAll(titlePane[4],hbox);
		StackPane.setAlignment(hbox, Pos.TOP_CENTER);
		gridPane.add(stackPane[0], 1, 1, 2, 1);
		gridPane.add(stackPane[4], 1, 2, 2, 1);
		gridPane.add(stackPane[1], 1, 3);
		gridPane.add(stackPane[2], 1, 4, 2, 1);
		gridPane.add(stackPane[3], 1, 5, 2,1);
		
		
		Button payButton = new Button("Pay");

		SimpleStringProperty _year = new SimpleStringProperty();
		SimpleStringProperty _mont = new SimpleStringProperty();
		year.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				_year.set(arg2);
			}
			
		});
		
		months.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				_mont.set(arg2);
			}
			
		});
	
			  
		textFields[0].textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.length() != 16) {
					displayLabel[0].setText("Card number must be 16 digits long");
					textFields[0].setStyle("-fx-border-color: red;");
					titlePane[0].setExpanded(true);
				}else if(arg2.isEmpty()) {
					textFields[0].setStyle("-fx-border-color: gray;");
					titlePane[0].setExpanded(false);
				}
				else {
					textFields[0].setStyle("-fx-border-color: green;");
					titlePane[0].setExpanded(false);
				}
			}
		});

		textFields[1].textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (textFields[1].getText().length() != 3) {
					displayLabel[1].setText("Card number must be 3 digits long");
					textFields[1].setStyle("-fx-border-color: red;");
					titlePane[1].setExpanded(true);
				}else if(arg2.isEmpty()) {
					textFields[1].setStyle("-fx-border-color: gray;");
					titlePane[1].setExpanded(false);
				}
				else {
					textFields[1].setStyle("-fx-border-color: green;");
					titlePane[1].setExpanded(false);
				}
			}
		});

		textFields[2].textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (!textFields[2].getText().contains(" ")) {
						displayLabel[2].setText("Enter Name and Surname");
						textFields[2].setStyle("-fx-border-color: red;\n");
						titlePane[2].setExpanded(true);
					}else if(arg2.isEmpty()) {
						textFields[2].setStyle("-fx-border-color: gray;\n");
						titlePane[2].setExpanded(false);
					}
					else {
						textFields[2].setStyle("-fx-border-color: green;\n");
						titlePane[2].setExpanded(false);
					}
			}
		});

		textFields[3].textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				try {
				if (Integer.parseInt(arg2) > Integer.parseInt(getDue())) {
					displayLabel[1].setText("Sum cannot be greate than amount due to be paid");
					textFields[1].setStyle("-fx-border-color: red;");
					titlePane[1].setExpanded(true);
				}else if(arg2.isEmpty()) {
					textFields[1].setStyle("-fx-border-color: gray;");
					titlePane[1].setExpanded(false);
				}
				else {
					textFields[1].setStyle("-fx-border-color: green;");
					titlePane[1].setExpanded(false);
				}
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}});

		payButton.setOnAction(event -> {
		
		});


		return gridPane;

	}
}*/
