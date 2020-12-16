package com.ct.apt_compiler;


import com.ct.apt_annotations.ARouter;
import com.ct.apt_annotations.Builder;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * ARouter注解 编译器
 */
//使用AutoService
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.ct.apt_annotations.ARouter", "com.ct.apt_annotations.Event", "com.ct.apt_annotations.Builder"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("module")
public class ARouterCompiler extends AbstractProcessor {

    //日志打印
    private Messager messager;

    //文件输出
    private Filer filer;

    //操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementTool;

    //type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elementTool = processingEnvironment.getElementUtils();
        typeTool = processingEnvironment.getTypeUtils();

        //这里可以获取由 Gradle传递过来的参数信息
        Map<String, String> gradleMap = processingEnvironment.getOptions();
        String module = gradleMap.get("module");
        messager.printMessage(Diagnostic.Kind.NOTE, "Current Module:" + module);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.size() == 0)
            return false;
        //printInfo(set, roundEnvironment);
        //buildHelloWord();
        //buildDynamicClass(set, roundEnvironment);
        buildBuilder(roundEnvironment);
        return false;
    }


    /**
     * @param set 我们能够支持 并被使用的注解 集合
     */
    private void printInfo(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        //打印 所有支持 并使用的注解信息
        for (TypeElement typeElement : set
        ) {
            messager.printMessage(Diagnostic.Kind.NOTE, "TypeElement Info:" + typeElement.getSimpleName());

        }


        //获取 所有使用的ARouter的元素
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);

        for (Element element : elements) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Use ARouter Element:" + element.getSimpleName() + "--->" + elementTool.getPackageOf(element));

            //获取 使用指定元素的包信息
            elementTool.getPackageOf(element);


            messager.printMessage(Diagnostic.Kind.NOTE, "Is Type as : " + typeTool.isSubtype(element.asType(), elementTool.getTypeElement("android.app.Activity").asType()));

        }


    }


    /**
     * 模块一
     * package com.ct.apt_generate.demo;
     * <p>
     * public final class HelloWorld {
     * <p>
     * public static void main(String[] args) {
     * System.out.println("Hello, JavaPoet!");
     * }
     * }
     */
    private void buildHelloWord() {
        messager.printMessage(Diagnostic.Kind.NOTE, "Start Build HelloWorld Java");

        //创建方法
        MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                //添加修饰符
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                //添加返回值
                .returns(void.class)
                //添加参数信息
                .addParameter(String[].class, "args")
                //添加方法中的内容
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        //创建类
        TypeSpec type = TypeSpec.classBuilder("HelloWorld")
                //添加修饰符
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mainMethod)
                .build();

        //创建包名
        JavaFile javaFile = JavaFile.builder("com.ct.apt_generate.demo", type).build();

        try {
            javaFile.writeTo(filer);
        } catch (Exception e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Generate HelloWord Java Fail!!" + e.getMessage());
        }


        messager.printMessage(Diagnostic.Kind.NOTE, "End Build HelloWorld Java");
    }

    /**
     * 生成动态的类
     * <p>
     * 规则是 注解名$使用注解的类名
     */
    private void buildDynamicClass(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        messager.printMessage(Diagnostic.Kind.NOTE, "Start Build Dynamic Class");
        for (TypeElement typeElement : set) {
            //获取注解名称
            String annoName = typeElement.getSimpleName().toString();

            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(typeElement);


            for (Element element : elements) {


                //获取 使用注解的类名
                String clsName = element.getSimpleName().toString();

                String typeName = annoName + "$" + clsName;


                //构建方法


                //构建类
                TypeSpec typeSpec = TypeSpec.classBuilder(typeName)
                        .addModifiers(Modifier.PUBLIC)
                        .build();

                //构建包名
                String packageName = elementTool.getPackageOf(element).getQualifiedName().toString() + ".generate";

                JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
                try {
                    messager.printMessage(Diagnostic.Kind.NOTE, "To Write Dynamic Class:" + packageName + "  " + typeName);
                    javaFile.writeTo(filer);
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "To Write Dynamic Class Fail:" + packageName + "  " + typeName + "  " + e.getMessage());
                }


            }


        }

        messager.printMessage(Diagnostic.Kind.NOTE, "End Build Dynamic Class");

    }


    private void buildBuilder(RoundEnvironment roundEnvironment) {
        Set<? extends Element> set = roundEnvironment.getElementsAnnotatedWith(Builder.class);

        for (Element element : set) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                List<? extends Element> mList = element.getEnclosedElements();
                messager.printMessage(Diagnostic.Kind.NOTE, "Current Element====" + element);
                String typeName = "Builder$" + typeElement.getSimpleName();
                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName);
                for (Element element1 : mList) {
                    if (element1 instanceof VariableElement) {
                        VariableElement variableElement = (VariableElement) element1;

                        String methodName = "set_" + variableElement.getSimpleName().toString();


                        TypeName type = TypeName.get(variableElement.asType());
                        String name = variableElement.getSimpleName().toString();

                        type
                        TypeName returnName = TypeName.get(elementTool.getTypeElement(typeName).asType());
                        messager.printMessage(Diagnostic.Kind.NOTE, "Current Element:::::" + variableElement.asType() + methodName + ""+returnName);
                        MethodSpec methodSpec = MethodSpec.methodBuilder(methodName)
                                .addModifiers(Modifier.PUBLIC)
                                .returns()
                                .addStatement("return this")
                                .addParameter(type, name)
                                .build();

                        typeBuilder.addMethod(methodSpec);

                    }


                }

                TypeSpec typeSpec = typeBuilder.addModifiers(Modifier.PUBLIC)
                        .build();
                String packageName = elementTool.getPackageOf(element).getQualifiedName().toString() + ".generate";
                JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
                try {
                    javaFile.writeTo(filer);
                } catch (Exception e) {

                }


            }


        }


    }


}