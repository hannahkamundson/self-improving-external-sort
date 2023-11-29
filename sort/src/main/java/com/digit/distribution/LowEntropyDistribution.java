package com.digit.distribution;

import com.digit.command.Config;
import com.digit.io.Writer;
import com.google.common.base.Preconditions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class LowEntropyDistribution implements Distribution {
    private final int[] lows;
    private final int[] highs;
    private final double[] probabilities;
    private final Random generator = new Random(Config.SEED);

    public LowEntropyDistribution(int numberOfLines) {
        this.lows = new int[numberOfLines];
        this.highs = new int[numberOfLines];
        this.probabilities = new double[numberOfLines];

        // Generate the values that will be used

        for (int i = 0; i < numberOfLines; i++) {
            int a = generator.nextInt(Config.MAX_NUMBER);
            int b = generator.nextInt(Config.MAX_NUMBER);

            if (a <= b) {
                lows[i] = a;
                highs[i] = b;
            }

            probabilities[i] = generator.nextDouble(1.0);
        }
    }


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
        Preconditions.checkArgument(numberOfLines == lows.length,
                "You need to specify the number of lines in the config");
        for (int i = 0; i < numberOfLines; i++) {
            double probability = generator.nextDouble(1.0);

            if (probability < probabilities[i]) {
                writer.write(lows[i]);
            } else {
                writer.write(highs[i]);
            }
        }

        writer.flush();
    }
}
