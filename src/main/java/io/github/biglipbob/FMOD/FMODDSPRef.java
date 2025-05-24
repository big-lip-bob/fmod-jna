package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

import io.github.biglipbob.utils.*;

import static io.github.biglipbob.FMOD.FMODFFI.FMODDSPCallbackFFI;
import static io.github.biglipbob.FMOD.FMODConstants.*;
import static io.github.biglipbob.FMOD.FMOD.*;

import org.jetbrains.annotations.*;

/**
 * The {@code FMODDSPRef} class represents a Digital Signal Processor (DSP) in FMOD.
 * It allows for real-time audio effects processing.
 *
 * <p>For more details, refer to the FMOD DSP API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-dsp.html">FMOD DSP API</a>
 * </p>
 */
public class FMODDSPRef {
    final Pointer ptr;

    FMODDSPRef(Pointer ptr) {
        this.ptr = ptr;
    }

    // ---------------------------------------- //
    // Connections
    // ---------------------------------------- //

    /// Adds a DSP unit as an input to this object.
    public FMODDSPConnection addInput(FMODDSPRef dsp, int connectionType) {
        PointerByReference connRef = new PointerByReference();
        int result = CALL.FMOD_DSP_AddInput(ptr, dsp.ptr, connRef, connectionType);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_AddInput", result);
        return new FMODDSPConnection(ptr);
    }

