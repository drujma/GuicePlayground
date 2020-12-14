package com.feps.factories;

import com.feps.handlers.HandleMessage;
import com.feps.handlers.IMessageHandler;
import com.feps.messages.IMessage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.invoke.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;


public class MessageHandlerFactory {
    public Map<Class<? extends IMessage>, IMessageHandler<IMessage>> getMessageHandlers() throws ReflectiveOperationException{
        //scan urls that contain 'my.package', include inputs starting with 'my.package', use the default scanners
        Map<Class<? extends IMessage>, IMessageHandler<IMessage>> messageHandlers = new HashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.feps.handlers"))
                .setScanners(new MethodAnnotationsScanner()));
        final Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(HandleMessage.class);

        for (Method m : methodsAnnotatedWith) {
            final Class<?> declaringClass = m.getDeclaringClass();
            final HandleMessage annotation = m.getAnnotation(HandleMessage.class);
            final Class<? extends IMessage> value = annotation.value();
            messageHandlers.put(value, findConsumer(declaringClass, value, m.getName()));
        }

        return messageHandlers;
    }

    private IMessageHandler<IMessage> findConsumer(Class<?> handlerClass, Class<? extends IMessage> messageClass, String methodName) {
        try {
            MethodHandle mh = MethodHandles.lookup().findVirtual(handlerClass,
                    methodName, MethodType.methodType(void.class, messageClass));
            return MethodHandleProxies.asInterfaceInstance(
                    IMessageHandler.class, mh.bindTo(handlerClass.cast(getObjectInstance(getEmptyConstructor(handlerClass)))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Constructor<?> getEmptyConstructor(Class<?> cls) {
        return Arrays.stream(cls.getConstructors())
                .filter(constructor -> constructor.getParameterTypes().length == 0)
                .findFirst()
                .get();
    }

    private Object getObjectInstance(Constructor<?> constructor) throws ReflectiveOperationException{
        return constructor.newInstance();
    }
}
