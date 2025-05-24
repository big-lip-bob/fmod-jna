package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import com.sun.jna.ptr.*;

import io.github.biglipbob.utils.*;

import static io.github.biglipbob.FMOD.FMODFFI.FMODSystemCallbackFFI;
import static io.github.biglipbob.FMOD.FMODConstants.*;
import static io.github.biglipbob.FMOD.FMOD.*;

import org.jetbrains.annotations.*;

/**
 * The {@code FMODSystemRef} class provides an interface for managing the FMOD system.
 * It allows for initialization, channel control, sound playback, and other core functionalities.
 *
 * <p>For more details, refer to the FMOD System API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-system.html">FMOD System API</a>
 * </p>
 */
public class FMODSystemRef {
    final Pointer ptr;

    FMODSystemRef(Pointer ptr) {
        this.ptr = ptr;
    }

    /// Updates the FMOD system.
    public void update() {
        int result = CALL.FMOD_System_Update(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_Update", result);
    }

    /// Suspend mixer thread and relinquish usage of audio hardware while maintaining internal state.
    public void mixerSuspend() {
        int result = CALL.FMOD_System_MixerSuspend(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_MixerSuspend", result);
    }

    /// Resume mixer thread and reacquire access to audio hardware.
    public void mixerResume() {
        int result = CALL.FMOD_System_MixerResume(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_MixerResume", result);
    }

    // ---------------------------------------- //
    // Device selection
    // ---------------------------------------- //

    /// Sets the type of output interface used to run the mixer.
    public void setOutput(int outputType) {
        int result = CALL.FMOD_System_SetOutput(ptr, outputType);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetOutput", result);
    }

    /// Retrieves the type of output interface used to run the mixer.
    // stub: public int getOutput() { return 0; }

    /// Retrieves the number of output drivers available for the selected output type.
    // stub: public int getNumDrivers() { return 0; }

    /// Retrieves identification information about a sound device specified by its INDEX.
    // stub: public String getDriverInfo(int driverIndex) { return null; }

    /// Sets the output driver for the selected output type.
    // stub: public void setDriver(int driverIndex) { }

    /// Retrieves the output driver for the selected output type.
    // stub: public int getDriver() { return 0; }

    // ---------------------------------------- //
    // Setup
    // ---------------------------------------- //

    /// Retrieves the maximum number of software mixed Channels possible.
    public int getSoftwareChannels() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_System_GetSoftwareChannels(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetSoftwareChannels", result);
        return ref.getValue();
    }

    /// Sets the maximum number of software mixed Channels possible.
    public void setSoftwareChannels(int channels) {
        int result = CALL.FMOD_System_SetSoftwareChannels(ptr, channels);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetSoftwareChannels", result);
    }

    /// Overload with speaker and raw parameters.
    public void setSoftwareFormat(int rate, int speaker, int raw) {
        int result = CALL.FMOD_System_SetSoftwareFormat(ptr, rate, speaker, raw);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetSoftwareFormat", result);
    }

    /// Retrieves the output format for the software mixer.
    public Triplet<Integer> getSoftwareFormat() {
        IntByReference rateRef = new IntByReference(), modeRef = new IntByReference(), rawRef = new IntByReference();
        int result = CALL.FMOD_System_GetSoftwareFormat(ptr, rateRef, modeRef, rawRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetSoftwareFormat", result);
        return new Triplet<>(rateRef.getValue(), modeRef.getValue(), rawRef.getValue());
    }

    /// Sets the output format for the software mixer.
    public void setSampleRate(int rate) {
        setSoftwareFormat(rate, 0, 0);
    }

    /// Extra: Retrieves the software format rate.
    public int getSampleRate() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_System_GetSoftwareFormat(ptr, ref, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetSoftwareFormat", result);
        return ref.getValue();
    }

    /// Sets the buffer size for the FMOD software mixing engine.
    public void setDSPBufferSize(int bufferLength, int numBuffers) {
        int result = CALL.FMOD_System_SetDSPBufferSize(ptr, bufferLength, numBuffers);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetDSPBufferSize", result);
    }

    /// Retrieves the buffer size settings for the FMOD software mixing engine.
    public Tuplet<Integer> getDSPBufferSize() {
        IntByReference sizeRef = new IntByReference(), numRef = new IntByReference();
        int result = CALL.FMOD_System_GetDSPBufferSize(ptr, sizeRef, numRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetDSPBufferSize", result);
        return new Tuplet<>(sizeRef.getValue(), numRef.getValue());
    }

    /// Retrieves the buffer length for the FMOD software mixing engine.
    public int getDSPBufferLength() {
        IntByReference sizeRef = new IntByReference();
        int result = CALL.FMOD_System_GetDSPBufferSize(ptr, sizeRef, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetDSPBufferSize", result);
        return sizeRef.getValue();
    }

    /// Retrieves the buffer length for the FMOD software mixing engine.
    public int getDSPBufferCount() {
        IntByReference countRef = new IntByReference();
        int result = CALL.FMOD_System_GetDSPBufferSize(ptr, null, countRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetDSPBufferSize", result);
        return countRef.getValue();
    }

    /// Sets the default file buffer size for newly opened streams.
    public void setStreamBufferSize(int units, int timeUnit) {
        int result = CALL.FMOD_System_SetStreamBufferSize(ptr, units, timeUnit);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetStreamBufferSize", result);
    }

    /// Retrieves the default file buffer size for newly opened streams.
    public int getStreamBufferSize() {
        IntByReference size = new IntByReference(), type = new IntByReference();
        int result = CALL.FMOD_System_GetStreamBufferSize(ptr, size, type);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetStreamBufferSize", result);
        if (type.getValue() != FMOD_TIMEUNIT_RAWBYTES)
            throw new IllegalStateException("FMOD_System_GetStreamBufferSize returned something else than FMOD_TIMEUNIT_RAWBYTES");
        return size.getValue();
    }

    /// Overload setting buffer size in bytes.
    public void setStreamBufferSize(int bytes) {
        setStreamBufferSize(bytes, FMOD_TIMEUNIT_RAWBYTES);
    }

    /// Retrieves the advanced settings for the system object.
    public FMODAdvancedSettings getAdvancedSettings() {
        FMODAdvancedSettings settings = new FMODAdvancedSettings();
        int result = CALL.FMOD_System_GetAdvancedSettings(ptr, settings.getPointer());
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetAdvancedSettings", result);
        settings.read();
        return settings;
    }

    /// Sets advanced settings for the system object.
    public void setAdvancedSettings(FMODAdvancedSettings settings) {
        if (settings != null) settings.write();
        int result = CALL.FMOD_System_SetAdvancedSettings(ptr, settings != null ? settings.getPointer() : null);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetAdvancedSettings", result);
    }

    /// Sets the position of the specified speaker for the current speaker mode.
    public void setSpeakerPosition(int speaker, float x, float y, boolean active) {
        int result = CALL.FMOD_System_SetSpeakerPosition(ptr, speaker, x, y, active ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetSpeakerPosition", result);
    }

    /// Retrieves the position of the specified speaker for the current speaker mode.
    public Triple<Float, Float, Boolean> getSpeakerPosition(int speaker) {
        FloatByReference x = new FloatByReference(), y = new FloatByReference();
        IntByReference active = new IntByReference();
        int result = CALL.FMOD_System_GetSpeakerPosition(ptr, speaker, x, y, active);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetSpeakerPosition", result);
        return new Triple<>(x.getValue(), y.getValue(), active.getValue() != 0);
    }

    /// Sets the global 3D settings.
    // stub: public void set3DSettings(float dopplerScale, float distanceFactor, float rolloffScale) { }

    /// Retrieves the global 3D settings.
    // stub: public Triplet<Float> get3DSettings() { return null; }

    /// Sets the number of 3D listeners.
    // stub: public void set3DNumListeners(int numListeners) { }

    /// Retrieves the number of 3D listeners.
    // stub: public int get3DNumListeners() { return 0; }

    /// Sets a callback to allow custom calculation of distance attenuation.
    // stub: public void set3DRolloffCallback(Callback callback) { }

    // ---------------------------------------- //
    // File system setup
    // ---------------------------------------- //

    /// Set callbacks to implement all file I/O instead of using the platform native method.
    // stub: public void setFileSystem(/* parameters */) { }

    /// 'Piggyback' on FMOD file reading routines to capture data as it's read.
    // stub: public void attachFileSystem(/* parameters */) { }

    // ---------------------------------------- //
    // Plug-in support
    // ---------------------------------------- //

    /// Specify a base search path for plug-ins.
    // stub: public void setPluginPath(String path) { }

    /// Loads an FMOD plug-in from a file.
    // stub: public void loadPlugin(String filename) { }

    /// Unloads an FMOD plug-in.
    // stub: public void unloadPlugin(int pluginHandle) { }

    /// Retrieves the number of nested plug-ins.
    // stub: public int getNumNestedPlugins(int pluginHandle) { return 0; }

    /// Retrieves the handle of a nested plug-in.
    // stub: public int getNestedPlugin(int pluginHandle, int INDEX) { return 0; }

    /// Retrieves the number of loaded plug-ins.
    // stub: public int getNumPlugins() { return 0; }

    /// Retrieves the handle of a plug-in.
    // stub: public int getPluginHandle(int pluginType, int INDEX) { return 0; }

    /// Retrieves information for the selected plug-in.
    // stub: public String getPluginInfo(int pluginHandle) { return null; }

    /// Selects an output type given a plug-in handle.
    // stub: public void setOutputByPlugin(int pluginHandle) { }

    /// Retrieves the plug-in handle for the current output type.
    // stub: public int getOutputByPlugin() { return 0; }

    /// Create a DSP unit with a specified plug-in handle.
    // stub: public DSP createDSPByPlugin(int pluginHandle) { return null; }

    /// Retrieve the description structure for a DSP plug-in.
    // stub: public String getDSPInfoByPlugin(int pluginHandle) { return null; }

    /// Register a Codec plug-in.
    // stub: public void registerCodec(Object codecDescription) { }

    /// Register a DSP plug-in.
    // stub: public void registerDSP(Object dspDescription) { }

    /// Register an Output plug-in.
    // stub: public void registerOutput(Object outputDescription) { }

    // ---------------------------------------- //
    // Network configuration
    // ---------------------------------------- //

    /// Retrieves the URL of the proxy server.
    public String getNetworkProxy() {
        try (Memory buffer = new Memory(512)) {
            int result = CALL.FMOD_System_GetNetworkProxy(ptr, buffer.getPointer(0), 512);
            if (result != FMOD_OK) throw new FMODException("FMOD_NetworkProxy", result);
            return buffer.getString(0);
        }
    }

    /// Set a proxy server for internet connections.
    public void setNetworkProxy(String proxy) {
        int result = CALL.FMOD_System_SetNetworkProxy(ptr, proxy);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetNetworkProxy", result);
    }

    /// Retrieve the network timeout value.
    public int getNetworkTimeout() {
        IntByReference timeoutRef = new IntByReference();
        int result = CALL.FMOD_System_GetNetworkTimeout(ptr, timeoutRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_NetworkTimeout", result);
        return timeoutRef.getValue();
    }

    /// Set the timeout for network streams.
    public void setNetworkTimeout(int timeout) {
        int result = CALL.FMOD_System_SetNetworkTimeout(ptr, timeout);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetNetworkTimeout", result);
    }

    // ---------------------------------------- //
    // Information
    // ---------------------------------------- //

    /// Retrieves the FMOD version and build number.
    public Tuplet<Integer> getVersion() {
        IntByReference verRef = new IntByReference(), buildRef = new IntByReference();
        int result = CALL.FMOD_System_GetVersion(ptr, verRef, buildRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetVersion", result);
        return new Tuplet<>(buildRef.getValue(), verRef.getValue());
    }

    /// Retrieves an output type specific internal native interface.
    private Pointer getOutputHandle() {
        PointerByReference handleRef = new PointerByReference();
        int result = CALL.FMOD_System_GetOutputHandle(ptr, handleRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetOutputHandle", result);
        return handleRef.getValue();
    }

    /// Retrieves the number of currently playing Channels.
    public Tuplet<Integer> getChannelsPlaying() {
        IntByReference channelsRef = new IntByReference();
        IntByReference realRef = new IntByReference();
        int result = CALL.FMOD_System_GetChannelsPlaying(ptr, channelsRef, realRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetChannelsPlaying", result);
        return new Tuplet<>(channelsRef.getValue(), realRef.getValue());
    }

    /// Retrieves the CPU usage of the Core API.
    // stub: public float getCPUUsage() { return 0; }

    /// Retrieves information about file reads.
    // stub: public String getFileUsage() { return null; }

    /// Retrieves the default mix matrix.
    // stub: public float[] getDefaultMixMatrix() { return null; }

    /// Retrieves the channel count for a given speaker mode.
    public int getSpeakerModeChannels(int mode) {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_System_GetSpeakerModeChannels(ptr, mode, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetSpeakerModeChannels", result);
        return ref.getValue();
    }

    // ---------------------------------------- //
    // Creation and retrieval
    // ---------------------------------------- //

    /// Loads a sound into memory.
    public FMODSound createSound(Pointer data, int mode, FMODCreateSoundExInfo exInfo) {
        PointerByReference soundRef = new PointerByReference();
        if (exInfo != null) exInfo.write();
        int result = CALL.FMOD_System_CreateSound(ptr, data, mode, exInfo != null ? exInfo.getPointer() : null, soundRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_CreateSound", result);
        return new FMODSound(soundRef.getValue());
    }

    public FMODSound createSound(String resource, int mode, FMODCreateSoundExInfo exInfo) {
        PointerByReference soundRef = new PointerByReference();
        if (exInfo != null) exInfo.write();
        int result = CALL.FMOD_System_CreateSound(ptr, resource, mode, exInfo != null ? exInfo.getPointer() : null, soundRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_CreateSound", result);
        return new FMODSound(soundRef.getValue());
    }

    public FMODSound createSound(String resource, int mode) {
        return createSound(resource, mode, null);
    }

    public FMODSound createSound(String resource) {
        return createSound(resource, 0);
    }

    /// Opens a sound for streaming.
    public FMODSound createStream(String resource) {
        return createSound(resource, FMOD_CREATESTREAM);
    }

    /// Helper: Internal method to create a DSP by type pointer.
    Pointer createDSPByTypePointer(int dspType) {
        PointerByReference dspRef = new PointerByReference();
        int result = CALL.FMOD_System_CreateDSPByType(ptr, dspType, dspRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_CreateDSPByType", result);
        return dspRef.getValue();
    }

    /// Create a DSP unit with a specified built-in type INDEX.
    public FMODDSP createDSPByType(int dspType) { // Favor fmod.dsp module
        return new FMODDSP(createDSPByTypePointer(dspType));
    }

    /// Create a ChannelGroup object.
    public FMODChannelGroup createChannelGroup(String name) {
        PointerByReference groupRef = new PointerByReference();
        int result = CALL.FMOD_System_CreateChannelGroup(ptr, name, groupRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_CreateChannelGroup", result);
        return new FMODChannelGroup(groupRef.getValue());
    }

    /// Creates a SoundGroup object.
    public FMODSoundGroup createSoundGroup(String name) {
        PointerByReference groupRef = new PointerByReference();
        int result = CALL.FMOD_System_CreateSoundGroup(ptr, name, groupRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_CreateSoundGroup", result);
        return new FMODSoundGroup(groupRef.getValue());
    }


    /// Creates a virtual reverb object.
    // stub: public Object createReverb3D() { return null; }

    /// Plays a Sound on a Channel.
    public FMODChannel playSound(FMODSound sound, @Nullable FMODChannelGroupRef group, boolean paused) {
        PointerByReference channelRef = new PointerByReference();
        int result = CALL.FMOD_System_PlaySound(ptr, sound.ptr, group != null ? group.ptr : null, paused ? 1 : 0, channelRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_PlaySound", result);
        return new FMODChannel(channelRef.getValue());
    }

    /// Plays a DSP on a Channel.
    public FMODChannel playDSP(FMODDSPRef dsp, @Nullable FMODChannelGroupRef group, boolean paused) {
        PointerByReference channelRef = new PointerByReference();
        int result = CALL.FMOD_System_PlayDSP(ptr, dsp.ptr, group != null ? group.ptr : null, paused ? 1 : 0, channelRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_PlayDSP", result);
        return new FMODChannel(channelRef.getValue());
    }

    /// Retrieves a handle to a Channel by ID.
    public FMODChannel getChannel(int channelID) {
        PointerByReference channelRef = new PointerByReference();
        int result = CALL.FMOD_System_GetChannel(ptr, channelID, channelRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_getChannel", result);
        return new FMODChannel(channelRef.getValue());
    }

    /// Retrieve the description structure for a built-in DSP plug-in.
    // stub: public Object getDSPInfoByType(int dspType) { return null; }

    /// Retrieves the master ChannelGroup.
    public FMODChannelGroupRef getMasterChannelGroup() {
        PointerByReference groupRef = new PointerByReference();
        int result = CALL.FMOD_System_GetMasterChannelGroup(ptr, groupRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetMasterChannelGroup", result);
        return new FMODChannelGroupRef(groupRef.getValue());
    }

    /// Retrieves the default SoundGroup.
    public FMODSoundGroupRef getMasterSoundGroup() {
        PointerByReference groupRef = new PointerByReference();
        int result = CALL.FMOD_System_GetMasterSoundGroup(ptr, groupRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetMasterSoundGroup", result);
        return new FMODSoundGroupRef(groupRef.getValue());
    }

    // ---------------------------------------- //
    // Runtime control
    // ---------------------------------------- //

    /// Sets the position, velocity and orientation of the 3D listener.
    // stub: public void set3DListenerAttributes(int listener, float[] pos, float[] vel, float[] forward, float[] up) { }

    /// Retrieves the 3D listener attributes.
    // stub: public Object get3DListenerAttributes(int listener) { return null; }

    /// Sets parameters for the global reverb environment.
    // stub: public void setReverbProperties(Object reverbProps) { }

    /// Retrieves the current reverb environment for the specified reverb instance.
    // stub: public Object getReverbProperties(int instance) { return null; }

    /// Connects a ChannelGroup to an audio port.
    // stub: public void attachChannelGroupToPort(FMODChannelGroup group, int portIndex) { }

    /// Disconnects a ChannelGroup from an audio port.
    // stub: public void detachChannelGroupFromPort(FMODChannelGroup group) { }

    // ---------------------------------------- //
    // Recording
    // ---------------------------------------- //

    /// Retrieves the number of recording devices.
    // stub: public int getRecordNumDrivers() { return 0; }

    /// Retrieves information about a recording device.
    // stub: public String getRecordDriverInfo(int driverIndex) { return null; }

    /// Retrieves the current recording position in PCM sampleCount.
    // stub: public int getRecordPosition() { return 0; }

    /// Starts the recording engine.
    // stub: public void recordStart() { }

    /// Stops the recording engine.
    // stub: public void recordStop() { }

    /// Retrieves the state of the recording API.
    // stub: public boolean isRecording() { return false; }

    // ---------------------------------------- //
    // Geometry management
    // ---------------------------------------- //

    /// Creates a geometry object.
    // stub: public Object createGeometry(int maxPolygons, int maxVertices) { return null; }

    /// Sets the maximum world size for the geometry engine.
    // stub: public void setGeometrySettings(float maxWorldSize) { }

    /// Retrieves the maximum world size for the geometry engine.
    // stub: public float getGeometrySettings() { return 0; }

    /// Creates a geometry object from pre-saved data.
    // stub: public Object loadGeometry(byte[] data) { return null; }

    /// Calculates geometry occlusion between a listener and a sound source.
    // stub: public float getGeometryOcclusion(Object geometry, float[] sourcePos, float[] listenerPos) { return 0; }

    // ---------------------------------------- //
    // General
    // ---------------------------------------- //

    /// Locks the DSP engine.
    public void lockDSP() {
        int result = CALL.FMOD_System_LockDSP(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_LockDSP", result);
    }

    /// Unlocks the DSP engine.
    public void unlockDSP() {
        int result = CALL.FMOD_System_UnlockDSP(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_UnlockDSP", result);
    }

    /// Sets the callback for System level notifications.
    void setCallback(FMODSystemCallbackFFI callback, int callbackMask) {
        int result = CALL.FMOD_System_SetCallback(ptr, callback, callbackMask);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetCallback", result);
    }

    /// Retrieves a user value associated with this object.
    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_System_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    /// Sets a user value associated with this object.
    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_System_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_SetUserData", result);
    }
}
