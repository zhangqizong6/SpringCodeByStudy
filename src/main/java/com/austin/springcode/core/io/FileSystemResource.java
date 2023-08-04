package com.austin.springcode.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: FileSystemResource
 * @author: zqz
 * @date: 2023/7/28 15:18
 */

/**
 * 过指定文件路径的方式读取文件信息
 */
public class FileSystemResource implements Resource {

    private final String path;
    private final File file;

    public FileSystemResource(String path) {
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public final String getPath() {
        return this.path;
    }
}
