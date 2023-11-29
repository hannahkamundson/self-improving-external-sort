package com.digit.distribution;

import com.digit.command.Config;

public class DistributionFactory {
    public static Distribution create(DistributionMode mode) {
        switch(mode) {
            case UNIFORM:
                return new UniformDistribution();
            case LOW_ENTROPY:
                return new LowEntropyDistribution(Config.NUMBER_LINES);
            default:
                throw new IllegalArgumentException("You must choose one of the allowed modes");
        }
    }
}
