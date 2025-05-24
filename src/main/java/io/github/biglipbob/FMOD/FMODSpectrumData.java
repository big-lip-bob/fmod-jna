package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import java.util.*;

public class FMODSpectrumData extends Structure {
    public int length;
    public int channels;
    public Pointer[] spectrum = new Pointer[32]; // Has to be public because of JNA

    public FMODSpectrumData() { super(); }

    FMODSpectrumData(Pointer p) {
        super(p);
        read();
    }

    public int getLength() {
        return length;
    }

    public int getChannels() {
        return channels;
    }

    public float[] getChannelSpectrum(int channel) {
        if (channel < 0 || channel >= channels)
            throw new IllegalArgumentException("Invalid channel number: " + channel);
        return spectrum[channel].getFloatArray(0, length);
    }

    public float[][] getSpectrum() {
        float[][] spectrum = new float[channels][];
        for (int channel = 0; channel < channels; channel++) spectrum[channel] = getChannelSpectrum(channel);
        return spectrum;
    }

    @Override protected List<String> getFieldOrder() {
        return Arrays.asList("length", "channels", "spectrum");
    }
}
