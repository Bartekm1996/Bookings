
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.*;

/**
*The main method calls the Application class
*which initializes and launches the javafx program.
*
*The stage style is set to UNDECORATED, which removes the os 
*native application buttons and container border
*/

public class Main extends Application  {

    public static void main(String args[])throws ExceptionInInitializerError {
    	Locale.setDefault(Locale.ENGLISH);
    	Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage)throws Exception
    {	    
    	
    	addUser.readBlockedUsers(); //reads user who are locked due to failed login attempts,it compares the username | email entered against the list of blocked users
    	addUser.readUserCsvFile();
    	addUser.readAdminCsvFile();

		primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(userLogin.userLoginScene(primaryStage));
        primaryStage.centerOnScreen();
        primaryStage.show();
        
    }

}
