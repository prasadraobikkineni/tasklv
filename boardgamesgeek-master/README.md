# boardgamesgeek

A simple BDD Test application.

Create an acceptance test for BoardGameGeek service covering the following scenario:

Step 1: WEB Open homepage of the site  
Step 2: WEB Navigate to the page of the most top game with increasing rank in "The Hotness" left side menu  
Step 3: API Retrieve information about a particular game from site api  
Step 4: API Parse response to get most voted option in Language Dependence poll  
Step 5: WEB Assert Language Dependence text presented in the game page Description block equals the most voted Language Dependence level.  

Can be started with:  
> mvn clean install

Some configuration settings can be set in src/main/resources/app.properties such as:  
RUN_TESTS_IN_HEADLESS_MODE=true
