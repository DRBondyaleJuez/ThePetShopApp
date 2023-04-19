import application.web.ImageCollectorClient;
import org.junit.jupiter.api.Test;

public class testController {

    @Test
    public void observeImageUrlFetchOutput(){

        ImageCollectorClient imageCollectorClient = new ImageCollectorClient();
        String imageURL = "https://m.media-amazon.com/images/I/81Rpyhufx0L._AC_SX679_.jpg";
        byte[] imageByteArray = imageCollectorClient.requestImageData(imageURL);
        System.out.println(imageByteArray);
        for (byte b:imageByteArray) {
            System.out.println(b);
        }



    }




}
