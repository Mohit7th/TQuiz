package mainGUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Result {
	private StringProperty userId, course, name, marks;
	
	public Result(String userId, String marks){
		this.userId = new SimpleStringProperty (userId);
		//this.course = new SimpleStringProperty (course);
		//this.name = new SimpleStringProperty (name);
		this.marks = new SimpleStringProperty (marks);
		
	}
	
	//userId property
	public void setUserId(String uid){
		userId.set(uid);
	}
	public String getUserId(){
		return userId.get();
	}
	
	//course property
	public void setCourse(String c){
		course.set(c);
	}
	public String getCourse(){
		return course.get();
	}
	
	//name property
	public void setName(String n){
		name.set(n);
	}
	public String getName(){
		return name.get();
	}
	
	//marks property
	public void setMarks(String m){
		marks.set(m);
	}
	public String getMarks(){
		return marks.get();
	}

}
