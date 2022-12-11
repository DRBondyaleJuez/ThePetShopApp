import controller.CreateAccountController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import viewController.CreateAccountViewController;

public class testController {

    @Test
    public void testNameUniquenessEvaluatedProperlyTrueCase(){
        CreateAccountController createAccountController = new CreateAccountController();
        boolean newUserNameUnique = createAccountController.isNameUnique("Michael");

        Assertions.assertTrue(newUserNameUnique, "The new username is unique.");

    }

    @Test
    public void testNameUniquenessEvaluatedProperlyFalseCase(){
        CreateAccountController createAccountController = new CreateAccountController();
        boolean newUserNameUnique = createAccountController.isNameUnique("Mike");

        Assertions.assertTrue(!newUserNameUnique, "The new username is not unique.");

    }

    @Test
    public void testEmailUniquenessEvaluatedProperlyTrueCase(){
        CreateAccountController createAccountController = new CreateAccountController();
        boolean newUserEmailUnique = createAccountController.isEmailUnique("bye@bug.es");

        Assertions.assertTrue(newUserEmailUnique, "The new email is unique.");

    }

    @Test
    public void testEmailUniquenessEvaluatedProperlyFalseCase(){
        CreateAccountController createAccountController = new CreateAccountController();
        boolean newUserEmailUnique = createAccountController.isEmailUnique("jordan@fly.com");

        Assertions.assertTrue(!newUserEmailUnique, "The new email is not unique.");

    }

    @Test
    public void testAddNewUserToDatabase(){
        CreateAccountController createAccountController = new CreateAccountController();
        boolean userAddedCorrectly = createAccountController.addNewUserToDatabase("Pablo2","Picasso10","guernica2@arte.es");

        Assertions.assertTrue(userAddedCorrectly, "The new user was added without problems");

    }

}
