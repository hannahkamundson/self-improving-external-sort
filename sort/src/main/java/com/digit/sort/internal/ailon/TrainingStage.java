package com.digit.sort.internal.ailon;

import com.digit.command.Config;
import com.digit.sort.internal.InternalSortStrategy;

/**
 * This handles the training stage of Ailon's Algorithm. There are 2 components: (1) first you train to find the
 * correct buckets (2) then you find the trees for each line of the algorithm
 */
public class TrainingStage {
    /**
     * We do the bucket training first and then the tree training. What is the sample number of the final tree
     * training?
     */
    private final int TREE_SAMPLES_MAX = Config.BUCKET_SAMPLES + Config.TREE_SAMPLES;

    private BucketTraining bucketTraining;
    private TreeTraining treeTraining;

    public TrainingStage() {
        bucketTraining = new BucketTraining(Config.MAX_NUMBER - Config.MIN_NUMBER + 1, Config.BUCKET_SAMPLES, Config.NUMBER_LINES);
    }

    public int getTrainingMax() {
        return TREE_SAMPLES_MAX;
    }

    public void train(int sampleNumber, int[] sorted) {
        if (sampleNumber < Config.BUCKET_SAMPLES) {
            bucketTraining.train(sorted);
        } else if (sampleNumber < TREE_SAMPLES_MAX) {
            treeTraining.train(sorted);
        } else {
            throw new IndexOutOfBoundsException(String.format("The sample number shouldn't be trained: %s", sampleNumber));
        }
    }

    /**
     * Give the training stage time to learn. This is called in the same round after train. This allows us to not
     * have a large amount of data in memory while learning
     */
    public void learn(int sampleNumber) {
        // If this is our last bucket training
        if (sampleNumber == (Config.BUCKET_SAMPLES - 1)) {
            // Set the next training strategy (tree training) based on the bucket results
            treeTraining = new TreeTraining(bucketTraining.bucketSeparatorResult());
            // Null this out so garbage collection deals with whatever is in there
            bucketTraining = null;
        }
    }

    public InternalSortStrategy createSort() {
        return new AilonSort();
    }
}
