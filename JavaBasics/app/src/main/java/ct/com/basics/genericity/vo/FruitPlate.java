package ct.com.basics.genericity.vo;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :
 * 这个类主要查看泛型擦除
 * <p>
 * FruitPlate经过泛型擦除后,里面的set为setT(Fruit)
 * Plate经过泛型擦除后,里面的set为setT(Object)
 * 所以为了保证多态性 会生成一个桥方法 bridge setT(Object)在里面把Object转为Fruit
 */
public class FruitPlate<T extends Fruit> extends Plate<T> {


    @Override
    public T getT() {
        return super.getT();
    }

    @Override
    public void setT(T t) {
        super.setT(t);
    }

    @Override
    public String toString() {
        return getT().getName();
    }
}