    /// Retrieves the DSP unit at the specified INDEX in the input list.
    public Tuple<FMODDSPRef, FMODDSPConnection> getInput(int index) {
        PointerByReference dspRef = new PointerByReference(), connRef = new PointerByReference();
        int result = CALL.FMOD_DSP_GetInput(ptr, index, dspRef, connRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetInput", result);
        return new Tuple<>(new FMODDSPRef(dspRef.getValue()), new FMODDSPConnection(connRef.getValue()));
    }

    /// Retrieves the DSP unit at the specified INDEX in the output list.
    public Tuple<FMODDSPRef, FMODDSPConnection> getOutput(int index) {
        PointerByReference dspRef = new PointerByReference(), connRef = new PointerByReference();
        int result = CALL.FMOD_DSP_GetOutput(ptr, index, dspRef, connRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetOutput", result);
        return new Tuple<>(new FMODDSPRef(dspRef.getValue()), new FMODDSPConnection(connRef.getValue()));
    }

    /// Retrieves the number of DSP units in the input list.
    public int getNumInputs() {
        IntByReference numInputs = new IntByReference();
        int result = CALL.FMOD_DSP_GetNumInputs(ptr, numInputs);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetNumInputs", result);
        return numInputs.getValue();
    }

    /// Retrieves the number of DSP units in the output list.
    public int getNumOutputs() {
        IntByReference numOutputs = new IntByReference();
        int result = CALL.FMOD_DSP_GetNumOutputs(ptr, numOutputs);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetNumOutputs", result);
        return numOutputs.getValue();
    }

    /// Disconnects all inputs and/or outputs.
    public void disconnectAll(boolean inputs, boolean outputs) {
        int result = CALL.FMOD_DSP_DisconnectAll(ptr, inputs ? 1 : 0, outputs ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_DisconnectAll", result);
    }

    /// Disconnects the specified input DSP.
    public void disconnectFrom(@Nullable FMODDSPRef dsp, @Nullable FMODDSPConnection conn) {
        int result = CALL.FMOD_DSP_DisconnectFrom(ptr, dsp != null ? dsp.ptr : null, conn != null ? conn.ptr : null);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_DisconnectFrom", result);
    }

    // ---------------------------------------- //
    // Parameters
    // ---------------------------------------- //

    /// Retrieves the INDEX of the first data parameter of a particular data type.
    // stub: public int getDataParameterIndex(int dataType) { return 0; }

    /// Retrieves the number of parameters exposed by this unit.
    public int getNumParameters() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetNumParameters(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetNumParameters", result);
        return ref.getValue();
    }

    /// Retrieves information about a specified parameter.
    public FMODParameterInfo getParameterInfo(int index) {
        PointerByReference infoRef = new PointerByReference();
        int result = CALL.FMOD_DSP_GetParameterInfo(ptr, index, infoRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterInfo", result);
        return new FMODParameterInfo(infoRef.getValue());
    }

    /// Sets a floating point parameter by INDEX.
    public void setParameterFloat(int index, float value) {
        int result = CALL.FMOD_DSP_SetParameterFloat(ptr, index, value);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetParameterFloat", result);
    }

    /// Retrieves a floating point parameter by INDEX.
    public float getParameterValueFloat(int index) {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_DSP_GetParameterFloat(ptr, index, ref, null, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterFloat", result);
        return ref.getValue();
    }

    /// Sets an integer parameter by INDEX.
    public void setParameterInt(int index, int value) {
        int result = CALL.FMOD_DSP_SetParameterInt(ptr, index, value);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetParameterInt", result);
    }

    /// Retrieves an integer parameter by INDEX.
    public int getParameterValueInt(int index) {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetParameterInt(ptr, index, ref, null, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterInt", result);
        return ref.getValue();
    }

    /// Sets a boolean parameter by INDEX.
    public void setParameterBool(int index, boolean value) {
        int result = CALL.FMOD_DSP_SetParameterBool(ptr, index, value ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetParameterBool", result);
    }

    /// Retrieves a boolean parameter by INDEX.
    public boolean getParameterValueBool(int index) {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetParameterBool(ptr, index, ref, null, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterBool", result);
        return ref.getValue() != 0;
    }

    /// Sets a binary data parameter by INDEX.
    protected <T extends Structure> void setParameterData(int index, T value) {
        value.write();
        int result = CALL.FMOD_DSP_SetParameterData(ptr, index, value.getPointer(), value.size());
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetParameterData", result);
    }

    /// Sets a binary data parameter by INDEX.
    protected void setParameterData(int index, Memory value) {
        int result = CALL.FMOD_DSP_SetParameterData(ptr, index, value.getPointer(0), (int) value.size());
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetParameterData", result);
    }

    /// Retrieves the name of a floating point parameter.
    public String getParameterNameFloat(int index) {
        try (Memory buffer = new Memory(FMOD_DSP_GETPARAM_VALUESTR_LENGTH)) {
            int result = CALL.FMOD_DSP_GetParameterFloat(ptr, index, null, buffer, (int) buffer.size());
            if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterFloat", result);
            return buffer.getString(0);
        }
    }

    /// Retrieves the name of an integer parameter.
    public String getParameterNameInt(int index) {
        try (Memory buffer = new Memory(FMOD_DSP_GETPARAM_VALUESTR_LENGTH)) {
            int result = CALL.FMOD_DSP_GetParameterInt(ptr, index, null, buffer, (int) buffer.size());
            if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterInt", result);
            return buffer.getString(0);
        }
    }

    /// Retrieves the name of a boolean parameter.
    public String getParameterNameBool(int index) {
        try (Memory buffer = new Memory(FMOD_DSP_GETPARAM_VALUESTR_LENGTH)) {
            int result = CALL.FMOD_DSP_GetParameterBool(ptr, index, null, buffer, (int) buffer.size());
            if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterBool", result);
            return buffer.getString(0);
        }
    }

    /// Retrieves the name of a data parameter.
    public String getParameterNameData(int index) {
        try (Memory buffer = new Memory(FMOD_DSP_GETPARAM_VALUESTR_LENGTH)) {
            IntByReference ref = new IntByReference();
            int result = CALL.FMOD_DSP_GetParameterData(ptr, index, null, ref, buffer, (int) buffer.size());
            if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterData", result);
            return buffer.getString(0);
        }
    }

    /// Retrieves a binary data parameter value as a Structure.
    protected <T extends Structure> T getParameterValueData(int index, Class<T> type) {
        PointerByReference dataRef = new PointerByReference();
        IntByReference lengthRef = new IntByReference();
        int result = CALL.FMOD_DSP_GetParameterData(ptr, index, dataRef, lengthRef, null, 0);
        if (result != FMODConstants.FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterData", result);
        T inst = Structure.newInstance(type, dataRef.getValue());
        if (inst.size() != lengthRef.getValue())
            throw new RuntimeException("Inconsistent data type sizes: " + inst.size() + " != " + lengthRef.getValue());
        inst.read();
        return inst;
    }

    /// Retrieves a binary data parameter value with a specified size.
    protected Pointer getParameterValueDataSized(int index, int size) {
        PointerByReference dataRef = new PointerByReference();
        IntByReference lengthRef = new IntByReference();
        int result = CALL.FMOD_DSP_GetParameterData(ptr, index, dataRef, lengthRef, null, 0);
        if (result != FMODConstants.FMOD_OK) throw new FMODException("FMOD_DSP_GetParameterData", result);
        if (size != lengthRef.getValue())
            throw new IllegalStateException("Inconsistent data type sizes: " + size + " != " + lengthRef.getValue());
        return dataRef.getValue();
    }

    // ---------------------------------------- //
    // Channel format
    // ---------------------------------------- //

    /// Sets the PCM input format this DSP is to receive when processing.
    public void setChannelFormat(int rate, int channels, int format) {
        int result = CALL.FMOD_DSP_SetChannelFormat(ptr, rate, channels, format);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetChannelFormat", result);
    }

    /// Retrieves the PCM input format this DSP will receive when processing.
    public Triplet<Integer> getChannelFormat() {
        IntByReference formatRef = new IntByReference(), typeRef = new IntByReference(), speakerRef = new IntByReference();
        int result = CALL.FMOD_DSP_GetChannelFormat(ptr, formatRef, typeRef, speakerRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetChannelFormat", result);
        return new Triplet<>(formatRef.getValue(), typeRef.getValue(), speakerRef.getValue());
    }

    public void setChannelFormat(int rate) {
        setChannelFormat(rate, 0, 0);
    }

    public int getChannelFormatRate() {
        IntByReference formatRef = new IntByReference();
        int result = CALL.FMOD_DSP_GetChannelFormat(ptr, formatRef, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetChannelFormat", result);
        return formatRef.getValue();
    }

    /// Retrieves the output format this DSP produces when processing based on the input specified.
    public Triplet<Integer> getOutputChannelFormat(int inMask, int inChannels, int inSpeakerMode) {
        IntByReference outMask = new IntByReference(), outChannels = new IntByReference(), outSpeakerMode = new IntByReference();
        int result = CALL.FMOD_DSP_GetOutputChannelFormat(ptr, inMask, inChannels, inSpeakerMode, outMask, outChannels, outSpeakerMode);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetChannelFormat", result);
        return new Triplet<>(outMask.getValue(), outChannels.getValue(), outSpeakerMode.getValue());
    }

    // ---------------------------------------- //
    // Metering
    // ---------------------------------------- //

    /// Sets the input and output signal metering enabled states.
    public void setMeteringEnabled(boolean inputEnabled, boolean outputEnabled) {
        int result = CALL.FMOD_DSP_SetMeteringEnabled(ptr, inputEnabled ? 1 : 0, outputEnabled ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetMeteringEnabled", result);
    }

    /// Retrieves the input and output signal metering enabled states.
    public Tuplet<Boolean> getMeteringEnabled() {
        IntByReference inRef = new IntByReference();
        IntByReference outRef = new IntByReference();
        int result = CALL.FMOD_DSP_GetMeteringEnabled(ptr, inRef, outRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetMeteringEnabled", result);
        return new Tuplet<>(inRef.getValue() != 0, outRef.getValue() != 0);
    }

    /// Retrieves the signal metering enabled metering information.
    public Tuplet<FMODDSPMeteringInfo> getMeteringInfo() {
        FMODDSPMeteringInfo inputPtr = new FMODDSPMeteringInfo(), outputPtr = new FMODDSPMeteringInfo();
        int result = CALL.FMOD_DSP_GetMeteringInfo(ptr, inputPtr.getPointer(), outputPtr.getPointer());
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetMeteringInfo", result);
        inputPtr.read();
        outputPtr.read();
        return new Tuplet<>(inputPtr, outputPtr);
    }

    /// Retrieves the input metering information.
    public FMODDSPMeteringInfo getInputMeteringInfo() {
        FMODDSPMeteringInfo inputPtr = new FMODDSPMeteringInfo();
        int result = CALL.FMOD_DSP_GetMeteringInfo(ptr, inputPtr.getPointer(), null);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetMeteringInfo", result);
        inputPtr.read();
        return inputPtr;
    }

    /// Retrieves the output metering information.
    public FMODDSPMeteringInfo getOutputMeteringInfo() {
        FMODDSPMeteringInfo outputPtr = new FMODDSPMeteringInfo();
        int result = CALL.FMOD_DSP_GetMeteringInfo(ptr, null, outputPtr.getPointer());
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetMeteringInfo", result);
        outputPtr.read();
        return outputPtr;
    }

    // ---------------------------------------- //
    // Processing
    // ---------------------------------------- //

    /// Retrieves the processing active state.
    public boolean getActive() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetActive(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetActive", result);
        return ref.getValue() != 0;
    }

    /// Sets the processing active state.
    public void setActive(boolean active) {
        int result = CALL.FMOD_DSP_SetActive(ptr, active ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetActive", result);
    }

    /// Retrieves the processing bypass state.
    public boolean getBypass() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetBypass(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetBypass", result);
        return ref.getValue() != 0;
    }

    /// Sets the processing bypass state.
    public void setBypass(boolean bypass) {
        int result = CALL.FMOD_DSP_SetBypass(ptr, bypass ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetBypass", result);
    }

    /// Sets the scale of the wet and dry signal components.
    public void setWetDryMix(float pre, float post, float dry) {
        int result = CALL.FMOD_DSP_SetWetDryMix(ptr, pre, post, dry);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetWetDryMix", result);
    }

    /// Retrieves the scale of the wet and dry signal components.
    public Triplet<Float> getWetDryMix() {
        FloatByReference pre = new FloatByReference(), post = new FloatByReference(), dry = new FloatByReference();
        int result = CALL.FMOD_DSP_GetWetDryMix(ptr, pre, post, dry);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetWetDryMix", result);
        return new Triplet<>(pre.getValue(), post.getValue(), dry.getValue());
    }

    /// Retrieves the idle state.
    public boolean getIdle() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetIdle(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetIdle", result);
        return ref.getValue() != 0;
    }

    // ---------------------------------------- //
    // General
    // ---------------------------------------- //

    /// Displays or hides a DSP unit configuration dialog box inside the target window.
    // stub: public void showConfigDialog(long hwnd, boolean show) { }

    /// Resets the DSP's internal state, making it ready for a new input signal.
    public void reset() {
        int result = CALL.FMOD_DSP_Reset(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_Reset", result);
    }

    /// Retrieves the pre-defined type of FMOD registered DSP unit.
    public int getType() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSP_GetType(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetType", result);
        return ref.getValue();
    }

    /// Retrieves information about this DSP unit.
    public FMODDSPInfo getInfo() {
        try (Memory nameBuf = new Memory(32)) {
            IntByReference verRef = new IntByReference(), chanRef = new IntByReference(), widthRef = new IntByReference(), heightRef = new IntByReference();
            int result = CALL.FMOD_DSP_GetInfo(ptr, nameBuf, verRef, chanRef, widthRef, heightRef);
            if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetType", result);
            return new FMODDSPInfo(nameBuf.getString(0), verRef.getValue(), chanRef.getValue(), widthRef.getValue(), heightRef.getValue());
        }
    }

    /// Retrieves statistics on the mixer thread CPU usage for this unit.
    public Tuplet<Integer> getCPUUsage() {
        IntByReference exclRef = new IntByReference(), inclRef = new IntByReference();
        int result = CALL.FMOD_DSP_GetCPUUsage(ptr, exclRef, inclRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetCPUUsage", result);
        return new Tuplet<>(exclRef.getValue(), inclRef.getValue());
    }

    /// Retrieves a user value associated with this object.
    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_DSP_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    /// Sets a user value associated with this object.
    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_DSP_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetUserData", result);
    }

    /// Sets the callback for DSP notifications.
    void setCallback(FMODDSPCallbackFFI callback) {
        int result = CALL.FMOD_DSP_SetCallback(ptr, callback);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_SetCallback", result);
    }

    /// Retrieves the parent System object.
    public FMODSystemRef getSystem() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_DSP_GetSystemObject(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_GetSystemObject", result);
        return new FMODSystemRef(ref.getValue());
    }
}
