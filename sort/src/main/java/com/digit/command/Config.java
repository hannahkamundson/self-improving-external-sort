package com.digit.command;

public class Config {
    /**
     * The minimum number in the distribution. This is always 0 because BucketTraining depends on that
     */
    public final static int MIN_NUMBER = 0;

    /**
     * The maximum possible number in the distribution
     */
    public static int MAX_NUMBER = 10000;

    /**
     * How many blocks should we use for the bucket training phase?
     */
    public static int BUCKET_SAMPLES = 1;

    /**
     * How many blocks should we use for the tree training phase?
     */
    public static int TREE_SAMPLES = 0;

    /**
     * How many lines are in each file?
     */
    public static int NUMBER_LINES = -3;

    public static int SEED = 1234;
}
