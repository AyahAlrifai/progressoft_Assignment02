# Getting Started
# as developer you should set project proprties in this file src\main\resources\properties\Progressoft.properties

    /** logs configuration **/

        log-path=[[ D:/ayah/progressoft/api/src/main/resources/ApplicationLogs/logs.log ]] 

    /** Database configuration **/
        db-account=[[root]]
        db-password=[[1234123456]]
        db-host=[[localhost]]
        db-port=[[3306]]
        db-database=[[progressoft]]

# run application 
    Change this part of Makefile
        mysql -u  [[db-acount]] -p[[db-password]] -h [[db-host]] [[db-database]] < ./src/project/progressoft_dic_dictionary.sql
	mysql -u  [[db-acount]] -p[[db-password]] -h [[db-host]] [[db-database]] < ./src/project/progressoft_dic_dictionaryentry.sql
	mysql -u  [[db-acount]] -p[[db-password]] -h [[db-host]] [[db-database]] < ./src/project/progressoft_fx_deal.sql
    in cmd run this command --> make 
