package com.king.app.workhelper.fragment;

import android.widget.TextView;

import com.king.app.workhelper.R;
import com.king.app.workhelper.common.AppBaseFragment;
import com.king.app.workhelper.model.entity.GitHubUser;
import com.king.app.workhelper.model.entity.MovieEntity;
import com.king.app.workhelper.api.GitHubApi;
import com.king.app.workhelper.api.MovieApi;
import com.king.app.workhelper.retrofit.RetrofitManager;
import com.king.app.workhelper.retrofit.callback.OnResponseCallback;
import com.king.app.workhelper.retrofit.model.HttpResults;
import com.king.app.workhelper.retrofit.subscriber.HttpSubscriber;
import com.king.applib.log.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit.
 * http://mp.weixin.qq.com/s?__biz=MzI5NjQxNDE3Ng==&mid=2247483665&idx=1&sn=c87127b2617e11fe52e36d7144a613ee&mpshare=1&scene=1&srcid=1010ClfxSMqG3vMfgRd8mOcD#rd
 * Created by VanceKing on 2016/11/26.
 */

public class RetrofitSampleFragment extends AppBaseFragment {

    private HttpSubscriber<GitHubUser> subscriber;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_sample_retrofit;
    }

    @OnClick(R.id.tv_common_retrofit)
    public void retrofitRequest() {
        final String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieEntity> call = movieApi.getTopMovie(0, 10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                Logger.i("results: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                Logger.i("onFailure");
            }
        });
    }

    @OnClick(R.id.tv_rx_retrofit)
    public void retrofitSample(final TextView textView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(OkHttpUtils.getInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        final GitHubApi service = retrofit.create(GitHubApi.class);
        service.getUser("Guolei1130").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GitHubUser>() {
                    @Override public void onNext(GitHubUser gitHubUser) {
                        Logger.i("GitUserService->" + gitHubUser.toString());
                        textView.setText(gitHubUser.login);
                    }

                    @Override public void onError(Throwable e) {
                        Logger.i("GitUserService->onError");
                    }

                    @Override public void onComplete() {
                        Logger.i("GitUserService->onCompleted");
                    }
                });

    }

    @OnClick(R.id.tv_rx_retrofit_aaa)
    public void aaa() {
        subscriber = new HttpSubscriber<>(new OnResponseCallback<GitHubUser>() {
            @Override public void onSuccess(GitHubUser results) {
                Logger.i(results.toString());
            }

            @Override public void onFailure(int code, String msg) {
                Logger.i("code" + code + ";msg: " + msg);
            }
        });

        final GitHubApi service = RetrofitManager.getInstance().getRetrofit().create(GitHubApi.class);

        Observable<HttpResults<GitHubUser>> guolei1130 = service.getUserEx("Guolei1130");
        RetrofitManager.getInstance().toSubscribe(guolei1130, subscriber);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        subscriber.unSubscribe();
    }
}
