package io.github.biglipbob.FMOD;

import java.lang.ref.Cleaner;
import com.sun.jna.Pointer;

import static io.github.biglipbob.FMOD.FMODConstants.FMOD_OK;
import static io.github.biglipbob.FMOD.FMOD.*;

/**
 * The {@code FMODSystem} class owns an FMOD system instance.
 * <p>
 * Unlike {@link FMODSystemRef}, this class owns the FFI system instance and will manage its memory release.
 * It allows for initialization, channel control, sound playback, and other core functionalities.
 * </p>
 *
 * <p>For more details, refer to the FMOD System API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-system.html">FMOD System API</a>
 * </p>
 */
public class FMODSystem extends FMODSystemRef implements AutoCloseable {
    final Cleaner.Cleanable cleanable;

    FMODSystem(Pointer ptr) {
        super(ptr);
        cleanable = CLEANER.register(this, () -> release(ptr));
    }

    /// Closes and frees this object and its resources.
    private static void release(Pointer ptr) { // close is called automatically on demand
        int result = CALL.FMOD_System_Close(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_System_Close", result);
        synchronized (FMOD.class) {
            result = CALL.FMOD_System_Release(ptr);
        }
        if (result != FMOD_OK) throw new FMODException("FMOD_System_Release", result);
    }

    @Override public void close() {
        cleanable.clean();
    }
}