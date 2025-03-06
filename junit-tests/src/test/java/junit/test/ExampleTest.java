package junit.test;// testing to see if JUnit was properly integrated!
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {
    @Test
    void additionShouldBeCorrect() {
        assertEquals(4, 2 + 2);
    }
}