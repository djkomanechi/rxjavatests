package com.example.test.myapplication.loader;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AsyncLoader {

    private Subscription mSubscription;

    private int mCounter;

    public void init() {
        android.util.Log.d("anase", "init");
        mSubscription = Observable.just(1,2,3,4)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Integer, Boolean>() { // 2
                    @Override
                    public Boolean call(Integer i) {
                        return i % 2 == 0;
                    }
                })
                .map( new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer i) {
                        return i*i;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        android.util.Log.d("anase", "onCompleted!!!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        android.util.Log.d("anase", "onError!!!");
                    }

                    @Override
                    public void onNext(Integer i) {
                        android.util.Log.d("anase", "onNext!!! : " + i);

                    }
                });
        mCounter = 4;
    }

    public void doit() {
        android.util.Log.d("anase", "doit (e)");
        mCounter++;
        mSubscription = Observable.just(mCounter)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Integer, Boolean>() { // 2
                    @Override
                    public Boolean call(Integer i) {
                        return i % 2 == 0;
                    }
                })
                .map( new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer i) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return i*i;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        android.util.Log.d("anase", "onCompleted!!!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        android.util.Log.d("anase", "onError!!!");
                    }

                    @Override
                    public void onNext(Integer i) {
                        android.util.Log.d("anase", "onNext!!! : " + i);

                    }
                });
        android.util.Log.d("anase", "doit (x)");
    }

    public void release() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
