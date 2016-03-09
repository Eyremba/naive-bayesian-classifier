package catalinc.tools.text_classifier;

import java.util.HashMap;
import java.util.Map;

class Category {
    private String name;
    private Map<String, Counter> words;

    public Category(String name) {
        this.name = name;
        this.words = new HashMap<>();
    }

    public String name() {
        return name;
    }

    public void add(String word) {
        Counter counter = words.get(word);
        if (counter == null) {
            counter = new Counter(0);
            words.put(word, counter);
        }
        counter.increment();
    }

    public int frequency(String word) {
        Counter counter = words.get(word);
        if (counter == null) return 0;
        return counter.value();
    }

    public int size() {
        return words.size();
    }
}
