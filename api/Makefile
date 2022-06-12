all:
	mysql -u  root -p1234123456 progressoft < D:/ayah/progressoft/api/src/project/progressoft_dic_dictionary.sql
	mysql -u  root -p1234123456 progressoft < D:/ayah/progressoft/api/src/project/progressoft_dic_dictionaryentry.sql
	mysql -u  root -p1234123456 progressoft < D:/ayah/progressoft/api/src/project/progressoft_fx_deal.sql
	@echo "start initialization Application"
	@echo "Maven clean && install"
	mvn clean install
	@echo "run Application"
	java -jar ./target/progessoft-api-0.0.1-SNAPSHOT.jar 
