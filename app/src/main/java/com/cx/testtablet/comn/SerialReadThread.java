package com.cx.testtablet.comn;

import android.os.SystemClock;

import com.cx.testtablet.comn.message.LogManager;
import com.cx.testtablet.comn.message.RecvMessage;
import com.cx.testtablet.utils.ByteUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读串口线程
 */
public class SerialReadThread extends Thread {

    private static final String TAG = "SerialReadThread";

    private BufferedInputStream mInputStream;

    public SerialReadThread(InputStream is) {
        mInputStream = new BufferedInputStream(is);
    }

    @Override
    public void run() {
        byte[] received = new byte[1024];
        int size;


        while (true) {

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {

                int available = mInputStream.available();

                if (available > 0) {
                    size = mInputStream.read(received);
                    if (size > 0) {
                        onDataReceive(received, size);
                    }
                } else {
                    // 暂停一点时间，免得一直循环造成CPU占用率过高
                    SystemClock.sleep(1);
                }
            } catch (IOException e) {
            }
            //Thread.yield();
        }

    }

    /**
     * 处理获取到的数据
     *
     * @param received
     * @param size
     */
    private void onDataReceive(byte[] received, int size) {
        // TODO: 2018/3/22 解决粘包、分包等
        String hexStr = ByteUtil.bytes2HexStr(received, 0, size);
        LogManager.instance().post(new RecvMessage(hexStr));
    }

    /**
     * 停止读线程
     */
    public void close() {

        try {
            mInputStream.close();
        } catch (IOException e) {
        } finally {
            super.interrupt();
        }
    }
}
