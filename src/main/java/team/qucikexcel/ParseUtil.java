package team.qucikexcel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过反射解析对象
 * Created by lzf on 2017/8/7.
 */
class ParseUtil {

    /**
     * 获取类属性
     * @param className
     * @return
     */
     protected static List<String> getProperties(String className){
        List<String> list = new ArrayList<String>();//存放类属性
        try {
            Class<?> classType = Class.forName(className);
            Field[] fields = classType.getDeclaredFields();//获取类字段
            for (int i = 0; i < fields.length; i++){
                Field fieldName = fields[i];
                String property = fieldName.getName();//获取属性名
                list.add(property);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取首字母大写属性
     * @param className
     * @return
     */
    protected static List<String> getUpperProperties(String className){
        List<String> list = getProperties(className);
        List<String> l = new ArrayList<String>();
        for(String property: list){
            String str = property.substring(0,1);
            property = property.replaceFirst(str,str.toUpperCase());//第一个字母大写化
            l.add(property);
        }
        return l;
    }
}
