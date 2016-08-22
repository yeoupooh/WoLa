package com.subakstudio.rx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import com.subakstudio.wola.model.WolaConfig;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.FuncN;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static javax.xml.ws.Endpoint.create;

/**
 * Created by jinwoomin on 5/4/16.
 */
public class RxJavaTest {
    private WolaConfig config;

    @Test
    public void testCreate() throws Exception {
        Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(String.valueOf(i));
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            public void onError(Throwable e) {
                System.out.println("onError");
            }

            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }
        });
    }

    @Test
    public void testLoginAndPost() throws Exception {
        loadConfig(getConfigFileStream()).subscribe(new Subscriber<WolaConfig>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WolaConfig wolaConfig) {
                config = wolaConfig;
            }
        });
        OkHttpClient client = new OkHttpClient();
        Observable<String> obLogin = login(
                client,
                config.hosts.get(2).options.getValue("url"),
                config.hosts.get(2).options.getValue("username"),
                config.hosts.get(2).options.getValue("password")
        );
        Observable<String> obPost = post(client);
        obPost.startWith(obLogin)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("login and post: oncomplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("login and post: onerror: e=" + e);

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("login and post: onnext: s=" + s);
                    }
                });
    }

    private InputStream getConfigFileStream() {
        return getClass().getResourceAsStream("/wola.config.json");
    }

    @Test
    public void testLoadConfig() {
        loadConfig(getClass().getResourceAsStream("/wola.config.json"))
                .subscribe(new Subscriber<WolaConfig>() {
                    public void onCompleted() {
                        System.out.println("loadconfig: oncomplete");
                    }

                    public void onError(Throwable e) {
                        System.out.println("loadconfig: onerror: e=" + e);
                    }

                    public void onNext(WolaConfig wolaConfig) {
                        System.out.println("loadconfig: onnext: config=" + wolaConfig);
                    }
                });
    }

    private Observable<WolaConfig> loadConfig(final InputStream inputStream) {
        return Observable.create(new Observable.OnSubscribe<WolaConfig>() {
            public void call(Subscriber<? super WolaConfig> subscriber) {
                ObjectMapper om = new ObjectMapper();
                try {
                    WolaConfig config = om.readValue(inputStream, WolaConfig.class);
                    subscriber.onNext(config);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<String> post(OkHttpClient client) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
//                Request request = new Request.Builder()
//                        .url(url)
//                        .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//                        .addHeader("Accept-Encoding", "gzip, deflate, sdch")
//                        .addHeader("Accept-Language", "en-US,en;q=0.8,ko;q=0.6")
//                        .addHeader("Cache-Control", "no-cache")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Pragma", "no-cache")
//                        .addHeader("Upgrade-Insecure-Requests", "1")
//                        .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36")
//                        .build();
//                Response response = null;
//                try {
//                    response = client.newCall(request).execute();
//                    subscriber.onNext(response.body().string());
//                    subscriber.onCompleted();
//                } catch (IOException e) {
//                    subscriber.onError(e);
//                }

                subscriber.onNext("dopost");
                subscriber.onCompleted();
            }
        });
    }

    private Observable<String> login(final OkHttpClient client, final String url, String username, String password) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .addHeader("Accept-Encoding", "gzip, deflate, sdch")
                        .addHeader("Accept-Language", "en-US,en;q=0.8,ko;q=0.6")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Pragma", "no-cache")
                        .addHeader("Upgrade-Insecure-Requests", "1")
                        .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36")
                        .build();

                // TODO post urlenc
                RequestBody res = new FormEncodingBuilder()
                        .add("", "")
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    subscriber.onNext(response.body().string());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
