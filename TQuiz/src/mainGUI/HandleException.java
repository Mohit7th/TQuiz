package mainGUI;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Mohit Uniyal This is the universal exception handling class it will
 *         handle all the exception that might be generated during program
 *         execution Date : 2-8-2015, 8:42am
 */
public class HandleException {

	final static int WRONG_PATH = 1, WRONG_PORT = 2, WRONG_UNAME = 3,
			WRONG_PASSWD = 4, SERVER_OFFLINE = 5, NO_PRINTER=11, NO_FILE_SELECTED = 12;
	String warningString = "";

	Alert errAlertBx, warnAlertBx, infoAlertBx;

	HandleException() {
		errAlertBx = new Alert(AlertType.ERROR);
		errAlertBx.setTitle("Error Dialog");
		warnAlertBx = new Alert(AlertType.WARNING);
		warnAlertBx.setTitle("Error Dialog");
		infoAlertBx = new Alert(AlertType.INFORMATION);
		infoAlertBx.setTitle("Error Dialog");
		
	}

	HandleException(String errMsg){
		warnAlertBx = new Alert(AlertType.WARNING);
		warnAlertBx.setTitle("Error Dialog");
		warnAlertBx.setHeaderText("Printer Error");
		warningString = "Printer is not connected, Please Connect a printer and then Retry";
		warnAlertBx.setContentText(warningString);
		warnAlertBx.showAndWait();
	}
	// method to display prompt messages
	static public void displayPrompt(int exNo) {
			new HandleException().setWarningString(exNo);
	}

	// this method will set all the warning message string that will be
	// displayed on the prompt
	public void setWarningString(int exNo) {
		switch (exNo) {
		case WRONG_PATH:
			warningString = "Entered Server Address is not valid!";
			break;
		case WRONG_PORT:
			warningString = "Entered Port No is not Valid";
			break;
		case WRONG_UNAME:
			warningString = "Entered User Name is not Valid";
			break;
		case WRONG_PASSWD:
			warningString = "Entered Password is not valid";
			break;
		case SERVER_OFFLINE:
			warningString = "Server is offline, Unable to connect to Server";
			break;
		case NO_PRINTER:
			warningString = "Printer is not connected, Please Connect a printer and then Retry";
			break;
		case NO_FILE_SELECTED:
			warningString = "Please Select a file, Then CLick Save button";
			break;
		}

		String headTxtWarnMsg = "";
		if(exNo == NO_FILE_SELECTED){
			headTxtWarnMsg = "No File Selected";
			infoAlertBx.setHeaderText(headTxtWarnMsg);
			infoAlertBx.setContentText(warningString);
			infoAlertBx.showAndWait();
		}else if(exNo == NO_PRINTER){
			headTxtWarnMsg = "Printer Problem";
			warnAlertBx.setHeaderText(headTxtWarnMsg);
			warnAlertBx.setContentText(warningString);
			warnAlertBx.showAndWait();
		}else{
			//setting message to be displayed on the alert box and then display alert
			headTxtWarnMsg = "Server Connectivity Failed!";
			errAlertBx.setHeaderText(headTxtWarnMsg);
			errAlertBx.setContentText(warningString);
			errAlertBx.showAndWait();
		}
	}
}
