# RichFaces - Ajax enabled JSF 2.0 component library
    
* RichFaces 4.3.2-SNAPSHOT SHOWCASE
* <http://richfaces.org>
* March 2011
* This software is distributed under the terms of the FSF Lesser Gnu Public License (see lgpl.txt)
    
RichFaces showcase is an application created to show **RichFaces components
in action.** It contains a set of small use-cases implemented using RichFaces components.
    
## Mobile compatibility
    
This showcase is also mobile compatible with *WebKit* based browsers; this
includes *iOS*, *Android*, and Others. After you deploy the showcase, just
browse to the default context path (for example, <http://localhost:8080/showcase>) with a mobile iOS or Android based device.
    
You will see the showcase has been modified to fit and **dynamically** adjust to mobile screen widths and orientations. 
    
We removed some components from the RichFaces Mobile Showcase which were not mobile ready. For example, ``rich:tooltip``, ``rich:extendedDataTable``, ``rich:jquery``, and ``rich:popupPanel`` either did not make sense in a mobile environment or needed a heavy rewrite for touch interfaces.
    
``rich:dragDrop`` however, does work on iOS Mobile Safari, but not on Android. To use rich:dragDrop in Mobile Safari browsers, you can include this snippet of [JavaScript](https://github.com/richfaces/components/blob/develop/mobile-compatibility/rf-dnd.js) at the bottom of your JSF template.
    
The examples shown in the application were chosen from the most popular
questions at the user forum. You will find a lot of useful information on 
how to implement RichFaces components and **re-use** the code to implement the 
components in your own application.
    
This file explains how to build and execute the showcase application on
your own server.
    
    
## Building and running the application
    
### 1. Requirements
    
In order to build the Showcase application you will need:
    
* Maven 3.0.3 or later
* Servers: Apache Tomcat 6.0 or any JEE6 application server (e.g. JBoss AS 7)
* JDK 1.6
    
Also optional Additional Software would make it easy for you to work with the sources of the application:
    
* *Eclipse IDE* + *JBoss Tools* (to explore and run the application in IDE). You're obviously free to use any other IDE you wish, but JBoss Tools is recommended.
    
### 2. Build/Deploy
#### Deploying on Tomcat
To build the project for Tomcat you need to navigate to the ``/examples/richfaces-showcase`` and run:
    
``mvn clean package``
    
When you see the *BUILD SUCCESSFUL* message you can deploy the 
application on the server. To deploy it on Tomcat, copy the *.war* 
file from 'target' folder to ``TOMCAT_HOME/webapps`` folder. Then, launch the *startup.sh* or *startup.bat* script from ``TOMCAT_HOME/bin/`` directory to start the server.
    
#### Deploying on JEE6 server (JBoss AS 6)
    
To build the project for a JEE6 server you need to navigate to the ``/examples/richfaces-showcase`` and run:
    
``mvn clean package -Pjee6``
    
When you see the *BUILD SUCCESSFUL* message you can deploy the application on the server. To deploy it on the application server copy the *.war* file from 'target' folder to the ``JBOSS_HOME/server/default/deploy`` folder (or change "default/" to used profile folder). Then, launch the *run.sh* or run*.bat* script from ``JBOSS_HOME/bin/`` directory to start the server.
    
#### Deploying on JBoss AS 7
    
To build the project for a JEE6 server you need to navigate to the ``/examples/richfaces-showcase`` and run
    
* for JBoss AS 7.0.x:
    
``mvn clean package -Pjbas7``
    
* or for JBoss AS 7.1.x
    
``mvn clean package -Pjbas71``
    
When you see the *BUILD SUCCESSFUL* message you can deploy the application on the server.
    
First, make sure the application server is running.  To start the server:
launch the *standalone.sh* or *standalone.bat* script from ``JBOSS_HOME/bin/`` together with parameter ``--server-config=standalone-full.xml`` for both latest JBoss AS 7.1.x and for latest JBoss AS 7.0.x.
    
As of *JBoss AS 7.1* you must create the **JMS user account/password** to enable the JMS push sample.  Add a new user to the ApplicationRealm with the ``$JBOSS_HOME/bin/add-user.bat`` or ``add-user.sh`` script, with the:
            
            username: guest
            password: password
                role: guest
    
To **deploy** it on the application server, use either:
    
1. Use the jboss-as-maven-plugin to deploy to a running application server:
``mvn jboss-as:deploy -Pjbas7``
2. Copy the *.war* file from 'target' folder to the folder: ``JBOSS_HOME/standalone/deployments``
    
After deploying the examples to your server open a browser and type 
<http://localhost:8080/richfaces-showcase> to view the examples.
    
#### Publishing to Google Application Engine (GAE)
    
We have completed a resource plugin which generates static resources allowing us to create Google App Engine compatible applications using RichFaces.  This means deploying to and working with GAE is a piece of cake. Here is how you can deploy your own RichFaces showcase example:
    
1. Update the GAE application name, and register it with google. Do this by updating the ``/webapp-gae/WEB-INF/appengine-web.xml`` following the GAE standard documentation.
    
2. if using MyFaces: Configure the MyFaces encryption secret values in the ``/webapp-gae/WEB-INF/web.xml``. Replace the "-- Insert some secret here --" with a base64 encoded password. This is important because otherwise MyFaces will generate random keys, and this causes issues in a cluster environment like GAE. 
    
3. For more information on JSF encryption check: [MyFaces](http://wiki.apache.org/myfaces/Secure_Your_Applicatio) or [Mojarra](http://wikis.sun.com/display/GlassFish/JavaServerFacesRI#JavaServerFacesRI-HowcanIsecureviewstatewhenusingclientsidestatesaving%3F)
              
4. Build using: ``mvn clean package -Pgae``
    
5. Use GAE SDK and the appcfg script just as you would for any other GAE application.
    
6. There are some additional changes that were made in order to make richfaces-showcase GAE compatible:
    * WebConfiguration class from ``com.sun.faces`` were patched removing code which is not compatible with GAE 
    * *web.xml* with additional properties for GAE created and placed at ``src\main\webapp-gae\WEB-INF\`` (it replaces common one during build with GAE profile)
    * check *pom.xml* GAE profile section in order to check additional dependencies for that configuration
    
    
### 3. Setting up Eclipse to work with the showcase
    
In order to explore, run and deploy the Showcase using Eclipse IDE you can use one of the following options:
    
1. You could use **JBoss Tools** with *m2eclipse plugin* and just import 
the examples as maven-based projects.
    * Open Eclipse and go to *File* > *Import*
    * Expand the project type named Maven and select Import Maven Project
    * Browse the directory in which you placed the examples, select it and press OK.
    * Press next on the import wizard to finish importing the examples.
2. Or if you are not using *m2eclipse* execute use Maven to convert the 
project to an Eclipse project:
    * Open a terminal inside the RichFaces examples directory
    * execute ``mvn eclipse:ecllipse``
    * Open Eclipse and select *File* > *Import*
    * Select Import as existing project into workspace after build is complete.
    * Follow the instructions on the import wizard.
    
**You are now able to work with the Showcase within Eclipse.**
    
* In order to deploy the Showcase on the JBoss AS 7.1.x from the Eclipse one needs to:
    * Select the right maven profile: ``jbas71``
    * Either by pressing hot key *CTRL* + *ALT* + *P*, while the showcase project is selected
    * Or right click on the showcase project in the *project explorer* --> *Properties* --> *Maven* --> fill in the input *jbas71*
    * Alter the deployment assembly
        * Right click on the showcase project --> *Properties* --> *Deployment assembly*. By default there should be: ``src/main/java``, ``src/main/resources``, ``src/main/resources-jbas71/``, ``src/main/webapp``
        * One needs to add ``src/main/webapp-jbas71``: hit the add button and select the folder option, find the ``webapp-jbas71`` and add it
    * Now the showcase can be **deployed**, be sure that you are loading the showcase application on the correct context root and also that there was not added a default *persistence.xml* in ``src/main/resources/META-INF`` (delete it). The URL one should access looks like: <http://localhost:8080/showcase>
    
    
### 4. More information and resources
    
For more information on RichFaces 4, please visit RichFaces website at:
    
* <http://richfaces.org>

Or check the Wiki for more information on build instructions for the examples at:
    
* <http://community.jboss.org/wiki/HowtoworkwithRichFaces4xexamplessources>

To report or check the status of issues related to RichFaces Examples use the project's Jira at:
        
* <https://issues.jboss.org/browse/RF>
    
Community support and help can be obtained from the RichFaces forums at:
    
* <http://community.jboss.org/en/richfaces?view=discussions>