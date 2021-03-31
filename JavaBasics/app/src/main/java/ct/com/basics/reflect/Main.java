package ct.com.basics.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

import ct.com.basics.CLog;
import ct.com.basics.reflect.vo.B;
import ct.com.basics.reflect.vo.Person;
import ct.com.basics.reflect.vo.TestType;
import ct.com.basics.reflect.vo.Tom;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC : 注解和反射的使用
 */
class Main {

    @Name("注解")
    private String name;

    public static void main(String[] args) {
        //getTomClass();
        //getConstructorAndBuild();
        //getField();
        //getMethod();

        //针对泛型的反射
        //getTypeVariable();
        //getParameterizedType();
        //getGenericArrayType();
        //getWildcardType();

        //getGenericType();


        //测试
        new Main().doReflect();

       // new B();

    }


    /**
     * 校验 反射创建的类 是否会执行静态代码块 构造方法
     * <p>
     * Class.forName 在加载类的时候会执行 静态代码块
     * ClassLoader  在加载类的时候不会执行 静态代码块
     */
    private void doReflect() {
        try {
            Class<?> cls = Class.forName("ct.com.basics.reflect.vo.Person");
//            cls.newInstance();
//            CLog.log("===========================");
//            Class<?> cls2 = getClass().getClassLoader().loadClass("ct.com.basics.reflect.vo.Person");
            //cls2.newInstance();
 //           CLog.log("===>" + cls2);
        } catch (Exception e) {
            CLog.log("异常:" + e);
        }
    }


    //反射就是在运行状态中,对于任意一个类,都能够知道这个类的所有属性和
    //方法;对于任意一个对象,都能够调用它的任意方法和属性;并且能改变它的属性。
    // 是Java被视为动态语言的关键。

    /**
     * 通过反射获取Class对象
     * <p>
     * 获取Class对象的三种方式
     * 1. 通过类名获取 类名.class
     * 2. 通过对象获取 对象名.getClass()
     * 3. 通过全类名获取 Class.forName(全类名) classLoader.loadClass(全类名)
     */
    private static void getTomClass() {

        Tom tom = new Tom(130, "宝马");
        Class<?> cls;
        //第一种 通过调用对象的getClass方法获取
        cls = tom.getClass();
        CLog.log("通过对象调用getClass()方法获取的Class:" + cls.getName());

        //第二种 通过类直接获取
        cls = Tom.class;
        CLog.log("通过类直接获取的Class:" + cls.getName());
        //第三种 通过对象的全类名获取
        try {
            cls = Class.forName("ct.com.basics.reflect.vo.Tom");
            CLog.log("通过全类名获取的Class:" + cls.getName());
        } catch (ClassNotFoundException e) {

        }

        //通过使用 isInstance 判断传入对象是否是该Class的一个实例或子类
        CLog.log("判断是否是Tom:" + Person.class.isInstance(tom));

        //判断Class对象是否和传入的Class对象一致或子类
        CLog.log("Class对象是否和传入的Class对象一致:" + Person.class.isAssignableFrom(Tom.class));

    }


    /**
     * 获取类中的构造函数 和创建对象
     */
    private static void getConstructorAndBuild() {

        try {
            Class<?> cls = Class.forName("ct.com.basics.reflect.vo.Person");

            Constructor<?>[] constructors = cls.getConstructors();

            for (Constructor constructor : constructors) {
                CLog.log("获取Person类声明的公开的构造函数:" + constructor.getName() + "--获取权限修饰符" + Modifier.toString(constructor.getModifiers()));
                for (Class cls2 : constructor.getParameterTypes()) {
                    CLog.log("获取指定的公开的构造方法:" + cls2.getName());
                }
                CLog.log("=================================");
            }
//            Constructor constructor = cls.getConstructor(int.class, String.class);
//            for (Class cls2 : constructor.getParameterTypes()) {
//                CLog.log("获取指定的公开的构造方法:" + cls2.getName());
//            }
            Constructor<?>[] constructors2 = cls.getDeclaredConstructors();
            for (Constructor constructor2 : constructors2) {
                CLog.log("获取Person类声明的构造函数:" + constructor2.getName() + "--获取权限修饰符" + Modifier.toString(constructor2.getModifiers()));
                for (Class cls2 : constructor2.getParameterTypes()) {
                    CLog.log("获取指定的构造方法:" + cls2.getName());
                }
                CLog.log("=================================");
            }
//            Constructor constructor2 = cls.getDeclaredConstructor(String.class, int.class, int.class);
//            for (Class cls2 : constructor2.getParameterTypes()) {
//                CLog.log("获取指定的构造方法:" + cls2.getName());
//            }


            //构建对象
            Person person = (Person) cls.newInstance();
            CLog.log("通过Class.newInstance创建对象，只能创建有无参且公开的构造函数的对象" + person);

            CLog.log("=================================");
            //通过获取指定构造函数穿件对象
            Constructor constructor3 = cls.getDeclaredConstructor(String.class, int.class, int.class);
            if (!constructor3.isAccessible())//如果构造函数不是公开的
                constructor3.setAccessible(true);//绕过安全检测

            Person person1 = (Person) constructor3.newInstance("反射创建", 10, 20);
            CLog.log("通过获取指定构造函数穿件对象" + person1);
        } catch (Exception e) {
            CLog.log(e.getMessage());
        }

    }

