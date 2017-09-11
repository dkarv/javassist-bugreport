package instr;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class TestInstrumentation implements ClassFileTransformer {
    public static void premain(String argument, Instrumentation instrumentation) {
        instrumentation.addTransformer(new TestInstrumentation());
    }

    public byte[] transform(ClassLoader loader, String className, Class clazz,
                            java.security.ProtectionDomain domain, byte[] bytes) {
        if (className.equals("example/Example")) {
            return transform(bytes);
        } else {
            return bytes;
        }
    }

    private byte[] transform(byte[] b) {
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = null;
        try {
            clazz = pool.makeClass(new ByteArrayInputStream(b));
            for (CtBehavior method : clazz.getDeclaredBehaviors()) {
                transform(method);
            }
            return clazz.toBytecode();
        } catch (CannotCompileException e) {
            System.err.println("Can't compile " + e.getReason());
        } catch (NotFoundException e) {
            System.err.println("Not found " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error" + e.getMessage());
        } finally {
            if (clazz != null) {
                clazz.detach();
            }
        }
        throw new IllegalStateException("Could not finish transformation");
    }

    private void transform(CtBehavior method) throws CannotCompileException, NotFoundException {
        method.insertBefore("System.out.println(\"before " + method.getName() + "\");");
        method.insertAfter("System.out.println(\"after " + method.getName() + "\");", true);
        CtClass etype = ClassPool.getDefault().get("java.lang.Exception");
        method.addCatch("{ System.out.println(\"catch " + method.getName() + "\"); throw $e; }", etype);
    }
}
