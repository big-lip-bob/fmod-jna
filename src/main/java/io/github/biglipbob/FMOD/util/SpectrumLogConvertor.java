package io.github.biglipbob.FMOD.util;

import io.github.biglipbob.FMOD.FMODSpectrumData;

/**
 * Utility class for converting spectrum data to logarithmic scale.
 */
public final class SpectrumLogConvertor {

    private SpectrumLogConvertor() { }

    /**
     * Converts the entire spectrum to logarithmic scale for all channels.
     *
     * @param spectrumData The spectrum data to convert
     * @param sampleRate The sample rate in Hz
     * @param fftWindowSize The FFT window size
     * @param minFreq The FFT analysis starting frequency
     * @param maxFreq The FFT analysis stopping frequency
     * @return A 2D array containing the log-scaled spectrum for all channels
     */
    public static float[][] convertAllToLogScale(FMODSpectrumData spectrumData, 
            float sampleRate, int fftWindowSize, float minFreq, float maxFreq) {
        int length = spectrumData.getLength();
        int channels = spectrumData.getChannels();

        float[][] spectrum = spectrumData.getSpectrum();

        float[][] logSpectrum = new float[channels][length];
        for (int channel = 0; channel < channels; channel++)
            logSpectrum[channel] = convertChannelToLogScale(spectrum[channel], 
                length, sampleRate, fftWindowSize, minFreq, maxFreq);

        return logSpectrum;
    }

    /**
     * Converts a single channel of spectrum data to logarithmic scale.
     *
     * @param channelSpectrum The spectrum data for a single channel
     * @param length The length of the spectrum data
     * @param sampleRate The sample rate in Hz
     * @param fftWindowSize The FFT window size
     * @param fmin The minimum frequency to consider (Hz)
     * @param fmax The maximum frequency to consider (Hz)
     * @return An array containing the log-scaled spectrum for the channel
     */
    public static float[] convertChannelToLogScale(
        float[] channelSpectrum, int length,
        float sampleRate, int fftWindowSize,
        float fmin, float fmax
    ) {
        float[] logSpectrum = new float[length];
        float freqResolution = initialFreqResolution(sampleRate, fftWindowSize);
        float logMin = (float) log2(fmin + 1);
        float logMax = (float) log2(fmax + 1);

        for (int bin = 0; bin < length; bin++) {
            freqResolution = adaptFreqResolution(freqResolution, bin);
            float[] bandRange = computeBandRange(logMin, logMax, bin, length);
            int[] binRange = computeBinRange(bandRange, freqResolution, length);
            logSpectrum[bin] = findMaxInRange(channelSpectrum, binRange[0], binRange[1]);
        }

        return logSpectrum;
    }

    /**
     * Calculates the initial frequency resolution (in Hz per bin).
     *
     * @param sampleRate The audio sample rate in Hz
     * @param fftWindowSize The FFT window size
     * @return The frequency resolution
     */
    private static float initialFreqResolution(float sampleRate, int fftWindowSize) {
        return sampleRate / 2 / fftWindowSize; // Nyquist frequency divided by window size
    }

    /**
     * Adjusts the frequency resolution for specific frequency bins to improve resolution
     * in bass and high-end regions.
     *
     * @param freqResolution The current frequency resolution
     * @param bin The current frequency bin index
     * @return The adjusted frequency resolution
     */
    private static float adaptFreqResolution(float freqResolution, int bin) {
        if (bin == 100 || bin == 1600 || bin == 1950)
            return freqResolution * 2; // Adaptive resolution for bass/high frequencies
        return freqResolution;
    }

    /**
     * Computes the logarithmic frequency band range (start and end frequencies).
     *
     * @param logMin Log2 of the minimum frequency
     * @param logMax Log2 of the maximum frequency
     * @param bin The current bin index
     * @param length The total number of bins
     * @return An array containing [bandStart, bandEnd] in Hz
     */
    private static float[] computeBandRange(float logMin, float logMax, int bin, int length) {
        float bandStartLog = logMin + (logMax - logMin) * (bin / (float) length);
        float bandEndLog = logMin + (logMax - logMin) * ((bin + 1) / (float) length);
        float bandStart = (float) Math.pow(2, bandStartLog);
        float bandEnd = (float) Math.pow(2, bandEndLog);
        return new float[]{bandStart, bandEnd};
    }

    /**
     * Computes the corresponding bin indices for the given frequency range.
     *
     * @param bandRange The frequency band [start, end] in Hz
     * @param freqResolution The frequency resolution in Hz per bin
     * @param maxLength The maximum length of the spectrum
     * @return An array containing [binStart, binEnd]
     */
    private static int[] computeBinRange(float[] bandRange, float freqResolution, int maxLength) {
        int binStart = Math.max(0, (int) (bandRange[0] / freqResolution));
        int binEnd = Math.min(maxLength - 1, (int) (bandRange[1] / freqResolution));
        return new int[]{binStart, binEnd};
    }

    /**
     * Finds the maximum value in a segment of the spectrum.
     *
     * @param spectrum The spectrum data
     * @param start The starting bin index
     * @param end The ending bin index
     * @return The maximum value found in the range
     */
    private static float findMaxInRange(float[] spectrum, int start, int end) {
        float max = 0;
        for (int i = start; i <= end; i++) {
            if (max < spectrum[i]) max = spectrum[i];
        }
        return max;
    }

    /**
     * Computes log base 2 of a number.
     *
     * @param x The number
     * @return log base 2 of x
     */
    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

}