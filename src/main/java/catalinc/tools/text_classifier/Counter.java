package catalinc.tools.text_classifier;

class Counter {
    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public void increment() {
        this.value++;
    }

    public int value() {
        return value;
    }
}
