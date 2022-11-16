package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        try(RandomAccessFile aFile = new RandomAccessFile(file.getPath(), "r");
            FileChannel inChannel = aFile.getChannel();) {

            long fileSize = inChannel.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < fileSize; i++) {
                strBuilder.append((char) buffer.get());
            }

            String[] splitArray = strBuilder.toString().split("\\r?\\n");
            profile.setName(splitArray[0].split(": ")[1]);
            profile.setAge(Integer.parseInt(splitArray[1].split(": ")[1]));
            profile.setEmail(splitArray[2].split(": ")[1]);
            profile.setPhone(Long.parseLong(splitArray[3].split(": ")[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profile;
    }
}
