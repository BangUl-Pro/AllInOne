package com.ironfactory.allinoneenglish.utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IronFactory on 2016. 4. 30..
 */
public class VideoInputStream extends FileInputStream {

    public VideoInputStream(File file) throws FileNotFoundException {
        super(file);
    }

    public VideoInputStream(FileDescriptor fd) {
        super(fd);
    }

    public VideoInputStream(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }
}
