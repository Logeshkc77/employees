
import java.io.*;
import java.util.*;

import org.json.simple.*;
  
class EmployeeMain{

	public String getDetails(String input){
		Scanner getInput = new Scanner(System.in);
		System.out.println("Enter Employee "+input);
		String userValue=getInput.next();
		return userValue;
	}

	public JSONObject getEmployeeDetails(int user){
		JSONObject newEmployeeDetails=new JSONObject();
		if(user!=3)
			newEmployeeDetails.put("Id",getDetails("Id"));
		newEmployeeDetails.put("Name",getDetails("Name"));
		newEmployeeDetails.put("Age",getDetails("Age"));
		newEmployeeDetails.put("Salary",getDetails("Salary"));
		newEmployeeDetails.put("Address",getDetails("Address"));
		newEmployeeDetails.put("Dob",getDetails("Dob"));
		newEmployeeDetails.put("Fathername",getDetails("Father Name"));
		newEmployeeDetails.put("Mothername",getDetails("Mather Name"));
		newEmployeeDetails.put("Location",getDetails("Location"));
		newEmployeeDetails.put("Email",getDetails("Email"));
		if(user!=3)
			newEmployeeDetails.put("Password",getDetails("Password"));
		newEmployeeDetails.put("Phonenumber",getDetails("Phone number"));

      return newEmployeeDetails;
	}

