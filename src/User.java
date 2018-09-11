import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;


public class User {
	
	/**
	 * SimpleStringProperty from javafx.beans.property is used as it's supported by javafx's tableview whcih works
	 * on properties
	 * 
	 */
	private final SimpleStringProperty userName;
	private final SimpleStringProperty Name;
	private final SimpleStringProperty surName;
	private final SimpleStringProperty Email;
	private final SimpleStringProperty Date;
	private final SimpleStringProperty AccountBalance;
	private final SimpleStringProperty PreviouseBalance;
	private String request;
	private String picPath;
	private int age;
	private String password;
	private MenuBar Options;
	
private MenuBar statmentButton(String username) {
		
		
		final MenuBar menuBar = new MenuBar();
		final Menu menu = new Menu("Manage");
		
		MenuItem viewStatment = new MenuItem("View Statment");
		MenuItem deleteUser = new MenuItem("Delete User");
		MenuItem unblockUser = new MenuItem("Unblock User");
		MenuItem regeneratePassword = new MenuItem("Genereate Password");
		    if(addUser.blockedUsers.contains(username)) {
		    		menu.getItems().addAll(viewStatment,deleteUser,unblockUser);
		    }else if(addUser.passwordRequest.contains(username)) {
	    		menu.getItems().addAll(viewStatment,deleteUser,regeneratePassword);
		    }
		    else{
	    		menu.getItems().addAll(viewStatment,deleteUser);
		    }
			menuBar.getMenus().add(menu);
			
			viewStatment.setOnAction(event -> {
				 tableLeauge.userActivityTableView.getColumns().clear();
				 tableLeauge.userActivityTableView.getItems().clear();
			tableLeauge.viewStatement(username);
			});
			deleteUser.setOnAction(event -> deleteUser());
			unblockUser.setOnAction(event -> {
				addUser.blockedUsers.remove(username);
				addUser.forgotPasswords.remove(username);
				resetPassword();
				Register.errorMessage("User " + this.getUserName() + "un-blocked");
			 	tableLeauge.UsersTableView.setRowFactory(user -> {
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
		 				}};
		 				return row ;
			 	});
			 	menu.getItems().clear();
			 	menu.getItems().addAll(viewStatment,deleteUser);
			});
			regeneratePassword.setOnAction(event -> {
				resetPassword();
			});
		
		return menuBar;
	}
	
private void resetPassword() {
	addUser.users.get(this.getEmail()).setPassword("Reset");
	for(User user : addUser.userListView) {
		if(user.getEmail().equals(this.getEmail())) {
			user.setPassword("Reset");
			Register.errorMessage("Users password has been reset");
		}
	}
	addUser.saveEditedAccounts();
	addUser.updateBlockedUser();
	addUser.initEverything();
}

private void deleteUser() {
		
		if(addUser.users.containsKey(this.getEmail()) && addUser.userBookings.containsKey(this.getEmail())) {
				if(addUser.userBookings.get(this.getEmail()).isEmpty()) {
					
					addUser.users.remove(this.getEmail());
					addUser.userBookings.remove(this.getEmail());
					tableLeauge.user.removeIf(user -> user.getEmail().equals(this.getEmail()));
					addUser.userListView.removeIf(user -> user.getEmail().equals(this.getEmail()));
					if(addUser.blockedUsers.contains(this.getUserName())) {
						addUser.blockedUsers.remove(this.getUserName());
					}
					for(String facility : addUser.facilitesList) {
						addUser.facilityBooking.get(facility).removeIf(booking -> booking.getUserName().equals(this.getEmail()));
						for(String date : addUser.dates) {
							if(addUser.facilityDateBookings.containsKey(date)) {
								addUser.facilityDateBookings.get(date).get(facility).removeIf(booking -> booking.getUserName().equals(this.getEmail()));
						}}
					}
					tableLeauge.UsersTableView.getColumns().clear();
					addUser.saveEditedAccounts();
					tableLeauge.displayUsers();

				}else {
				
					if(this.getUserName().equals("null")) {
						Register.errorMessage("Cannont delete " + this.getEmail()+ " as he has\n\t outstanding bookings");
					}else {
						Register.errorMessage("Cannont delete " + this.getUserName()+ " as he has\n\t outstanding bookings");
					}
					tableLeauge.bookingsView.getColumns().clear();
					tableLeauge.bookingDisplay(true);
					tableLeauge.bookingsView.setItems(FXCollections.observableArrayList(addUser.userBookings.get(this.getEmail())));

				}
		}
				
	}
	
public User(String username, String name, String surname ,String email,String date,String picPath,String password,String balance,String Balance,String request)
    {
		this.userName = new SimpleStringProperty(username);
		this.surName = new SimpleStringProperty(surname);
		this.Name = new SimpleStringProperty(name);
		this.Email = new SimpleStringProperty(email);
		this.Date = new SimpleStringProperty(date);
		this.picPath = picPath;
		this.password = password;
		this.AccountBalance = new SimpleStringProperty(balance);
		this.PreviouseBalance = new SimpleStringProperty(Balance);
		this.Options = statmentButton(email);
		this.request = request;
    }

	public void setPicPath(String picturePath) {this.picPath = picturePath;}
		public String getPicPath() {return this.picPath;}
											
	public void setDateOfBirth(String date) {Date.set(date);}
		public String getDateOfBirth() {return Date.get();}
	
	public void setUserName(String username) {userName.set(username);}
		public String getUserName() {return userName.get();}
		
	public void setName(String name) {Name.set(name);}
		public String getName() {return Name.get();}
		
	public void setSurName(String surname) {surName.set(surname);}
		public String getSurName() {return surName.get();}	
		
	public String getEmail() {return Email.get();}
		
	public int getAge() {return age;}
		
	public void setPassword(String password) {this.password = password;};
		public String getPassword() {return password;};
		
	public void setOptions(MenuBar statmentButton) {this.Options = statmentButton;}
		public MenuBar getOptions() {return Options;}
		
	public void setAccountBalance(String balance) {this.AccountBalance.set(balance);}
		public String getAccountBalance() {return this.AccountBalance.get();}

	public void setRequest(String request) {this.request = request;}
		public String getRequest() {return this.request;}
		
	public void setPreviouseBalance(String balance) {this.PreviouseBalance.set(balance);}
		public String getPreviouseBalance() {return this.PreviouseBalance.get();}
		
}

