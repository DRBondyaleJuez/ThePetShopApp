
# 🐶 🦴 💻 __THE PET SHOP APP__ 🛒 🐾 🐕
## A simple access-controlled application to simulate an app layout and navigation for buying dog products. Project to showcase the use of Java, PostgreSQL and libraries Javafx FXML and HTTPComponents among others.
___

![GitHub contributors](https://img.shields.io/github/contributors/DRBondyaleJuez/ThePetShopApp)
![GitHub repo size](https://img.shields.io/github/repo-size/DRBondyaleJuez/ThePetShopApp)
___

## __DESCRIPTION & USAGE__
This code was used as a training exercise to practice coding in java, postgreSQL, javafx FXML, automatize client processes and several design patterns.

The application is structured following a MVC design pattern precisely a Model-View-ViewController-Controller. Additionally, it has a persistence
package to mediate between the program and the relational database and the storage. Also, a web package for a small client process.

The application is designed to allow the user the creation of a unique user account stored with the necessary encryption in the database.
Then the user can sign in to a profile page displaying the purchase history of the user. They can also go to the page of products.
the user can browse the product page and when an item is selected to buy a window pops up to specify the number of units and confirm the action.
When an item is bought the purchase information is added to the user profiles purchase history.

The user account data, the product details and the purchase information are stored in the relational database and accessed when required by the application.

The images of the products are obtained from online based on the stored urls in the products records.

<div style="text-align: center;">

![Diagram](https://user-images.githubusercontent.com/98281752/234335774-2681b59e-a12e-43c0-a6d6-86260b7074a8.png)

</div>

## __Navigation between Views:__
![Navigation0](https://user-images.githubusercontent.com/98281752/234335792-f9e1e998-eeca-4002-995a-577313d4013e.png)
![Navigation1](https://user-images.githubusercontent.com/98281752/234335809-61147deb-fd38-48bb-96e2-3024391d13e8.png)
![Navigation2](https://user-images.githubusercontent.com/98281752/234335821-b01b359e-0617-470e-acb1-061e36ea9067.png)
![Navigation3](https://user-images.githubusercontent.com/98281752/234335831-5c840353-2ffd-4ef1-8262-d2c13e0b7d6b.png)
![Navigation4](https://user-images.githubusercontent.com/98281752/234335841-9ab2e6b3-c3f7-421c-a09e-077ad42dbeb1.png)
![Using PetShopApp gif](https://user-images.githubusercontent.com/98281752/234351816-1e4d96b4-4c2c-475d-9ea8-5b92c51f9fec.gif)
___
___



## __PERSISTENCE & RELATIONAL DATABASE__

This application's storage is based on a relational database coded in this case in [PostgreSQL](https://www.postgresql.org/).

The relational database follows the following self-explanatory diagram designed using [drawSQL](https://drawsql.app/teams/danrbj/diagrams/beercatalogue):

<div style="text-align: center;">

[![SQLDiagram](https://user-images.githubusercontent.com/98281752/234360979-d6977ffe-84cf-47ad-a74e-d80a6f98174e.png)](https://drawsql.app/teams/danrbj/diagrams/beercatalogue)

</div>

The username and email in the *users'* table are both unique. The password is stored encrypted in the form of byte array.
In the *products'* table the combination of the product name and subtype
is unique. All ids are primary keys and unique too.

___
___


## __INSTALLATION INSTRUCTIONS__
### __For IDE:__
<!-- OL -->
1. Clone the repository in your local server
2. Run the project's Main Class in your IDE

### __For Ubuntu (In terminal):__
<!-- OL -->
1. If necessary [install java version 11 or higher](https://stackoverflow.com/questions/52504825/how-to-install-jdk-11-under-ubuntu)

    ```bash
        sudo apt-get install openjdk-11-jdk
    ```


2. If necessary [install maven version 3.6.3 or higher](https://phoenixnap.com/kb/install-maven-on-ubuntu)

   ```bash 
       sudo apt install maven
   ``` 

3. If necessary [install git](https://www.digitalocean.com/community/tutorials/how-to-install-git-on-ubuntu-20-04)

   ```bash 
       apt install git
   ```

4. Clone the repository

   ```bash 
       git clone https://github.com/DRBondyaleJuez/ThePetShopApp.git
   ```

5. Go to the project folder. Make sure the [pom.xml](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) is there.

6. Create [.jar file](https://en.wikipedia.org/wiki/JAR_(file_format)) executable in target folder using the following code:

    ```bash
        mvn install 
    ```
   in case tests are giving trouble, this can be used to install without tests. However, it is not recommended.
   ```bash
        mvn package -Dmaven.test.skip
    ```

7. Build the database and tables needed for this application's persistence. Follow the recommendations in the resources.RelationalDatabaseSchema which describes the code in PostgreSQL.
   ([Help creating your first database in pgAdmin](https://www.tutorialsteacher.com/postgresql/create-database))


8. Fill in the parameters needed in the secrets.properties for access to the PostgreSQL database and for the encryption

   These are:
    <!-- OL -->
    - <ins>*DBUser*</ins>  (String username of the database software)
    - <ins>*DBPassword*</ins>  (String password of the database)
    - <ins>*encryptionKey*</ins>  (String a key for the encryption)
    - <ins>*saltSize*</ins>  (int the size of the desired salt)
    - <ins>*initialSubstringPositionForTransposition*</ins>  (int related to the transposition during the encryption. It should __not__ be 0, 1 or larger than half the encryption key size)

   Make sure the PropertiesReader boots the properties from the secrets.properties file

9. This code uses javafx FXML, so we recommend the use of the following code  to run the program :

    ([*Source*](https://github.com/openjfx/javafx-maven-plugin))
    
    ```bash 
        mvn javafx:run
    ```

___
___
## __INSTRUCTIONS FOR CONTRIBUTORS__
The objective of the project was to practice and apply java knowledge. No further contributions will be needed all of this is just a training excercise.

Hope you may find the code useful and please acknowledge its origin and authorship if used for any other purpose.


