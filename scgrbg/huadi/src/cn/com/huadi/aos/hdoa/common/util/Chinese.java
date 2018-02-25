package cn.com.huadi.aos.hdoa.common.util;

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Chinese.java




// Referenced classes of package huadi:
//            ErrorProcessor

public class Chinese
{

    public Chinese()
    {
    }

    public static String characterSetTranslate(int i, int j, String s)
    {
        String s1 = null;
        String s2 = null;
        if(s == null)
            return null;
        switch(i)
        {
        case 0: // '\0'
            s1 = null;
            break;

        case 1: // '\001'
            s1 = "ISO8859_1";
            break;

        case 2: // '\002'
            s1 = "UTF8";
            break;

        case 3: // '\003'
            s1 = "UTF-16";
            break;

        case 4: // '\004'
            s1 = "EUC_CN";
            break;

        case 5: // '\005'
            s1 = "GBK";
            break;

        case 6: // '\006'
            s1 = "MS936";
            break;
        }
        switch(j)
        {
        case 0: // '\0'
            s2 = null;
            break;

        case 1: // '\001'
            s2 = "ISO8859_1";
            break;

        case 2: // '\002'
            s2 = "UTF8";
            break;

        case 3: // '\003'
            s2 = "UTF-16";
            break;

        case 4: // '\004'
            s2 = "EUC_CN";
            break;

        case 5: // '\005'
            s2 = "GBK";
            break;

        case 6: // '\006'
            s2 = "MS936";
            break;
        }
        try
        {
            if(s1 == null && s2 == null)
            {
                String s4 = s;
                return s4;
            }
            if(s1 != null && s2 == null)
            {
                String s5 = new String(s.getBytes(s1));
                return s5;
            }
            if(s1 == null && s2 != null)
            {
                String s6 = new String(s.getBytes(), s2);
                return s6;
            } else
            {
                String s7 = new String(s.getBytes(s1), s2);
                return s7;
            }
        }
        catch(Exception exception)
        {
            //ErrorProcessor.prompt("huadi.Chinese", "characterSetTranslate() Error: ".concat(String.valueOf(String.valueOf(s))), exception);
        }
        return null;
    }

    public static String ISO8859ToGBK(String s)
    {
        try
        {
            if(s == null)
            {
                String s1 = "";
                return s1;
            } else
            {
                String s2 = new String(s.getBytes("ISO8859_1"), "GBK");
                return s2;
            }
        }
        catch(Exception exception)
        {
            //ErrorProcessor.prompt("huadi.Chinese", "ISO8859ToGBK() Error: ".concat(String.valueOf(String.valueOf(s))), exception);
        }
        return null;
    }
    
    public static String ISO8859ToUTF8(String s)
    {
    	try
    	{
    		if(s == null)
    		{
    			String s1 = "";
    			return s1;
    		} else
    		{
    			String s2 = new String(s.getBytes("ISO8859_1"), "UTF-8");
    			return s2;
    		}
    	}
    	catch(Exception exception)
    	{
    		//ErrorProcessor.prompt("huadi.Chinese", "ISO8859ToGBK() Error: ".concat(String.valueOf(String.valueOf(s))), exception);
    	}
    	return null;
    }

    public static String GBKToISO8859(String s)
    {
        try
        {
            if(s == null)
            {
                String s1 = "";
                return s1;
            } else
            {
                String s2 = new String(s.getBytes("GBK"), "ISO8859_1");
                return s2;
            }
        }
        catch(Exception exception)
        {
           // ErrorProcessor.prompt("huadi.Chinese", "GBKToISO8859 Error: ".concat(String.valueOf(String.valueOf(s))), exception);
        }
        return null;
    }
}