    /**
     * 获取类的成员变量
     */
    private static void getField() {

        try {
            Class cls = Class.forName("ct.com.basics.reflect.vo.Tom");

            //获取Class声明的所有公开字段 包括父类的公开字段
            Field[] fields = cls.getFields();
            for (Field field : fields) {
                CLog.log("获取Tom声明的公开字段:" + field.getName() +
                        "--字段的修饰符:" + Modifier.toString(field.getModifiers()) +
                        "--字段的类型" + field.getType().getName() +
                        "---字段声明的类:" + field.getDeclaringClass().getName()
                );
            }
            CLog.log("======================================");
            //获取Class声明的所有字段 不包括父类的字段

            Field[] declaredFields = cls.getDeclaredFields();

            for (Field field : declaredFields) {
                CLog.log("获取Tom声明的字段:" + field.getName() +
                        "--字段的修饰符:" + Modifier.toString(field.getModifiers()) +
                        "--字段的类型" + field.getType().getName() +
                        "---字段声明的类:" + field.getDeclaringClass().getName()
                );

            }
            CLog.log("======================================");
            //如果想要获取父类的字段
            Class supCls = cls.getSuperclass();
            if (supCls != null) {
                declaredFields = supCls.getDeclaredFields();
                for (Field field : declaredFields) {
                    CLog.log("获取父类声明的字段:" + field.getName() +
                            "--字段的修饰符:" + Modifier.toString(field.getModifiers()) +
                            "--字段的类型" + field.getType().getName() +
                            "---字段声明的类:" + field.getDeclaringClass().getName()
                    );

                }
            }

            CLog.log("======================================");
            //反射修改对象的字段
            Person person = new Person(18, "学生");
            CLog.log("反射修改前:" + person);
            Field field = cls.getDeclaredField("work");
            if (!field.isAccessible())
                field.setAccessible(true);
            field.set(person, "打工仔");
            CLog.log("反射修改后:" + person);


        } catch (Exception e) {
            CLog.log(e.getMessage());
        }

    }


    /**
     * 获取类的方法
     */
    private static void getMethod() {

        try {
            Class cls = Class.forName("ct.com.basics.reflect.vo.Tom");
            //获取Class声明的所有公开方法 包括父类的公开方法
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {

                CLog.log("获取类声明的公开方法:" + method.getName()
                        + "--方法修饰符:" + Modifier.toString(method.getModifiers())
                        + "--方法的返回值:" + method.getReturnType().getName()
                        + "--方法的参数:");

                for (Class type : method.getParameterTypes()) {
                    CLog.log("---" + type.toString());
                }

            }
            Method[] declaredMethods = cls.getDeclaredMethods();


            //
            //反射调用对象的方法
            Person person = new Person(18, "学生");
            CLog.log("反射修改前:" + person);
            Method method = person.getClass().getDeclaredMethod("setAge", int.class);
            if (!method.isAccessible())
                method.setAccessible(true);
            method.invoke(person, 28);
            CLog.log("反射修改后:" + person);

        } catch (Exception e) {
            CLog.log("异常:" + e.getMessage());
        }

    }

    //============================针对泛型的反射=====================================

