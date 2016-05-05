package com.subakstudio.rx;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinwoomin on 5/4/16.
 */
public class RxJavaTest {
    @Test
    public void testCreate() throws Exception{
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
}
