

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.mail.internet.*;
import javax.mail.*;
import javax.activation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;  

public class EmployeeDBHandler {
   static final String JDBC_DRIVER="org.postgresql.Driver";
   static final String DB_URL="jdbc:postgresql://localhost:5432/employeedatabase";  //your db name

   static final String USER="postgres";
   static final String PASS="123";

   Connection conn;
   Statement st;
   KeyPair keypair;
   PrivateKey privateKey;
   PublicKey publicKey;
   EmployeeDBHandler()throws Exception{
      restoreKeys();
      connectionHandler();
      disconnectionHandler();
   }
   public void connectionHandler() {
      try {
         Class.forName(JDBC_DRIVER);
         this.conn = DriverManager.getConnection(DB_URL,USER,PASS);
         this.st=this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//          for(int i=1;i<=10;i++){
//             int x=100+i;
// //             String sql="DELETE FROM employees WHERE id='"+x+"'";
//             String sql="insert into employees (id,name,age,salary,address,dob,fathername,mothername,location,email,phone) values('"+x+"','Logesh',21,15000,'Namakkal','25-07-2000','Chandran','kaliyammal','namakkal','a"+x+"@gmail.com','9876543210')";
//             st.executeUpdate(sql);
//             String password="a"+x;
//             String encryptedPassword= passwordEncryption(password);
//             sql="insert into login (select max(sno),'"+encryptedPassword+"' from employees);";
//             // sql="insert into login values("+i+",'a"+x+"')";
//             st.executeUpdate(sql);
//          }
      }catch (Exception e){
         // e.printStackTrace();
         System.err.println("40"+e.getMessage());
      }
   }
   public void disconnectionHandler() {
      try {
         this.conn.close();
         this.st.close();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
      }
   }

   private void restoreKeys()throws Exception{
      this.privateKey=RSAAlgorithm.PrivateKeyReader();
      this.publicKey=RSAAlgorithm.PublicKeyReader();
   }

