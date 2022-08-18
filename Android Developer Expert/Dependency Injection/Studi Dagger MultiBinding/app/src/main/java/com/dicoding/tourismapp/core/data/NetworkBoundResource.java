package com.dicoding.tourismapp.core.data;

import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse;
import com.dicoding.tourismapp.core.utils.AppExecutors;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Budiliauw87 on 2020-12-30.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private AppExecutors mExecutors;
    private PublishSubject<Resource<ResultType>> result = PublishSubject.create();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Disposable disposableDb, disposableRes;

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.mExecutors = appExecutors;

        disposableDb = loadFromDB().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(resultType -> {
                    loadFromDB().unsubscribeOn(Schedulers.io());
                    if (shouldFetch(resultType)) {
                        fetchFromNetwork();
                    } else {
                        result.onNext(Resource.success(resultType));
                    }
                });
        mCompositeDisposable.add(disposableDb);
    }

    protected abstract Boolean shouldFetch(ResultType data);

    protected abstract Flowable<ResultType> loadFromDB();

    protected abstract Flowable<ApiResponse<RequestType>> createCall();

    protected abstract void saveCallResult(RequestType data);

    protected void onFetchFailed() {
    }

    private void fetchFromNetwork() {
        Flowable<ApiResponse<RequestType>> apiResponse = createCall();
        result.onNext(Resource.loading(null));
        disposableRes = apiResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .doOnComplete(() -> mCompositeDisposable.dispose())
                .subscribe(response -> {
                    switch (response.statusRespone) {
                        case SUCCESS:
                            saveCallResult(response.body);
                            disposableDb = loadFromDB()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .take(1)
                                    .subscribe(resultType -> {
                                        loadFromDB().unsubscribeOn(Schedulers.io());
                                        result.onNext(Resource.success(resultType));
                                    });
                            break;
                        case EMPTY:
                            disposableDb = loadFromDB()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .take(1)
                                    .subscribe(resultType -> {
                                        loadFromDB().unsubscribeOn(Schedulers.io());
                                        result.onNext(Resource.success(resultType));
                                    });
                            break;
                        case ERROR:
                            onFetchFailed();
                            result.onNext(Resource.error(response.message, null));
                            break;
                    }
                });
        mCompositeDisposable.add(disposableRes);

    }

    public Flowable<Resource<ResultType>> asFlowable() {
        return result.toFlowable(BackpressureStrategy.BUFFER);
    }
}
