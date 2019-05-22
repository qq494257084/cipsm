package com.tiens.cipsm.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author liyaling
 * @Email ts_liyaling@qq.com
 * @Date 2019/4/18 16:09
 * @Description //TODO
 */
public class MyUtils {
    private static Toast toast;

    private MyUtils() {

    }

    public static boolean isBlank(CharSequence charSequence) {
        if (charSequence == null)
            return true;
        String s = String.valueOf(charSequence);
        return s.length() == 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null)
            return true;
        String s = String.valueOf(charSequence);
        return s.length() == 0 || s.trim().length() == 0;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    /**
     * 全透状态栏
     */
    public static void setStatusBarFullTransparent(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static void BaiduMapInitialize(Context context) {
        SDKInitializer.initialize(context.getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int hpx2hpx(Context context, float hpxValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        return (int) (hpxValue * heightPixels / MyStaticVariable.UIHEIGHT);
    }

    public static int wpx2wpx(Context context, float wpxValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return (int) (wpxValue * widthPixels / MyStaticVariable.UIWIDTH);
    }

    public static void wViewAdapter(Context context, View view, float wpxValue) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = wpx2wpx(context, wpxValue);
        view.setLayoutParams(layoutParams);
    }

    public static void hViewAdapter(Context context, View view, float hpxValue) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = hpx2hpx(context, hpxValue);
        view.setLayoutParams(layoutParams);
    }

    public static void ViewAdapter(Context context, View view, float wpxValue, float hpxValue) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = wpx2wpx(context, wpxValue);
        layoutParams.height = hpx2hpx(context, hpxValue);
        view.setLayoutParams(layoutParams);
    }

    public static void TextAdapter(Context context, TextView textView, float pxValue) {
        int hpx = hpx2hpx(context, pxValue);
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextSize(px2sp(context, Float.parseFloat("" + hpx)));
    }

    public static void singleLoginDialog() {
        if (MyStaticVariable.currentContext == null) {
            System.exit(0);
            return;
        }
        new AlertDialog.Builder(MyStaticVariable.currentContext)
                .setTitle("异常信息")
                .setMessage("账号在异地登录，请重新登录。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        System.exit(0);
                    }
                })
                .show();
        return;
    }


    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
    }

    public static String dateTimeToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
    }

    //写一个md5加密的方法
    public static String md5(byte[] bytes) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(bytes);
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * @param url    url地址
     * @param method 请求方法
     * @return 返回对应的HttpURLConnection
     */
    public static HttpURLConnection getHttpURLConnection(String url, String method) {
        return getHttpURLConnection(url, method, MyStaticVariable.CONNECTTIMEOUT);
    }

    /**
     * @param urls    url地址
     * @param method  请求方法
     * @param timeout 连接超时时间，单位：毫秒
     * @return 返回对应的HttpURLConnection
     */
    public static HttpURLConnection getHttpURLConnection(String urls, String method, int timeout) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urls);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setReadTimeout(timeout);
            httpURLConnection.setConnectTimeout(timeout);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }

    /**
     * @param urls   url地址
     * @param method 请求方法
     * @param param  请求参数
     * @return 返回对应的HttpURLConnection
     */
    public static HttpURLConnection getHttpURLConnectionWithParam(String urls, String method, String param) {
        return getHttpURLConnectionWithParam(urls, method, param, MyStaticVariable.CONNECTTIMEOUT);
    }

    /**
     * @param urls    url地址
     * @param method  请求方法
     * @param param   请求参数
     * @param timeout 连接超时时间，单位：毫秒
     * @return 返回对应的HttpURLConnection
     */
    public static HttpURLConnection getHttpURLConnectionWithParam(String urls, String method, String param, int timeout) {
        HttpURLConnection httpURLConnection = getHttpURLConnection(urls, method, timeout);
        httpURLConnection.setRequestProperty("Connection", "close");
        if (MyUtils.isNotEmpty(param))
            try {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(param);
                dataOutputStream.flush();
                dataOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return httpURLConnection;
    }

    /**
     * @param urls   url地址
     * @param method 请求方法
     * @param param  请求参数
     * @param clazz  转换之后的实体类
     * @param <T>    实体类类型
     * @return 返回列表结果
     */
    public static <T> List<T> getListByURLWithParam(String urls, String method, String param, Class<T> clazz) {
        return getListByURLWithParam(urls, method, param, MyStaticVariable.CONNECTTIMEOUT, clazz);
    }

    /**
     * @param urls    url地址
     * @param method  请求方法
     * @param param   请求参数
     * @param timeout 连接超时时间，单位：毫秒
     * @param clazz   转换之后的实体类
     * @param <T>     实体类类型
     * @return 返回列表结果
     */
    public static <T> List<T> getListByURLWithParam(String urls, String method, String param, int timeout, Class<T> clazz) {
        List<T> list = new LinkedList<>();
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnectionWithParam(urls, method, param, timeout);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Map<String, Object> map = JSON.parseObject(stringBuilder.toString());
            Object list1 = map.get("list");
            if (list1 != null) {
                JSONArray jsonArray = (JSONArray) list1;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    T t1 = jsonObject.toJavaObject(clazz);
                    list.add(t1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param urls    url地址
     * @param method  请求方法
     * @param param   请求参数
     * @param timeout 连接超时时间，单位：毫秒
     * @return 返回映射结果
     */
    public static Map<String, Object> getMapByURLWithParam(String urls, String method, String param, int timeout) {
        Map<String, Object> map = new HashMap<>();
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnectionWithParam(urls, method, param, timeout);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            map = JSON.parseObject(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private static String searchDistrictCode(String district) {
        String result;
        Pattern compile = Pattern.compile("[^\\d]*?" + district + "[^\\d]*?:[\\d]+");
        Matcher matcher = compile.matcher(MyStaticVariable.CITYCODE);
        if (matcher.find()) {
            result = matcher.group();
            return "http://www.weather.com.cn/data/cityinfo/" + result.split(":")[1] + ".html";
        }
        return null;
    }

    public static void toast(String msg, int duration) {
        if (toast == null)
            toast = Toast.makeText(MyStaticVariable.applicationContext, msg, duration);
        toast.setText(msg);
        toast.show();
    }

    public static void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 根据经纬度计算面积
     * @param list 经纬度列表
     * @return 面积值（平方米）
     */
    public static BigDecimal caculateArea(List<LatLng> list) {
        int i = 0;
        BigDecimal temp = new BigDecimal(0);
        for (; i < list.size() - 1; i++) {
            BigDecimal b1 = new BigDecimal(list.get(i).latitude - list.get(i + 1).latitude);
            BigDecimal b2 = new BigDecimal(list.get(i).longitude + list.get(i + 1).longitude);
            temp = temp.add(b1.multiply(b2));
        }
        BigDecimal b1 = new BigDecimal(list.get(i).latitude - list.get(0).latitude);
        BigDecimal b2 = new BigDecimal(list.get(i).longitude + list.get(0).longitude);
        temp = temp.add(b1.multiply(b2));
        BigDecimal result = temp.divide(new BigDecimal(2), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(9101160000.085981));
        return result;
    }
}
