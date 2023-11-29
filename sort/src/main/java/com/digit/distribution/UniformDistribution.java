package com.digit.distribution;

import com.digit.command.Config;
import com.digit.io.Writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class UniformDistribution implements Distribution {
    private static final Random generator = new Random(Config.SEED);

    @Override
    public void generateFile(File file, int numberOfLines) {
        Writer.bufferedWriter(file, writer -> {
            try {
                doGenerateFile(writer, numberOfLines);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void doGenerateFile(BufferedWriter writer, int numberOfLines) throws IOException {
        for (int i = 0; i < numberOfLines; i++) {
            writer.write(generator.nextInt(Config.MAX_NUMBER));
        }

        writer.flush();
    }
}
