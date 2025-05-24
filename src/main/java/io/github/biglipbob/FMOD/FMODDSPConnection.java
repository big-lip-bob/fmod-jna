package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

import io.github.biglipbob.utils.Tuplet;

import static io.github.biglipbob.FMOD.FMODConstants.FMOD_OK;
import static io.github.biglipbob.FMOD.FMOD.*;

/**
 * The {@code FMODDSPConnection} class represents a connection between two DSP units.
 * It allows for chaining and routing of audio effects.
 *
 * <p>For more details, refer to the FMOD DSPConnection API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-dspconnection.html">FMOD DSPConnection API</a>
 * </p>
 */
public class FMODDSPConnection {
    Pointer ptr;

    FMODDSPConnection(Pointer ptr) {
        this.ptr = ptr;
    }

    /// Retrieves the connection's volume scale.
    public float getMix() {
        FloatByReference mixRef = new FloatByReference();
        int result = CALL.FMOD_DSPConnection_GetMix(ptr, mixRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetMix", result);
        return mixRef.getValue();
    }

    /// Sets the connection's volume scale.
    public void setMix(float volume) {
        int result = CALL.FMOD_DSPConnection_SetMix(ptr, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_SetMix", result);
    }

    /// Retrieves the size of the 2-dimensional pan matrix.
    public Tuplet<Integer> getMixMatrixSize() {
        IntByReference outRef = new IntByReference(), inRef = new IntByReference();
        int result = CALL.FMOD_DSPConnection_GetMixMatrix(ptr, null, outRef, inRef, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetMixMatrix", result);
        return new Tuplet<>(outRef.getValue(), inRef.getValue());
    }

    /// Retrieves a 2-dimensional pan matrix that maps the signal from input channels (columns) to output speakers (rows).
    public float[][] getMixMatrix() {
        IntByReference outRef = new IntByReference(), inRef = new IntByReference();
        int result = CALL.FMOD_DSPConnection_GetMixMatrix(ptr, null, outRef, inRef, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetMixMatrix", result);
        int out = outRef.getValue(), in = inRef.getValue();
        try (Memory buffer = new Memory(4L * out * in)) {
            result = CALL.FMOD_DSPConnection_GetMixMatrix(ptr, buffer, outRef, inRef, in);
            if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetMixMatrix", result);
            return createMixMatrixFrom(buffer, out, in);
        }
    }

    /// Sets a 2-dimensional pan matrix that maps the signal from input channels (columns) to output speakers (rows).
    public void setMixMatrix(float[][] matrix) {
        try (Memory buffer = createMixMatrixMemory(matrix)) {
            int result = CALL.FMOD_DSPConnection_SetMixMatrix(ptr, buffer, matrix.length, matrix[0].length, matrix[0].length);
            if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_SetMixMatrix", result);
        }
    }

    /// Retrieves the connection's input DSP unit.
    public FMODDSP getInput() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_DSPConnection_GetInput(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetInput", result);
        return new FMODDSP(ref.getValue());
    }

    /// Retrieves the connection's output DSP unit.
    public FMODDSP getOutput() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_DSPConnection_GetOutput(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetOutput", result);
        return new FMODDSP(ref.getValue());
    }

    /// Retrieves the type of the connection between two DSP units.
    public int getType() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_DSPConnection_GetType(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetType", result);
        return ref.getValue();
    }

    /// Retrieves a user value associated with this object.
    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_DSPConnection_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    /// Sets a user value associated with this object.
    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_DSPConnection_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSPConnection_SetUserData", result);
    }
}
