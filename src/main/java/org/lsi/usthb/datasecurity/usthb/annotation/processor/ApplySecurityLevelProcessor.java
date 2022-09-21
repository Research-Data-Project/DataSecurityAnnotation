package org.lsi.usthb.datasecurity.usthb.annotation.processor;



import org.lsi.usthb.datasecurity.usthb.annotation.DataSecurityLevel;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@SupportedAnnotationTypes(value = {"org.lsi.usthb.annotation.DataSecurityLevel","org.lsi.usthb.annotation.SecurityBuilder"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ApplySecurityLevelProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, Map<String, String>> classMappedByFields = new HashMap<>();
        Map<String, String> annotatedFieldName = new HashMap<>();

        String className = "";
        Set<? extends Element> annotatedWithSecurityBuilder = roundEnv.getElementsAnnotatedWith(DataSecurityLevel.class);
        // for each class annotated with Security builder we create a builder
        for (Element element : annotatedWithSecurityBuilder) {
            if (element.getKind() != ElementKind.FIELD) {
                continue;
            }
            TypeMirror fieldType = element.asType();
            String fullTypeClassName = fieldType.toString();
            annotatedFieldName.put(element.getSimpleName().toString(), fullTypeClassName);
            className = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
            try {
                writeBuilderFile(className,annotatedFieldName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writeBuilderFile(className,annotatedFieldName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return true;
    }

    private void writeBuilderFile(String className,Map<String, String> annotatedFieldName) throws IOException {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "SecurityBuilder";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();

            }
            out.println();
            out.print("public class ");
            out.print(builderSimpleClassName);
            out.print(" extends ");
            out.print(className);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" object = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        return object;");
            out.println("    }");
            out.println();
            annotatedFieldName.entrySet().forEach(setter -> {
                String methodName = setter.getKey();
                String argumentType = setter.getValue();
                out.println("@Override");
                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" set");
                out.print(methodName);

                out.print("(");

                out.print(argumentType);
                out.println(" value) {");
                out.print("        object.");
                out.print(methodName);
                out.println("(value);");
                out.println("        return this;");
                out.println("    }");
                out.println();
            });

            out.println("}");

        }
    }
/*
    private void writeBuilderFile(Map<String, Map<String, String>> annotatedFieldName) throws IOException {
        for (Map.Entry<String, Map<String, String>> entry : annotatedFieldName.entrySet()) {
            String className = entry.getKey();
            String packageName = null;
            int lastDot = className.lastIndexOf('.');
            if (lastDot > 0) {
                packageName = className.substring(0, lastDot);
            }
            String simpleClassName = className.substring(lastDot + 1);
            String builderClassName = className + "Builder";
            String newSettersAndGettersClassName = builderClassName.substring(lastDot + 1);
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(newSettersAndGettersClassName);
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                if (packageName != null) {
                    out.print("package ");
                    out.print(packageName);
                    out.println(";");
                    out.println();
                }
                out.println("import org.lsi.usthb.domain.crypto.algo.impl.RSA;");
                out.println("import org.lsi.usthb.domain.crypto.model.CryptoOperationType;");
                out.println();
                out.print("public class ");
                out.print(newSettersAndGettersClassName);
                out.println(" {");
                out.println();

                for (Map.Entry<String, String> fields : entry.getValue().entrySet()) {
                    out.print("  private ");
                    out.print(fields.getValue());
                    out.print(" ");
                    out.print(fields.getKey());
                    out.print(";");
                    out.print("  public void set");
                    out.print(firstLetterToUpperCase(fields.getKey()));
                    out.print("(");
                    out.print(fields.getValue());
                    out.print(" ");
                    out.print(fields.getKey());
                    out.print(") {");
                    out.println("  RSA rsa = new RSA();");
                    out.println("byte [] b = rsa.processData(");
                    out.print(fields.getKey());
                    out.print(".getBytes(), CryptoOperationType.ENCRYPT);");
                    out.println();
                    out.print("this.");
                    out.print(fields.getKey());
                    out.print("=");
                    out.print("String.valueOf(b)");
                    out.print(";");
                    out.println("    }");
                    out.println();

                }
                out.println("}");

            }
        }

    }
*/
    private String firstLetterToUpperCase(String s) {
        StringBuilder stringBuilder = new StringBuilder(s);
        StringBuilder newStringBuilder = new StringBuilder();
        newStringBuilder.append(stringBuilder.substring(0, 1).toUpperCase()).append(stringBuilder.substring(1));
        return newStringBuilder.toString();
    }

}