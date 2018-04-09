package com.example.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author VanceKing
 * @since 2018/3/27.
 */

public class RunnableCallableSample {
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        try {
            RunnableCallableSample main = new RunnableCallableSample();
            main.futureTaskSample2();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void futureTaskSample2() throws ExecutionException, InterruptedException{
        //FutureTask 构造函数中调用Executors.callable()方法，通过 Executors.RunnableAdapter(RunnableAdapter<T> implements Callable<T>) 转换成Callable执行
        FutureTask<String> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                printResult();
            }
        }, "id-2");
        Future<String> future = mExecutor.submit(futureTask, "aaa");
        System.out.println("future result: " + future.get());//FutureTask.get() 会阻塞当前线程
    }

    private void futureTaskSample1() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                printResult();
            }
        }, "id-1");
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("future result: " + futureTask.get());//FutureTask.get() 会阻塞当前线程
        System.out.println("behind FutureTask.get()");
    }

    private void callableSample1() throws ExecutionException, InterruptedException {
        Future<?> result = mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                printResult();
            }
        });
        System.out.println("future result : " + result.get());
    }

    private void callableSample2() throws ExecutionException, InterruptedException {
        Future<Integer> result = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return printResult();
            }
        });

        System.out.println("future result from callable : " + result.get());
    }

    private void runnableSample() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                printResult();
            }
        }.start();
    }

    /**
     * 效率底下的斐波那契数列, 耗时的操作
     */
    private static int fibc(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return fibc(num - 1) + fibc(num - 2);
    }

    private static int printResult() {
        System.out.println(System.currentTimeMillis());
        int fibc = fibc(38);
        System.out.println(fibc);
        System.out.println(System.currentTimeMillis());
        return fibc;
    }
}