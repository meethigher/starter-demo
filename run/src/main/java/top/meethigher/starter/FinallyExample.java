package top.meethigher.starter;

public class FinallyExample {
    public static int divide(int a, int b) {
        int i = a / b;
        try {
            System.out.println(i);
            return i;
        } finally {
            i = 1 / 1;
            System.out.println("执行finally块中的代码");
        }
    }

    public static void main(String[] args) {
        int result = divide(10, 2);
        System.out.println("结果为：" + result);
    }
}
