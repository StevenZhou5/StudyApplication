package study.zhouzhenwu.com.mydemo.java.testclass;

/**
 * Created by Steven on 2018/5/30.
 */

public class TestUser {
    @UserName(value = "TestUser", age = 10)
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
