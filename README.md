# TrainingRepository
This repository contains several training projects, one of which is a web application named certificationCenter. 
This is a business card site for a certification center. The main purpose of this application is to work with client applications 
to confirm the conformity of products with the requirements of technical regulations.
User on behalf of a particular organisation should authorize for working with site. User with role of the client can apply for, edit 
or delete an application with a set of documents. The client can view the list of submitted by him applications and see the status 
of work on them. He can also view a certain application and download attachments.
User with role of the expert can view the list of the all applications, register them and set the status of work. Also he can view a 
certain application and download attachments.

If you need to deploy a web application on a Tomcat server, you need to create an XML file in the server directory: 
/${apache-tomcat location}/conf/Catalina/localhost/certificationCenter.xml
This file should contain the following:
<Context docBase="${Path to the compiled and built project}/certificationCenter/target/certificationCenter-1.0-SNAPSHOT" path="" reloadable="true" > 
        <Resource
            name="jdbc/certificationCenter"
            author="Container"
            type="javax.sql.DataSource"
            maxActive="10"
            maxIdle="4"
            maxWait="10000"
            username="root"
            password="Mysql:root"
            driverClassName="com.mysql.cj.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/certificationCenter"
    />
</Context>
