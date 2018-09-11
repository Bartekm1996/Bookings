import javafx.beans.property.*;

public class date {

	private SimpleStringProperty date;
	private SimpleStringProperty hour;
	private SimpleStringProperty Avibility;
	
	public date(String _date,String _hour,String _avb) {
		this.date = new SimpleStringProperty(_date);
		this.hour = new SimpleStringProperty(_hour);
		this.Avibility = new SimpleStringProperty(_avb);
	}
	
	public String getDate() {return this.date.get();}
	public String getHour() {return this.hour.get();}
	public String getAvibility(){return this.Avibility.get();}
	
}
