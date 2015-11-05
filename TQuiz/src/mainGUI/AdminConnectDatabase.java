package mainGUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import dataEntry.*;

/**
 * 
 * @author Mohit Uniyal, This frame will provide interface to enter Address,
 *         port no, user name and password Date : 1-8-2015, 7:38pm
 */
public class AdminConnectDatabase extends Application {
	static String serverAddress, portNo, userName, dbPassword = "";
	private TextField serverAddTxtFld, portNoTxtFld, userNameTxtFld;
	private PasswordField dbPasswordTxtFld;
	private Button btnConnect;
	private Stage globalStg;

	public static String dbType = "jdbc:mysql://";

	public AdminConnectDatabase() {
		serverAddTxtFld = new TextField();
		portNoTxtFld = new TextField("3306");
		userNameTxtFld = new TextField();
		dbPasswordTxtFld = new PasswordField();
		btnConnect = new Button("Connect");
		dbType = new String("jdbc:mysql://");
	}

	public static void main(String[] args) {
		launch();
	}

	public void start(Stage adminStage) throws Exception {
		globalStg = adminStage;
		adminStage.setTitle("ADMIN : By Mohit Uniyal");
		BorderPane mainLayout = new BorderPane();
		// all the components will be displayed on this centerGrid
		GridPane centerGrid = new GridPane();

		// adds all the components to the center of the frame
		createMainGui(mainLayout, centerGrid);
		// creates radio buttons and handles thier events
		handleRdBtnEvents(centerGrid);
		// handling button events for database connectivity and user inputs
		connectToDatabase();
		// only accepting numeric values
		checkPortValue();

		// setting scene and displaying form
		mainLayout.setCenter(centerGrid);
		Scene mainScene = new Scene(mainLayout, 420, 280);
		adminStage.setScene(mainScene);
		adminStage.show();
	}

	private void createMainGui(BorderPane mainLayout, GridPane centerGrid) {

		// adding center text boxes and labels vertically
		centerGrid.setPadding(new Insets(20, 0, 0, 50));
		centerGrid.setHgap(10);
		centerGrid.setVgap(10);

		// creating Frame heading
		Label dbConfig = new Label("Db Configuration");
		dbConfig.setPadding(new Insets(20, 0, 0, 48));
		mainLayout.setTop(dbConfig);

		// setting color for dbConfig label
		dbConfig.setStyle("-fx-text-fill: red; -fx-font-size: 30pt");

		// setting image to the form
		ImageView connImg = new ImageView(new Image("./img/img.png"));
		connImg.setFitWidth(70);
		connImg.setFitHeight(90);

		// adding labels and text boxes on the frames and button
		centerGrid.add(new Label("User Name"), 0, 1);
		centerGrid.add(userNameTxtFld, 2, 1);
		centerGrid.add(new Label("Password "), 0, 2);
		centerGrid.add(dbPasswordTxtFld, 2, 2);
		centerGrid.add(new Label("Address"), 0, 3);
		centerGrid.add(serverAddTxtFld, 2, 3);
		centerGrid.add(portNoTxtFld, 4, 3);
		portNoTxtFld.setPrefColumnCount(3);
		centerGrid.add(btnConnect, 4, 4);

		// adding a waring message label and image to the frame
		centerGrid.add(connImg, 3, 0);

		// modifying default behavior of the controls
		GridPane.setColumnSpan(connImg, 2);
		GridPane.setRowSpan(connImg, 3);

		// default text in the text boxes
		userNameTxtFld.setPromptText("Ex - root");
		dbPasswordTxtFld.setPromptText("Ex - root");
		serverAddTxtFld.setPromptText("Ex - 127.0.0.1");
		portNoTxtFld.setPromptText("Port No");

		portNoTxtFld.setTooltip(new Tooltip(
				"Port Number to access database server"));
		dbPasswordTxtFld.setTooltip(new Tooltip(
				"Password can be left empty, if not Available"));
		userNameTxtFld.setTooltip(new Tooltip("User Name of database Admin"));
		btnConnect.setTooltip(new Tooltip(
				"Click to Connect to database, and Access Admin Module"));

	}

	// limits port value input to only numeric values
	private void checkPortValue() {
		// TODO Auto-generated method stub
		portNoTxtFld.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable,
					String oldval, String newval) {
				if (!newval.matches("\\d*")) {
					// checking for last entered character in the textbox
					portNoTxtFld.setText(oldval);
					MyToast.createToast("*Only numeric values allowed");
				}
			}
		});
	}

	// checks the values in the textfields if they are valid connects to
	// database
	private void connectToDatabase() {
		btnConnect.setOnAction((ae) -> {
			boolean success = false;
			// retrieving all text box values
				serverAddress = serverAddTxtFld.getText();
				dbPassword = dbPasswordTxtFld.getText();
				portNo = portNoTxtFld.getText();
				userName = userNameTxtFld.getText();
				// if no fields are empty, connect to database
				if ((serverAddress.length() == 0) || (portNo.length() == 0)
						|| (userName.length() == 0)) {
					MyToast.createToast("Fields can't be left blank!");
				} else {
					System.out.println("connected");
					try {
						new DbConnection().connectToDb();
						success = true;
					} catch (Exception e) {
						System.out.println("1 admin ex : " + e);
					}
				}

				if (success) {
					(((Node) ae.getSource())).getScene().getWindow().hide();
					String[] arr = new String[6];
					AdminSetting.uiLauncher(arr, globalStg);
				}
			});
	}

	// this method creates two radio buttons and handles their events
	private void handleRdBtnEvents(GridPane centerGrid) {
		ToggleGroup rdBtnTg = new ToggleGroup();

		RadioButton sqlServer = new RadioButton("SQL Server");
		RadioButton orlServer = new RadioButton("Oracle Server");

		// setting toggle behaviour so that only one can be selected at once
		sqlServer.setToggleGroup(rdBtnTg);
		sqlServer.setSelected(true);
		orlServer.setToggleGroup(rdBtnTg);

		centerGrid.add(sqlServer, 0, 0);
		centerGrid.add(orlServer, 2, 0);

		// sets the database driver type and port no on selecting a particular
		// database type
		sqlServer.setOnAction((ae) -> {
			portNoTxtFld.setText("3306");
			dbType = "jdbc:mysql://";
		});
		orlServer.setOnAction((ae) -> {
			portNoTxtFld.setText("1521");
			dbType = "jdbc:oracle:thin://";
		});
	}
}
