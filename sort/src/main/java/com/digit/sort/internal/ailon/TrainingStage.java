package com.digit.sort.internal.ailon;

import com.digit.sort.internal.InternalSortStrategy;

/**
 * This handles the training stage of Ailon's Algorithm. There are 2 components: (1) first you train to find the
 * correct buckets (2) then you find the trees for each line of the algorithm
 */
public class TrainingStage {
    private final int TREE_SAMPLES_MAX = BucketTraining.BUCKET_SAMPLES + TreeTraining.TREE_SAMPLES;

    private BucketTraining bucketTraining;
    private TreeTraining treeTraining;

    public TrainingStage() {
        bucketTraining = new BucketTraining();
    }

    public int getTrainingMax() {
        return TREE_SAMPLES_MAX;
    }

    public void train(int sampleNumber, int[] sorted) {
        if (sampleNumber < BucketTraining.BUCKET_SAMPLES) {
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
        if (sampleNumber == (BucketTraining.BUCKET_SAMPLES - 1)) {
            // Set the next training strategy (tree training) based on the bucket results
            treeTraining = new TreeTraining(bucketTraining.bucketResult());
            bucketTraining = null;
        }
    }

    public InternalSortStrategy createSort() {
        return new AilonSort();
    }
}
