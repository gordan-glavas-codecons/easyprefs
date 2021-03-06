package net.globulus.easyprefs.processor.codegen;

import net.globulus.easyprefs.processor.ExposedMethod;

import java.io.IOException;
import java.util.EnumSet;

import javax.lang.model.element.Modifier;

import javawriter.EzprefsJavaWriter;

public class PrefMethodCodeGen implements CodeGen<ExposedMethod> {

    @Override
    public void generateCode(ExposedMethod method, EzprefsJavaWriter jw) throws IOException {
        jw.emitEmptyLine();
        jw.beginMethod(method.returnType, method.name, EnumSet.of(Modifier.PUBLIC, Modifier.STATIC), method.params);
        StringBuilder params = new StringBuilder();
        for (int i = 1; i < method.params.length; i += 2) {
            if (i > 1) {
                params.append(", ");
            }
            params.append(method.params[i]);
        }
        String start;
        if (method.returnType.equals("void")) {
            start = "";
        } else {
            start = "return ";
        }
        jw.emitStatement("%s%s(%s)", start, method.originalMethod, params.toString());
        jw.endMethod();
    }
}
