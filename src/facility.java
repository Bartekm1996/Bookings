import javafx.collections.FXCollections;
import javafx.scene.control.*;

public class facility {

	private String Facility;
	private Button Button;
	private String suspendUntil;
	private String suspendFrom;
	private String facilityId;
	
	private Button addButton(String facility) {
	
		Button button = new Button(facility);
			   button.setOnAction(event -> {
				   tableLeauge.setFacility(facility);
				   tableLeauge.bookingsView.getColumns().clear();
				   if(addUser.admins.containsKey(userLogin.getCurrentUser())) {
					   tableLeauge.bookingDisplay(true); 
				   }else {
					   tableLeauge.bookingDisplay(false); 

				   }
				   
				   	if(addUser.facilityBooking.containsKey(facility)) {

				   		Label label = new Label();
				   			  label.setStyle("-fx-font-size: 18px;" + "-fx-text-fill: black;");
				 	   tableLeauge.bookingsView.getItems().clear();
						if(addUser.facilityBooking.get(facility).isEmpty()) {
						   label.setText("No Bookings yet made for this facility");
						   tableLeauge.bookingsView.getItems().clear();
						   tableLeauge.bookingsView.setPlaceholder(label);

					   } 
					   else {
						   
						   tableLeauge.bookingsView.getItems().clear();
						   tableLeauge.bookingsView.setItems(FXCollections.observableArrayList(addUser.facilityBooking.get(this.getFacility())));
					   
					   }}}
			   

			   );
		
		
		return button;
	}
	
	
	public facility(String facilityId,String _facility,String dateUntil,String dateFrom) {
		this.Facility = _facility;
		this.Button = addButton(_facility);
		this.suspendFrom = dateFrom;
		this.suspendUntil = dateUntil;
		this.facilityId = facilityId;
	}
	
	public void setDateFrom(String startDate) {this.suspendFrom = startDate;}
		public String getDateFrom() {return this.suspendFrom;};
		
	public void setDateUntil(String endDate) {this.suspendUntil = endDate;}
		public String getDateUntil() {return this.suspendUntil;}
	
	public String getFacilityId() {return this.facilityId;}
	
	public String getFacility() {return this.Facility;}
	
	public void setButton(Button _button) {this.Button = _button;}
	
	public Button getButton() {return this.Button;}
	
}
