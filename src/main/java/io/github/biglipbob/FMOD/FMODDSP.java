package io.github.biglipbob.FMOD;

import java.lang.ref.Cleaner;
import com.sun.jna.Pointer;

import static io.github.biglipbob.FMOD.FMODConstants.FMOD_OK;
import static io.github.biglipbob.FMOD.FMOD.*;

/**
 * The {@code FMODDSP} class owns a FMOD DSP (Digital Signal Processor).
 * <p>
 * Unlike {@link FMODDSPRef}, this class owns the FFI DSP instance and will manage its memory release.
 * It provides access to an existing DSP's properties.
 * </p>
 *
 * <p>For more details, refer to the FMOD DSP API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-dsp.html">FMOD DSP API</a>
 * </p>
 */
public class FMODDSP extends FMODDSPRef implements AutoCloseable {
    final Cleaner.Cleanable cleanable;

    FMODDSP(Pointer ptr) {
        super(ptr);
        cleanable = CLEANER.register(this, () -> release(ptr));
    }

    public FMODDSP(FMODSystemRef system, int dspType) {
        this(system.createDSPByTypePointer(dspType));
    }

    private static void release(Pointer ptr) {
        int result = CALL.FMOD_DSP_Release(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_DSP_Release", result);
    }

    /// Releases the memory for the object.
    @Override public void close() {
        cleanable.clean();
    }
}
