package org.kryogenic.rsbot.struct.stream;

/**
 * @author: Kale
 * @date: 26/06/13
 */
public abstract class Job {
    final State[] states;

    public Job(final State... states) {
        this.states = states;
    }

    public abstract boolean go();
}
