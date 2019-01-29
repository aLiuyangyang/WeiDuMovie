package com.bw.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:封装网络工具类
 */
public class RetrofitManager<T> {
    private static RetrofitManager mRetrofitManager;
    private final BaseApis mBaseApis;

    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }
    private RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
           @Override
           public Response intercept(Chain chain) throws IOException {
           Request request = chain.request();
           SharedPreferences userMessage = MyApp.getApplication().getSharedPreferences("UserMessage", Context.MODE_PRIVATE);
           String sessionId = userMessage.getString("sessionId", "");
           String userId = userMessage.getString("userId", "");
           Request.Builder newBuilder = request.newBuilder();

           newBuilder.method(request.method(),request.body());

           if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
               newBuilder.addHeader("userId",userId);
               newBuilder.addHeader("sessionId",sessionId);
           }
           Request request1 = newBuilder.build();

           return chain.proceed(request1);
        }
   }
        );
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.Base_Url)
                .client(client)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }

    public void get(String url,HttpListener listener){
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public void delete(String url,HttpListener listener){
        mBaseApis.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public void postFormBodyObject(String url, Map<String,Object> params, List<Object> list, HttpListener listener){

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (list.size()==1) {
            for (int i = 0; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),RequestBody.create(MediaType.parse("multipart/octet-stream"),file));
            }
        }

        mBaseApis.Image(url,params,builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    public void post(String url, Map<String,String> map,HttpListener listener){
        if(map==null){
            map= new HashMap<>();
        }
        mBaseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }

    public void put(String url,Map<String,String> map,HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        mBaseApis.put(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

   public Observer getObserver(final HttpListener listener){
       Observer observer= new Observer<ResponseBody>() {
           @Override
           public void onCompleted() {

           }

           @Override
           public void onError(Throwable e) {
               if(listener!=null){
                   listener.onFail(e.getMessage());
               }
           }

           @Override
           public void onNext(ResponseBody responseBody) {
               try {
                   String data = responseBody.string();
                   if(listener!=null){
                       listener.onSuccess(data);
                   }
               } catch (IOException e) {
                   e.printStackTrace();
                   if(listener!=null){
                       listener.onFail(e.getMessage());
                   }
               }
           }

       };
       return observer;
   }



    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String data);
    }

    private HttpListener listener;

    public void result(HttpListener listener){
        this.listener=listener;
    }



}

