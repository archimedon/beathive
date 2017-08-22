# BeatHive


Install and Run
===============


Dependencies
------------

- Requires: Ant 1.6.5+
- Tomcat 5.0.x+ installed
- Java 1.5+

- Core Libraries:
  - App core
    * Spring 2.0
	* Acegi Security 1.0.2
    * Struts 1.2.9
    * Struts Menu 2.4.2

  - Database/ORM
    * Hibernate 3.2.ga
    * MySQL JDBC Driver 5.0.3+ OR PostgreSQL JDBC Driver 8.0-405
    * EhCache 1.2.3
    * OSCache 2.3.2
	* Cybersource 1.0

  - Rendering & Ajax
    * WebWork 2.2.4
    * DisplayTag 1.1
    * Tapestry 4.0.1
    * Facelets 1.1.11
    * JSTL 1.1.2
    * Ajax4JSF 1.0.2
    * DWR 1.1.1

  - Logging and testing
    * Commons Logging 1.1
    * Commons Validator 1.2.0
    * Cargo 0.7
    * Log4j 1.2.11

  - Build and packaging tools
    * URL Rewrite Filter 2.6.0
    * Rename Packages 1.1
    * WebTest build 1393
    * XFire 1.2.2

Run
===
1. `cp lib/junit.jar $ANT_HOME/lib`
2. Expects SMTP server on localhost (configure in web/WEB-INF/classes/mail.properties)
3. Default database server is mysql on local (configure in build.properties)
4. Create configured database
5. `ant setup-tomcat deploy`
	- copies beathive.xml file to $CATALINA_HOME/webapps ($CATALINA_HOME/conf/Catalina/
6. Start Tomcat if not running
