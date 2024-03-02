package org.eve;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

@DisplayName("App Test")
public class ApplicationTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void Application_Test() throws Exception{
        // Arrange
        Path inputFile = Paths.get("src","test","resources","test_input_one.txt");
        List<String> arguments= new ArrayList<>(List.of("INPUT_FILE="+inputFile.toString()));
        Path actualOutputFile = Paths.get("src","test","resources","test_input_one_actual_output.txt");
        Path expectedOutputFile = Paths.get("src","test","resources","test_input_one_expected_output.txt");
        // Act

        String actualOutput = tapSystemOut(() -> App.run(arguments));
        Files.writeString(actualOutputFile, actualOutput, StandardCharsets.UTF_8);

        // Assert
        Assertions.assertTrue(compareByMemoryMappedFiles(expectedOutputFile,actualOutputFile));
    }
        // https://www.baeldung.com/java-compare-files
    private boolean compareByMemoryMappedFiles(Path path1, Path path2) throws IOException {
        try (RandomAccessFile randomAccessFile1 = new RandomAccessFile(path1.toFile(), "r");
             RandomAccessFile randomAccessFile2 = new RandomAccessFile(path2.toFile(), "r")) {

            FileChannel ch1 = randomAccessFile1.getChannel();
            FileChannel ch2 = randomAccessFile2.getChannel();
            if (ch1.size() != ch2.size()) {
                return false;
            }
            long size = ch1.size();
            MappedByteBuffer m1 = ch1.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            MappedByteBuffer m2 = ch2.map(FileChannel.MapMode.READ_ONLY, 0L, size);

            return m1.equals(m2);
        }
    }
}
