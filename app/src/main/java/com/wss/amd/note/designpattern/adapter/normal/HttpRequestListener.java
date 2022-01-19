package com.wss.amd.note.designpattern.adapter.normal;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public interface HttpRequestListener {

    void onStart();

    void onProgress(int progress, int count);

    void onSuccess();

    void onError();

    void onComplete();
}
