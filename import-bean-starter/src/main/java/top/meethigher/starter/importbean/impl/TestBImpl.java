package top.meethigher.starter.importbean.impl;

import top.meethigher.starter.importbean.TestA;

public class TestBImpl implements TestA {

    private final TestA testA;


    public TestBImpl(TestA testA) {
        this.testA = testA;
        System.out.println("TestB实例化");
    }
}
