package io.github.biglipbob.FMOD.dsp;

import io.github.biglipbob.FMOD.FMODDSP;
import io.github.biglipbob.FMOD.FMODSpectrumData;
import io.github.biglipbob.FMOD.FMODSystemRef;

import static io.github.biglipbob.FMOD.FMODConstants.*;

/**
 * The {@code DSPFFT} class represents an FMOD Fast Fourier Transform (FFT) DSP effect.
 * <p>
 * Unlike {@link FMODDSP}, this class specifically wraps the FMOD FFT effect,
 * allowing for direct access to real-time frequency analysis of an audio signal.
 * It enables retrieval of spectrum data for visualization and further signal processing.
 * <p>
 * For more details, refer to the FMOD FFT DSP documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/effects-reference.html#fft">FMOD FFT DSP</a>
 */
public class DSPFFT extends FMODDSP {
    public static final int DSP_TYPE = FMOD_DSP_TYPE_FFT;

    public DSPFFT(FMODSystemRef system) {
        super(system, DSP_TYPE);
    }

    // FMOD_DSP_FFT_WINDOWSIZE - Must be a power of 2 between 128 and 16384. - Default 2048
    public int getWindowSize() {
        return getParameterValueInt(FMOD_DSP_FFT_WINDOWSIZE);
    }

    public void setWindowSize(int windowSize) {
        setParameterInt(FMOD_DSP_FFT_WINDOWSIZE, windowSize);
    }

    // FMOD_DSP_FFT_WINDOW - Default FMOD_DSP_FFT_WINDOW_HAMMING
    public int getWindowType() {
        return getParameterValueInt(FMOD_DSP_FFT_WINDOW);
    }

    public void setWindowType(int windowType) {
        setParameterInt(FMOD_DSP_FFT_WINDOW, windowType);
    }

    // FMOD_DSP_FFT_BAND_START_FREQ - Default 0
    public float getBandStartFreq() {
        return getParameterValueFloat(FMOD_DSP_FFT_BAND_START_FREQ);
    }

    public void setBandStartFreq(float bandStartFreq) {
        setParameterFloat(FMOD_DSP_FFT_BAND_START_FREQ, bandStartFreq);
    }

    // FMOD_DSP_FFT_BAND_STOP_FREQ - Default 22000
    public float getBandEndFreq() {
        return getParameterValueFloat(FMOD_DSP_FFT_BAND_STOP_FREQ);
    }

    public void setBandEndFreq(float bandEndFreq) {
        setParameterFloat(FMOD_DSP_FFT_BAND_STOP_FREQ, bandEndFreq);
    }

    // FMOD_DSP_FFT_SPECTRUMDATA - RO - FMOD_DSP_PARAMETER_FFT
    public FMODSpectrumData getSpectrumData() {
        return getParameterValueData(FMOD_DSP_FFT_SPECTRUMDATA, FMODSpectrumData.class);
    }

    // FMOD_DSP_FFT_RMS - RO - float
    public float getRMS() {
        return getParameterValueFloat(FMOD_DSP_FFT_RMS);
    }

    // FMOD_DSP_FFT_SPECTRAL_CENTROID - RO - float
    public float getSpectralCentroid() {
        return getParameterValueFloat(FMOD_DSP_FFT_SPECTRAL_CENTROID);
    }

    // FMOD_DSP_FFT_IMMEDIATE_MODE - Default false
    public boolean getImmediateMode() {
        return getParameterValueBool(FMOD_DSP_FFT_IMMEDIATE_MODE);
    }

    public void setImmediateMode(boolean immediateMode) {
        setParameterBool(FMOD_DSP_FFT_IMMEDIATE_MODE, immediateMode);
    }

    // FMOD_DSP_FFT_DOWNMIX - Default FMOD_DSP_FFT_DOWNMIX_NONE
    public int getDownMix() {
        return getParameterValueInt(FMOD_DSP_FFT_DOWNMIX);
    }

    public void setDownMix(int downMix) {
        setParameterInt(FMOD_DSP_FFT_DOWNMIX, downMix);
    }

    // FMOD_DSP_FFT_CHANNEL - Default -1
    public int getChannel() {
        return getParameterValueInt(FMOD_DSP_FFT_CHANNEL);
    }

    public void setChannel(int channel) {
        setParameterInt(FMOD_DSP_FFT_CHANNEL, channel);
    }
}
