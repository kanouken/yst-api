package org.common.tools.db;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具包
 * @author xx
 *
 */

@SuppressWarnings("all")
public class ReflectUtil {
	  private static Logger log = LoggerFactory.getLogger(ReflectUtil.class);

	  private static Object operate(Object obj, String fieldName, Object fieldVal, String type)
	  {
	    Object ret = null;
	    try
	    {
	      Class classType = obj.getClass();

	      Field[] fields = classType.getDeclaredFields();
	      for (int i = 0; i < fields.length; i++)
	      {
	        Field field = fields[i];
	        if (!field.getName().equals(fieldName)) {
	          continue;
	        }
	        String firstLetter = fieldName.substring(0, 1)
	          .toUpperCase();
	        if ("set".equals(type))
	        {
	          String setMethodName = "set" + firstLetter + 
	            fieldName.substring(1);
	          Method setMethod = classType.getMethod(setMethodName, 
	            new Class[] { field.getType() });
	          ret = setMethod.invoke(obj, new Object[] { fieldVal });
	        }
	        if ("get".equals(type))
	        {
	          String getMethodName = "get" + firstLetter + 
	            fieldName.substring(1);
	          Method getMethod = classType.getMethod(getMethodName, 
	            new Class[0]);
	          ret = getMethod.invoke(obj, new Object[0]);
	        }
	        return ret;
	      }

	    }
	    catch (Exception e)
	    {
	      log.warn("reflect error:" + fieldName, e);
	    }
	    return ret;
	  }

	  public static Object getVal(Object obj, String fieldName)
	  {
	    return operate(obj, fieldName, null, "get");
	  }

	  public static void setVal(Object obj, String fieldName, Object fieldVal)
	  {
	    operate(obj, fieldName, fieldVal, "set");
	  }

	  private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes)
	  {
	    for (Class superClass = object.getClass(); superClass != Object.class; )
	    {
	      try
	      {
	        return superClass.getDeclaredMethod(methodName, parameterTypes);
	      }
	      catch (NoSuchMethodException localNoSuchMethodException)
	      {
	        superClass = superClass.getSuperclass();
	      }

	    }

	    return null;
	  }

	  private static void makeAccessible(Field field)
	  {
	    if (!Modifier.isPublic(field.getModifiers()))
	    {
	      field.setAccessible(true);
	    }
	  }

	  private static Field getDeclaredField(Object object, String filedName)
	  {
	    for (Class superClass = object.getClass(); superClass != Object.class; )
	    {
	      try
	      {
	        return superClass.getDeclaredField(filedName);
	      }
	      catch (NoSuchFieldException localNoSuchFieldException)
	      {
	        superClass = superClass.getSuperclass();
	      }

	    }

	    return null;
	  }

	  public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
	    throws InvocationTargetException
	  {
	    Method method = getDeclaredMethod(object, methodName, parameterTypes);

	    if (method == null)
	    {
	      throw new IllegalArgumentException("Could not find method [" + 
	        methodName + "] on target [" + object + "]");
	    }

	    method.setAccessible(true);
	    try
	    {
	      return method.invoke(object, parameters);
	    }
	    catch (IllegalAccessException localIllegalAccessException)
	    {
	    }

	    return null;
	  }

	  public static void setFieldValue(Object object, String fieldName, Object value)
	  {
	    Field field = getDeclaredField(object, fieldName);

	    if (field == null) {
	      throw new IllegalArgumentException("Could not find field [" + 
	        fieldName + "] on target [" + object + "]");
	    }
	    makeAccessible(field);
	    try
	    {
	      field.set(object, value);
	    }
	    catch (IllegalAccessException e)
	    {
	      e.printStackTrace();
	    }
	  }

	  public static Object getFieldValue(Object object, String fieldName)
	  {
	    Field field = getDeclaredField(object, fieldName);
	    if (field == null) {
	      throw new IllegalArgumentException("Could not find field [" + 
	        fieldName + "] on target [" + object + "]");
	    }
	    makeAccessible(field);

	    Object result = null;
	    try
	    {
	      result = field.get(object);
	    }
	    catch (IllegalAccessException e)
	    {
	      e.printStackTrace();
	    }

	    return result;
	  }
	  
	  
	   public static Map<String, Object> transBean2Map(Object obj) {  
		   
	        if(obj == null){  
	            return null;  
	        }          
	        Map<String, Object> map = new HashMap<String, Object>();  
	        try {  
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	            for (PropertyDescriptor property : propertyDescriptors) {  
	                String key = property.getName();  
	  
	                // 过滤class属性  
	                if (!key.equals("class")) {  
	                    // 得到property对应的getter方法  
	                    Method getter = property.getReadMethod();  
	                    Object value = getter.invoke(obj);  
	  
	                    map.put(key, value);  
	                }  
	  
	            }  
	        } catch (Exception e) {  
	            System.out.println("transBean2Map Error " + e);  
	        }  
	  
	        return map;  
	  
	    } 
	  
	  
}
