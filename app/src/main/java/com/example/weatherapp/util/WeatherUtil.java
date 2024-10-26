package com.example.weatherapp.util;

import android.widget.ImageView;

import com.example.weatherapp.R;

/**
 * 天气工具类
 *
 * @author llw
 */
public class WeatherUtil {

    /**
     * 根据传入的状态码修改填入的天气图标
     *
     * @param weatherStateIcon 显示的ImageView
     * @param code             天气状态码
     */
    public static void changeIcon(ImageView weatherStateIcon, int code) {
        switch (code) {
            //晴
            case 100:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_100);
                break;
            //多云
            case 101:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_101);
                break;
            //少云
            case 102:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_102);
                break;
            //晴间多云
            case 103:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_103);
                break;
            //阴 V7
            case 104:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_104);
                break;
            //晴 晚上  V7
            case 150:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_150);
                break;
            //晴间多云 晚上  V7
            case 151://新增
            case 152://新增
            case 153:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_153);
                break;
            //阴 晚上  V7
            case 154:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_154);
                break;
            //有风
            case 200:
                //微风
            case 202:
                //和风
            case 203:
                //清风
            case 204:
                //因为这几个状态的图标是一样的
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_200);
                break;
            //平静
            case 201:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_201);
                break;
            //强风/劲风
            case 205:
                //疾风
            case 206:
                //大风
            case 207:
                //因为这几个状态的图标是一样的
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_205);
                break;
            //烈风
            case 208:
                //风暴
            case 209:
                //狂爆风
            case 210:
                //飓风
            case 211:
                //龙卷风
            case 212:
                //热带风暴
            case 213:
                //因为这几个状态的图标是一样的
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_208);
                break;
            //阵雨
            case 300:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_300);
                break;
            //强阵雨
            case 301:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_301);
                break;
            //雷阵雨
            case 302:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_302);
                break;
            //强雷阵雨
            case 303:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_303);
                break;
            //雷阵雨伴有冰雹
            case 304:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_304);
                break;
            //小雨
            case 305:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_305);
                break;
            //中雨
            case 306:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_306);
                break;
            //大雨
            case 307:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_307);
                break;
            //极端降雨
            case 308:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_312);
                break;
            //毛毛雨/细雨
            case 309:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_309);
                break;
            //暴雨
            case 310:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_310);
                break;
            //大暴雨
            case 311:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_311);
                break;
            //特大暴雨
            case 312:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_312);
                break;
            //冻雨
            case 313:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_313);
                break;
            //小到中雨
            case 314:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_306);
                break;
            //中到大雨
            case 315:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_307);
                break;
            //大到暴雨
            case 316:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_310);
                break;
            //大暴雨到特大暴雨
            case 317:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_312);
                break;
            //雨
            case 399:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_399);
                break;
            //小雪
            case 400:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_400);
                break;
            //中雪
            case 401:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_401);
                break;
            //大雪
            case 402:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_402);
                break;
            //暴雪
            case 403:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_403);
                break;
            //雨夹雪
            case 404:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_404);
                break;
            //雨雪天气
            case 405:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_405);
                break;
            //阵雨夹雪
            case 406:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_406);
                break;
            //阵雪
            case 407:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_407);
                break;
            //小到中雪
            case 408:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_408);
                break;
            //中到大雪
            case 409:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_409);
                break;
            //大到暴雪
            case 410:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_410);
                break;
            //雪
            case 499:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_499);
                break;
            //薄雾
            case 500:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_500);
                break;
            //雾
            case 501:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_501);
                break;
            //霾
            case 502:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_502);
                break;
            //扬沙
            case 503:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_503);
                break;
            //扬沙
            case 504:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_504);
                break;
            //沙尘暴
            case 507:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_507);
                break;
            //强沙尘暴
            case 508:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_508);
                break;
            //浓雾
            case 509:
                //强浓雾
            case 510:
                //大雾
            case 514:
                //特强浓雾
            case 515:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_509);
                break;
            //中度霾
            case 511:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_511);
                break;
            //重度霾
            case 512:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_512);
                break;
            //严重霾
            case 513:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_513);
                break;
            //热
            case 900:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_900);
                break;
            //冷
            case 901:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_901);
                break;
            //未知
            default:
                weatherStateIcon.setBackgroundResource(R.mipmap.icon_999);
                break;
        }
    }
}
