package com.digit.sort.internal.ailon;

import com.digit.sort.internal.InternalSortStrategy;

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

    public void train(int sampleNumber, int[] unsorted) {
        if (sampleNumber < BucketTraining.BUCKET_SAMPLES) {
            bucketTraining.train(unsorted);
        } else if (sampleNumber < TREE_SAMPLES_MAX) {
            if (sampleNumber == BucketTraining.BUCKET_SAMPLES) {
                treeTraining = new TreeTraining(bucketTraining.bucketResult());
                bucketTraining = null;
            }

            treeTraining.train(unsorted);
        } else {
            throw new IndexOutOfBoundsException(String.format("The sample number shouldn't be trained: %s", sampleNumber));
        }

    }

    public InternalSortStrategy createSort() {
        return new AilonSort();
    }


}
