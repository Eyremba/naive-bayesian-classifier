# naive-bayesian-classifier

Java implementation of Dr.Dobb's [Naive Bayes Classifier](http://www.drdobbs.com/architecture-and-design/naive-bayesian-text-classification/184406064).

# example

```java
import catalinc.tools.text_classifier.NaiveBayesClassifier;

public class Main {

    public static void main(String[] args) {
        TextClassifier classifier = new TextClassifier();
        classifier.train("The big dog barks", "dog");
        classifier.train("The small cat meows", "cat");

        System.out.println(classifier.classify("something that barks"));        // -> dog
        System.out.println(classifier.classify("something small that meows"));  // -> cat
    }
}
```