package com.doubleclick.downloader.core;

import java.util.concurrent.Executor;

public interface ExecutorSupplier {

    DownloadExecutor forDownloadTasks();

    Executor forBackgroundTasks();

    Executor forMainThreadTasks();

}