   private PrivateKey getPrivateKey(){
      return this.privateKey;
   }
   private PublicKey getPublicKey(){
      return this.publicKey;
   }
   private  String passwordEncryption(String password)throws Exception
   {
      PublicKey publicKey = getPublicKey();
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] encryptedbyte = cipher.doFinal(password.getBytes());
      return Base64.getEncoder().encodeToString(encryptedbyte);
   }
   private  String passwordDecryption(String password)throws Exception
   {
      // System.out.println(password);
      byte passwordByte[]=Base64.getDecoder().decode(password.getBytes());
      PrivateKey privateKey=getPrivateKey();
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      String decryptedString = new String(cipher.doFinal(passwordByte));
      return decryptedString;
   }


   public  JSONArray connvertResultSettoJSONArray(ResultSet resultSet){
      JSONArray  employeesDetails = new JSONArray ();
      String columns[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","PASSWORD","NUMBER"};

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      try{
         while ( resultSet.next() ) {
            JSONObject employeeDetails =new JSONObject();
            employeeDetails.put(columns[0],Integer.toString(resultSet.getInt("sno")));
            employeeDetails.put(columns[1],resultSet.getString("id"));
            employeeDetails.put(columns[2],resultSet.getString("name"));
            employeeDetails.put(columns[3],Integer.toString(resultSet.getInt("age")));
            employeeDetails.put(columns[4],(resultSet.getString("salary")));
            employeeDetails.put(columns[5],resultSet.getString("Address"));
            employeeDetails.put(columns[6],dateFormat.format(resultSet.getDate("Dob")));
            employeeDetails.put(columns[7],resultSet.getString("FatherName"));
            employeeDetails.put(columns[8],resultSet.getString("MotherName"));
            employeeDetails.put(columns[9],resultSet.getString("Location"));
            employeeDetails.put(columns[10],resultSet.getString("Email"));
            employeeDetails.put(columns[11],resultSet.getString("Password"));
            employeeDetails.put(columns[12],resultSet.getString("phone"));
            employeesDetails.add(employeeDetails);
         }
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return employeesDetails;
   }
   public String addNewEmployeeDetails(String query,String password){
      String updateStatement="";
      try{
         connectionHandler();
         String sql="insert into employees (id,name,age,salary,address,dob,fathername,mothername,location,email,phone) values("+query+")";
         st.executeUpdate(sql);
         String encryptedPassword= passwordEncryption(password);
         sql="insert into login (select max(sno),'"+encryptedPassword+"' from employees);";
         st.executeUpdate(sql);
         updateStatement="one row added";
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         updateStatement="add row Failed!!!";
       }
       return updateStatement;
   }
   public String findRole(int roleId){
      ResultSet resultSet=null;
      String userRole="";
      try{
         connectionHandler();
         resultSet = st.executeQuery("select * from roles where roleid="+roleId+";");
         while (resultSet.next() ) {
            userRole = resultSet.getString("role");
         }
         disconnectionHandler();         
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return userRole;
   }

   public String assignRole(String empId,int roleId){
      String updateStatement="";
      try{
         connectionHandler();
         String sql="insert into employeesRole (select sno,"+roleId+" from employees where id='"+empId+"');";
         st.executeUpdate(sql);
         updateStatement="assign Role!!!";
         disconnectionHandler();
      }catch (Exception e){
         updateStatement="assign Role Failed!!!";
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return updateStatement;
   }

   public  JSONArray getRoles(){
      JSONArray roles = new JSONArray();
      ResultSet rolesSet=null;
      try{
         connectionHandler();
         rolesSet = this.st.executeQuery("select * from roles");
         while ( rolesSet.next() ) {
            JSONObject role =new JSONObject();
            role.put("roleid",rolesSet.getString("roleid"));
            role.put("role",rolesSet.getString("role"));
            roles.add(role);
         }
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return roles;
   }

   public  JSONArray viewEmployeeDetails(int start,int end){
     JSONArray result = new JSONArray();
      ResultSet resultSet=null;
      try{
         connectionHandler();
         resultSet = this.st.executeQuery("select employees.sno,id,name,age,salary,address,dob,fathername,mothername,location,email,phone,password from employees,login where employees.sno=login.sno ORDER BY sno OFFSET "+start+"rows fetch next "+end+"rows only;");
         result = connvertResultSettoJSONArray(resultSet);
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return result;
   }
   public String updateEmployeeDetails(String query,String emId,String password){
      try{
      connectionHandler();
        String sql="Update employees set "+query+"WHERE id='"+emId+"'";
        st.executeUpdate(sql);
        String encryptedPassword= passwordEncryption(password);
        sql="Update login set password='"+encryptedPassword+"' where sno=(select sno from employees where id='"+emId+"')";
        st.executeUpdate(sql);
         disconnectionHandler();
         return "Updated Successfully";
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return "Updated Failed!!!";
       }
   }
   public String deleteEmployeeDetails(String emId){
      try{
        connectionHandler();
        String sql="DELETE FROM login WHERE sno=(select sno from employees where id='"+emId+"')";
          st.executeUpdate(sql);
          sql="DELETE FROM employeesrole WHERE sno=(select sno from employees where id='"+emId+"')";
          st.executeUpdate(sql);
          sql="DELETE FROM employees WHERE id='"+emId+"'";
          st.executeUpdate(sql);
          disconnectionHandler();
          return "Deleted Successfully";
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return "Deleted Failed!!!";
       }
   }
   public JSONArray searchEmployeeDetails(int start,int end,int type,String search){
      JSONArray employeeDetails = new JSONArray();
      ResultSet resultSet=null;
      try{
         connectionHandler();
         String databaseFetchStartQuery="select employees.sno,id,name,age,salary,address,dob,fathername,mothername,location,email,phone,password from employees,login where employees.sno=login.sno";
         if(type==1){
            resultSet = st.executeQuery(databaseFetchStartQuery+" and ('"+search+"' in (id,name,address,fathername,mothername,location,email,phone)) ORDER BY employees.sno OFFSET "+start+"rows fetch next "+end+"rows only;");
         }if(type==2){
            resultSet = st.executeQuery(databaseFetchStartQuery+" and ("+search+" in (employees.sno,age,salary)) ORDER BY employees.sno OFFSET "+start+"rows fetch next "+end+"rows only;");
         }if(type==3){
            resultSet = st.executeQuery(databaseFetchStartQuery+" and ('"+search+"' in (dob)) ORDER BY employees.sno OFFSET "+start+"rows fetch next "+end+"rows only;");
         }
         employeeDetails = connvertResultSettoJSONArray(resultSet);
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return employeeDetails;
   }

   public boolean userAuthentication(String email,String userPassword,int roleId){
      ResultSet rsValidation =null;
      boolean validation=false;
      try{
         connectionHandler();
         String sql="select password from login join employees on employees.sno=login.sno join employeesrole on employees.sno=employeesrole.sno where roleid="+roleId+" and email='"+email+"';";
         rsValidation = st.executeQuery(sql);
         while (rsValidation.next() ) {
            String password = rsValidation.getString("password");
            String decryptedPassword = passwordDecryption(password);
            if(decryptedPassword.compareTo(userPassword)==0){
               validation=true;
               break;
            }
         }
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return validation;
   }
   public boolean otpSend(String email){
      ResultSet rs =null;
      boolean validation=false;
      try{
         connectionHandler();
         String sql="select otplength,expirationtime from settings,employees where employees.sno=settings.sno and employees.email='"+email+"';";
         rs = st.executeQuery(sql);
         int otplength = 6,expirationtime=30;
         while (rs.next() ) {
            if(rs.getInt("otplength")!=0)
               otplength = rs.getInt("otplength");
            if(rs.getInt("expirationtime")!=0)
               expirationtime = rs.getInt("expirationtime");
         }
         // System.out.println(otplength);
         // System.out.println(expirationtime);
         Random random = new Random();
         int emailOtp = random.nextInt(((int)Math.pow(10,otplength))-1);
         String sub="Your One time Password!!!";
         String msg=" OTP : "+emailOtp+"\nvalid for "+expirationtime+" sec";
         JSONArray userEmail = new JSONArray();
         userEmail.add(email);
         sendMail(userEmail,sub,msg);
         sql="insert into otpstore (select sno,"+emailOtp+",CURRENT_TIMESTAMP+interval'"+expirationtime+" second' from employees where email='"+email+"');";
         st.executeUpdate(sql);

         validation=true;
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return validation;
   }

   public boolean otpAuthentication(String email,int otp){
      ResultSet rs =null;
      boolean validation=false;
      try{
         connectionHandler();
         String sql="select 'true' as verified from otpstore join employees on employees.sno=otpstore.sno where email='"+email+"' and otp="+otp+" and validityend>CURRENT_TIMESTAMP;";
         rs = st.executeQuery(sql);
         // System.out.println(sql);
         while (rs.next() ) {
            validation=true;
            break;
         }
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return validation;
   }

   public boolean changeOtpLength(String email,int length){
      ResultSet rs =null;
      boolean validation=false;
      try{
         connectionHandler();
         String sql="select sno from employees where email='"+email+"';";
         rs = st.executeQuery(sql);
         int sno=-1;
         while (rs.next() ) {
            sno = rs.getInt("sno");
         }
         sql="INSERT INTO settings(sno,otplength) values("+sno+","+length+") on CONFLICT(sno) DO  UPDATE set otplength="+length+";";
         st.executeUpdate(sql);
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return validation;
   }
   public boolean changeOtpExpirationTime(String email,int time){
      ResultSet rs =null;
      boolean validation=false;
      try{
         connectionHandler();
         String sql="select sno from employees where email='"+email+"';";
         rs = st.executeQuery(sql);
         String sno="";
         while (rs.next() ) {
            sno = rs.getString("sno");
         }
         sql="INSERT INTO settings(sno,expirationtime) values("+sno+","+time+") on CONFLICT(sno) DO  UPDATE set expirationtime="+time+";";
         st.executeUpdate(sql);
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return validation;
   }

   public  JSONArray viewUserDetails(String email){
     JSONArray userDetails = new JSONArray();
      ResultSet resultSetUserDetails=null;

      try{
         connectionHandler();
         resultSetUserDetails = this.st.executeQuery("select employees.sno,id,name,age,salary,address,dob,fathername,mothername,location,email,phone,password from employees,login where login.sno = (select employees.sno from employees where email='"+email+"') and (employees.sno=login.sno)");
         userDetails = connvertResultSettoJSONArray(resultSetUserDetails);
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return userDetails;
   }
   public String updateUserDetails(String query,String emailId){
      try{
      connectionHandler();
        //  String sql="Update login set password='"+password+"' where sno=(select sno from employees where email='"+emailId+"')";
        // st.executeUpdate(sql);
        String sql="Update employees set "+query+"WHERE email='"+emailId+"'";
        st.executeUpdate(sql);
         disconnectionHandler();
         return "Updated Successfully";
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return "Updated Failed!!!";
       }
   }
   public boolean updateUserPassword(int sno,int status,String adminEmailId){
      String passwordStatus="";
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      int alreadyUpdatedorNot=0;
      if(status==2){
         status=3;
      }
      try{
         connectionHandler();
         String sql="",userPassword="";
         ResultSet rs = st.executeQuery("select 'true' from passwordrequest where sno="+sno+" and status='2';");
         while (rs.next() ) {
            if(status==1){
               
               ResultSet rsUserPassword = st.executeQuery("select newpassword from passwordrequest where sno="+sno+" and status='2' order by requestedtime desc limit 1");
               while(rsUserPassword.next()){
                  userPassword=rsUserPassword.getString("newpassword");
               }
               sql="update login set password='"+userPassword+"' where sno="+sno+";";
                  st.executeUpdate(sql);
            }
            sql="update passwordrequest set status="+status+",approvedby=(select id from employees where email='"+adminEmailId+"') where sno="+sno+" and status='2';";
            st.executeUpdate(sql);
            alreadyUpdatedorNot=1;
            break;
         }
         if(alreadyUpdatedorNot==1){
            sql="select email from employees where sno="+sno+";";
            ResultSet rsUserEmails = st.executeQuery(sql);
            JSONArray userEmail = new JSONArray();
            while(rsUserEmails.next()){
               userEmail.add(rsUserEmails.getString("email"));
            }
            // userEmail.add("c.logesh2017@gmail.com");
            String sub="",msg="";
            if(status==1){
               sub="Your Password Updated!!!";
               msg="Password Updated By "+adminEmailId+"\nYour New Password :"+userPassword+"\n Date and Time :"+dateFormat.format(new Timestamp(System.currentTimeMillis()));
            }else{
               sub="Your Password Rejected!!!";
               msg="Password Rejected By "+adminEmailId+"\n Date and Time :"+dateFormat.format(new Timestamp(System.currentTimeMillis()));
            }
            sendMail(userEmail,sub,msg);
            disconnectionHandler();
         
            return true;
         }
         else{
            System.out.println("already Status Updated...");
            return false;
         }
         
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return false;
       }
       
   }
   public boolean requestChnagePassword(String emailId,String password){
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try{
      connectionHandler();
      //chnage
         String encryptedPassword= passwordEncryption(password);
         String sql="insert into passwordrequest (select sno,'"+encryptedPassword+"' from employees where email='"+emailId+"');";
         st.executeUpdate(sql);
         sql="select email from employees where sno in (select sno from employeesrole where roleid in (1,2));";
         ResultSet rsAdminEmails = st.executeQuery(sql);
         JSONArray adminEmails = new JSONArray();
         while(rsAdminEmails.next()){
            adminEmails.add(rsAdminEmails.getString("email"));
         }
         ResultSet rsUserSno = st.executeQuery(" select sno from employees where email='"+emailId+"';");
         String userSno="";
         while(rsUserSno.next()){
            userSno =rsUserSno.getString("sno");
         }
         // System.out.println(adminEmails);
         String msg=" User Sno : "+userSno+"\n User EmailId : "+emailId+"\n newpassword : "+encryptedPassword+"\n Date and Time :"+dateFormat.format(new Timestamp(System.currentTimeMillis()));
         String sub="Change password request";
         sendMail(adminEmails,sub,msg);
         disconnectionHandler();
         return true;
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return false;
       }
   }

   public  JSONArray getPasswordRequest(){
      JSONArray passwordRequest = new JSONArray();
      String statusArray[]={"Approved","pending","Rejected"};
      ResultSet requestSet=null;
      try{
         connectionHandler();
         requestSet = this.st.executeQuery("select * from passwordrequest");
         while ( requestSet.next() ) {
            JSONObject request =new JSONObject();
            request.put("sno",requestSet.getString("sno"));
            request.put("newpassword",requestSet.getString("newpassword"));
            int status = requestSet.getInt("status");
            // request.put("status",requestSet.getString("status"));
            request.put("status",statusArray[status-1]);
            passwordRequest.add(request);
         }
         disconnectionHandler();
      }catch (Exception e){
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
       }
       return passwordRequest;
   }

   public void updateModificationDetails(JSONArray jsonArrayUserDetails,File userDetailsUpdate)throws IOException{
      String columns[]={"ID","EMP ID","NAME","AGE","SALARY","ADDRESS","DOB","FATHERNAME","MOTHERNAME","LOCATION","EMAIL","PASSWORD","NUMBER",};
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
      String userDetails="";
      for (Object rowvalues : jsonArrayUserDetails) {
         JSONObject rowvalue = (JSONObject) rowvalues;
         for(String cloumn:columns) {
            String values = (String) rowvalue.get(cloumn);
            userDetails+=(String) rowvalue.get(cloumn)+" ";
         }
         userDetails+=dateFormat.format(new Timestamp(System.currentTimeMillis()))+" ";
         userDetails+=timeFormat.format(new Timestamp(System.currentTimeMillis()));

      }
      String userSno = userDetails.split(" ")[0];

      //file update

      BufferedReader updatedFile = new BufferedReader(new FileReader(userDetailsUpdate));
      // BufferedReader file = new BufferedReader(new FileReader(files.get(i)));
      StringBuffer inputBuffer = new StringBuffer();
      String line;
      while ((line = updatedFile.readLine()) != null) {
         String sno = line.split(" ")[0];
         if(sno.compareTo(userSno)!=0){
            inputBuffer.append(line);
            inputBuffer.append("\n");
         }
     }
     inputBuffer.append(userDetails);
     updatedFile.close();
     BufferedWriter updatedFileAppend=new BufferedWriter(new FileWriter(userDetailsUpdate));
     updatedFileAppend.write(inputBuffer.toString());
     updatedFileAppend.close();
   }

   public void sendMail(JSONArray adminEmails,String sub,String msg){
      String senderEmail = "c.logesh2017@gmail.com";
      String senderPassword="Logeshkc@12345";
      // String sub="Change password request";
      // String msg=" Sno: "+sno+"\n emailId: "+emailId+"\n newpassword: "+newpassword;
      Properties props = new Properties();    
       props.put("mail.smtp.host", "smtp.gmail.com");    
       props.put("mail.smtp.socketFactory.port", "465");    
       props.put("mail.smtp.socketFactory.class",    
               "javax.net.ssl.SSLSocketFactory");    
       props.put("mail.smtp.auth", "true");    
       props.put("mail.smtp.port", "465");    
       Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
            return new PasswordAuthentication(senderEmail,senderPassword);  
        }    
       });      
       try {    
        MimeMessage message = new MimeMessage(session);
        for(Object adminmail:adminEmails){
            String recipient=(String)adminmail;
           // message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
           InternetAddress[] address = {new InternetAddress(recipient)};  
           message.setRecipients(Message.RecipientType.TO, address);  
           message.setSubject(sub);    
           message.setText(msg);    
           //send message  
           Transport.send(message);
        }
        // System.out.println("message sent successfully");    
       } catch (MessagingException e) {
         throw new RuntimeException(e);
      }    
             
   }
}

