package catalinc.tools.text_classifier;

import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class NaiveBayesianClassifierTest {

    private NaiveBayesianClassifier classifier;

    @Before
    public void beforeTest() {
        classifier = new NaiveBayesianClassifier();
    }

    @Test
    public void untrained() {
        assertEquals("", classifier.classify("something"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void trainNullText() {
        classifier.train(null, "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void trainEmptyText() {
        classifier.train("", "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void trainNullName() {
        classifier.train("text", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void trainEmptyName() {
        classifier.train("text", null);
    }

    @Test
    public void classify() throws Exception {
        Files.lines(getPathFromResource("train_data.in")).forEach(line -> {
            String[] tokens = line.split(" \\| ");
            if (tokens.length == 2) {
                classifier.train(tokens[0], tokens[1]);
            }
        });

        Files.lines(getPathFromResource("classify_data.in")).forEach(line -> {
            String[] tokens = line.split(" \\| ");
            if (tokens.length == 2) {
                assertEquals(tokens[1], classifier.classify(tokens[0]));
            }
        });
    }

    private Path getPathFromResource(String resourceName) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
    }
}