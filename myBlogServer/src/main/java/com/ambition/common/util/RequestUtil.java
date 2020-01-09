package com.ambition.common.util;


import com.ambition.common.enums.BrowserTypeEnum;
import com.ambition.common.enums.DeviceTypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <pre>
 *
 * @title :
 * @description : 请求工具类
 * @company :
 * @author :
 * @time: 2017年9月29日 下午1:53:49
 * </pre>
 */
public class RequestUtil {
    /**
     * 获取用户请求url中的ip：port/工程名
     *
     * @param request
     * @return
     * @Description
     * @author 张国栋
     */
    public static String getServerHost(HttpServletRequest request) {
        String host = request.getHeader("HostPort");

        if (host == null || host.length() == 0 || "unknown".equalsIgnoreCase(host)) {
            return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        } else {
            return request.getScheme() + "://" + host + request.getContextPath();
        }

    }

    /**
     * 获取用户真实ip
     *
     * @param request
     * @return
     * @Description
     * @author 张国栋
     */
    public static String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，中第一个非unknown的有效IP字符串
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                String[] ipArray = ipAddress.split(",");
                for (String ip : ipArray) {
                    if (!"unknown".equalsIgnoreCase(ipAddress)) {
                        ipAddress = ip;
                        break;
                    }
                }
            }
        }
        return ipAddress;
    }

    /**
     * 获取浏览器类型
     *
     * @param request
     * @return
     * @Description
     * @author 张国栋
     */
    public static BrowserTypeEnum getBrowserType(HttpServletRequest request) {
        // 获取浏览器类型
        BrowserTypeEnum browserType = BrowserTypeEnum.OTHER_TYPE;
        String userAgent = request.getHeader("user-agent");
        // 待扩展TODO
        if (userAgent == null || "".equals(userAgent)) {
            browserType = null;
        } else if (userAgent.indexOf("MSIE") > 0) {
            browserType = BrowserTypeEnum.IE_TYPE;
        } else if (userAgent.indexOf("Firefox") > 0) {
            browserType = BrowserTypeEnum.HUOHU_TYPE;
        } else if (userAgent.indexOf("Chrome") > 0) {
            browserType = BrowserTypeEnum.CHROME_TYPE;
        } else if (userAgent.indexOf("Safari") > 0) {
            browserType = BrowserTypeEnum.IE_PHONE_TYPE;
        } else if (userAgent.indexOf("Camino") > 0) {
            browserType = BrowserTypeEnum.OTHER_TYPE;
        } else if (userAgent.indexOf("Konqueror") > 0) {
            browserType = BrowserTypeEnum.OTHER_TYPE;
        }
        return browserType;
    }

    public static DeviceTypeEnum getDeviceType(HttpServletRequest request) {
        DeviceTypeEnum deviceType = DeviceTypeEnum.OTHER;
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("Android")) {
            deviceType = DeviceTypeEnum.ANDROID;
        } else if (userAgent.contains("iPhone")) {
            deviceType = DeviceTypeEnum.IPHONE;
        } else if (userAgent.contains("iPad")) {
            deviceType = DeviceTypeEnum.IPAD;
        }

        return deviceType;
    }

    public static String getRequestPath(HttpServletRequest request) {
        if (request != null) {
            return request.getServletPath();
        }
        return null;
    }

//    public static void notNoll(String code, Object... objArray) {
//        for(Object obj:objArray){
//            if (obj == null) {
//                throw new OpBusinessException(code);
//            }
//        }
//    }

//    public static void notStringNoll(String str, String code) {
//        if (StringUtils.isBlank(str)) {
//            throw new OpBusinessException(code);
//        }
//
//    }

//    public static <E> void checkNotNullOrMaxSizeByCollection(Collection<E> obj, String code, Integer maxSize) {
//        if (CollectionUtils.isEmpty(obj) || obj.contains(null) || obj.size() > maxSize) {
//            throw new OpBusinessException(code);
//        }
//
//    }
}
