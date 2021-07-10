
import java.io.*;
import java.util.*;
import org.json.simple.*;

class EmployeeHandler{
	List<File> files=new ArrayList<File>();
	int fileCount,lastEmployeeId=-1;
	// Scanner getInput = new Scanner(System.in);
	public EmployeeHandler(List<File> files,int fileCount){
		this.files=files;
		this.fileCount=fileCount;
	}
	public EmployeeHandler(){
	}
	public void findLastEmployeeId(List<File> files)throws IOException{
		int employeeid=-1;
		for (int i = 0; i < files.size(); i++) {
			BufferedReader readFile = new BufferedReader(new FileReader(files.get(i)));
	        String line,words[];
			while ((line = readFile.readLine()) != null){
			    words=line.split(" "); 
		        if( employeeid < Integer.parseInt(words[0])){
		        	employeeid=Integer.parseInt(words[0]);
		        }
			}
        }
        this.lastEmployeeId=employeeid;
	}
	public int employeeIdAlreadyExistorNot(String emId)throws IOException{
		for (int i = 0; i < this.files.size(); i++) {
			BufferedReader file = new BufferedReader(new FileReader(files.get(i)));
	        String line;
	        while ((line = file.readLine()) != null) {
	        	String word = line.split(" ")[1];
	           	if(word.compareTo(emId)==0){
	           		return 1;
	           	}
	        }
	    }
	    return 0;
	}
	public JSONArray sortArray(List<String> fileValues,int size,int start,int end){
		for(int i = 0; i<size-1; i++)   
		{  
			for (int j = i+1; j<size; j++)   
			{  
				Integer first = Integer.parseInt(fileValues.get(i).split(" ")[0]);
                Integer secend = Integer.parseInt(fileValues.get(j).split(" ")[0]);
				if(first.compareTo(secend)>0)    // 
				{  
					String tmp = fileValues.get(i);
		            fileValues.set(i,fileValues.get(j));
					fileValues.set(j,tmp); 
				}  
			}  
		}
		
		JSONArray  JSONValue = new JSONArray ();
		String titles[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","PASSWORD","NUMBER"};
		end=end>fileValues.size()?fileValues.size():end;
		for(int i=start;i<end;i++){
        	String words[] = fileValues.get(i).split(" ");
        	int j=0;
        	JSONObject temp = new JSONObject();
        	for(String word:words){
        		temp.put(titles[j++],word);
        	}
        	JSONValue.add(temp);
        }
		//System.out.print(JSONValue);    
		return JSONValue;
	}
	public String addNewEmployeeDetails(String newEmployee)throws IOException{
		BufferedWriter append=new BufferedWriter(new FileWriter(files.get(0),true));
		int count=-1;
		for (int i = 0; i < this.files.size(); i++) {
			count=0;
            BufferedReader read = new BufferedReader(new FileReader(files.get(i)));
            //System.out.println(files.get(i));
            String mystring;
            while ((mystring = read.readLine()) != null) {
                count++;
            }
            if(count!=5){
            	append = new BufferedWriter(new FileWriter(files.get(i),true));
            	break;
            }
        }
        //System.out.println(count);
        if(count==5){
        	++this.fileCount;
        	append = new BufferedWriter(new FileWriter
        		("C:\\Users\\Logesh kc\\Desktop\\Zoho\\.\\employee\\employeeDetails"+this.fileCount+".txt",true));
        	this.files.add(new File("C:\\Users\\Logesh kc\\Desktop\\Zoho\\.\\employee\\employeeDetails"+this.fileCount+".txt"));
        }
        if(employeeIdAlreadyExistorNot(newEmployee.split(" ")[0])==0){
	        append.write(++this.lastEmployeeId+" ");
	        append.write(newEmployee);
	    }else{
	    	return "Add Failed ... Emp ID already Used!!!";
	    }
        append.close();
        return "Added Successfully";
	}
	public JSONArray viewEmployeeDetails(int start,int end)throws IOException{
		
		List<String> fileValues=new ArrayList<String>();
		int len=0;
		for (int i = 0; i < this.files.size(); i++) {
			BufferedReader read = new BufferedReader(new FileReader(files.get(i)));
			String line;
            while ((line = read.readLine()) != null) {
            	fileValues.add(line);
                len++;
            }
        }
		JSONArray currentValue = new JSONArray();
		currentValue= sortArray(fileValues,len,start,end);
//		System.out.println(currentValue.size());
		return currentValue;
        //return fileValues;
	}
	public JSONArray viewUpdateUserDetails(File updatedFilePath)throws IOException{
		
		List<String> listUpdatedUserDetails=new ArrayList<String>();
		int len=0;
		BufferedReader updatedUser = new BufferedReader(new FileReader(updatedFilePath));
		String line;
        while ((line = updatedUser.readLine()) != null) {
        	listUpdatedUserDetails.add(line);
            len++;
        }
        
		JSONArray jsonUpdatedUserDetails = new JSONArray();
		String columns[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","PASSWORD","NUMBER","DATE","TIME"};
		for(int i=0;i<len;i++){
        	String userInfo[] = listUpdatedUserDetails.get(i).split(" ");
        	int j=0;
        	JSONObject userDetails = new JSONObject();
        	for(String user:userInfo){
        		userDetails.put(columns[j++],user);
        	}
        	jsonUpdatedUserDetails.add(userDetails);
        }
        // System.out.println(jsonUpdatedUserDetails);
		return jsonUpdatedUserDetails ;
	}
	public String updateEmployeeDetails(String updateEmployee,String emId)throws IOException{
		int count=0;
		if((updateEmployee.split(" ")[0].compareTo(emId)==0) || (employeeIdAlreadyExistorNot(updateEmployee.split(" ")[0])==0)){
			for (int i = 0; i < this.files.size(); i++) {
				BufferedReader file = new BufferedReader(new FileReader(files.get(i)));
		        StringBuffer inputBuffer = new StringBuffer();
		        String line;
		        while ((line = file.readLine()) != null) {
		        	String word = line.split(" ")[1];
		           	if(word.compareTo(emId)==0){
		           		inputBuffer.append(line.split(" ")[0]+" ");
		           		// inputBuffer.append(getEmployeeDetails());
		           		inputBuffer.append(updateEmployee);
		           		count=1;
		           	}else{
		           		inputBuffer.append(line);
		           		inputBuffer.append("\n");
		           	}
		        }
		        file.close();
		        BufferedWriter append=new BufferedWriter(new FileWriter(files.get(i)));
		        append.write(inputBuffer.toString());
		        append.close();
		        if(count==1){
		        	break;
		        }
		    }
	    }else{
	    	return "Update Failed ... Emp ID already Used!!!";
	    }
		return "Updated Successfully";
	}
	public String deleteEmployeeDetails(String emId)throws IOException{
	
		for (int i = 0; i < this.files.size(); i++) {
			BufferedReader file = new BufferedReader(new FileReader(files.get(i)));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        while ((line = file.readLine()) != null) {
	        	String word = line.split(" ")[1];
	           	if(word.compareTo(emId)!=0){
	           		inputBuffer.append(line);
	           		inputBuffer.append("\n");
	           	}
	        }
	        file.close();
	        BufferedWriter append=new BufferedWriter(new FileWriter(files.get(i)));
	        append.write(inputBuffer.toString());
	        append.close();
	    }
		return "Deleted Successfully";
	}
	public JSONArray searchEmployeeDetails(int start,int end,String search)throws IOException{
		List<String> fileValues=new ArrayList<String>();
		int len=0;
		for (int i = 0; i < this.files.size(); i++) {
			BufferedReader read = new BufferedReader(new FileReader(files.get(i)));
			String line;
	        while ((line = read.readLine()) != null) {
	        	String words[] = line.split(" ");
	        	for(String word:words){
	        		if(word.compareTo(search)==0){
	        			fileValues.add(line);
	            		len++;
	        			break;
	        		}
	        	}
	            
	        }
	    }
		JSONArray currentValue = sortArray(fileValues,len,start,end);
	    return currentValue;
	}
}
