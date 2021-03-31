 package ct.com.basics.genericity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ct.com.basics.genericity.vo.Apple;
import ct.com.basics.genericity.vo.ApplePlate;
import ct.com.basics.genericity.vo.Food;
import ct.com.basics.genericity.vo.Fruit;
import ct.com.basics.genericity.vo.FruitPlate;
import ct.com.basics.genericity.vo.Pear;
import ct.com.basics.genericity.vo.Plate;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :  JAVA 泛型 限定符 extends super
 * <p>
 * List<T> T 表示泛型类型
 */
class LimitDemo {


    public static void main(String[] args) {
        new LimitDemo().limit01();
    }

    /**
     * 泛型限定
     */
    public void limit01() {

        //错误
        //因为泛型不是协变得
        //在这里编译器认为 Plate<Fruit> 和 Plate<Apple>是没有继承关系
        //Plate<Fruit> plate = new Plate<Apple>();
        List<Apple> appleList = new ArrayList<>();
        //错误 理由和上面一致
        // List<Apple> 和 List<Fruit>没有继承关系
        //假设下面的方法是成立的 则会出现以下List<Apple>被添加了一个Fruit对象.导致泛型不安全 -->PS1
        //inspect(appleList);

//        在 Java 中，大家比较熟悉的是通过继承机制而产生的类型体系结构。
//        比如 String 继承自 Object。根据Liskov 替换原则，子类是可以替换父类的。
//        当需要 Object 类的引用的时候，如果传入一个 String 对象是没有任何问题的。但是反过来的话，即用父类的引用替换子类引用的时候，就需要进行强制类型转换。
//        编译器并不能保证运行时刻这种转换一定是合法的。这种自动的子类替换父类的类型转换机制，对于数组也是适用的(数组是协变的)。
//        String[] 可以替换 Object[]。但是泛型的引入，对于这个类型系统产生了一定的影响。正如前面提到的 List<String> 是不能替换掉 List<Object> 的。
//
//       引入泛型之后的类型系统增加了两个维度：一个是类型参数自身的继承体系结构，另外一个是泛型类或接口自身的继承体系结构。
//       第一个指的是对于 List<String> 和 List<Object> 这样的情况，类型参数 String 是继承自 Object 的。
//       而第二种指的是 List 接口继承自 Collection 接口。对于这个类型系统，有如下的一些规则：
//
//        1.相同类型参数的泛型类的关系取决于泛型类自身的继承体系结构。即 List<String> 是 Collection<String> 的子类型，List<String> 可以替换 Collection<String>。这种情况也适用于带有上下界的类型声明。
//        2.当泛型类的类型声明中使用了通配符的时候， 其子类型可以在两个维度上分别展开。
//        如对 Collection<? extends Number> 来说，其子类型可以在 Collection 这个维度上展开，即 List<? extends Number> 和 Set<? extends Number> 等；
//        也可以在 Number 这个层次上展开，即 Collection<Double> 和 Collection<Integer> 等。
//        如此循环下去，ArrayList<Long> 和 HashSet<Double> 等也都算是 Collection<? extends Number> 的子类型。
//        3.如果泛型类中包含多个类型参数，则对于每个类型参数分别应用上面的规则。

        //针对第一种
        Plate<Fruit> plate2 = new Plate<>();
        Plate<Fruit> plate3 = new FruitPlate<>();

        ArrayList<Fruit> mList1 = new ArrayList<>();
        LinkedList<Fruit> mList2 = new LinkedList<>();
        //在泛型类上里氏替换原则
        //ArrayList继承List
        match(mList1);
        match(mList2);
        //在类型参数上里氏替换原则
        List<Apple> mList3 = new ArrayList<>();
        List<Pear> mList4 = new ArrayList<>();
        match(mList3);
        match(mList4);


        //但是其实装水果的盘子 是可以包涵装苹果的盘子
        //所以出现了通配符
        // <?> 可以看成<? extends Object> 不可以使用set  可以使用get方法 获取的是Object类型 主要是为了类型安全检查
        // <? extends T> 表示 泛型上界 泛型限定为T和T的子类 不可以使用set方法 可以使用get方法 获取的是T类型
        // <? super T>   表示 泛型下界 泛型限定为T和T的父类 可以使用set方法 传递的数据是T和T的子类 可以使用get方法 获取的是Object类型
        Plate<? extends Fruit> plate = new Plate<Apple>();
        //错误 不可以使用set
        //plate.setT(new Fruit());
        //plate.setT(new Apple());
        Fruit fruit = plate.getT();

        Plate<? super Fruit> plate1 = new Plate<Food>();
        plate1.setT(new Apple());
        plate1.setT(new Pear());
        // plate1.setT(new Food());
        Object data = plate1.getT();


    }

    //使用了通配符
    private void match(List<? extends Fruit> mList) {
    }

    private void inspect(List<Fruit> list) {
        for (Fruit fruit : list)
            System.out.println(fruit.getName());
        //PS1
        list.add(new Fruit());
    }

}
