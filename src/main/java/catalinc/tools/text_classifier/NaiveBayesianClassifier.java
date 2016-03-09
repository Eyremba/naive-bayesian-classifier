package catalinc.tools.text_classifier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java version of <a href="http://www.drdobbs.com/architecture-and-design/naive-bayesian-text-classification/184406064">Naive Text Classifier</a>.
 */
public class NaiveBayesianClassifier {
    private Map<String, Category> categories;

    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");

    public NaiveBayesianClassifier() {
        this.categories = new HashMap<>();
    }

    /**
     * Add the words from given text to a category.
     *
     * @param text  text to add
     * @param name  category name
     */
    public void train(String text, String name) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("text must not be null or empty");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must not be null or empty");
        }

        Category category = categories.get(name);
        if (category == null) {
            category = new Category(name);
            categories.put(name, category);
        }

        List<String> words = splitWords(text);
        for (String word : words) {
            category.add(word);
        }
    }

    /**
     * Get the classification for a given text.
     *
     * @return Category name or empty string if no category was found for the given text.
     */
    public String classify(String text) {
        Objects.requireNonNull(text, "text must not be null");

        List<String> words = splitWords(text);
        int totalWords = 0;
        for (Category category : categories.values()) {
            totalWords += category.size();
        }

        double bestScore = Double.NEGATIVE_INFINITY;
        Category bestCategory = new Category("");

        for (Category category : categories.values()) {
            double score = score(words, category);
            score += Math.log(((double) category.size()) / totalWords);
            if (score > bestScore) {
                bestScore = score;
                bestCategory = category;
            }
        }

        return bestCategory.name();
    }

    private List<String> splitWords(String text) {
        List<String> words = new LinkedList<>();
        Matcher matcher = WORD_PATTERN.matcher(text);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }

    private double score(List<String> words, Category category) {
        double score = 0.0;
        for (String word : words) {
            int frequency = category.frequency(word);
            if (frequency == 0) {
                score += Math.log(0.01 / category.size());
            } else {
                score += Math.log(((double) frequency) / category.size());
            }
        }
        return score;
    }
}