	public String padding(){
		String columns[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","NUMBER"};
		String result="";
		for(String column:columns){
			result+=String.format("%-" + 12 + "s",column)+" ";
		}
		return result;
	}
	
	public List<Integer> navigationBar(int start,int end,int resultSize) {
		Scanner getInput = new Scanner(System.in);
		List<String> navigate=new ArrayList<String>();
		List<Integer> startEndValues = new ArrayList<Integer>();
		int navigateLength=0,ch=-1,exit=1;
	      if(resultSize>=10){
	        	navigate.add("Continue");
	        	navigateLength++;
	      }
	      if(start>0){
	        	navigate.add("Go back");
	        	navigateLength++;
	      }
	      navigate.add("Exit");
	      navigateLength++;
	      for(int i=0;i<navigateLength;i++){
	        	System.out.println(i+1+"."+navigate.get(i));
	      }
	      System.out.println("Choose one...");
	      ch=getInput.nextInt();
	      if(navigate.get(ch-1)=="Continue"){
	        	start=end;
	        	end=end+10;
	      }else if(navigate.get(ch-1)=="Go back"){
	        	end=start;
	        	start=start-10;
	      }else if(navigate.get(ch-1)=="Exit"){
	        	exit=0;
	        	startEndValues.add(exit);
	      }
	      startEndValues.add(start);
	      startEndValues.add(end);
	      
		return startEndValues;
	}
	public void ViewFileEmployeeDetails(JSONArray object) throws IOException {
		JSONArray printValues = new JSONArray();
		int start=0,end=10;
		while(true) {
			// System.out.println(object.get(0));
//			break;
			printValues=((EmployeeHandler)object.get(0)).viewEmployeeDetails(start,end);
			printEmployeeDetails(printValues);
			List<Integer> startEndValues = navigationBar(start,end,printValues.size());
			if(startEndValues.size()==3) {
				break;
			}
			start=startEndValues.get(0);
			end=startEndValues.get(1);
		}
	}
	
	public void ViewDBEmployeeDetails(EmployeeDBHandler employeeDBHandler){
		JSONArray printValues = new JSONArray();
		int start=0,end=10;
		while(true) {
			printValues=employeeDBHandler.viewEmployeeDetails(start,10);
			// System.out.println("sd"+printValues);
			printEmployeeDetails(printValues);
			List<Integer> startEndValues = navigationBar(start,end,printValues.size());
			if(startEndValues.size()==3) {
				break;
			}
			start=startEndValues.get(0);
			end=startEndValues.get(1);
		}
	}

	public void searchFileEmployeeDetails(JSONArray object,String search) throws IOException {
		JSONArray printValues = new JSONArray();
		int start=0,end=10;
		while(true) {
			System.out.println(object.get(0));
//			break;
			printValues=((EmployeeHandler) object.get(0)).searchEmployeeDetails(start,end,search);
			printEmployeeDetails(printValues);
			List<Integer> startEndValues = navigationBar(start,end,printValues.size());
			if(startEndValues.size()==3) {
				break;
			}
			start=startEndValues.get(0);
			end=startEndValues.get(1);
		}
	}

	public void searchDBEmployeeDetails(EmployeeDBHandler employeeDBHandler,int type,String search) throws IOException {
		JSONArray printValues = new JSONArray();
		int start=0,end=10;
		while(true) {
			//System.out.println(object.get(0));
//			break;
			printValues=employeeDBHandler.searchEmployeeDetails(start,10,type,search);
			printEmployeeDetails(printValues);
			List<Integer> startEndValues = navigationBar(start,end,printValues.size());
			if(startEndValues.size()==3) {
				break;
			}
			start=startEndValues.get(0);
			end=startEndValues.get(1);
		}
	}

	public void printAllroles(JSONArray roles){
		System.out.print("\n"+String.format("%-" + 8 + "s","roleId"));
		System.out.println(String.format("%-" + 8 + "s","role"));
		for (Object roleValues : roles) {
	        JSONObject role = (JSONObject) roleValues;
	        System.out.print(String.format("%-" + 8 + "s",role.get("roleid")));
			  System.out.println(String.format("%-" + 8 + "s",role.get("role")));
	    }
	}
	public void printPasswordRequest(JSONArray passwordRequests){
		Scanner getInput = new Scanner(System.in);
		System.out.print("\n"+String.format("%-" + 8 + "s","Sno"));
		System.out.print(String.format("%-" + 14 + "s","New Password"));
		System.out.println(String.format("%-" + 8 + "s","Status"));
		for (Object passwordRequest : passwordRequests) {
	        JSONObject password = (JSONObject) passwordRequest;
	        System.out.print(String.format("%-" + 8 + "s",password.get("sno")));
			  System.out.print(String.format("%-" + 14 + "s",password.get("newpassword")));
			  System.out.println(String.format("%-" + 8 + "s",password.get("status")));
	    }
	}
	public void printEmployeeDetails(JSONArray fileValues){
		System.out.println(padding());
		
		String columns[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","NUMBER"};
		for (Object rowvalues : fileValues) {
	        JSONObject rowvalue = (JSONObject) rowvalues;
	        String rows="";
	        for(String cloumn:columns) {
		        String values = (String) rowvalue.get(cloumn);
		        rows+=String.format("%-" + 12 + "s",values)+" ";
	        }
	        System.out.println(rows);
	    }
	}

	public void printUserUpdatedDetails(JSONArray userDetails){
		
		String columns[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","NUMBER","DATE","TIME"};
		
		for(String column:columns){
			System.out.print(String.format("%-" + 11 + "s",column));
		}
		System.out.println();
		for (Object rowvalues : userDetails) {
	        JSONObject rowvalue = (JSONObject) rowvalues;
	        String rows="";
	        for(String cloumn:columns) {
		        String values = (String) rowvalue.get(cloumn);
		        rows+=String.format("%-" + 11 + "s",values);
	        }
	        System.out.println(rows);
	    }
	}

	public static void main(String[] args)throws Exception {
		Scanner getInput = new Scanner(System.in);

		EmployeeMain employeeMain = new EmployeeMain();
		EmployeeBean employeeBean = new EmployeeBean();
		EmployeeDBHandler employeeDBHandler=new EmployeeDBHandler();
		EmployeeHandler employeeFileHandler = new EmployeeHandler();
		// employeeDBHandler.sendMail("c.logesh2017@gmail.com");
		File userUpdateDetails = new File("C:\\Users\\Logesh kc\\Desktop\\Zoho\\employee\\userUpdatedDetails.txt");
		while(true){
			System.out.println("1.Admin\n2.Operator\n3.User\n4.Exit");
			int userOptions=getInput.nextInt();
			if(userOptions==1){
				String adminEmailId,adminpassword;
				System.out.println("Enter your Email ID");
				adminEmailId=getInput.next();
				System.out.println("Enter your Password");
				adminpassword=getInput.next();
				if((adminEmailId.compareTo("admin")==0 && adminpassword.compareTo("admin")==0) || employeeDBHandler.userAuthentication(adminEmailId,adminpassword,1)==true){
					employeeDBHandler.otpSend(adminEmailId);
					System.out.println("Enter OTP:");
  					int operatorOtp=getInput.nextInt();
  					if(employeeDBHandler.otpAuthentication(adminEmailId,operatorOtp)==true){	
						System.out.println("Login Successfully !!!\n");
						System.out.println("1.File Handler\n2.DataBase Handler");
						int handler=getInput.nextInt();
						String updateStatement="";

						if(handler==1){
							final File folder=new File("C:\\Users\\Logesh kc\\Desktop\\Zoho\\employee");
							List<File> files=new ArrayList<File>();
							int fileCount=0,lastEmployeeId=-1,ch;
							for (final File fileEntry : folder.listFiles()) {
						        files.add(new File(fileEntry.getAbsolutePath()));
						        fileCount++;
							}
							employeeFileHandler = new EmployeeHandler(files,fileCount);

							System.out.println("\n1.Add\n2.view\n3.update\n4.Delete\n5.search\n0.logout\n");
							ch=getInput.nextInt();
							while(ch!=0){
								if(ch==1){
									if(lastEmployeeId==-1){
										employeeFileHandler.findLastEmployeeId(files);
									}
									employeeBean.setData(employeeMain.getEmployeeDetails(1));
									updateStatement = employeeFileHandler.addNewEmployeeDetails(employeeBean.getDatatoFile());
									System.out.println(updateStatement);
								}else if(ch==2){
									JSONArray object = new JSONArray();
									object.add(employeeFileHandler);
									employeeMain.ViewFileEmployeeDetails(object);
								}else if(ch==3){
									String emId;
									System.out.println("Enter Which Emp Id you Will update: ");
									emId=getInput.next();
									employeeBean.setData(employeeMain.getEmployeeDetails(1));
									updateStatement= employeeFileHandler.updateEmployeeDetails(employeeBean.getDatatoFile(),emId);
									System.out.println(updateStatement);
								}else if(ch==4){
									String emId;
									System.out.println("Enter Which Emp Id you Will Delete: ");
									emId=getInput.next();
									updateStatement = employeeFileHandler.deleteEmployeeDetails(emId);
									System.out.println(updateStatement);
								}else if(ch==5){
									String search;
									System.out.println("Enter Which value you Will Search: ");
									search=getInput.next();
									JSONArray object = new JSONArray();
									object.add(employeeFileHandler);
									//JSONObject fileValues = employeeFileHandler.searchEmployeeDetails(search);
									employeeMain.searchFileEmployeeDetails(object,search);
								}else if(ch==0){
									break;
								}else{
									System.out.println("choose currect Value!!\n");
								}
								System.out.println("\n1.Add\n2.view\n3.update\n4.Delete\n5.search\n0.Logout\n");
								ch=getInput.nextInt();
							}
						}else if(handler==2){
							 
							 String result;
							 
							 while(true){
							 	System.out.println("\n1.Add\n2.Assign role\n3.view\n4.update\n5.Delete\n6.search\n7.view user modification audit\n8.Approve password request\n0.Logout\n");
							 	int ch=getInput.nextInt();
					         try {
					            if(ch==1){
					            	employeeBean.setData(employeeMain.getEmployeeDetails(1));
					            	updateStatement = employeeDBHandler.addNewEmployeeDetails(employeeBean.getDatatoAdd(),employeeBean.getPassword());
					            	System.out.println(updateStatement);
					               //result = employeeDBHandler.addNewEmployeeDetails(employeeMain.getEmployeeDetails());
					            	//System.out.println(result);
					            }else if(ch==2){
										String empID;
					               System.out.println("Enter Which Emp Id you Will add role: ");
					               empID=getInput.next();
					               employeeMain.printAllroles(employeeDBHandler.getRoles());
					               System.out.println("Enter Which Role Id you Will assign: ");
					               int roleid=getInput.nextInt();
					               employeeDBHandler.assignRole(empID,roleid);
					            }else if(ch==3){
										employeeMain.ViewDBEmployeeDetails(employeeDBHandler);
					            }else if(ch==4){
					               String emId;
					               System.out.println("Enter Which Emp Id you Will update: ");
					               emId=getInput.next();
					               employeeBean.setData(employeeMain.getEmployeeDetails(1));
					               updateStatement = employeeDBHandler.updateEmployeeDetails(employeeBean.getDatatoUpdate(1),emId,employeeBean.getPassword());
					               System.out.println(updateStatement);
					               //employeeDBHandler.updateEmployeeDetails(employeeMain.getEmployeeDetails(),emId);
					            }else if(ch==5){
					               String emId;
					               System.out.println("Enter Which Emp Id you Will Delete: ");
					               emId=getInput.next();
					               updateStatement = employeeDBHandler.deleteEmployeeDetails(emId);
					               System.out.println(updateStatement);
					            }else if(ch==6){
					               String search;
										int type;
										System.out.println("Enter Which column you Will Search: ");
										System.out.println("1.id,name,address,fathername,mothername,location,email,phone");
										System.out.println("2.sno,age,salary");
										System.out.println("3.Dob");
										type=getInput.nextInt();
										System.out.println("Enter Which value you Will Search: ");
										search=getInput.next();
										employeeMain.searchDBEmployeeDetails(employeeDBHandler,type,search);
					            }else if(ch==7){
					            	employeeMain.printUserUpdatedDetails(employeeFileHandler.viewUpdateUserDetails(userUpdateDetails));
					            }else if(ch==8){
					            	employeeMain.printPasswordRequest(employeeDBHandler.getPasswordRequest());
					            	int sno,passwordStatus;
									   System.out.println("Enter Sno Which User Password Updated");
									   sno=getInput.nextInt();
									   System.out.println("User Password Approve/Reject");
									   System.out.println("1.approved\n2.Rejected");
									   passwordStatus=getInput.nextInt();
									   boolean updatestatement = employeeDBHandler.updateUserPassword(sno,passwordStatus,adminEmailId);
					            	if(updatestatement==true){
					            		System.out.println("Update password Successfully");
					            	}else{
					            		System.out.println("Failed..");
					            	}
					            }else if(ch==0){
										break;
									}else{
					               System.out.println("choose currect Value!!\n");
					            }
					         } catch (Exception e) {
					            e.printStackTrace();
					            System.err.println(e.getClass().getName()+": "+e.getMessage());
					            System.exit(0);
					         }
					         // System.out.println("\n1.Add\n2.Assign role\n3.view\n4.update\n5.Delete\n6.search\n7.view user modification audit\n0.Logout\n");
					         // ch=getInput.nextInt();
					      }
					   }
					}else{
						System.out.println("OTP Invalid!!!");
					}
				}else{
			   	System.out.println("Password Invalid!!!");
			   }
			}else if(userOptions == 2){
				String operatorEmailId,operatorpassword;
				System.out.println("Enter your Email ID");
				operatorEmailId=getInput.next();
				System.out.println("Enter your Password");
				operatorpassword=getInput.next();

				if(employeeDBHandler.userAuthentication(operatorEmailId,operatorpassword,2)==true){
					employeeDBHandler.otpSend(operatorEmailId);
					System.out.println("Enter OTP:");
  					int operatorOtp=getInput.nextInt();
  					if(employeeDBHandler.otpAuthentication(operatorEmailId,operatorOtp)==true){
						System.out.println("Login Successfully !!!\n");
						String updateStatement="";
						while(true){
							System.out.println("\n1.View\n2.view user modification audit\n3.approve password request\n0.logout\n");
							int ch=getInput.nextInt();
							if(ch==1){
								JSONArray operatorDetails = employeeDBHandler.viewUserDetails(operatorEmailId);
								employeeMain.printEmployeeDetails(operatorDetails);
							}else if(ch==2){
								employeeMain.printUserUpdatedDetails(employeeFileHandler.viewUpdateUserDetails(userUpdateDetails));
							}else if(ch==3){
								employeeMain.printPasswordRequest(employeeDBHandler.getPasswordRequest());
			            	int sno,passwordStatus;
							   System.out.println("Enter Sno Which User Password Updated");
							   sno=getInput.nextInt();
							   System.out.println("User Password Approve/Reject");
							   System.out.println("1.approved\n2.Rejected");
							   passwordStatus=getInput.nextInt();
							   boolean updatestatement = employeeDBHandler.updateUserPassword(sno,passwordStatus,operatorEmailId);
			            	if(updatestatement==true){
			            		System.out.println("Update password Successfully");
			            	}else{
			            		System.out.println("Update password Failed..");
			            	}
							}else if(ch==0){
								break;
							}else{
								System.out.println("choose currect Value!!\n");
							}
						}
					}else{
						System.out.println("OTP Invalid!!!");
					}
				}else{
					System.out.println("Password Invalid!!!");
				}
			}else if(userOptions == 3){
				String userEmailId,userpassword;
				System.out.println("Enter your Email ID");
				userEmailId=getInput.next();
				System.out.println("Enter your Password");
				userpassword=getInput.next();

				if(employeeDBHandler.userAuthentication(userEmailId,userpassword,3)==true){
					employeeDBHandler.otpSend(userEmailId);
					System.out.println("Enter OTP:");
  					int userOtp=getInput.nextInt();
  					if(employeeDBHandler.otpAuthentication(userEmailId,userOtp)==true){
						System.out.println("Login Successfully !!!\n");
						String updateStatement="";
						while(true){
							System.out.println("\n1.View\n2.update\n3.change password\n4.Settings\n0.logout\n");
							int ch=getInput.nextInt();
							if(ch==1){
								JSONArray userDetails = employeeDBHandler.viewUserDetails(userEmailId);
								employeeMain.printEmployeeDetails(userDetails);
							}else if(ch==2){
								employeeBean.setData(employeeMain.getEmployeeDetails(3));
								updateStatement = employeeDBHandler.updateUserDetails(employeeBean.getDatatoUpdate(3),userEmailId);
						      System.out.println(updateStatement);
						      userEmailId=employeeBean.getEmailid();
						      JSONArray userDetails = employeeDBHandler.viewUserDetails(userEmailId);
								employeeDBHandler.updateModificationDetails(userDetails,userUpdateDetails);
								
							}else if(ch==3){
								boolean updatestatement = employeeDBHandler.requestChnagePassword(userEmailId,employeeMain.getDetails("Password"));
								if(updatestatement==true){
			            		System.out.println("request password Successfully");
			            	}else{
			            		System.out.println("request password Failed..");
			            	}
							}else if(ch==4){
								System.out.println("\n1.Change OTP length\n2.Change OTP Expiration Time");
								ch=getInput.nextInt();
								if(ch==1){
									while(true){
										System.out.println("Enter OTP length(greater than or equal 6):");
										int length=getInput.nextInt();
										if(length>=6){
											employeeDBHandler.changeOtpLength(userEmailId,length);
											break;
										}else{
											System.out.println("Invalid OTP length!!!");
										}
									}
								}else if(ch==2){
									while(true){
										System.out.println("Enter OTP Expiration Time(greater than or equal 30 sec):");
										int time=getInput.nextInt();
										if(time>=30){
											employeeDBHandler.changeOtpExpirationTime(userEmailId,time);
											break;
										}else{
											System.out.println("Invalid OTP Expiration Time!!!");
										}
									}
								}
							}else if(ch==0){
								break;
							}else{
								System.out.println("choose currect Value!!\n");
							}
						}
					}else{
						System.out.println("OTP Invalid!!!");
					}
				}else{
					System.out.println("Password Invalid!!!");
				}
			}else if(userOptions==4){
				break;
			}
		}
	}
}
