
import org.json.simple.JSONObject;

class EmployeeBean implements java.io.Serializable{

	private String id,name,age,salary,address,dob,fatherName,motherName,
					location,email,password,phone;

	public EmployeeBean(){

	}
	public void setData(JSONObject newEmployee){
		this.id=(String) newEmployee.get("Id");
		this.name=(String) newEmployee.get("Name");
		this.age=(String) newEmployee.get("Age");
		this.salary=(String) newEmployee.get("Salary");
		this.address=(String) newEmployee.get("Address");
		this.dob=(String) newEmployee.get("Dob");
		this.fatherName=(String) newEmployee.get("Fathername");
		this.motherName=(String) newEmployee.get("Mothername");
		this.location=(String) newEmployee.get("Location");
		this.email=(String) newEmployee.get("Email");
		this.password=(String) newEmployee.get("Password");
		this.phone=(String) newEmployee.get("Phonenumber");
	}
	public String getDatatoFile(){
		String result="";
		result+=this.id+" "+
				this.name+" "+
				this.age+" "+
				this.salary+" "+
				this.address+" "+
				this.dob+" "+
				this.fatherName+" "+
				this.motherName+" "+
				this.location+" "+
				this.email+" "+
				this.password+" "+
				this.phone+"\n";
		return result;
	}
	public String getDatatoAdd(){
		String result="";
		result+="'"+this.id+"'"+","+
				"'"+this.name+"'"+","+
				this.age+","+
				this.salary+","+
				"'"+this.address+"'"+","+
				"'"+this.dob+"'"+","+
				"'"+this.fatherName+"'"+","+
				"'"+this.motherName+"'"+","+
				"'"+this.location+"'"+","+
				"'"+this.email+"'"+","+
				"'"+this.phone+"'";
		//System.out.println(result);
		return result;
	}
	public String getDatatoUpdate(int user){
		String result="";
		if(user!=3)
			result+="id="+this.id+",";
		result+="name="+"'"+this.name+"'"+","+
				"age="+this.age+","+
				"salary="+this.salary+","+
				"address="+"'"+this.address+"'"+","+
				"dob="+"'"+this.dob+"'"+","+
				"fathername="+"'"+this.fatherName+"'"+","+
				"mothername="+"'"+this.motherName+"'"+","+
				"location="+"'"+this.location+"'"+","+
				"email="+"'"+this.email+"'"+","+
				"phone="+"'"+this.phone+"'";
		//System.out.println(result);
		return result;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return this.password;
	}
	public String getEmailid() {
		return this.email;
	}
}
