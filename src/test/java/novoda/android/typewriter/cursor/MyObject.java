package novoda.android.typewriter.cursor;

import novoda.android.typewriter.annotation.Mapper;

public class MyObject {

    @Mapper("some_value")
    public String test2;

    public String test;

    public void setTest(String hello) {
        this.test = hello;
    }

    public void setTest2(String hello) {
        this.test2= hello;
    }
}
