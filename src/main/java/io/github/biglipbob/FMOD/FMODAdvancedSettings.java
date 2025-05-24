package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import java.util.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class FMODAdvancedSettings extends Structure {
    public int cbSize;
    public int maxMPEGCodecs;
    public int maxADPCMCodecs;
    public int maxXMACodecs;
    public int maxVorbisCodecs;
    public int maxAT9Codecs;
    public int maxFADPCMCodecs;
    public int maxOpusCodecs;
    public int ASIONumChannels;
    public Pointer ASIOChannelList;
    public Pointer ASIOSpeakerList;
    public float vol0virtualvol;
    public int defaultDecodeBufferSize;
    public short profilePort;
    public int geometryMaxFadeTime;
    public float distanceFilterCenterFreq;
    public int reverb3Dinstance;
    public int DSPBufferPoolSize;
    public int resamplerMethod;
    public int randomSeed;
    public int maxConvolutionThreads;
    public int maxSpatialObjects;

    public FMODAdvancedSettings() {
        super();
        cbSize = size();
    }

    @Override protected List<String> getFieldOrder() {
        return Arrays.asList(
                "cbSize", "maxMPEGCodecs", "maxADPCMCodecs", "maxXMACodecs", "maxVorbisCodecs",
                "maxAT9Codecs", "maxFADPCMCodecs", "maxOpusCodecs", "ASIONumChannels", "ASIOChannelList",
                "ASIOSpeakerList", "vol0virtualvol", "defaultDecodeBufferSize", "profilePort", "geometryMaxFadeTime",
                "distanceFilterCenterFreq", "reverb3Dinstance", "DSPBufferPoolSize", "resamplerMethod", "randomSeed",
                "maxConvolutionThreads", "maxSpatialObjects"
        );
    }
}

