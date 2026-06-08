package com.nainesh.lld.ThreadPool;

class Main{
    public static void main(String[] args) {
        //submit(task) -> task add to queue -> worked thread pickup my task -> task.run()
        MyThreadPool tp = new MyThreadPool(2,3,2,10);

        for(int i=0;i<=10;i++){
            tp.submit(new Task(i));
        }

        tp.shutdown();
    }
}