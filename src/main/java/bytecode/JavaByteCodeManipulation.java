package bytecode;

import proguard.classfile.*;
import proguard.classfile.constant.Constant;
import proguard.classfile.editor.ClassBuilder;
import proguard.classfile.io.ProgramClassReader;
import proguard.classfile.io.ProgramClassWriter;

import java.io.*;

public class JavaByteCodeManipulation {

    public static void main(String[] args) {
        buildByteCodeFile();
//        parseByteCode();
    }

    private static void buildByteCodeFile() {
        ProgramClass programClass =
                new ClassBuilder(
                        VersionConstants.CLASS_VERSION_17,
                        AccessConstants.PUBLIC,
                        "bytecode/HelloWorld",
                        ClassConstants.NAME_JAVA_LANG_OBJECT)
                        .addMethod(
                                AccessConstants.PUBLIC | AccessConstants.STATIC,
                                "main",
                                "([Ljava/lang/String;)V",
                                50,

                                // Compose the equivalent of this java code:
                                //     System.out.println("Hello, world!");
                                //     System.out.println("Sum: " + sum(5, 3));
                                code ->
                                        code.getstatic("java/lang/System", "out", "Ljava/io/PrintStream;")
                                            .ldc("Hello, world!")
                                            .invokevirtual("java/io/PrintStream", "println", "(Ljava/lang/String;)V")
                                            .getstatic("java/lang/System", "out", "Ljava/io/PrintStream;")
                                            .new_("java/lang/StringBuilder")
                                            .dup()
                                            .invokespecial("java/lang/StringBuilder", "<init>", "()V")
                                            .ldc("Sum: ")
                                            .invokevirtual("java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;")
                                            .iconst(5)
                                            .iconst(3)
                                            .invokestatic("bytecode/HelloWorld", "sum", "(II)I")
                                            .invokevirtual("java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;")
                                            .invokevirtual("java/lang/StringBuilder", "toString", "()Ljava/lang/String;")
                                            .invokevirtual("java/io/PrintStream", "println", "(Ljava/lang/String;)V")
                                            .return_())
                        .addMethod(
                                AccessConstants.PUBLIC | AccessConstants.STATIC,
                                "sum",
                                "(II)I",
                                10,

                                // Compose the equivalent of this java code:
                                //     return a + b;
                                code ->
                                        code.iload_0()
                                            .iload_1()
                                            .iadd()
                                            .ireturn())
                        .getProgramClass();

        try {
            try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("src/main/java/bytecode/HelloWorld.class"))) {
                programClass.accept(new ProgramClassWriter(dataOutputStream));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void parseByteCode() {
        try {
            // Read the class file
            try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream("src/main/java/bytecode/JavaByteCode.class"))) {
                ProgramClass programClass = new ProgramClass();
                ProgramClassReader classReader = new ProgramClassReader(dataInputStream);
                programClass.accept(classReader);

                // Print basic class information
                System.out.println("Class name: " + programClass.getName());
                System.out.println("Super class: " + programClass.getSuperName());
                System.out.println("Access flags: " + programClass.getAccessFlags());
                System.out.println("Version: " + programClass.u4version);

                // Print methods information
                System.out.println("\nMethods:");
                for (int i = 0; i < programClass.u2methodsCount; i++) {
                    Method method = programClass.methods[i];
                    System.out.println("- " + method.getName(programClass) + " " + method.getDescriptor(programClass));
                }

                // Print fields information
                System.out.println("\nFields:");
                for (int i = 0; i < programClass.u2fieldsCount; i++) {
                    Field field = programClass.fields[i];
                    System.out.println("- " + field.getName(programClass) + " " + field.getDescriptor(programClass));
                }

                for (int i = 0; i < programClass.constantPool.length; i++) {
                    Constant constant = programClass.constantPool[i];
                    if (constant != null) {
                        System.out.println("Constant #" + i + ": " + constant.getProcessingFlags() +
                                " Tag: " + constant.getTag());
                    }
                }

            }
        } catch (IOException exception) {
            System.err.println("Error reading class file: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}
