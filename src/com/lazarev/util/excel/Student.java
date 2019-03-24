package vstup_test;

public class Student {

    private String keyProp;

    private int mark;

    public String getKeyProp() {
        return keyProp;
    }

    public void setKeyProp(String keyProp) {
        this.keyProp = keyProp;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        if (mark>100) throw new RuntimeException("invalid mark");
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "keyProp='" + keyProp + '\'' +
                ", mark=" + mark +
                '}';
    }
}
