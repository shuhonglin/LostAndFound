package com.moonlight.nasa.lostandfound;

import in.srain.cube.request.*;
import in.srain.cube.util.CLog;

public class DemoRequestData {

    public static void getImageList(final RequestFinishHandler<JsonData> requestFinishHandler) {


        CacheAbleRequestHandler requestHandler = new CacheAbleRequestJsonHandler() {

            //缓存数据加载完回调
            @Override
            public void onCacheData(JsonData data, boolean outOfDate) {
                CLog.d("demo-request",
                        "data has been loaded form cache, out of date: %s", outOfDate);
            }

            //数据加载完回调。
            @Override
            public void onCacheAbleRequestFinish(JsonData data, CacheAbleRequest.ResultType type, boolean outOfDate) {
                requestFinishHandler.onRequestFinish(data);
                CLog.d("demo-request",
                        "onCacheAbleRequestFinish: result type: %s, out of date: %s", type, outOfDate);
            }

            @Override
            public JsonData processOriginData(JsonData jsonData) {
                return jsonData;
            }

            //数据加载失败回调
            @Override
            public void onRequestFail(FailData requestResultType) {
                CLog.d("demo-request", "onRequestFail");
            }

            //服务器数据加载完回调
            @Override
            public void onRequestFinish(JsonData data) {
                CLog.d("demo-request", "onRequestFinish");
            }
        };

        CacheAbleRequest<JsonData> request = new CacheAbleRequest<JsonData>(requestHandler);
        String url = "http://cube-server.liaohuqiu.net/api_demo/image-list.php";
        request.setCacheTime(3600);
        request.setTimeout(1000);
        request.getRequestData().setRequestUrl(url);
        request.setAssertInitDataPath("image-list.json");
        request.setCacheKey("image-list");
        request.send();
    }



}
