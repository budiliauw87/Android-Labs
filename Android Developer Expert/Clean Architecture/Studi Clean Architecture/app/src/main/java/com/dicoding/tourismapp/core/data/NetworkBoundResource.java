package com.dicoding.tourismapp.core.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse;
import com.dicoding.tourismapp.core.utils.AppExecutors;

/**
 * Created by Budiliauw87 on 2020-12-30.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public  abstract class NetworkBoundResource<ResultType, RequestType> {
    private AppExecutors mExecutors;
    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.mExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDB();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType resultType) {
                result.removeSource(dbSource);
                if(shouldFetch(resultType))
                    fetchFromNetwork(dbSource);
                else
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            result.setValue(Resource.success(newData));
                        }
                    });
            }
        });
    }
    protected abstract Boolean shouldFetch(ResultType data);
    protected abstract LiveData<ResultType> loadFromDB();
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
    protected abstract void saveCallResult(RequestType data);
    protected void onFetchFailed() {}

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponseLiveData = createCall();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newResult) {
                result.setValue(Resource.loading(newResult));
            }
        });

        result.addSource(apiResponseLiveData, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(ApiResponse<RequestType> requestTypeApiResponse) {
                result.removeSource(dbSource);
                result.removeSource(apiResponseLiveData);

                switch (requestTypeApiResponse.statusRespone){
                    //ketika kosong ambil data dari database
                    case EMPTY:
                        mExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                result.addSource(loadFromDB(), new Observer<ResultType>() {
                                    @Override
                                    public void onChanged(ResultType resultType) {
                                        result.setValue(Resource.success(resultType));
                                    }
                                });
                            }
                        });
                        break;
                    case ERROR:
                        onFetchFailed();
                        result.addSource(dbSource, new Observer<ResultType>() {
                            @Override
                            public void onChanged(ResultType resultType) {
                                result.setValue(Resource.error(requestTypeApiResponse.message,resultType));
                            }
                        });
                        break;
                    case SUCCESS:
                        mExecutors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                saveCallResult(requestTypeApiResponse.body);
                                mExecutors.mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        result.addSource(loadFromDB(), new Observer<ResultType>() {
                                            @Override
                                            public void onChanged(ResultType newData) {
                                                result.setValue(Resource.success(newData));
                                            }
                                        });
                                    }
                                });

                            }
                        });
                        break;
                }
            }
        });


    }
    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
