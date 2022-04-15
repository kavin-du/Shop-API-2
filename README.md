# Shop API deployment guide


## 1. Run the tests first.
Go to `src/test/java/com/example/shop/service` folder and open up `OrderServiceTest` and `ProductServiceTest`.
These two files contain the unit tests for all the services in the application.

![image](https://user-images.githubusercontent.com/59405594/163510069-3698a81e-38e9-4c30-8768-d1069a25f709.png)

Click on the play button in front of each class name to run all the tests.

Otherwise, if you want to run the tests using terminal, use the following command.

`./gradlew cleanTest test`

## 2. Set up a MySQL database
Here I will be using Docker to deploy a MySQL container. If you know other ways to set up a database, please skip this step after adding 
the username and password of the database to `src/main/resources/application.properties` file.

i) You need to have Docker Desktop installed on your computer.
[https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)

ii) Clone this repository and go to MySQL folder.
[https://github.com/kavin-du/Docker-Compose-Files](https://github.com/kavin-du/Docker-Compose-Files)

iii) To start the MySQL container ->
`docker-compose -f mysql.yml up -d`

iv) After you have finished with your application, you can stop the MySQL container ->
`docker-compose -f mysql.yml down`

## 3. Build and run the project.

**You need to have Java 17 installed on your computer because the application was built using Java 17.**
If you do not have java 17 properly installed in your system, It will give you this error when building.

![image](https://user-images.githubusercontent.com/59405594/163510718-e5d77135-6441-40f9-b7d5-e08b433749b3.png)

i) If you want to run this application using Intellij, Go to `ShopApplication` class and click the play button in front of the `main()` method.

ii) If you want to run the application using the command line, follow the below steps.

After Java 17 is properly set up, run the following command to build and start the application.
`./gradlew clean bootRun`

Gradle is usually showing the build process as `83%`, but it is perfectly fine. You can use your 
application without any issues. 

Now the application is running at [http://localhost:3000](http://localhost:3000)
To read the API docs go to [http://localhost:3000/docs](http://localhost:3000/docs)


