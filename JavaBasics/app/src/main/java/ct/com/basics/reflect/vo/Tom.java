package ct.com.basics.reflect.vo;

import ct.com.basics.CLog;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :
 */
public class Tom extends Person {

    private int weight;

    public String car;


    static {
        CLog.log("Tom 类的静态代码块执行");
    }

    private Tom(int weight) {
        this.weight = weight;
    }

    public Tom(int weight, String car) {
        this.weight = weight;
        this.car = car;
    }

    private String getCar() {
        return car;
    }

    protected void setCar(String car) {
        this.car = car;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    static class TomA {

    }
}
