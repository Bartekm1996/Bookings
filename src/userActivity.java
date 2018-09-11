import javafx.beans.property.*;

public class userActivity {

	private SimpleStringProperty activity;
	private SimpleStringProperty date;
	private SimpleStringProperty Operation;
	private SimpleStringProperty Amount;
	private SimpleStringProperty Price;
	
	userActivity(String date, String activity,String op,String BookingPrice,String payment){
		
		this.activity = new SimpleStringProperty(activity);
		this.date = new SimpleStringProperty(date);
		this.Operation = new SimpleStringProperty(op);
		this.Amount = new SimpleStringProperty(payment);
		this.Price = new SimpleStringProperty(BookingPrice);
	}
	
	public String getPrice() {
		return this.Price.get();
	}

	public String getActivity() {
		return this.activity.get();
	}
	
	public String getDate() {
		return this.date.get();
	}
	
	public String getOperation() {
		return this.Operation.get();
	}

	public String getAmount() {
		return this.Amount.get();
	}
	
}
