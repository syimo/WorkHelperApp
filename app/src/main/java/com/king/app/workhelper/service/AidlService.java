package com.king.app.workhelper.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.king.app.workhelper.IAidlModelListener;
import com.king.app.workhelper.IRemoteService;
import com.king.app.workhelper.constant.GlobalConstant;
import com.king.app.workhelper.model.AidlModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author VanceKing
 * @since 2018/3/27.
 */

public class AidlService extends Service {
    public static final String PERMISSION_NAME = "com.aidl.permission";
    private final CopyOnWriteArrayList<AidlModel> mAidlModels = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<IAidlModelListener> mAidlModelListeners = new CopyOnWriteArrayList<>();

    @Override public void onCreate() {
        Log.i(GlobalConstant.LOG_TAG, "[RemoteService] onCreate()");
        super.onCreate();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(GlobalConstant.LOG_TAG, "[RemoteService] onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        //权限验证
        /*int check = checkCallingOrSelfPermission(PERMISSION_NAME);
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }*/
        Log.i(GlobalConstant.LOG_TAG, "[RemoteService] onBind()");
        return mRemoteService;
    }

    @Override public boolean onUnbind(Intent intent) {
        Log.i(GlobalConstant.LOG_TAG, "[RemoteService] onUnbind()");
        return super.onUnbind(intent);
    }

    @Override public void onDestroy() {
        Log.i(GlobalConstant.LOG_TAG, "[RemoteService] onDestroy()");
        super.onDestroy();
    }

    IRemoteService.Stub mRemoteService = new IRemoteService.Stub() {

        @Override public void add(AidlModel model) throws RemoteException {
//            SystemClock.sleep(5000);//会阻塞调用者线程
            mAidlModels.add(model);
        }

        @Override public List<AidlModel> getModels() throws RemoteException {
            return mAidlModels;
        }

        @Override public void registerModelListener(IAidlModelListener listener) throws RemoteException {
            if (!mAidlModelListeners.contains(listener)) {
                mAidlModelListeners.add(listener);
            }
        }

        @Override public void unRegisterModelListener(IAidlModelListener listener) throws RemoteException {
            mAidlModelListeners.remove(listener);
        }

        /**此处可用于权限拦截**/
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission(PERMISSION_NAME);
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (packageName == null || !packageName.startsWith("com.king")) {
                return false;
            }
            Log.i(GlobalConstant.LOG_TAG, "服务权限验证成功。packageName："+packageName);
            return super.onTransact(code, data, reply, flags);
        }
    };
}
