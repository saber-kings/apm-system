package cn.saberking.jvav.apm.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Saber污妖王
 * TODO: 自定义 Bean 操作工具类
 * @version 0.0.1
 * @date 2020/6/10
 * @description 注意，当复制和被复制任意一个对象包含了 @Accessors(chain = true) 注解，
 * 则不能使用CGLIB的BeanCopier实现的copy，可以使用spring的BeanUtils实现的copy
 */
@Slf4j
public class BeanUtils {
    /**
     * BeanCopier 的缓存
     */
    static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 从配置文件中获取指定的属性拷贝方式：
     * 1：Cglib 的 BeanCopier，2：我的，3：带类型转换器的，其他：Spring 的 BeanUtils
     */
    @Value("${bean.copy.type}")
    private static int beanCopyType;

    /**
     * Cglib 的属性拷贝方式
     */
    private static final int CGLIB_COPY = 1;

    /**
     * 我的属性拷贝方式
     */
    private static final int MY_COPY = 2;

    /**
     * 带转换器的属性拷贝方式，即属性名相同，类型不同，也能拷贝
     */
    private static final int CONVERT_COPY = 3;

    static {
        if (beanCopyType == CGLIB_COPY) {
            // 忽略非法反射警告
            disableAccessWarnings();
        }
    }

    /**
     * 忽略非法反射警告  适用于jdk11
     */
    private static void disableAccessWarnings() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

            Class<?> loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }

    /**
     * 属性拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        switch (beanCopyType) {
            case CGLIB_COPY:
                cglibCopy(source, target, false);
                break;
            case MY_COPY:
                myCopy(source, target);
                break;
            case CONVERT_COPY:
                // 当源和目标类的属性类型不同时，可以通过自定义转换器拷贝该属性的值
                cglibCopy(source, target, true);
                break;
            default:
                // 其他情况一律采用 Spring 的属性拷贝方式
                springCopy(source, target);
                break;
        }
    }

    /**
     * Spring 自带 BeanCopier 即 Cglib 的 copy 实现的属性拷贝，最快
     *
     * @param source       源文件的
     * @param target       目标文件
     * @param useConverter 是否使用类型转换器
     */
    private static void cglibCopy(Object source, Object target, boolean useConverter) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        String key = genKey(source.getClass(), target.getClass());
        BeanCopier beanCopier;
        if (BEAN_COPIER_CACHE.containsKey(key)) {
            beanCopier = BEAN_COPIER_CACHE.get(key);
        } else {
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), useConverter);
            BEAN_COPIER_CACHE.put(key, beanCopier);
        }
        if (useConverter) {
            beanCopier.copy(source, target, new CommConverter());
        } else {
            beanCopier.copy(source, target, null);
        }
    }

    /**
     * 生成key
     *
     * @param srcClazz 源文件的class
     * @param tgtClazz 目标文件的class
     * @return string
     */
    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz) {
        return srcClazz.getName() + tgtClazz.getName();
    }

    /**
     * 自写属性拷贝，最慢
     *
     * @param source 源文件的
     * @param target 目标文件
     */
    public static void myCopy(Object source, Object target) {
        Map<String, Object> sourceMap = new HashMap<>(8);
        Field[] sourceFields = source.getClass().getDeclaredFields();
        for (Field sourceField : sourceFields) {
            try {
                //去除static final属性（serialVersionUID）
                if (Modifier.isFinal(sourceField.getModifiers())) {
                    continue;
                }
                sourceField.setAccessible(true);
                //获取属性值
                sourceMap.put(sourceField.getName(), sourceField.get(source));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Field[] targetFields = getAllFields(target.getClass());

        for (Field targetField : targetFields) {
            targetField.setAccessible(true);
            if (sourceMap.get(targetField.getName()) == null) {
                continue;
            }
            try {
                //给属性赋值
                targetField.set(target, sourceMap.get(targetField.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取该类的属性及父类属性
     *
     * @param clazz 该类
     * @return 该类的属性数值
     */
    private static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }

    /**
     * Spring 自带 BeanUtils 实现的属性拷贝，第二快
     *
     * @param source 源文件的
     * @param target 目标文件
     */
    public static void springCopy(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
}
