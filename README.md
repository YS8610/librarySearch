## Simple Book Search Website Using Openlibrary Api  
  
The website is generated though Spring boot. The search result are generated by taking the user input and then sending a request using open library api to fetch the result in json format. Only key and title are being retrieved from the open library api.  
  
The api used is https://openlibrary.org/dev/docs/api/search.json?fields=title,key&limit=20&q=userinput  
  
The server will then result the list of max 20 result links. User can click on the links to find out more on the search result. The individual book result will give title, description, excerpt and cached information. Result will be cached using Redis to reduce api call to openlibrary and make it faster.  
  
The configuration file for setting redis is in src\main\resources\application.properties  
  
The password for redis is set to be the environment variable. If it is not found, it can be set in src\main\java\tfip\modserver\librarysearch\config\RedisConfig.java  
  
To run,  
```
mvn spring-boot:run
```
