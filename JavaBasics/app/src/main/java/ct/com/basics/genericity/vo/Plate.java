package ct.com.basics.genericity.vo;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :
 */
public class Plate<T> {

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }


    @Override
    public String toString() {
        return t.toString();
    }
}
