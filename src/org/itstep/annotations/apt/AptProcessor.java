package org.itstep.annotations.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

public class AptProcessor extends AbstractProcessor {
    public static final String SUFFIX = "AutoGenerate";
    public static final String PREFIX = "alex_";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement typeElement : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(typeElement)) {
                Messager messager = processingEnv.getMessager();
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing:" + e.toString());
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing:" + e.getSimpleName());
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing:" + e.getEnclosedElements().toString());

                APTAnnotation aptAnnotation = e.getAnnotation(APTAnnotation.class);

                String name = e.getSimpleName().toString();
                char c = Character.toUpperCase(name.charAt(0));
                name = String.valueOf(c + name.substring(1));

                Element enclosingElement = e.getEnclosingElement();
                String enclosingQualifiedname;
                if (enclosingElement instanceof PackageElement) {
                    enclosingQualifiedname = ((PackageElement) enclosingElement).getQualifiedName().toString();
                } else {
                    enclosingQualifiedname = ((TypeElement) enclosingElement).getQualifiedName().toString();
                }
                try {
                    String generatePackageName = enclosingQualifiedname.substring(0, enclosingQualifiedname.lastIndexOf("."));
                    String genarateClassName = PREFIX + enclosingElement.getSimpleName() + SUFFIX;
                    JavaFileObject f = processingEnv.getFiler().createSourceFile(genarateClassName);
                    messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + f.toUri());
                    Writer w = f.openWriter();
                    try {
                        PrintWriter pw = new PrintWriter(w);
                        pw.println("package " + generatePackageName + ";");
                        pw.println("\npublic class " + genarateClassName + " { ");
                        pw.println("\n    /** 打印值 */");
                        pw.println("    public static void print" + name + "() {");
                        pw.println("        // 注解的父元素: " + enclosingElement.toString());
                        pw.println("        System.out.println(\"代码生成的路径: " + f.toUri() + "\");");
                        pw.println("        System.out.println(\"注解的元素: " + e.toString() + "\");");
                        pw.println("        System.out.println(\"注解的版本: " + aptAnnotation.version() + "\");");
                        pw.println("        System.out.println(\"注解的作者: " + aptAnnotation.author() + "\");");
                        pw.println("        System.out.println(\"注解的日期: " + aptAnnotation.date() + "\");");

                        pw.println("    }");
                        pw.println("}");
                        pw.flush();
                    } finally {
                        w.close();
                    }
                } catch (IOException e1) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e1.toString());
                }
            }
        }
        return true;
    }
}
