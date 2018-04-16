package mobile.saltside.com.parsedataapplication.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mobile.handler.com.handler_lib.NetworkService;
import mobile.handler.com.handler_lib.ServeResponseMessage;
import mobile.saltside.com.parsedataapplication.contractor.ViewContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class PresenterLayer {

    private ViewContract view;
    private NetworkService service;
    private Subscription subscription;

    public PresenterLayer(ViewContract view, NetworkService service) {
        this.view = view;
        this.service = service;
    }

    public void loadRxData() {
        view.showRxInProcess();
        Observable<ServeResponseMessage[]> servicePreparedObservable = (Observable<ServeResponseMessage[]>)
                service.getPreparedObservable(service.getAPI().getItemsObservable(), ServeResponseMessage.class, true, true);
        subscription = servicePreparedObservable.subscribe(new Observer<ServeResponseMessage[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(ServeResponseMessage[] serveResponseMessage) {
                view.loadRxData(new ArrayList<>(Arrays.asList(serveResponseMessage)));
            }
        });
    }

    public void loadRetroData() {
        view.showRxInProcess();
        Call<ServeResponseMessage[]> call = service.getAPI().getItems();
        call.enqueue(new Callback<ServeResponseMessage[]>() {
            @Override
            public void onResponse(Call<ServeResponseMessage[]> call, Response<ServeResponseMessage[]> response) {
                view.loadRetroData(new ArrayList<>(Arrays.asList(response.body())));

            }

            @Override
            public void onFailure(Call<ServeResponseMessage[]> call, Throwable t) {

            }
        });
    }

    public void rxUnSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
