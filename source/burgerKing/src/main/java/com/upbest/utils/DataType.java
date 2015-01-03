package com.upbest.utils;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataType {

    private static transient Log logger = LogFactory.getLog(DataType.class);
    /**
     * 数据类型
     */
    public static final String DATATYPE_STRING = "String";
    public static final String DATATYPE_SHORT = "Short";
    public static final String DATATYPE_INTEGER = "Integer";
    public static final String DATATYPE_LONG = "Long";
    public static final String DATATYPE_DOUBLE = "Double";
    public static final String DATATYPE_FLOAT = "Float";
    public static final String DATATYPE_BYTE = "Byte";
    public static final String DATATYPE_CHAR = "Char";
    public static final String DATATYPE_BOOLEAN = "Boolean";
    public static final String DATATYPE_DATE = "Date";
    public static final String DATATYPE_TIME = "Time";
    public static final String DATATYPE_DATETIME = "DateTime";
    public static final String DATATYPE_OBJECT = "Object";

    public static final String DATATYPE_short = "short";
    public static final String DATATYPE_int = "int";
    public static final String DATATYPE_long = "long";
    public static final String DATATYPE_double = "double";
    public static final String DATATYPE_float = "float";
    public static final String DATATYPE_byte = "byte";
    public static final String DATATYPE_char = "char";
    public static final String DATATYPE_boolean = "boolean";

    public static final String DATA_FORMAT_yyyyMMdd = "yyyy-MM-dd";
    public static final String DATA_FORMAT_HHmmss = "HH:mm:ss";
    public static final String DATA_FORMAT_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static Object transfer(Object value, Class type) {
        if (value == null)
            return null;
        if ((value instanceof String) && (value.toString().trim().equals(""))) {
            if (String.class.equals(type))
                return value;
            else
                return null;
        }
        if (type.equals(Short.class) || type.equals(short.class)) {
            if (value instanceof Short)
                return value;
            else
                return new Short(new java.math.BigDecimal(value.toString()).shortValue());
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            if (value instanceof Integer)
                return value;
            else
                return new Integer(new java.math.BigDecimal(value.toString()).intValue());
        } else if (type.equals(Character.class) || type.equals(char.class)) {
            if (value instanceof Character)
                return value;
            else
                return new Character(value.toString().charAt(0));
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            if (value instanceof Long)
                return value;
            else
                return new Long(new java.math.BigDecimal(value.toString()).longValue());
        } else if (type.equals(String.class)) {
            if (value instanceof String)
                return value;
            else
                return value.toString();
        } else if (type.equals(java.util.Date.class)) {
            if (value instanceof java.util.Date)
                return value;
            else {
                try {

                    return new java.util.Date(value.toString());
                } catch (Exception e) {
                    String msg = "datatype 'Date' transform error: " + value.toString();
                    throw new RuntimeException(msg);
                }
            }
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            if (value instanceof Double)
                return value;
            else
                return new Double(new java.math.BigDecimal(value.toString()).doubleValue());
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            if (value instanceof Float)
                return value;
            else
                return new Float(new java.math.BigDecimal(value.toString()).floatValue());
        } else if (type.equals(Byte.class) || type.equals(byte.class)) {
            if (value instanceof Byte)
                return value;
            else
                return new Byte(new java.math.BigDecimal(value.toString()).byteValue());
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            if (value instanceof Boolean)
                return value;
            else if (value instanceof java.lang.Number) {
                if (((Number) value).doubleValue() > 0)
                    return new Boolean(true);
                else
                    return new Boolean(false);
            } else if (value instanceof String) {
                if (((String) value).equalsIgnoreCase("true") || ((String) value).equalsIgnoreCase("y"))
                    return new Boolean(true);
                else
                    return new Boolean(false);
            } else {
                String msg = "datatype 'Boolean' transform error: " + value.toString();
                throw new RuntimeException(msg);
            }
        } else {
            return value;
        }
    }

    public static String getAsString(Object obj) {
        if (obj == null)
            return null;
        else
            return obj.toString();
    }

    // 当obj是null时返回""
    public static String getAsStringNotNull(Object obj) {
        if (obj == null) {
            return "";
        } else
            return obj.toString();
    }

    public static short getAsShort(Object obj) {
        if (obj == null) // 对象为空返回0
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).shortValue();
        else
            return ((Short) transfer(obj, Short.class)).shortValue();
    }

    public static int getAsInt(Object obj) {
        if (obj == null) // 对象为空返回0
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).intValue();
        else
            return ((Integer) transfer(obj, Integer.class)).intValue();
    }

    public static long getAsLong(Object obj) {
        if (obj == null) // 对象为空返回0
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).longValue();
        if (obj.equals("Null") || obj.equals(""))
            return 0;

        else {
            return ((Long) transfer(obj, Long.class)).longValue();
        }
    }

    public static double getAsDouble(Object obj) {
        if (obj == null) // 对象为空返回0
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).doubleValue();
        else
            return ((Double) transfer(obj, Double.class)).doubleValue();
    }

    public static float getAsFloat(Object obj) {
        if (obj == null) // 对象为空返回0
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).floatValue();
        if (obj.equals("Null") || obj.equals(""))
            return 0;
        else
            return ((Float) transfer(obj, Float.class)).floatValue();
    }

    public static byte getAsByte(Object obj) {
        if (obj == null) // 对象为空返回0
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).byteValue();
        else
            return ((Byte) transfer(obj, Byte.class)).byteValue();
    }

    public static boolean getAsBoolean(Object obj) {
        if (obj == null) // 对象为空返回false
            return false;
        if (obj instanceof Boolean)
            return ((Boolean) obj).booleanValue();
        if (obj instanceof Character) {
            Character c = (Character) obj;
            if (c == 'T' || c == 't')
                return true;
            else
                return false;
        }

        else
            return ((Boolean) transfer(obj, Boolean.class)).booleanValue();
        // throw new RuntimeException("数据不是一个Boolean对象，不能转换为boolean类型");
    }

    public static char getAsChar(Object obj) {
        if (obj == null) // 对象为空返回false
            return '0';
        if (obj instanceof Character)
            return ((Character) obj).charValue();
        else if ((obj instanceof String) && (((String) obj).length() == 1)) {
            return ((String) obj).charAt(0);
        } else if (obj instanceof Boolean) {
            if ((Boolean) obj)
                return 'T';
            else
                return 'F';
        } else
            return ((Character) transfer(obj, Character.class)).charValue();
    }

    public static java.util.Date getAsDate(Object obj) {
        // logger.info("date:"+obj);
        if (obj == null)
            return null;
        if (obj instanceof java.util.Date)
            return (java.util.Date) obj;
        if (obj instanceof String) {
            try {
                // System.out.println("date is:"+obj);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.parse((String) obj);
                // }catch (ParseException e){
                // throw new IllegalArgumentException(e);
            } catch (Exception e) {
                logger.error("can't convert to date:" + obj + ",return null");
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
                //return null;
            }

        }
        if (obj instanceof Number) {
            return new java.util.Date(getAsLong(obj));
        } else {
            String msg = "datatype 'Date' transform error: " + obj.toString();
            throw new RuntimeException(msg);
        }
    }
    public static java.util.Date getAsDate2(Object obj) {
        // logger.info("date:"+obj);
        if (obj == null)
            return null;
        if (obj instanceof java.util.Date)
            return (java.util.Date) obj;
        if (obj instanceof String) {
            try {
                // System.out.println("date is:"+obj);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.parse((String) obj);
                // }catch (ParseException e){
                // throw new IllegalArgumentException(e);
            } catch (Exception e) {
                logger.error("can't convert to date:" + obj + ",return null");
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
                //return null;
            }

        }
        if (obj instanceof Number) {
            return new java.util.Date(getAsLong(obj));
        } else {
            String msg = "datatype 'Date' transform error: " + obj.toString();
            throw new RuntimeException(msg);
        }
    }
    
    public static java.util.Date getAsDate(Object obj,String pattern) {
        // logger.info("date:"+obj);
        if (obj == null)
            return null;
        if (obj instanceof java.util.Date)
            return (java.util.Date) obj;
        if (obj instanceof String) {
            try {
                // System.out.println("date is:"+obj);
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                return dateFormat.parse((String) obj);
                // }catch (ParseException e){
                // throw new IllegalArgumentException(e);
            } catch (Exception e) {
                logger.error("can't convert to date:" + obj + ",return null");
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
                //return null;
            }

        }
        if (obj instanceof Number) {
            return new java.util.Date(getAsLong(obj));
        } else {
            String msg = "datatype 'Date' transform error: " + obj.toString();
            throw new RuntimeException(msg);
        }
    }

    public static String getAsDateFormat(String dataformat, Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof java.util.Date)
            return " to_date('" + obj.toString() + "', '" + dataformat + "')";
        else {
            String msg = "datatype 'Date' transform error: " + obj.toString();
            throw new RuntimeException(msg);
        }
    }

    public static String getAsDateFormat_yyyyMMddHHmmss(Object obj) {
        return getAsDateFormat(DataType.DATA_FORMAT_yyyyMMddHHmmss, obj);
    }

    public static BigDecimal getAsBigDecimal(Object o) {
        return o == null ? null : new BigDecimal(String.valueOf(o));
    }

    public static BigDecimal getAsBigDecimal(Float f) {
        return f == null ? null : new BigDecimal(String.valueOf(f));
    }

    public static BigDecimal getAsBigDecimal(Double d) {
        return d == null ? null : new BigDecimal(String.valueOf(d));
    }

    // DecimalFormat format = new DecimalFormat(".0000");
    public static BigDecimal getAsBigDecimal(Object o, DecimalFormat format) {
        return o == null ? null : new BigDecimal(format.format(o));
    }
    
    public static String formatDate(Date date,String pattern){
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	return sdf.format(date);
    }

    /*
     * Transform from date to String
     * 
     */

    public static String getTimeStr_yyyyMMddHHmmss(java.util.Date d) {
        String timeStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeStr = sdf.format(d);

        } catch (Exception e) {
            throw new RuntimeException("Date format yyyy-MM-dd HH:mm:ss error!");
        }
        return timeStr;

    }

    /*
     * for NBI DateString format
     * we get String from Database like "2010-12-25 14:10:07.0"
     * input   2010-12-25 14:10:07.0
     * output  2010-12-25 14:10:07
     */
    public static String formatDateStrToHHmmss(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof java.util.Date) {
            String timeStrFrDatabase = obj.toString();
            String formatStr = timeStrFrDatabase.toString();
            if (timeStrFrDatabase != null && timeStrFrDatabase.endsWith(".0")) {
                formatStr = timeStrFrDatabase.substring(0, timeStrFrDatabase.lastIndexOf('.'));
            }
            return formatStr;
        } else {
            String msg = "datatype 'Date' transform error: " + obj.toString();
            throw new RuntimeException(msg);
        }

    }

    /*
     * for NBI Float format
     * we get data from Database like "0.0969772599999999956876806095351639669388"
     * these data was calculated by avg function, and was not cut off with 4 bit precise number
     * input   0.0969772599999999956876806095351639669388
     * output  0.0969
     * however this is a little unreasonable, because we didn't use "四舍五入"
     * if our oracle database store precised float data, then i didn't do this any more!
     */
    public static String formatFloatStrTo4bitOLD(String floatNum) {
        String formatFloat = floatNum;
        if (floatNum != null) {
            int index = floatNum.lastIndexOf('.');
            if (index > 0) {
                int cutBit = (index + 5 > floatNum.length()) ? floatNum.length() : index + 5;
                formatFloat = floatNum.substring(0, cutBit);
            }
        }
        return formatFloat;
    }

    public static String formatFloatStrTo4bit(String floatNum) {
        String formatFloat = floatNum;
        double originNum = getAsDouble(floatNum);
        double round4 = Math.round(originNum * 10000) / 10000;
        formatFloat = String.valueOf(round4);
        return formatFloat;

    }

    public static String round4Str(BigDecimal floatNum) {
        if (floatNum == null) {
            return null;
        }
        String formatFloat = floatNum.toString();
        double originNum = floatNum.doubleValue();
        double round4 = Math.round(originNum * 10000) / 10000.0;
        formatFloat = String.valueOf(round4);
        return formatFloat;
    }

    public static long str2Long(String str, long defaultValue) {
        if (str != null && str.trim().matches("^\\d+$")) {
            return Long.parseLong(str.trim());
        }
        return defaultValue;
    }

    public static double str2Double(String str, double defaultValue) {
        if (str != null && str.trim().matches("^\\d+(\\.\\d+)?$")) {
            return Double.parseDouble(str.trim());
        }
        return defaultValue;
    }

    public static int str2Int(String str, int defaultValue) {
        if (str != null && str.trim().matches("^\\d+$")) {
            return Integer.parseInt(str.trim());
        }
        return defaultValue;
    }
    
}
