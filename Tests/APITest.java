import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class APITest {

    //Want to check if I can edit data from class in two separate objects

    @Test
    void factoryTestForTrue(){
        API obj1 = API.getInstance();
        API obj2 = API.getInstance();

        obj1.getPatients().get(0).setFirstname("Kathy");

        assertTrue(obj2.getPatients().get(0).getFirstname().equals("Kathy"));
    }

    @Test
    void factoryTestForFalse(){
        API obj1 = API.getInstance();
        API obj2 = API.getInstance();

        obj1.getPatients().get(0).setFirstname("Kathy");

        assertFalse(obj2.getPatients().get(0).getFirstname().equals("Dave"));
    }

}