    /**
     * 获取类中声明的泛型参数的信息
     * <p>
     * 泛型直接作为字段存在
     * <p>
     * TypeVariable：声明在class中 泛型变量
     * signature TM;
     * T+泛型占位符
     * T表示是泛型
     */
    private static void getTypeVariable() {

        try {
            TestType<Tom> tomTestType = new TestType<>();

            Field field = tomTestType.getClass().getDeclaredField("data");
            //处理字段类型的泛型 使用TypeVariable
            //获取字段的描述信息 也就是类型信息
            Type type = field.getGenericType();
            CLog.log("泛型信息:" + type.getClass());

            TypeVariable typeVariable = (TypeVariable) type;
            CLog.log("获取泛型名称:" + typeVariable.getName());

            CLog.log("泛型信息:" + typeVariable.getGenericDeclaration());

            for (Type type1 : typeVariable.getBounds())
                CLog.log("获取泛型声明出的上|下界信息:" + type1.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取类中泛型参数的信息
     * <p>
     * 泛型作为一个字段的类型参数
     * <p>
     * ParameterizedType：泛型实例化参数信息
     * signature Ljava/util/Map<Ljava/lang/String;Lct/com/basics/reflect/vo/Tom;>;
     * L+引用类型
     * L表示是引用类型
     */
    private static void getParameterizedType() {
        try {

            TestType<Tom> type = new TestType<Tom>();
            Class cls = type.getClass();
            CLog.log(cls + "----" + cls.getGenericSuperclass());

            Field field = type.getClass().getDeclaredField("map");
            Field field2 = type.getClass().getDeclaredField("data");
            CLog.log(field2.getGenericType() + "字段的泛型信息:" + field.getGenericType());
            //强转成参数化的泛型对象
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

            //获取指定字段声明的所有泛型
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            //获取该Type所属的Type
            //主要是匿名内部类
            System.out.println("==" + parameterizedType.getOwnerType());
            System.out.println("获取原始的类型信息:" + parameterizedType.getRawType());
            for (Type type1 : actualTypeArguments) {
                System.out.println("获取声明类型信息:" + type1.toString());
                if (type1 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType1 = (ParameterizedType) type1;
                    System.out.println("==" + parameterizedType1.getOwnerType());
                }
            }

        } catch (Exception e) {
            System.out.println("异常:" + e.getMessage());
        }
    }


    /**
     * 获取类中数组泛型信息
     * <p>
     * 泛型作为一个数组的类型参数
     * <p>
     * GenericArrayType:数组类型泛型信息
     * signature [TM;
     * [+数组类型
     * [ 表示是数组类型 T表示泛型M
     */
    private static void getGenericArrayType() {

        try {

            TestType<Tom> testType = new TestType<>();
            Field field = testType.getClass().getDeclaredField("lists");

            System.out.println("泛型信息:" + field.getGenericType());
            //处理数组泛型信息 使用 GenericArrayType
            GenericArrayType genericArrayType = (GenericArrayType) field.getGenericType();

            System.out.println("获取数组声明的Type：" + genericArrayType.getGenericComponentType());


        } catch (Exception e) {
        }

    }

    /**
     * 获取通配符信息
     * <p>
     * WildcardType:通配符泛型信息
     * // signature Lct/com/basics/reflect/vo/TestType<+Lct/com/basics/reflect/vo/Person;>;
     * +表示上界
     * -表示下界
     */
    private static void getWildcardType() {
        try {
            TestType<Tom> testType = new TestType<>();
            Field field = testType.getClass().getDeclaredField("wildcard");

            Type type = field.getGenericType();
            System.out.println("泛型信息:" + type);

            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println("泛型信息：" + actualTypeArgument);
                if (actualTypeArgument instanceof WildcardType) {
                    WildcardType wildcardType = (WildcardType) actualTypeArgument;
                    Type[] upperBounds = wildcardType.getUpperBounds();
                    for (Type upper : upperBounds)
                        System.out.println("获取上界信息:" + upper);

                    Type[] lowerBounds = wildcardType.getLowerBounds();
                    for (Type lower : lowerBounds)
                        System.out.println("获取下界信息:" + lower);


                }
            }


        } catch (Exception e) {
            System.out.println("异常:" + e);
        }
    }

//    private static void getGenericType() {
//
//        //TestType<Tom> testType  = new TestType<>();
//ParameterizedType type1 = (ParameterizedType) new TestType<String>().getClass().getGenericSuperclass();
//        ParameterizedType type= (ParameterizedType) (new ArrayList<String>()).getClass().getGenericSuperclass();
//System.out.println(type);
//    }

}
