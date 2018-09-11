import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.input.*;
import java.awt.*;

public class Util {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final double screenWidth = screenSize.getWidth()/2.0;
    public static final double screenHeight = screenSize.getHeight()/2.0;
    
    private static double xPos = 0;
    private static double yPos = 0;
    
    /**
     * 
     * @param pane           The layout pane which is to be moved around 
     * @param primaryStage   The stage in which the layout pane is applied to
     * 
     * OnMousePressed gets the x and y coordinates of the mouse while its pressed
     * OnMouseDragged is setting the x and y coordinates of the mouse when its moved so the stage can be place at that position
     * The two above methods are overloaded with two different style layout panes
     * 
     * 
     */

    public static void OnMousePressed(BorderPane pane, Stage primaryStage){
        pane.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                xPos= primaryStage.getX() - event.getScreenX();
                yPos = primaryStage.getY() - event.getScreenY();
            }
        });
    }

    public static void OnMouseDragged(BorderPane pane, Stage primaryStage)
    {
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xPos);
                primaryStage.setY(event.getScreenY() + yPos);
            }
        });

    }

    public static Button closeButton(Stage stage) {
    	
    	Button closeButton = new Button();
    	closeButton = new Button();
    	closeButton.setId("closeButton");
    	closeButton.getStylesheets().add("toolBar.css");
    	closeButton.setMaxWidth(16);
    	closeButton.setMinWidth(16);
    	closeButton.setMinHeight(16);
    	closeButton.setMaxHeight(16);
    	closeButton.setOnAction(event -> stage.close());
    	return closeButton;
    	
    }


    /**
     * MenuBar contains logout menutItem and settings menuItem which will be 
     * implement in late development post exams
     * @param primaryStage
     * @return
     */
       
    public static MenuBar toolBarMenu(Stage primaryStage){
        
        MenuBar menubar = new MenuBar();
        		menubar.setId("Options");
        			
        Menu menu = new Menu();
             menu.setText("Options");
      
        MenuItem logout = new MenuItem("Logout", new ImageView("logout.png"));
        	     logout.setOnAction(event -> 
        	     {
        	    	 addUser.initEverything();   	  
        	 		 addUser.updateDecomisionedFacilites();
        	    	 primaryStage.setScene(userLogin.userLoginScene(primaryStage));
        	    	 primaryStage.setFullScreen(true);
        	    	 
        	     });
        	           
        MenuItem setting = new MenuItem("Settings");
        		 //setting.setOnAction(event -> settingStage(primaryStage)); To be implemented on late development unnecessary for the purpose of the project	
        		 setting.setDisable(true);

        		 menu.getItems().addAll(setting, logout);
        		 menubar.getMenus().add(menu);
        
        return menubar;
    }

    /**
     * ToolBar contains three Button close,minimize and expand the stage(container)
     * it takes a boolean @param resizableButtonActivated as not all windows
     * are expandable to full screen resolution 
     *
     * @param primaryStage
     * @param resizableButtonActivated
     * @return
     */
    
    public static HBox toolBar(Stage primaryStage, Boolean resizableButtonActivated){

        HBox toolBarButtonsBox = new HBox(10);
             toolBarButtonsBox.getStylesheets().add("toolBar.css");
             toolBarButtonsBox.setAlignment(Pos.TOP_LEFT);
        
        String buttonId[] = {"closeButton","iconifyButton","maximizeWindow"};
        	Button[] buttons = new Button[buttonId.length];
       
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button();
            buttons[i].setId(buttonId[i]);
            buttons[i].setMaxWidth(16);
            buttons[i].setMinWidth(16);
            buttons[i].setMinHeight(16);
            buttons[i].setMaxHeight(16);
            toolBarButtonsBox.getChildren().add(buttons[i]);
        }

        buttons[2].setVisible(resizableButtonActivated);

        buttons[0].setOnAction(event -> {
        	
        	addUser.initEverything();
        	addUser.updateDecomisionedFacilites();
        	Platform.exit();
        	
        });
            buttons[1].setOnAction(event -> primaryStage.setIconified(true));

        buttons[2].setOnAction(event -> {
            if(!primaryStage.isFullScreen()){
            	primaryStage.setFullScreen(true);
            		buttons[2].setId("minimizeWindow");}
            else {primaryStage.setFullScreen(false);
                        buttons[2].setId(buttonId[2]);
                            primaryStage.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
                                primaryStage.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
                          primaryStage.centerOnScreen();
        }});
        
        return toolBarButtonsBox;
    }
    

 
    

}
