package pop.lesson08;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static pop.lesson08.FileOps.appendToFile;
import static pop.lesson08.FileOps.writeToFile;

import pop.lesson08.FileOps.*;

public class FileOpsTest {

    @Test
    public void testWriteToFile() {
        writeToFile();
    }

    @Test
    public void testAppendToFile() {
        appendToFile();
    }
}