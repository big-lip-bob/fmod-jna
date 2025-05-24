package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import java.util.*;

@SuppressWarnings({"unused"})
public class FMODDSPMeteringInfo extends Structure {
    public int samples;
    public float[] peaks = new float[32];
    public float[] rms = new float[32];
    public short channels;

    FMODDSPMeteringInfo() {
        super();
    }

    FMODDSPMeteringInfo(Pointer p) {
        super(p);
        read();
    }

    @Override protected List<String> getFieldOrder() {
        return Arrays.asList("samples", "peaks", "rms", "channels");
    }
}
